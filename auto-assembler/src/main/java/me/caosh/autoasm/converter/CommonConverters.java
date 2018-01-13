package me.caosh.autoasm.converter;

import com.google.common.base.Converter;
import me.caosh.autoasm.util.DateConvertUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.util.Date;

import static me.caosh.autoasm.util.DateConvertUtils.*;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class CommonConverters {
    public static Converter<String, BigDecimal> stringBigDecimalConverter() {
        return new Converter<String, BigDecimal>() {
            @Override
            protected BigDecimal doForward(String s) {
                return new BigDecimal(s);
            }

            @Override
            protected String doBackward(BigDecimal bigDecimal) {
                return bigDecimal.toString();
            }
        };
    }

    public static Converter<String, Date> stringDateConverter() {
        return new Converter<String, Date>() {
            @Override
            protected Date doForward(String s) {
                return DateConvertUtils.yyyy_MM_dd_HH_mm_ss.parseLocalDateTime(s).toDate();
            }

            @Override
            protected String doBackward(Date date) {
                return yyyy_MM_dd_HH_mm_ss.print(date.getTime());
            }
        };
    }

    public static Converter<String, LocalDateTime> stringLocalDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime doForward(String s) {
                return DateConvertUtils.yyyy_MM_dd_HH_mm_ss.parseLocalDateTime(s);
            }

            @Override
            protected String doBackward(LocalDateTime localDateTime) {
                return yyyy_MM_dd_HH_mm_ss.print(localDateTime);
            }
        };
    }

    public static Converter<String, LocalDate> stringLocalDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            protected LocalDate doForward(String s) {
                return DateConvertUtils.yyyy_MM_dd.parseLocalDate(s);
            }

            @Override
            protected String doBackward(LocalDate localDate) {
                return yyyy_MM_dd.print(localDate);
            }
        };
    }

    public static Converter<String, LocalTime> stringLocalTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            protected LocalTime doForward(String s) {
                return DateConvertUtils.HH_mm_ss.parseLocalTime(s);
            }

            @Override
            protected String doBackward(LocalTime localTime) {
                return HH_mm_ss.print(localTime);
            }
        };
    }

    private CommonConverters() {
    }

    private static final CommonConverters _CODE_COVERAGE = new CommonConverters();
}
