package me.caosh.autoasm.handler;

import com.google.common.collect.ImmutableList;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * 读取handlers chain
 * 依次调用所有handlers直到任意一个handler返回非空
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class SourceObjectHandlerChain implements SourceObjectHandler {
    private final List<SourceObjectHandler> sourceObjectHandlerList;

    public SourceObjectHandlerChain(SourceObjectHandler... handlers) {
        this.sourceObjectHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, String propertyName) {
        for (SourceObjectHandler sourceObjectHandler : sourceObjectHandlerList) {
            Object value = sourceObjectHandler.read(targetPropertyDescriptor, sourceObject, propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public boolean write(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, Object value) {
        for (SourceObjectHandler sourceObjectHandler : sourceObjectHandlerList) {
            if (sourceObjectHandler.write(targetPropertyDescriptor, sourceObject, value)) {
                return true;
            }
        }
        return false;
    }
}
