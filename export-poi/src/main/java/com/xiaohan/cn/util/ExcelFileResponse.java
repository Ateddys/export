package com.xiaohan.cn.util;

import com.xiaohan.cn.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * excel 兼容mac浏览器
 *
 * @author teddy
 * @since 2022/12/30
 */
@Slf4j
public class ExcelFileResponse {

    private ExcelFileResponse() {
        // chou
    }

    private static final String RULE = "[\u4e00-\u9fa5]";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    public static void generateExcel(HttpServletResponse response, String fileName, Workbook workbook) {
        log.error("fileName:{}", fileName);
        try {
            response.setHeader("Access-Control-Expose-Headers", CONTENT_DISPOSITION);

            // 下载文件的默认名称带有中文 兼容safari浏览器
            if (isContainChinese(fileName)) {
                String headerValue = "attachment;";
                headerValue += " filename=\"" + encodeUriComponent(fileName) +"\";";
                headerValue += " filename*=utf-8''" + encodeUriComponent(fileName);
                response.setHeader(CONTENT_DISPOSITION, headerValue);
                response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            } else {
                response.setHeader(CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
                response.setHeader("content-Type", "application/vnd.ms-excel");
            }
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            throw new BaseException("EXPORT_EXCEL_FAIL");

        }
    }

    /**
     * <pre>
     * 符合 RFC 3986 标准的“百分号URL编码”
     * 在这个方法里，空格会被编码成%20，而不是+
     * 和浏览器的encodeURIComponent行为一致
     * </pre>
     * @param value 值
     * @return 加密后数据
     */
    private static String encodeUriComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replace("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    private static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile(RULE);
        Matcher m = p.matcher(str);
        return m.find();
    }

}
