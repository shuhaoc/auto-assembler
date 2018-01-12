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
public class TargetObjectWriteHandlerChain implements TargetObjectWriteHandler {
    private final List<TargetObjectWriteHandler> targetObjectWriteHandlerList;

    public TargetObjectWriteHandlerChain(TargetObjectWriteHandler... handlers) {
        this.targetObjectWriteHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public boolean write(PropertyDescriptor propertyDescriptor, Object targetObject, Object value) {
        for (TargetObjectWriteHandler targetObjectWriteHandler : targetObjectWriteHandlerList) {
            if (targetObjectWriteHandler.write(propertyDescriptor, targetObject, value)) {
                return true;
            }
        }
        // should not run to here
        return false;
    }

    @Override
    public Object read(PropertyDescriptor propertyDescriptor, Object targetObject, String propertyName) {
        for (TargetObjectWriteHandler targetObjectWriteHandler : targetObjectWriteHandlerList) {
            Object value = targetObjectWriteHandler.read(propertyDescriptor, targetObject, propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
