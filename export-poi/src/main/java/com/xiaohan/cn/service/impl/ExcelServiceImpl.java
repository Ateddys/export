package com.xiaohan.cn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xiaohan.cn.cache.RedisUtil;
import com.xiaohan.cn.cache.UserSessionUtil;
import com.xiaohan.cn.constant.BaseSymbol;
import com.xiaohan.cn.constant.ExportContant;
import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.handle.AbstractCommonsImportExcelHandler;
import com.xiaohan.cn.i18n.I18nUtils;
import com.xiaohan.cn.result.MasterDataApiResultCode;
import com.xiaohan.cn.result.vo.UserInfo;
import com.xiaohan.cn.exporter.ExportEntity;
import com.xiaohan.cn.result.vo.ImportProgressVo;
import com.xiaohan.cn.result.vo.PropertyInfo;
import com.xiaohan.cn.result.vo.SysConfig;
import com.xiaohan.cn.service.ExcelService;
import com.xiaohan.cn.service.SysConfigService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.xiaohan.cn.util.DateUtils;
import com.xiaohan.cn.util.ExcelFileResponse;
import com.xiaohan.cn.util.ExcelUtils;
import com.xiaohan.cn.util.MessageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 导入导出实现
 *
 * @author teddy
 * @since 2022/12/30
 */
@Service
public class ExcelServiceImpl<T> implements ExcelService<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private I18nUtils i18nUtils;

    @Autowired
    private RedisUtil redisUtils;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private final Map<String, AbstractCommonsImportExcelHandler> handlerMap = Maps.newConcurrentMap();

    /**
     * 这个构造方法的作用，其实不用也可以，为了防止重复注入，因此先清空，再注入
     * @param handlerMap beanMap
     */
    public ExcelServiceImpl(Map<String, AbstractCommonsImportExcelHandler> handlerMap) {
        this.handlerMap.clear();
        handlerMap.forEach(this.handlerMap::put);
    }

    /**
     * 获取当前配置
     * @param name 所属数据name
     * @return sys
     */
    private SysConfig getConfig(String name) {
        return sysConfigService.getConfig(name);
    }

    @Override
    public ImportProgressVo result(String name) {
        Object obj = redisUtils.hget(ExportContant.getImportRedisKey(name), UserSessionUtil.getLoggedInUser().getId());
        return obj == null ? null : (ImportProgressVo)obj;
    }

    /**
     * 组装HeaderRowNames
     * @param list 集合（导出/导入)
     * @param config 当前数据配置
     * @return rows
     */
    private String[] setHeaderRowNames(List<String> list, SysConfig config) {
        // 得到属性配置
        Map<String, PropertyInfo> propertyMap = config.getPropertyMap();
        // 列名容器
        List<String> headerRowNames = new ArrayList<>(list.size());
        for (String key : list) {
            if (propertyMap.containsKey(key)) {
                String labelKey = propertyMap.get(key).getLabelKey();
                String message = i18nUtils.getMessageWithRequest(labelKey, httpServletRequest);
                if (message.equals(labelKey) && BaseSymbol.Y.equals(propertyMap.get(key).getExtend())) {
                    // 扩展字段情况
                    key = key.substring(key.lastIndexOf(BaseSymbol.DOT) + 1);
                } else if (message.equals(labelKey) && BaseSymbol.Y.equals(propertyMap.get(key).getRef())) {
                    // 导出字段情况
                    key = key.substring(key.lastIndexOf(BaseSymbol.AT) + 1);
                }
                headerRowNames.add(message.equals(labelKey) ? key : message);
            }
        }
        return headerRowNames.toArray(new String[list.size()]);
    }

    @Override
    public String[] getHeaderRowNames(String name) {
        SysConfig config = this.getConfig(name);
        return setHeaderRowNames(config.getExportList(), config);
    }

    @Override
    public void importExcel(String name, MultipartFile file) {
        UserInfo userInfo = UserSessionUtil.getLoggedInUser();
        logger.info("即将处理的数据类型：{}, 用户：{}", name, userInfo.getFullName());
        // 查看当前用户有没有正在进行该类型的导入
        ImportProgressVo result = this.result(name);
        if (result != null && ExportContant.ImportStatusEnum.IN_PROGRESS.getKey().equals(result.getStatus())) {
            throw MessageUtils.buildException(MasterDataApiResultCode.IMPORT_IN_PROGRESS, userInfo.getFullName(), name);
        }
        if (handlerMap.containsKey(name)) {
            Sheet sheet = null;
            try {
                sheet = ExcelUtils.handleExcle(file);
            } catch (OfficeXmlFileException e) {
                throw new BaseException("文件格式不对");
            }
            // 超过最大条数
            if (handlerMap.get(name).getMaxHandleRowNum() < sheet.getLastRowNum()) {
                throw MessageUtils.buildException(MasterDataApiResultCode.IMPORT_MAX, handlerMap.get(name).getMaxHandleRowNum());
            }
            try {
                // 异步处理数据
                logger.info("异步处理数据：{}", name);
                handlerMap.get(name).handleSheet(userInfo, sheet);
            } catch (Exception e) {
                // 入库结果
                ImportProgressVo importProgressVo = (ImportProgressVo)redisUtils.hget(ExportContant.getImportRedisKey(name)
                        , userInfo.getId());
                handlerMap.get(name).updateImportProgressVo(importProgressVo, ExportContant.ImportStatusEnum.FAIL.getKey()
                        , ExportContant.ImportProgressEnum.END_SAVE, e.getMessage());
                throw new BaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void exportData(HttpServletResponse response, String name, T datas) {
        // 配置文件导出字段 属性映射
        SysConfig config = this.getConfig(name);

        // 获取表头
        String[] headerRowNames = this.setHeaderRowNames(config.getImportList(), config);

        // 导出列配置
        List<String> exportList = config.getImportList();

        // 属性映射配置
        Map<String, PropertyInfo> propertyMap = config.getPropertyMap();

        // 预备导出的数据all
        List<Object[]> list = new ArrayList<>();

        // 行数据集row
        List<Object> row = new ArrayList<>();

        JSONArray objects = JSON.parseArray(JSON.toJSONString(datas));
        JSONObject map;
        for (Object bean : objects) {
            for (String export : exportList) {
                try {
                    PropertyInfo propertyInfo = propertyMap.get(export);
                    if (propertyInfo == null) {
                        logger.error("获取参数({})配置为空", export);
                        continue;
                    }
                    String property;
                    if (BaseSymbol.Y.equals(propertyInfo.getRef())) {
                        // 导出字段处理
                        property = StringUtils.join(BeanUtils.getArrayProperty(bean, export), BaseSymbol.COMMA);
                    } else if (BaseSymbol.Y.equals(propertyInfo.getExtend())) {
                        // 扩展字段处理
                        String[] split = export.split("\\.");
                        map = JSON.parseObject(BeanUtils.getProperty(bean, split[0]));
                        property = (String) map.get(split[1]);
                    } else if (propertyInfo.getElementType().contains("date")) {
                        property = this.parseDate(BeanUtils.getProperty(bean, export), propertyInfo.getPattern());
                    } else {
                        property = BeanUtils.getProperty(bean, export);
                    }
                    row.add(property);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new BaseException(e.getMessage(), e);
                }
            }
            list.add(row.toArray());
            row.clear();
        }
        ExportEntity exportEntity = new ExportEntity(name, headerRowNames, list);
        exportEntity.setExportName(name + BaseSymbol.EXCEL_XLSX);
        logger.error("预导出数据的文件名称：{}", exportEntity.getExportName());
        ExcelFileResponse.generateExcel(response, exportEntity.getExportName(), exportEntity.export());
        list.clear();
    }

    private String parseDate(String value, String pattern) {
        if (value == null) {
            return BaseSymbol.EMPTY;
        }
        try {
            // 默认值
            if (StringUtils.isEmpty(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            return DateUtils.formatDate(new Date(Long.parseLong(value)), pattern);
        } catch (Exception e) {
            return value;
        }
    }

}
