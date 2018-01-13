package me.caosh.autoasm.handler;

import com.google.common.base.Converter;
import me.caosh.autoasm.AutoAssembler;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.converter.ConverterMapping;
import me.caosh.autoasm.converter.DefaultConverterMapping;
import me.caosh.autoasm.util.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 使用getter/setter方法读取、写入字段值
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class SourceObjectReflectionHandler implements SourceObjectHandler {
    private ConverterMapping converterMapping = new DefaultConverterMapping();

    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, String propertyName) {
        return PropertyUtils.getPropertySoftly(sourceObject, propertyName);
    }

    @Override
    public boolean write(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, Object value) {
        String propertyName = targetPropertyDescriptor.getName();
        Class<?> sourceClass = sourceObject.getClass();

        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(sourceClass, propertyName);
        if (propertyDescriptor == null) {
            return false;
        }

        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            return false;
        }

        Object convertFieldValue = reversedConvertFieldValue(targetPropertyDescriptor.getPropertyType(), value, propertyDescriptor.getPropertyType());

        try {
            writeMethod.invoke(sourceObject, convertFieldValue);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Invoke write method failed: <" + value.getClass().getSimpleName() + "> "
                    + sourceClass.getSimpleName() + "#"
                    + writeMethod.getName() + "(" + value + ")", e);
        }
    }

    private Object reversedConvertFieldValue(Class<?> targetPropertyType, Object value, Class<?> propertyType) {
        if (propertyType.equals(value.getClass())) {
            return value;
        }

        Converter converter = converterMapping.find(value.getClass(), propertyType);
        if (converter != null) {
            return converter.convert(value);
        }

        Convertible convertible = targetPropertyType.getAnnotation(Convertible.class);
        if (convertible != null) {
            return new AutoAssembler().disassemble(value, propertyType);
        }
        throw new IllegalArgumentException("Type mismatch and cannot convert: " + value.getClass().getSimpleName()
                + " to " + propertyType.getSimpleName());

    }
}
