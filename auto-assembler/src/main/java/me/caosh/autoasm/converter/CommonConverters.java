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
 * 常用类型的{@link Converter}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class CommonConverters {
    public static <T> Converter<T, T> assignDirectlyConverter() {
        return new Converter<T, T>() {
            @Override
            protected T doForward(T t) {
                return t;
            }

            @Override
            protected T doBackward(T t) {
                return t;
            }
        };
    }

    public static Converter<String, Boolean> stringBooleanConverter() {
        return new Converter<String, Boolean>() {
            @Override
            protected Boolean doForward(String s) {
                return Boolean.valueOf(s);
            }

            @Override
            protected String doBackward(Boolean b) {
                return String.valueOf(b);
            }
        };
    }

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
                return DateConvertUtils.YYYY_MM_DD_HH_MM_SS.parseLocalDateTime(s).toDate();
            }

            @Override
            protected String doBackward(Date date) {
                return YYYY_MM_DD_HH_MM_SS.print(date.getTime());
            }
        };
    }

    public static Converter<String, LocalDateTime> stringLocalDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime doForward(String s) {
                return DateConvertUtils.YYYY_MM_DD_HH_MM_SS.parseLocalDateTime(s);
            }

            @Override
            protected String doBackward(LocalDateTime localDateTime) {
                return YYYY_MM_DD_HH_MM_SS.print(localDateTime);
            }
        };
    }

    public static Converter<String, LocalDate> stringLocalDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            protected LocalDate doForward(String s) {
                return DateConvertUtils.YYYY_MM_DD.parseLocalDate(s);
            }

            @Override
            protected String doBackward(LocalDate localDate) {
                return YYYY_MM_DD.print(localDate);
            }
        };
    }

    public static Converter<String, LocalTime> stringLocalTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            protected LocalTime doForward(String s) {
                return DateConvertUtils.HH_MM_SS.parseLocalTime(s);
            }

            @Override
            protected String doBackward(LocalTime localTime) {
                return HH_MM_SS.print(localTime);
            }
        };
    }

    public static Converter<Date, LocalDateTime> dateLocalDateTimeConverter() {
        return new Converter<Date, LocalDateTime>() {
            @Override
            protected LocalDateTime doForward(Date date) {
                return LocalDateTime.fromDateFields(date);
            }

            @Override
            protected Date doBackward(LocalDateTime localDateTime) {
                return localDateTime.toDate();
            }
        };
    }

    public static Converter<Date, LocalDate> dateLocalDateConverter() {
        return new Converter<Date, LocalDate>() {
            @Override
            protected LocalDate doForward(Date date) {
                return LocalDate.fromDateFields(date);
            }

            @Override
            protected Date doBackward(LocalDate localDate) {
                return localDate.toDate();
            }
        };
    }

    public static Converter<Date, LocalTime> dateLocalTimeConverter() {
        return new Converter<Date, LocalTime>() {
            @Override
            protected LocalTime doForward(Date date) {
                return LocalTime.fromDateFields(date);
            }

            @Override
            protected Date doBackward(LocalTime localTime) {
                return localTime.toDateTimeToday().toLocalDateTime().toDate();
            }
        };
    }

    public static Converter<Date, java.sql.Date> date2DbDateConverter() {
        return new Converter<Date, java.sql.Date>() {
            @Override
            protected java.sql.Date doForward(Date date) {
                return new java.sql.Date(date.getTime());
            }

            @Override
            protected Date doBackward(java.sql.Date date) {
                return date;
            }
        };
    }

    public static Converter<Date, java.sql.Time> date2DbTimeConverter() {
        return new Converter<Date, java.sql.Time>() {
            @Override
            protected java.sql.Time doForward(Date date) {
                return new java.sql.Time(date.getTime());
            }

            @Override
            protected Date doBackward(java.sql.Time time) {
                return time;
            }
        };
    }

    public static Converter<Date, java.sql.Timestamp> dateTimestampConverter() {
        return new Converter<Date, java.sql.Timestamp>() {
            @Override
            protected java.sql.Timestamp doForward(Date date) {
                return new java.sql.Timestamp(date.getTime());
            }

            @Override
            protected Date doBackward(java.sql.Timestamp timestamp) {
                return timestamp;
            }
        };
    }

    private CommonConverters() {
    }

    private static final CommonConverters CODE_COVERAGE = new CommonConverters();
}
