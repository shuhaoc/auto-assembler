package me.caosh.autoasm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可转换POJO注解，用于在Auto assembler转换过程中，标明某个POJO是支持自动转换的
 * 当目标类的某个字段不是直接可转换的类型，而是另一个POJO时，可以在此POJO上定义此注解
 * 使Auto assembler递归调用自动装载、反装载逻辑进行转换
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Convertible {
}
