package me.caosh.autoasm;

import com.google.common.base.Converter;
import me.caosh.autoasm.converter.ConverterMapping;
import me.caosh.autoasm.converter.DefaultConverterMapping;
import me.caosh.autoasm.handler.*;
import me.caosh.autoasm.util.PropertyFindResult;
import me.caosh.autoasm.util.PropertyUtils;
import me.caosh.autoasm.util.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自动装载器，自动完成domain object与pojo之间或pojo之间的转换
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class AutoAssembler {
    private ReadHandler assembleReadHandler;
    private ReadHandler disassembleReadHandler;
    private PropertyFinder disassemblePropertyFinder;
    private ConverterMapping converterMapping = new DefaultConverterMapping();

    public AutoAssembler() {
        assembleReadHandler = new ReadHandlerChain(
                new FieldMappingAssembleReadHandler(),
                new ReflectionReadHandler()
        );
        disassembleReadHandler = new ReadHandlerChain(
                new ReflectionReadHandler()
        );
        disassemblePropertyFinder = new FieldMappingDisassemblePropertyFinder();
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
        T targetObject = ReflectionUtils.newInstance(targetClass);

        PropertyDescriptor[] targetPropertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {
            Method writeMethod = targetPropertyDescriptor.getWriteMethod();
            if (writeMethod != null) {
                String propertyName = targetPropertyDescriptor.getName();
                FieldMapping fieldMapping = getFieldMapping(propertyName, writeMethod);
                Object value = assembleReadHandler.read(fieldMapping, sourceObject, propertyName);
                if (value != null) {
                    Class<?> propertyType = targetPropertyDescriptor.getPropertyType();
                    Object convertedValue = getConvertedValue(value, propertyType, propertyType);
                    PropertyUtils.setProperty(targetPropertyDescriptor, targetObject, convertedValue);
                }
            }
        }
        return targetObject;
    }

    public <S, T> S disassemble(T targetObject, Class<S> sourceClass) {
        S sourceObject = ReflectionUtils.newInstance(sourceClass);

        Class<?> targetClass = targetObject.getClass();
        PropertyDescriptor[] targetPropertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {
            Method readMethod = targetPropertyDescriptor.getReadMethod();
            if (readMethod != null) {
                String propertyName = targetPropertyDescriptor.getName();
                FieldMapping fieldMapping = getFieldMapping(propertyName, readMethod);
                Object value = disassembleReadHandler.read(fieldMapping, targetObject, propertyName);
                if (value != null) {
                    PropertyFindResult propertyFindResult = disassemblePropertyFinder.findPropertyDescriptor(
                            sourceObject, propertyName, fieldMapping);
                    if (propertyFindResult != null) {
                        PropertyDescriptor propertyDescriptor = propertyFindResult.getPropertyDescriptor();
                        Class<?> propertyType = propertyDescriptor.getPropertyType();
                        Class<?> targetPropertyType = targetPropertyDescriptor.getPropertyType();
                        Object convertedValue = getConvertedValue(value, targetPropertyType, propertyType);
                        PropertyUtils.setProperty(propertyDescriptor, propertyFindResult.getOwnObject(), convertedValue);
                    }
                }
            }
        }
        return sourceObject;
    }

    private FieldMapping getFieldMapping(String propertyName, Method accessorMethod) {
        if ("class".equals(propertyName)) {
            // 每个对象都有一个class属性，不处理
            return null;
        }

        Field declaredField;
        try {
            declaredField = accessorMethod.getDeclaringClass().getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Get declared field (" + propertyName + ") from property declaring class <"
                    + accessorMethod.getDeclaringClass().getSimpleName() + "> failed", e);
        }

        return declaredField.getAnnotation(FieldMapping.class);
    }

    /**
     * 进行字段转换
     *
     * @param value                转换前字段值
     * @param targetPropertyType   目标类型，assemble时为返回值类型，disassemble时为入参类型
     * @param expectedPropertyType 返回值类型
     * @return 转换后字段值
     */
    private Object getConvertedValue(Object value, Class<?> targetPropertyType, Class<?> expectedPropertyType) {
        if (expectedPropertyType.equals(value.getClass())) {
            return value;
        }

        Converter converter = converterMapping.find(value.getClass(), expectedPropertyType);
        if (converter != null) {
            return converter.convert(value);
        }

        Convertible convertible = targetPropertyType.getAnnotation(Convertible.class);
        if (convertible != null) {
            return new AutoAssembler().disassemble(value, expectedPropertyType);
        }
        throw new IllegalArgumentException("Type mismatch and cannot convert: " + value.getClass().getSimpleName()
                + " to " + expectedPropertyType.getSimpleName());
    }
}
