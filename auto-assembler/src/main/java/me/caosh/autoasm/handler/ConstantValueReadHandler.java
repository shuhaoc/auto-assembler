package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;

import java.beans.PropertyDescriptor;

/**
 * 读取{@link FieldMapping#value()}配置的值
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ConstantValueReadHandler implements FieldMappingReadHandler {
    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, FieldMapping fieldMapping, Object sourceObject, String propertyPath) {
        if (!fieldMapping.value().isEmpty()) {
            return fieldMapping.value();
        }
        return null;
    }
}
