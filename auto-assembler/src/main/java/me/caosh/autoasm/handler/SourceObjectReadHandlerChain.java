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
public class SourceObjectReadHandlerChain implements SourceObjectReadHandler {
    private final List<SourceObjectReadHandler> sourceObjectReadHandlerList;

    public SourceObjectReadHandlerChain(SourceObjectReadHandler... handlers) {
        this.sourceObjectReadHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, String propertyName) {
        for (SourceObjectReadHandler sourceObjectReadHandler : sourceObjectReadHandlerList) {
            Object value = sourceObjectReadHandler.read(targetPropertyDescriptor, sourceObject, propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
