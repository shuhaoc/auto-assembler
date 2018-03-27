package me.caosh.autoasm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记目标对象的的字段在assemble/disassemble过程中不处理
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SkippedField {
}
