package com.xiaohan.cn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohan.cn.model.TSysConfig;

/**
 * 获取菜单配置
 *
 * @author teddy
 * @since 2022/12/29
 */
public interface TSysConfigService extends IService<TSysConfig> {

    /**
     * 根据模块获取配置
     * @param name 模块code
     * @return 配置
     */
    TSysConfig getConfig(String name);

    /**
     * 获取对应配置页面的row元素名
     * @param name 配置name
     * @return row元素集
     */
    default String[] getHeaderRowNames(String name){
        return getHeaderRowNames(getConfig(name));
    }

    /**
     * 已知config进行校验装配
     * 获取对应配置页面的row元素名
     * @param config 配置
     * @return row元素集
     */
    String[] getHeaderRowNames(TSysConfig config);

}
