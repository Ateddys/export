package com.xiaohan.cn.util;

import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.importer.ImportResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果处理工具类
 *
 * @author teddy
 * @since 2022/12/30
 */
public class ResultUtil {

    private ResultUtil() {
    }

    /**
     * 获取导入结果字符串
     *
     * @param importResult importResult
     * @return 导入结果字符串
     */
    public static String getImportResult(ImportResult importResult) {
        List<String> failRowMsg = new ArrayList<>();
        for (int i = 0; i < importResult.getFailRowCount(); i++) {
            failRowMsg.add(String.format("第%s行导入错误,%s", importResult.getFailRowNums().get(i) + 1, importResult.getFailRowMsgs().get(i)));
        }
        String resultFailMsg = StringUtils.isBlank(importResult.getFailMsg()) ? "" : importResult.getFailMsg();
        return String.format("成功: %s\n%s失败: %s, 失败原因: %s", importResult.getSuccessRowCount(),
                StringUtils.isNotEmpty(importResult.getExtraMsg()) ? "提示: " + importResult.getExtraMsg() + '\n' : "",
                importResult.getFailRowCount(), resultFailMsg + '\n' + StringUtils.join(failRowMsg, ";\n"));
    }

    /**
     * 根据workbook生成Excel文件
     *
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @param workbook workbook
     */
    public static void generateExcel(HttpServletResponse response, String fileName, Workbook workbook) {
        try {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            throw new BaseException("EXPORT_EXCEL_FAIL");

        }
    }
}
