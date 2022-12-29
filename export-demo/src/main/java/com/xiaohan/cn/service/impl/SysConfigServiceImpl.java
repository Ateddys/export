package com.xiaohan.cn.service.impl;

import com.xiaohan.cn.service.SysConfigService;
import com.xiaohan.cn.vo.SysConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author teddy
 * @since 2022/12/29
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Override
    public SysConfig getConfig(String name) {
        return new SysConfig();
    }

    @Override
    public void batchInsert(List<SysConfig> data) {

    }
}
