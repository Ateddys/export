package com.xiaohan.cn.handle;

import com.xiaohan.cn.importer.ImportResult;
import com.xiaohan.cn.vo.UserInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author teddy
 * @since 2022/12/30
 */
@Component
public class ConfigImportExcelHandler extends AbstractCommonsImportExcelHandler<UserInfo> {

    @Override
    String getConfigName() {
        return null;
    }

    @Override
    public void batchInsert(List<UserInfo> data, ImportResult result) {
        logger.info("添加");
    }


}
