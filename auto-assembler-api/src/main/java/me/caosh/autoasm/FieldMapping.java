package me.caosh.autoasm;

import me.caosh.autoasm.converter.ClassifiedConverter;
import me.caosh.autoasm.converter.NotConfiguredClassifiedConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 目标对象的字段映射配置
 * <p>
 * 目标对象必须为POJO，且保证所有fields的setter和getter和该field在同一个类中定义
 * <p>
 * 配置项优先级顺序（从高到低）：value, defaultValue, mappedProperty
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldMapping {
    /**
     * 常量值，配置该字段在assemble时直接使用的值，而不从源对象中读取
     * <p>
     * 空字符串（默认）表示未配置
     *
     * @return 常量值
     */
    String value() default "";

    /**
     * 默认值，配置该字段在disassemble至源对象时，如果从目标字段中读取的字段值为null，使用此默认值
     * <p>
     * 空字符串（默认）表示未配置
     *
     * @return 默认值
     */
    String defaultValue() default "";

    /**
     * 映射源对象中的的属性路径，默认值空串表示与注解所在的field同名
     *
     * @return 映射属性名
     */
    String mappedProperty() default "";

    /**
     * 自定义converter，仅针对配置的字段有效
     * <p>
     * 需要支持空参构造
     *
     * @return 自定义converter
     */
    Class<? extends ClassifiedConverter> customConverterClass() default NotConfiguredClassifiedConverter.class;
}
