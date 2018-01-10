package me.caosh.autoasm.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class DateConvertUtils {
    public static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter yyyy_MM_dd = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter HH_mm_ss = DateTimeFormat.forPattern("HH:mm:ss");

    private DateConvertUtils() {
    }

    private static final DateConvertUtils _CODE_COVERAGE = new DateConvertUtils();
}
