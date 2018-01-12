package me.caosh.autoasm.handler;

import com.google.common.collect.ImmutableList;
import me.caosh.autoasm.FieldMapping;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * {@link FieldMappingHandler}的handler chain
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class FieldMappingHandlerChain implements FieldMappingHandler {
    private final List<FieldMappingHandler> fieldMappingHandlerList;

    public FieldMappingHandlerChain(FieldMappingHandler... handlers) {
        fieldMappingHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, FieldMapping fieldMapping, Object sourceObject, String propertyPath) {
        for (FieldMappingHandler fieldMappingHandler : fieldMappingHandlerList) {
            Object value = fieldMappingHandler.read(targetPropertyDescriptor, fieldMapping, sourceObject, propertyPath);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
