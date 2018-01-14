package me.caosh.autoasm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置嵌套的POJO在Disassemble时动态创建的源对象嵌套类
 * 隐含{@link Convertible}语义
 *
 * @author shuhaoc@qq.com
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
}
