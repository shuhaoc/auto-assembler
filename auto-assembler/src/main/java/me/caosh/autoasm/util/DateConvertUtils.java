package me.caosh.autoasm.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class DateConvertUtils {
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter HH_MM_SS = DateTimeFormat.forPattern("HH:mm:ss");

    private DateConvertUtils() {
    }

    private static final DateConvertUtils CODE_COVERAGE = new DateConvertUtils();
}
