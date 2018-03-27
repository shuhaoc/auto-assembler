package me.caosh.autoasm;

import me.caosh.autoasm.converter.ClassifiedConverter;
import me.caosh.autoasm.converter.NotConfiguredClassifiedConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Auto assembler目标对象的字段映射配置
 *
 * 目标对象必须为POJO，且保证所有fields的setter和getter和该field在同一个类中定义
 *
 * 配置项优先级顺序（从高到低）：defaultValue, mappedProperty
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldMapping {
    /**
     * 常量值，注解的POJO为目标对象时，配置该字段在自动装载时直接填入value表示的值，而不从源对象中读取
     * 空字符串（默认）表示未配置
     *
     * @return 常量值
     */
    String value() default "";

    /**
     * 默认值，注解的POJO为目标对象，配置该字段在自动反装载至源对象时，如果字段值为null，使用defaultValue表示的值
     *
     * @return 默认值
     */
    String defaultValue() default "";

    /**
     * 映射对方的属性名，默认值""表示与注解所在field同名
     *
     * @return 映射属性名
     */
    String mappedProperty() default "";

    /**
     * 自定义converter，仅针对配置的字段有效
     * 需要支持空参构造
     *
     * @return 自定义converter
     */
    Class<? extends ClassifiedConverter> customConverterClass() default NotConfiguredClassifiedConverter.class;
}
