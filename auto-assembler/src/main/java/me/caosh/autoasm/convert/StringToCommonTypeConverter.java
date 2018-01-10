package me.caosh.autoasm.convert;

import me.caosh.autoasm.ClassifiedConverter;
import me.caosh.autoasm.util.DateConvertUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 常用类别转为String的converter
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class StringToCommonTypeConverter implements ClassifiedConverter<String, Object> {

    @Override
    public Object convert(String source, Class<Object> targetClass) {
        if (String.class.equals(targetClass)) {
            return source;
        } else if (Date.class.equals(targetClass)) {
            return DateConvertUtils.yyyy_MM_dd_HH_mm_ss.parseLocalDateTime(source).toDate();
        } else if (LocalDateTime.class.equals(targetClass)) {
            return DateConvertUtils.yyyy_MM_dd_HH_mm_ss.parseLocalDateTime(source);
        } else if (LocalDate.class.equals(targetClass)) {
            return DateConvertUtils.yyyy_MM_dd.parseLocalDate(source);
        } else if (LocalTime.class.equals(targetClass)) {
            return DateConvertUtils.HH_mm_ss.parseLocalTime(source);
        } else if (Integer.class.equals(targetClass)) {
            return Integer.valueOf(source);
        } else if (Long.class.equals(targetClass)) {
            return Long.valueOf(source);
        } else if (Float.class.equals(targetClass)) {
            return Float.valueOf(source);
        } else if (Double.class.equals(targetClass)) {
            return Double.valueOf(source);
        } else if (BigDecimal.class.equals(targetClass)) {
            return new BigDecimal(source);
        } else {
            throw new IllegalArgumentException("Non supported target type: " + targetClass.getName());
        }
    }
}
