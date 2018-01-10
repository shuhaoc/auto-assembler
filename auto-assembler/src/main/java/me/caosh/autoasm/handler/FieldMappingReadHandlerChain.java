package me.caosh.autoasm.handler;

import com.google.common.collect.ImmutableList;
import me.caosh.autoasm.FieldMapping;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * {@link FieldMappingReadHandler}的handler chain
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class FieldMappingReadHandlerChain implements FieldMappingReadHandler {
    private final List<FieldMappingReadHandler> fieldMappingReadHandlerList;

    public FieldMappingReadHandlerChain(FieldMappingReadHandler... handlers) {
        fieldMappingReadHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, FieldMapping fieldMapping, Object sourceObject, String propertyPath) {
        for (FieldMappingReadHandler fieldMappingReadHandler : fieldMappingReadHandlerList) {
            Object value = fieldMappingReadHandler.read(targetPropertyDescriptor, fieldMapping, sourceObject, propertyPath);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
