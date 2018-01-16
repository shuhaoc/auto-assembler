package me.caosh.autoasm.handler;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.util.PropertyFindResult;
import me.caosh.autoasm.util.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Scanner;

/**
 * 支持{@link FieldMapping#mappedProperty()}配置的{@link PropertyFinder}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class FieldMappingDisassemblePropertyFinder implements PropertyFinder {
    @Override
    public PropertyFindResult findPropertyDescriptor(Object object, String propertyName, FieldMapping fieldMapping) {
        String propertyPath = getPropertyPath(propertyName, fieldMapping);
        return findProperty(object, propertyPath);
    }

    private String getPropertyPath(String propertyName, FieldMapping fieldMapping) {
        if (fieldMapping != null) {
            String mappedProperty = fieldMapping.mappedProperty();
            if (!mappedProperty.isEmpty()) {
                return mappedProperty;
            }
        }
        return propertyName;
    }

    private static PropertyFindResult findProperty(Object object, String propertyPath) {
        Object nextProperty = null;
        Scanner scanner = new Scanner(propertyPath);
        scanner.useDelimiter("\\.");
        while (scanner.hasNext()) {
            String partialPropertyName = scanner.next();
            Object currentProperty = nextProperty == null ? object : nextProperty;
            if (!scanner.hasNext()) {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(currentProperty.getClass(),
                        partialPropertyName);
                if (propertyDescriptor != null) {
                    Type fieldGenericType = null;
                    try {
                        Field declaredField = getDeclaredField(propertyDescriptor);
                        fieldGenericType = declaredField.getGenericType();
                    } catch (NoSuchFieldException e) {
                        fieldGenericType = propertyDescriptor.getPropertyType();
                    }
                    return new PropertyFindResult(currentProperty, propertyDescriptor, fieldGenericType);
                }
            } else {
                nextProperty = PropertyUtils.getPropertySoftly(currentProperty, partialPropertyName);
                if (nextProperty == null) {
                    return null;
                }
            }
        }
        return null;
    }

    private static Field getDeclaredField(PropertyDescriptor propertyDescriptor) throws NoSuchFieldException {
        String propertyName = propertyDescriptor.getName();
        Method accessorMethod = MoreObjects.firstNonNull(propertyDescriptor.getWriteMethod(),
                propertyDescriptor.getReadMethod());
        return accessorMethod.getDeclaringClass().getDeclaredField(propertyName);
    }
}
