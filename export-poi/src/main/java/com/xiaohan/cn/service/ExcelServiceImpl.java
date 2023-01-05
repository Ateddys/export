package com.xiaohan.cn.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xiaohan.cn.base.model.TSysConfig;
import com.xiaohan.cn.constant.BaseSymbol;
import com.xiaohan.cn.constant.ExportContant;
import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.exporter.ExportEntity;
import com.xiaohan.cn.handle.AbstractCommonsImportExcelHandler;
import com.xiaohan.cn.result.MasterDataApiResultCode;
import com.xiaohan.cn.util.*;
import com.xiaohan.cn.vo.ImportProgressVo;
import com.xiaohan.cn.vo.PropertyInfo;
import com.xiaohan.cn.vo.UserInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private RedisUtil redisUtils;

    @Autowired
    private com.xiaohan.cn.base.service.TSysConfigService TSysConfigService;

    @Autowired
    private final Map<String, AbstractCommonsImportExcelHandler<T>> handlerMap = Maps.newConcurrentMap();

    /**
     * 这个构造方法的作用，其实不用也可以，为了防止重复注入，因此先清空，再注入
     *
     * @param handlerMap beanMap
     */
    public ExcelServiceImpl(Map<String, AbstractCommonsImportExcelHandler<T>> handlerMap) {
        this.handlerMap.clear();
        handlerMap.forEach(this.handlerMap::put);
    }

    /**
     * 获取当前配置
     *
     * @param name 所属数据name
     * @return sys
     */
    private TSysConfig getConfig(String name) {
        return TSysConfigService.getConfig(name);
    }

    @Override
    public void importExcelData(String name, MultipartFile file) {
        UserInfo userInfo = UserSessionUtil.getLoggedInUser();
        logger.info("即将处理的数据类型：{}, 用户：{}", name, userInfo.getFullName());
        // 查看当前用户有没有正在进行该类型的导入
        ImportProgressVo result = this.excelResult(name);
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
                ImportProgressVo importProgressVo = (ImportProgressVo) redisUtils.hget(ExportContant.getImportRedisKey(name)
                        , userInfo.getId());
                handlerMap.get(name).updateImportProgressVo(importProgressVo, ExportContant.ImportStatusEnum.FAIL.getKey()
                        , ExportContant.ImportProgressEnum.END_SAVE, e.getMessage());
                throw new BaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void exportExcelData(HttpServletResponse response, String name, List<T> datas) {
        // 配置文件导出字段 属性映射
        TSysConfig config = this.getConfig(name);

        // 获取表头
        String[] headerRowNames = TSysConfigService.getHeaderRowNames(config);

        // 导出列配置
        List<String> exportList = config.getImportList();

        // 属性映射配置
        Map<String, PropertyInfo> propertyMap = config.getPropertyMap();

        // 预备导出的数据all
        List<Object[]> list = new ArrayList<>();

        // 行数据集row
        List<String> row = new ArrayList<>();

        JSONObject map;
        for (T bean : datas) {
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
                    } else if (propertyInfo.getElementType().contains(BaseSymbol.DATE)) {
                        property = DateUtils.parseDate(BeanUtils.getProperty(bean, export), propertyInfo.getPattern());
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

    @Override
    public ImportProgressVo excelResult(String name) {
        Object obj = redisUtils.hget(ExportContant.getImportRedisKey(name), UserSessionUtil.getLoggedInUser().getId());
        return obj == null ? null : (ImportProgressVo) obj;
    }
}
