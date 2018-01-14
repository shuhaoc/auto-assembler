package me.caosh.autoasm.util;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 根据标量值查找枚举的工具类
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class ConvertibleEnumUtils {
    /**
     * 根据标量值查找枚举，查找失败抛出异常
     *
     * @param value       标量值
     * @param enumClass   枚举类
     * @param <ValueType> 标量值类型
     * @param <EnumType>  枚举类型
     * @return 枚举，非空
     */
    public static <ValueType, EnumType extends ConvertibleEnum<ValueType>>
    EnumType valueOf(ValueType value, Class<EnumType> enumClass) {
        ConvertibleEnum[] convertibleEnums = enumClass.getEnumConstants();
        for (ConvertibleEnum convertibleEnum : convertibleEnums) {
            if (convertibleEnum.getValue().equals(value)) {
                return (EnumType) convertibleEnum;
            }
        }
        throw new IllegalArgumentException("value=" + value + ", enumClass=" + enumClass);
    }

    private ConvertibleEnumUtils() {
    }

    private static final ConvertibleEnumUtils CODE_COVERAGE = new ConvertibleEnumUtils();
}
