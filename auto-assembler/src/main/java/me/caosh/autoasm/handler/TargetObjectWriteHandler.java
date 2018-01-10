package me.caosh.autoasm.handler;

import java.beans.PropertyDescriptor;

/**
 * 目标对象字段值写入handler
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public interface TargetObjectWriteHandler {
    /**
     * 写入目标对象字段值
     *
     * @param propertyDescriptor 目标字段的{@link PropertyDescriptor}
     * @param targetObject       目标对象
     * @param value              字段值
     * @return 是否写入成功
     */
    boolean write(PropertyDescriptor propertyDescriptor, Object targetObject, Object value);
}
