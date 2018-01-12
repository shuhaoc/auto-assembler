package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.util.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.Scanner;

/**
 * 使用getter方法读取字段值的{@link FieldMappingHandler}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ReflectionFieldMappingHandler implements FieldMappingHandler {
    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, FieldMapping fieldMapping, Object sourceObject, String propertyPath) {
        Object nextProperty = null;
        Scanner scanner = new Scanner(propertyPath);
        scanner.useDelimiter("\\.");
        while (scanner.hasNext()) {
            String partialPropertyName = scanner.next();
            Object currentProperty = nextProperty == null ? sourceObject : nextProperty;
            nextProperty = PropertyUtils.getPropertySoftly(currentProperty, partialPropertyName);
            if (nextProperty == null) {
                return null;
            }
        }
        return nextProperty;
    }
}
