package com.xiaohan.cn.service;

import com.xiaohan.cn.vo.SysConfig;

import java.util.List;

/**
 * 获取菜单配置
 *
 * @author teddy
 * @since 2022/12/29
 */
public interface SysConfigService {

    /**
     * 根据模块获取配置
     * @param name 模块code
     * @return 配置
     */
    SysConfig getConfig(String name);

    void batchInsert(List<SysConfig> data);
}
