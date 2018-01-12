package me.caosh.autoasm;

import me.caosh.autoasm.convert.StringToCommonTypeConverter;
import me.caosh.autoasm.convert.ToStringConverter;
import me.caosh.autoasm.handler.FieldMappingSupportHandler;
import me.caosh.autoasm.handler.SourceObjectHandler;
import me.caosh.autoasm.handler.SourceObjectHandlerChain;
import me.caosh.autoasm.handler.SourceObjectReflectionHandler;
import me.caosh.autoasm.handler.TargetObjectHandler;
import me.caosh.autoasm.handler.TargetObjectHandlerChain;
import me.caosh.autoasm.handler.TargetObjectReflectionHandler;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 自动装载器，自动完成domain object与pojo之间或pojo之间的转换
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class AutoAssembler {
    private static final ToStringConverter TO_STRING_CONVERTER = new ToStringConverter();
    private static final StringToCommonTypeConverter STRING_TO_COMMON_TYPE_CONVERTER = new StringToCommonTypeConverter();
    private SourceObjectHandler sourceObjectHandler;
    private TargetObjectHandler targetObjectHandler;

    public AutoAssembler() {
        sourceObjectHandler = new SourceObjectHandlerChain(
                new FieldMappingSupportHandler(),
                new SourceObjectReflectionHandler()
        );
        targetObjectHandler = new TargetObjectHandlerChain(
                new TargetObjectReflectionHandler()
        );
    }

    /**
     * 将source对象转换装载为targetClass的实例对象
     * <p>
     * 1. targetClass需要支持无参构造
     * 2. source对象的所有getters提供的属性值将被赋值给targetClass创建的实例对象的setters方法
     * 3. 赋值时支持基本的类型转换
     *
     * @param sourceObject 源对象
     * @param targetClass  目标类信息
     * @param <T>          目标类型
     * @return 目标对象，不可能为空
     */
    public <S, T> T assemble(S sourceObject, Class<T> targetClass) {
        T targetObject;
        try {
            targetObject = targetClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Create target object <" + targetClass.getSimpleName()
                    + "> using non-argument-constructor failed", e);
        }

        PropertyDescriptor[] targetPropertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {
            Method writeMethod = targetPropertyDescriptor.getWriteMethod();
            if (writeMethod != null) {
                String propertyName = targetPropertyDescriptor.getName();
                Object value = sourceObjectHandler.read(targetPropertyDescriptor, sourceObject, propertyName);
                if (value != null) {
                    Class<?> propertyType = targetPropertyDescriptor.getPropertyType();
                    Object convertedValue = getConvertedValue(value, propertyType);
                    targetObjectHandler.write(targetPropertyDescriptor, targetObject, convertedValue);
                }
            }
        }
        return targetObject;
    }

    public <S, T> S disassemble(T targetObject, Class<S> sourceClass) {
        S sourceObject;
        try {
            sourceObject = sourceClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Create source object <" + sourceClass.getSimpleName()
                    + "> using non-argument-constructor failed", e);
        }

        Class<?> targetClass = targetObject.getClass();
        PropertyDescriptor[] targetPropertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {
            Method readMethod = targetPropertyDescriptor.getReadMethod();
            if (readMethod != null) {
                String propertyName = targetPropertyDescriptor.getName();
                Object value = targetObjectHandler.read(targetPropertyDescriptor, targetObject, propertyName);
                if (value != null) {
                    Class<?> propertyType = targetPropertyDescriptor.getPropertyType();
                    Object convertedValue = getConvertedValue(value, propertyType);
                    sourceObjectHandler.write(targetPropertyDescriptor, sourceObject, convertedValue);
                }
            }
        }
        return sourceObject;
    }

    private Object getConvertedValue(Object value, Class<?> propertyType) {
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
            throw new IllegalArgumentException("Type mismatch and cannot convert: " + value.getClass().getSimpleName()
                    + " to " + propertyType.getSimpleName());
        }
    }
}
