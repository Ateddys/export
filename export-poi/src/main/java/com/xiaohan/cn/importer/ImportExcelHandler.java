package com.xiaohan.cn.importer;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel导入处理接口
 *
 * @author teddy
 * @since 2022/12/30
 */
public interface ImportExcelHandler {

    /**
     * 处理单个Sheet,处理成功返回null或空字符串,失败时返回错误信息
     *
     * @param sheet sheet
     * @return ImportResult
     */
    ImportResult handleSheet(Sheet sheet);
}
