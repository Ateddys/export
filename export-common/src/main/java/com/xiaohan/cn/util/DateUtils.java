package com.xiaohan.cn.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author teddy
 * @since 2022/12/29
 */
public class DateUtils {

    public static String getDateTime() {
        return LocalDateTime.now().toString();
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
