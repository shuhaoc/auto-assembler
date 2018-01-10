package me.caosh.autoasm.convert;

import me.caosh.autoasm.ClassifiedConverter;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Date;

import static me.caosh.autoasm.util.DateConvertUtils.*;

/**
 * 任意类型转String的converter
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ToStringConverter implements ClassifiedConverter<Object, String> {
    @Override
    public String convert(Object source, Class<String> targetClass) {
        if (Date.class.equals(source.getClass())) {
            return yyyy_MM_dd_HH_mm_ss.print(((Date) source).getTime());
        } else if (LocalDateTime.class.equals(source.getClass())) {
            return yyyy_MM_dd_HH_mm_ss.print((LocalDateTime) source);
        } else if (LocalDate.class.equals(source.getClass())) {
            return yyyy_MM_dd.print((LocalDate) source);
        } else if (LocalTime.class.equals(source.getClass())) {
            return HH_mm_ss.print((LocalTime) source);
        }
        return String.valueOf(source);
    }
}
