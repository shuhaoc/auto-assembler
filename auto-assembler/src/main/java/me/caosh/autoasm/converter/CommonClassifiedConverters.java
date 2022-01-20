package me.caosh.autoasm.converter;

import me.caosh.autoasm.ConvertibleEnum;
import me.caosh.autoasm.util.ConvertibleEnumUtils;

/**
 * 常用类型的{@link ClassifiedConverter}
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class CommonClassifiedConverters {
    public static ClassifiedConverter<Integer, Enum> integerEnumConverter() {
        return new AbstractClassifiedConverter<Integer, Enum>() {
            @Override
            public Enum doForward(Integer value, Class<Enum> returnClass) {
                return returnClass.getEnumConstants()[value];
            }

            @Override
            public Integer doBackward(Enum value, Class<Integer> returnClass) {
                return value.ordinal();
            }
        };
    }

    public static ClassifiedConverter<String, Enum> stringEnumConverter() {
        return new AbstractClassifiedConverter<String, Enum>() {
            @Override
            public Enum doForward(String value, Class<Enum> returnClass) {
                return Enum.valueOf(returnClass, value);
            }

            @Override
            public String doBackward(Enum value, Class<String> returnClass) {
                return value.name();
            }
        };
    }

    public static <T> ClassifiedConverter<ConvertibleEnum<T>, T> convertibleEnumConverter() {
        return new AbstractClassifiedConverter<ConvertibleEnum<T>, T>() {
            @Override
            public T doForward(ConvertibleEnum<T> value, Class<T> returnClass) {
                return value.getValue();
            }

            @Override
            public ConvertibleEnum<T> doBackward(T value, Class<ConvertibleEnum<T>> returnClass) {
                return ConvertibleEnumUtils.valueOf(value, returnClass);
            }
        };
    }

    private CommonClassifiedConverters() {
    }

    private static final CommonClassifiedConverters CODE_COVERAGE = new CommonClassifiedConverters();
}
