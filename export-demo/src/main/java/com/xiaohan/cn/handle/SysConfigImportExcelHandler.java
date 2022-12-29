package com.xiaohan.cn.handle;

import com.xiaohan.cn.constant.ExportContant;
import com.xiaohan.cn.poi.importer.ImportResult;
import com.xiaohan.cn.service.SysConfigService;
import com.xiaohan.cn.vo.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配置类
 *
 * @Author: teddy
 * @Date： 2021/7/23 11:19
 */
@Component(value = ExportContant.USER)
public class SysConfigImportExcelHandler extends AbstractCommonsImportExcelHandler<SysConfig> {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    protected String getConfigName() {
        return ExportContant.USER;
    }

    @Override
    protected void batchInsert(List<SysConfig> data, ImportResult result) {
        sysConfigService.batchInsert(data);
    }

    @Override
    public Integer getFailCellNum() {
        return 25;
    }
}
