package me.caosh.autoasm.handler;

import com.google.common.collect.ImmutableList;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * 写入handlers chain
 * 依次调用所有handlers直到任意一个handler返回true
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class TargetObjectHandlerChain implements TargetObjectHandler {
    private final List<TargetObjectHandler> targetObjectHandlerList;

    public TargetObjectHandlerChain(TargetObjectHandler... handlers) {
        this.targetObjectHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public boolean write(PropertyDescriptor propertyDescriptor, Object targetObject, Object value) {
        for (TargetObjectHandler targetObjectHandler : targetObjectHandlerList) {
            if (targetObjectHandler.write(propertyDescriptor, targetObject, value)) {
                return true;
            }
        }
        // should not run to here
        return false;
    }

    @Override
    public Object read(PropertyDescriptor propertyDescriptor, Object targetObject, String propertyName) {
        for (TargetObjectHandler targetObjectHandler : targetObjectHandlerList) {
            Object value = targetObjectHandler.read(propertyDescriptor, targetObject, propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
