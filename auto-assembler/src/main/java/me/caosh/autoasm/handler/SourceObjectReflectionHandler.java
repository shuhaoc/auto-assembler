package me.caosh.autoasm.handler;

import me.caosh.autoasm.AutoAssembler;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.convert.StringToCommonTypeConverter;
import me.caosh.autoasm.convert.ToStringConverter;
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
    private static final ToStringConverter TO_STRING_CONVERTER = new ToStringConverter();
    private static final StringToCommonTypeConverter STRING_TO_COMMON_TYPE_CONVERTER = new StringToCommonTypeConverter();

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

        // 类型不兼容，尝试进行转换
        if (String.class.equals(propertyType)) {
            // 目标类型为String的，直接String.valueOf
            return TO_STRING_CONVERTER.convert(value, String.class);
        } else if (String.class.equals(value.getClass())) {
            // 源类型为String的，尝试转换为通用类型
            return STRING_TO_COMMON_TYPE_CONVERTER.convert((String) value, (Class) propertyType);
        } else {
            Convertible convertible = targetPropertyType.getAnnotation(Convertible.class);
            if (convertible != null) {
                return new AutoAssembler().disassemble(value, propertyType);
            }
            throw new IllegalArgumentException("Type mismatch and cannot convert: " + value.getClass().getSimpleName()
                    + " to " + propertyType.getSimpleName());
        }
    }
}
