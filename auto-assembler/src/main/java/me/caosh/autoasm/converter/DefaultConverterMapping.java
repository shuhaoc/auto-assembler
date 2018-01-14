package me.caosh.autoasm.converter;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import me.caosh.autoasm.ConvertibleEnum;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 默认的{@link ConverterMapping}，内置常用的类型转换器
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class DefaultConverterMapping extends ConverterMapping {
    public DefaultConverterMapping() {
        register(String.class, Integer.class, Ints.stringConverter());
        register(String.class, Long.class, Longs.stringConverter());
        register(String.class, Float.class, Floats.stringConverter());
        register(String.class, Double.class, Doubles.stringConverter());
        register(String.class, BigDecimal.class, CommonConverters.stringBigDecimalConverter());
        register(String.class, Date.class, CommonConverters.stringDateConverter());
        register(String.class, LocalDateTime.class, CommonConverters.stringLocalDateTimeConverter());
        register(String.class, LocalDate.class, CommonConverters.stringLocalDateConverter());
        register(String.class, LocalTime.class, CommonConverters.stringLocalTimeConverter());
        register(Date.class, LocalDateTime.class, CommonConverters.dateLocalDateTimeConverter());
        register(Date.class, LocalDate.class, CommonConverters.dateLocalDateConverter());
        register(Date.class, java.sql.Date.class, CommonConverters.date2DbDateConverter());
        register(Date.class, java.sql.Time.class, CommonConverters.date2DbTimeConverter());
        register(Date.class, java.sql.Timestamp.class, CommonConverters.dateTimestampConverter());
        register(ConvertibleEnum.class, Object.class,
                (ClassifiedConverter) CommonClassifiedConverters.convertibleEnumConverter());
        register(Integer.TYPE, Integer.class, CommonConverters.<Integer>assignDirectlyConverter());
        register(Long.TYPE, Long.class, CommonConverters.<Long>assignDirectlyConverter());
        register(Float.TYPE, Float.class, CommonConverters.<Float>assignDirectlyConverter());
        register(Double.TYPE, Double.class, CommonConverters.<Double>assignDirectlyConverter());
    }
}
