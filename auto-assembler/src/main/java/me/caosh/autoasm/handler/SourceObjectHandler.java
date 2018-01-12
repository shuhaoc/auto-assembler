package me.caosh.autoasm.handler;

import java.beans.PropertyDescriptor;

/**
 * 源对象字段值读取handler
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public interface SourceObjectHandler {
    /**
     * 读取源对象字段值
     *
     * @param targetPropertyDescriptor 目标属性的{@link PropertyDescriptor}
     * @param sourceObject                源对象
     * @param propertyName             @return 返回字段值，可为空
     */
    Object read(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, String propertyName);

    boolean write(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, Object value);
}
