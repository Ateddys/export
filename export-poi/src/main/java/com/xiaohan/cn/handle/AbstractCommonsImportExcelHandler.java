package com.xiaohan.cn.handle;

import com.alibaba.fastjson.JSON;
import com.xiaohan.cn.cache.RedisUtil;
import com.xiaohan.cn.constant.BaseSymbol;
import com.xiaohan.cn.constant.ExportContant;
import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.importer.ImportResult;
import com.xiaohan.cn.result.vo.UserInfo;
import com.xiaohan.cn.importer.AbstractImportExcelRowHandler;
import com.xiaohan.cn.service.SysConfigService;
import com.xiaohan.cn.result.vo.ImportProgressVo;
import com.xiaohan.cn.result.vo.PropertyInfo;
import com.xiaohan.cn.result.vo.SysConfig;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.xiaohan.cn.util.DateUtils;
import com.xiaohan.cn.util.ExcelUtils;
import com.xiaohan.cn.util.ListUniqUtils;
import com.xiaohan.cn.util.ResultUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.xiaohan.cn.constant.ExportContant.MAX_PARAMS;

/**
 * tpm导入导出抽象类
 *
 * @author teddy
 * @since 2022/12/30
 */
@Configuration
public abstract class AbstractCommonsImportExcelHandler<T> extends AbstractImportExcelRowHandler {

    Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     * 入库的数据
     */
    private final ThreadLocal<List<T>> masterData = new ThreadLocal<>();

    /**
     * 扩展字段容器
     */
    private final ThreadLocal<Map<String, Object>> extendMap = new ThreadLocal<>();

    /**
     * 预备bean转换容器
     */
    private final ThreadLocal<Map<String, Object>> beanMap = new ThreadLocal<>();

    /**
     * 模版定义导出 "列" 配置
     */
    private final ThreadLocal<List<String>> exportList = new ThreadLocal<>();

    /**
     * 当前操作用户信息
     */
    private final ThreadLocal<UserInfo> userInfo = new ThreadLocal<>();
    /**
     * 当前导入进程
     */
    private final ThreadLocal<ImportProgressVo> importProgressVo = new ThreadLocal<>();

    /**
     * 属性定义
     */
    private final ThreadLocal<Map<String, PropertyInfo>> propertyMap = new ThreadLocal<>();

    /**
     * 配置service
     */
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 校验器
     */
    @Autowired
    private Validator validator;

    @Autowired
    private RedisUtil redisUtils;

    /**
     * 获取动态配置属性name方法
     *
     * @return string
     */
    abstract String getConfigName();

    /**
     * 获取数据集
     * @param data 数据集
     * @param result 导入信息
     */
    abstract void batchInsert(List<T> data, ImportResult result);

    /**
     * 插入前操作  初始化数据
     *
     * @param sheet 表格
     */
    @Override
    protected void beforeHandleSheet(Sheet sheet) {
        ListUniqUtils.start();
        super.beforeHandleSheet(sheet);
        masterData.set(new ArrayList<>());
        // TODO 获取导出的字段  和 字段详情
        SysConfig sysConfig = sysConfigService.getConfig(getConfigName());
        this.exportList.set(sysConfig.getExportList());
        this.propertyMap.set(sysConfig.getPropertyMap());
        this.extendMap.set(new HashMap<>());
        this.beanMap.set(new HashMap<>());
    }

    /**
     * 异步处理数据
     *
     * @param sheet 表格
     */
    @Async("taskExecutor")
    public synchronized void handleSheet(UserInfo userInfo, Sheet sheet) {
        this.importProgressVo.set(new ImportProgressVo(getConfigName(), userInfo.getFullName()));
        try {
            redisUtils.hset(ExportContant.getImportRedisKey(getConfigName()), userInfo.getId(), importProgressVo.get());
            this.userInfo.set(userInfo);
            ImportResult importResult = super.handleSheet(sheet);
            if (importResult.getFailRowCount() > 0) {
                // 校验失败
                String result = ResultUtil.getImportResult(importResult);
                this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.FAIL.getKey()
                        , ExportContant.ImportProgressEnum.END_VALIDATE, result);
            }else if(importResult.getSuccessRowCount() == 0){
                this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.SUCCESS.getKey()
                        , ExportContant.ImportProgressEnum.END_VALIDATE, importResult.getFailMsg());
            }
        }catch (Exception e) {
            this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.FAIL.getKey()
                    , ExportContant.ImportProgressEnum.END_VALIDATE, e.getMessage());
            throw new BaseException(e.getMessage(), e);
        }finally {
            logger.info("清空数据缓存");
            this.masterData.remove();
            this.beanMap.remove();
            this.exportList.remove();
            this.userInfo.remove();
            this.importProgressVo.remove();
            this.propertyMap.remove();
        }
    }

    /**
     * 更新进度
     * @param importProgressVo 进度
     */
    public void updateImportProgressVo(ImportProgressVo importProgressVo, Integer status,ExportContant.ImportProgressEnum importProgressEnum, String content) {
        importProgressVo.setLastModified(new Date());
        importProgressVo.setStatus(status);
        importProgressVo.getContent().put(importProgressEnum.getKey(), DateUtils.getDateTime() + " " + importProgressEnum.getName() + content);
        redisUtils.hset(ExportContant.getImportRedisKey(importProgressVo.getName()), userInfo.get().getId(), importProgressVo);
    }

    /**
     * 读取每一行(row)
     *
     * @param row Excel行
     * @return 错误消息
     */
    @Override
    protected String handleRow(Row row) {
        T entity = null;
        try {
            entity = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BaseException(e.getMessage(), e);
        }

        // 解析cell
        for (int i = 0; i < exportList.get().size(); i++) {
            String key = exportList.get().get(i);
            if (propertyMap.get().get(key).getElementType().contains("date")) {
                beanMap.get().put(key, ExcelUtils.readCellDate(row.getCell(i)));
                continue;
            }
            archive(key, ExcelUtils.readCellString(row.getCell(i)));
        }

        // 扩展字段是否存在
        if (extendMap.get().size() > 0) {
            beanMap.get().put("extend", JSON.toJSONString(extendMap));
            extendMap.remove();
        }

        // toBean
        try {
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            entity = JSON.parseObject(JSON.toJSONString(beanMap.get()), clazz);
        } catch (Exception e) {
            throw new BaseException(e.getMessage(), e);
        } finally {
            beanMap.get().clear();
        }

        // 校验
        Set<ConstraintViolation<T>> validate = validator.validate(entity, Default.class);
        if (!validate.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            validate.forEach(o -> {
                sb.append(o.getMessage());
                sb.append(";");
            });
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        masterData.get().add(entity);
        // 更新导入进度
        this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.IN_PROGRESS.getKey()
                , ExportContant.ImportProgressEnum.VALIDATING, row.getRowNum() + " / " + row.getSheet().getLastRowNum());
        return null;
    }

    /**
     * 组装map
     *
     * @param key       属性key
     * @param value     属性值（默认全部string）
     * param extendMap 扩展字段map
     * param beanMap   bean字段map
     */
    private void archive(String key, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        // 扩展字段
        if (BaseSymbol.Y.equals(propertyMap.get().get(key).getExtend())) {
            extendMap.get().put(key.substring(key.indexOf('.')+1), value);
        }
        // 导入字段
        if (BaseSymbol.Y.equals(propertyMap.get().get(key).getRef())) {
            beanMap.get().put(key, Arrays.asList(value.split(BaseSymbol.COMMA)));
        } else {
            beanMap.get().put(key, value);
        }
    }

    /**
     * excel读取完毕，开始入库
     *
     * @param sheet  表格sheet
     * @param result 读取参数实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterHandleSheet(Sheet sheet, ImportResult result) {
        super.afterHandleSheet(sheet, result);
        if (CollectionUtils.isNotEmpty(masterData.get()) && result.getFailRowCount() == BaseSymbol.ZERO) {
            // insert语句的字段个数
            Integer columnNum = this.getFailCellNum();
            // 数据条数
            int size = masterData.get().size();
            // 一次性最大插入的数据条数
            int maxCount = MAX_PARAMS/columnNum;
            // 拆分成n次插入
            int times = size/maxCount + 1;
            // 更新导入进度
            this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.IN_PROGRESS.getKey()
                    , ExportContant.ImportProgressEnum.START_SAVE, "");
            AtomicInteger currentNum = new AtomicInteger();
            try {
                // 分批导入数据
                Stream.iterate(0, n -> n + 1).limit(times).forEach(i -> {
                    List<T> collect = masterData.get().stream().skip((long) i * maxCount).limit(maxCount).collect(Collectors.toList());
                    batchInsert(collect, result);
                    logger.info("成功导入第{}批次数据，条数：{}", i + 1 , collect.size());
                    // 更新导入进度
                    currentNum.set(Math.min((i + 1) * maxCount, size));
                    this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.IN_PROGRESS.getKey()
                            , ExportContant.ImportProgressEnum.SAVING, currentNum  + " / " + size);
                });
            } catch (Exception e) {
                // 入库结果
                this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.FAIL.getKey()
                        , ExportContant.ImportProgressEnum.END_SAVE, "已入库：" + currentNum  + " / " + size + " 失败原因：" + e.getMessage());
                throw new BaseException(e.getMessage(), e);
            }
            // 入库结果
            if (currentNum.intValue() < size) {
                this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.FAIL.getKey()
                        , ExportContant.ImportProgressEnum.END_SAVE, "失败");
            }else {
                this.updateImportProgressVo(importProgressVo.get(), ExportContant.ImportStatusEnum.SUCCESS.getKey()
                        , ExportContant.ImportProgressEnum.END_SAVE, "成功");
            }
        }
        ListUniqUtils.end();
    }

    /**
     * insert语句的字段个数
     */
    @Override
    public Integer getFailCellNum() {
        return 50;
    }

    /**
     * 一次性上传最大条数
     */
    @Override
    public int getMaxHandleRowNum() {
        return 20000;
    }

}
