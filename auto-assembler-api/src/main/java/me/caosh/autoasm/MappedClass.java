package me.caosh.autoasm;

import me.caosh.autoasm.builder.NotConfiguredConvertibleBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置目标对象在disassemble时动态创建的源对象类或源对象的Builder类
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedClass {
    /**
     * 映射类
     *
     * @return 映射类
     */
    Class<?> value();

    /**
     * 映射类的Builder类
     *
     * @return 映射类的Builder类
     */
    Class<? extends ConvertibleBuilder> builderClass() default NotConfiguredConvertibleBuilder.class;
}
