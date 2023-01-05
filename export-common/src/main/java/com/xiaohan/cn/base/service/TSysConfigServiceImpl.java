package com.xiaohan.cn.base.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohan.cn.constant.BaseSymbol;
import com.xiaohan.cn.i18n.I18nUtils;
import com.xiaohan.cn.base.mapper.TSysConfigMapper;
import com.xiaohan.cn.base.model.TSysConfig;
import com.xiaohan.cn.result.ApiResultCode;
import com.xiaohan.cn.util.MessageUtils;
import com.xiaohan.cn.vo.PropertyInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author teddy
 * @since 2022/12/29
 */
@Service
public class TSysConfigServiceImpl extends ServiceImpl<TSysConfigMapper, TSysConfig> implements TSysConfigService {

    @Autowired
    private I18nUtils i18nUtils;

    @Autowired
    private TSysConfigMapper tSysConfigMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private QueryWrapper<TSysConfig> getBuildQueryWrapper(TSysConfig param) {
        QueryWrapper<TSysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(param.getName()), TSysConfig::getName, param.getName())
        ;
        return queryWrapper;
    }

    @Override
    public TSysConfig getConfig(String name) {
        return parseAndSet(tSysConfigMapper.selectOne(getBuildQueryWrapper(TSysConfig.builder().name(name).build())));
    }

    @Override
    public String[] getHeaderRowNames(TSysConfig config) {
        if (config == null) {
            throw MessageUtils.buildException(ApiResultCode.EXCEL_FAIL_MESSAGE, "请先去完成配置信息");
        }
        return setHeaderRowNames(config.getImportList(), config);
    }

    /**
     * 组装HeaderRowNames
     *
     * @param list   集合（导出/导入)
     * @param config 当前数据配置
     * @return rows
     */
    private String[] setHeaderRowNames(List<String> list, TSysConfig config) {
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

    /**
     * 解析属性映射字段
     *
     * @param config config
     */
    private TSysConfig parseAndSet(TSysConfig config) {
        if (config != null) {
            // 解析Excel的导入配置
            config.setImportList(JSON.parseArray(config.getImportMapping(), String.class));
            // 解析Excel的导出配置
            config.setExportList(JSON.parseArray(config.getExportMapping(), String.class));
            // 处理map
            Map<String, PropertyInfo> propertyMap = new HashMap<>();
            JSON.parseObject(Optional.ofNullable(config.getPropertyMapping()).orElse("[]"), LinkedList.class, Feature.OrderedField).forEach(o -> {
                PropertyInfo pro = JSON.parseObject(o.toString(), PropertyInfo.class, Feature.OrderedField);
                propertyMap.put(pro.getKey(), pro);
            });
            config.setPropertyMap(propertyMap);
        }
        return config;
    }

}
