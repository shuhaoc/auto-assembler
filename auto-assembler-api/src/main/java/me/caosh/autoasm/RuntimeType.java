package me.caosh.autoasm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置目标对象中的接口或抽象类的子类，用于实现运行时动态实例化子类对象
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RuntimeType {
    /**
     * 子类列表
     *
     * @return 子类列表
     */
    Class<?>[] value();
}
