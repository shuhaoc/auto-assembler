package me.caosh.autoasm.handler;

import com.google.common.base.Preconditions;
import me.caosh.autoasm.FieldMapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 支持{@link FieldMapping}的读取handler
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class FieldMappingSupportReadHandler implements SourceObjectReadHandler {
    private final FieldMappingReadHandler fieldMappingReadHandler;

    public FieldMappingSupportReadHandler() {
        fieldMappingReadHandler = new FieldMappingReadHandlerChain(
                new ConstantValueReadHandler(),
                new ReflectionFieldMappingReadHandler()
        );
    }

    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, String propertyName) {
        Method writeMethod = targetPropertyDescriptor.getWriteMethod();
        Preconditions.checkNotNull(writeMethod);

        Field declaredField;
        try {
            declaredField = writeMethod.getDeclaringClass().getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Get declared field (" + propertyName + ") from property declaring class <"
                    + writeMethod.getDeclaringClass().getSimpleName() + "> failed", e);
        }

        FieldMapping fieldMapping = declaredField.getAnnotation(FieldMapping.class);
        if (fieldMapping == null) {
            return null;
        }

        String propertyPath;
        String mappedProperty = fieldMapping.mappedProperty();
        if (!mappedProperty.isEmpty()) {
            propertyPath = mappedProperty;
        } else {
            propertyPath = propertyName;
        }
        return fieldMappingReadHandler.read(targetPropertyDescriptor, fieldMapping, sourceObject, propertyPath);
    }

}
