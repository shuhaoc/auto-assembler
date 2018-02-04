package me.caosh.autoasm;

import com.google.common.base.Converter;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import me.caosh.autoasm.builder.NotConfiguredConvertibleBuilder;
import me.caosh.autoasm.converter.ClassifiedConverter;
import me.caosh.autoasm.converter.ConverterMapping;
import me.caosh.autoasm.converter.DefaultConverterMapping;
import me.caosh.autoasm.converter.NotConfiguredClassifiedConverter;
import me.caosh.autoasm.handler.*;
import me.caosh.autoasm.util.AssemblerWithBuilder;
import me.caosh.autoasm.util.PropertyFindResult;
import me.caosh.autoasm.util.PropertyUtils;
import me.caosh.autoasm.util.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * 自动装载器，自动完成domain object与pojo之间或pojo之间的转换
 * {@link AutoAssembler}对象比较重，使用时建议使用单例
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class AutoAssembler {
    private static final String CLASS = "class";

    private ReadHandler assembleReadHandler;
    private ReadHandler disassembleReadHandler;
    private PropertyFinder disassemblePropertyFinder;
    private ConverterMapping converterMapping;

    AutoAssembler() {
        this(new DefaultConverterMapping());
    }

    AutoAssembler(ConverterMapping converterMapping) {
        this.assembleReadHandler = new ReadHandlerChain(
                new FieldMappingAssembleReadHandler(),
                new ReflectionReadHandler()
        );
        this.disassembleReadHandler = new ReadHandlerChain(
                new ReflectionReadHandler(),
                new FieldMappingDisassembleReadHandler()
        );
        this.disassemblePropertyFinder = new FieldMappingDisassemblePropertyFinder();
        this.converterMapping = converterMapping;
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
        ClassifiedConverter<S, T> scalaConverter = converterMapping.find((Class<S>) sourceObject.getClass(), targetClass);
        if (scalaConverter != null) {
            return scalaConverter.convert(sourceObject, targetClass);
        }
        RuntimeType runtimeType = targetClass.getAnnotation(RuntimeType.class);
        if (runtimeType != null) {
            return (T) convertValueOnAssembling(sourceObject, targetClass, null);
        }
        T targetObject = ReflectionUtils.newInstance(targetClass);
        assembleToTarget(sourceObject, targetObject);
        return targetObject;
    }

    public <S, BT extends ConvertibleBuilder> BT assemble(S sourceObject, BT targetBuilder) {
        assembleToTarget(sourceObject, targetBuilder);
        return targetBuilder;
    }

    public <S, T> List<T> assembleList(Iterable<S> sourceList, Class<T> targetElementClass) {
        if (Iterables.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        Class<S> sourceElementClass = (Class<S>) sourceList.iterator().next().getClass();
        Converter<S, T> converter = getConverterFor(sourceElementClass, targetElementClass);
        return Lists.newArrayList(Iterables.transform(sourceList, converter));
    }

    private void assembleToTarget(Object sourceObject, Object targetObject) {
        Class<?> targetClass = targetObject.getClass();
        PropertyDescriptor[] targetPropertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {
            Method writeMethod = targetPropertyDescriptor.getWriteMethod();
            if (writeMethod != null) {
                String propertyName = targetPropertyDescriptor.getName();
                Class<?> propertyType = targetPropertyDescriptor.getPropertyType();
                if (CLASS.equals(propertyName)) {
                    // 每个对象都有一个class属性，不处理
                    continue;
                }

                PropertyMeta propertyMeta = getPropertyMeta(propertyName, writeMethod, propertyType);
                if (propertyMeta.getSkippedField().isPresent()) {
                    // 配置为跳过的字段不处理
                    continue;
                }

                Object value = assembleReadHandler.read(propertyMeta.getFieldMapping().orNull(), sourceObject, propertyName);
                if (value != null) {
                    ClassifiedConverter<?, ?> converter = getAssembleConverter(propertyMeta.getFieldMapping().orNull(),
                            value, propertyType);
                    Object convertedValue = convertValueOnAssembling(value, propertyMeta.getFieldGenericType(), converter);
                    PropertyUtils.setProperty(targetPropertyDescriptor, targetObject, convertedValue);
                }
            }
        }
    }

    /**
     * 将targetObject反装载为targetObject实例对象
     * <p>
     * 1. sourceClass需要支持无参构造
     * 2. target对象的所有getters提供的属性值将被赋值给sourceClass创建的实例对象的setters方法
     * 3. 赋值时支持基本的类型转换
     *
     * @param targetObject 目标对象
     * @param sourceClass  源类型class
     * @param <S>          源类型
     * @param <T>          目标类型
     * @return 源对象
     */
    public <S, T> S disassemble(T targetObject, Class<S> sourceClass) {
        Class<T> targetClass = (Class<T>) targetObject.getClass();
        ClassifiedConverter<T, S> scalaConverter = converterMapping.find(targetClass, sourceClass);
        if (scalaConverter != null) {
            return scalaConverter.convert(targetObject, sourceClass);
        }
        MappedClass mappedClass = targetClass.getAnnotation(MappedClass.class);
        if (mappedClass != null) {
            return disassembleToMappedClass(targetObject, mappedClass);
        }
        S sourceObject = ReflectionUtils.newInstance(sourceClass);
        disassembleFromTarget(targetObject, sourceObject);
        return sourceObject;
    }

    public <SB, T> SB disassemble(T targetObject, SB sourceBuilder) {
        disassembleFromTarget(targetObject, sourceBuilder);
        return sourceBuilder;
    }

    public <S, T> List<S> disassembleList(Iterable<T> targetList, Class<S> sourceElementClass) {
        if (Iterables.isEmpty(targetList)) {
            return Collections.emptyList();
        }
        Class<T> targetElementClass = (Class<T>) targetList.iterator().getClass();
        Converter<T, S> converter = getConverterFor(sourceElementClass, targetElementClass).reverse();
        return Lists.newArrayList(Iterables.transform(targetList, converter));
    }

    private <S, T> S disassembleToMappedClass(T targetObject, MappedClass mappedClass) {
        Class<? extends ConvertibleBuilder> builderClass = mappedClass.builderClass();
        if (builderClass != NotConfiguredConvertibleBuilder.class) {
            ConvertibleBuilder sourceBuilder = null;
            try {
                sourceBuilder = builderClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            disassembleFromTarget(targetObject, sourceBuilder);
            return (S) sourceBuilder.build();
        }
        S sourceObject = (S) ReflectionUtils.newInstance(mappedClass.value());
        disassembleFromTarget(targetObject, sourceObject);
        return sourceObject;
    }

    private void disassembleFromTarget(Object targetObject, Object sourceObject) {
        Class<?> targetClass = targetObject.getClass();
        PropertyDescriptor[] targetPropertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {
            Method readMethod = targetPropertyDescriptor.getReadMethod();
            if (readMethod != null) {
                String propertyName = targetPropertyDescriptor.getName();
                Class<?> targetPropertyType = targetPropertyDescriptor.getPropertyType();
                if (CLASS.equals(propertyName)) {
                    // 每个对象都有一个class属性，不处理
                    continue;
                }

                PropertyMeta propertyMeta = getPropertyMeta(propertyName, readMethod, targetPropertyType);
                if (propertyMeta.getSkippedField().isPresent()) {
                    // 配置为跳过的字段不处理
                    continue;
                }

                Object value = disassembleReadHandler.read(propertyMeta.getFieldMapping().orNull(), targetObject, propertyName);
                if (value != null) {
                    PropertyFindResult propertyFindResult = disassemblePropertyFinder.findPropertyDescriptor(
                            sourceObject, propertyName, propertyMeta.getFieldMapping().orNull());
                    if (propertyFindResult != null) {
                        PropertyDescriptor propertyDescriptor = propertyFindResult.getPropertyDescriptor();
                        Class<?> propertyType = propertyDescriptor.getPropertyType();
                        ClassifiedConverter<?, ?> converter = getDisassembleConverter(propertyMeta.getFieldMapping().orNull(),
                                value, propertyType);
                        Object convertedValue = convertValueOnDisassembling(value, targetPropertyType,
                                propertyFindResult.getFieldGenericType(), converter);
                        PropertyUtils.setProperty(propertyDescriptor, propertyFindResult.getOwnObject(), convertedValue);
                    }
                }
            }
        }
    }

    private PropertyMeta getPropertyMeta(String propertyName, Method accessorMethod, Class<?> propertyType) {
        try {
            Field declaredField = accessorMethod.getDeclaringClass().getDeclaredField(propertyName);

            Type genericType = declaredField.getGenericType();
            FieldMapping fieldMapping = declaredField.getAnnotation(FieldMapping.class);
            SkippedField skippedField = declaredField.getAnnotation(SkippedField.class);
            return new PropertyMeta(genericType, fieldMapping, skippedField);
        } catch (NoSuchFieldException e) {
            // 找不到accessorMethod同名的field，仍然兼容，视为无注解配置
            return new PropertyMeta(propertyType, null, null);
        }
    }

    private ClassifiedConverter<?, ?> getAssembleConverter(FieldMapping fieldMapping, Object value, Class<?> propertyType) {
        if (fieldMapping != null) {
            Class<? extends ClassifiedConverter> customConverterClass = fieldMapping.customConverterClass();
            if (!NotConfiguredClassifiedConverter.class.equals(customConverterClass)) {
                // 不是默认值的，使用配置的converter类
                return ReflectionUtils.newInstance(customConverterClass);
            }
        }
        return converterMapping.find(value.getClass(), propertyType);
    }

    private ClassifiedConverter<?, ?> getDisassembleConverter(FieldMapping fieldMapping, Object value, Class<?> propertyType) {
        if (fieldMapping != null) {
            Class<? extends ClassifiedConverter> customConverterClass = fieldMapping.customConverterClass();
            if (!NotConfiguredClassifiedConverter.class.equals(customConverterClass)) {
                // 不是默认值的，使用配置的converter类并取反向converter
                return ReflectionUtils.newInstance(customConverterClass).reverse();
            }
        }
        return converterMapping.find(value.getClass(), propertyType);
    }

    /**
     * 在assemble中进行字段转换
     *
     * @param originalValue          转换前字段值
     * @param targetFieldGenericType 目标字段Type
     * @param converter              类型不兼容时使用的converter
     * @return 转换后字段值
     */
    private Object convertValueOnAssembling(Object originalValue,
                                            Type targetFieldGenericType,
                                            ClassifiedConverter converter) {
        Object value = stripOptionalValue(originalValue);
        if (value == null) {
            return null;
        }

        if (targetFieldGenericType instanceof ParameterizedType) {
            return convertGenericTypeField(originalValue, targetFieldGenericType, false);
        }

        // 非参数化字段的，视为普通字段，其他Type暂不支持
        Class<?> targetPropertyType = (Class<?>) targetFieldGenericType;

        if (targetPropertyType.isInstance(value)) {
            return value;
        }

        if (converter != null) {
            return converter.convert(value, targetPropertyType);
        }

        Convertible convertible = targetPropertyType.getAnnotation(Convertible.class);
        if (convertible != null) {
            return assemble(value, targetPropertyType);
        }
        RuntimeType runtimeType = targetPropertyType.getAnnotation(RuntimeType.class);
        if (runtimeType != null) {
            Class<?>[] subClasses = runtimeType.value();
            for (Class<?> subClass : subClasses) {
                MappedClass mappedClass = subClass.getAnnotation(MappedClass.class);
                if (mappedClass == null) {
                    throw new IllegalArgumentException("Runtime type subclass should be annotated with @MappedClass");
                }
                if (mappedClass.value().isInstance(value)) {
                    return assemble(value, subClass);
                }
            }
            return null;
        }
        throw new IllegalArgumentException("Type mismatch and cannot convert: " + value.getClass().getSimpleName()
                + " to " + targetPropertyType.getSimpleName());
    }

    private Object convertGenericTypeField(Object originalValue, Type expectedFieldGenericType, boolean reverse) {
        // 源字段、目标字段都是泛型集合，进行集合转换
        ParameterizedType parameterizedType = (ParameterizedType) expectedFieldGenericType;
        if (originalValue instanceof List
                && parameterizedType.getRawType() instanceof Class
                && List.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
            // NOTICE: 暂时只支持List，扩展需要重构

            List originalList = (List) originalValue;
            if (originalList.isEmpty()) {
                return Collections.emptyList();
            }

            Preconditions.checkArgument(parameterizedType.getActualTypeArguments().length == 1,
                    "Type argument of List must be 1");

            Class<?> originalClass = originalList.get(0).getClass();
            Class<?> expectedClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];

            if (expectedClass.isAssignableFrom(originalClass)) {
                return originalList;
            }

            Converter<?, ?> converterForElement;
            if (reverse) {
                converterForElement = getConverterFor(expectedClass, originalClass).reverse();
            } else {
                converterForElement = getConverterFor(originalClass, expectedClass);
            }
            return Lists.newArrayList(Lists.transform((List) originalValue, converterForElement));
        }
        // 暂不支持的抛出异常
        throw new IllegalArgumentException("Non-collection generic type fields not supported: " + expectedFieldGenericType);
    }

    /**
     * 在disassemble中进行字段转换
     *
     * @param originalValue            转换前字段值
     * @param targetPropertyType       目标类型，即入参类型
     * @param expectedFieldGenericType 期望返回的字段type
     * @param converter                类型不兼容时使用的converter
     * @return 转换后字段值
     */
    private Object convertValueOnDisassembling(Object originalValue,
                                               Class<?> targetPropertyType,
                                               Type expectedFieldGenericType,
                                               ClassifiedConverter converter) {
        Object value = stripOptionalValue(originalValue);
        if (value == null) {
            return null;
        }

        if (expectedFieldGenericType instanceof ParameterizedType) {
            return convertGenericTypeField(originalValue, expectedFieldGenericType, true);
        }

        // 非参数化字段的，视为普通字段，其他Type暂不支持
        Class<?> expectedPropertyType = (Class<?>) expectedFieldGenericType;

        if (expectedPropertyType.isInstance(value)) {
            return value;
        }

        if (converter != null) {
            return converter.convert(value, expectedPropertyType);
        }

        Convertible convertible = targetPropertyType.getAnnotation(Convertible.class);
        if (convertible != null) {
            return disassemble(value, expectedPropertyType);
        }

        MappedClass targetMappedClass = targetPropertyType.getAnnotation(MappedClass.class);
        if (targetMappedClass != null) {
            return disassemble(value, targetMappedClass.value());
        }

        RuntimeType runtimeType = targetPropertyType.getAnnotation(RuntimeType.class);
        if (runtimeType != null) {
            Class<?>[] subClasses = runtimeType.value();
            for (Class<?> subClass : subClasses) {
                MappedClass mappedClass = subClass.getAnnotation(MappedClass.class);
                if (mappedClass == null) {
                    throw new IllegalArgumentException("Runtime type subclass should be annotated with @MappedClass");
                }
                if (subClass.isInstance(value)) {
                    return disassemble(value, mappedClass.value());
                }
            }
            return null;
        }
        throw new IllegalArgumentException("Type mismatch and cannot convert: " + value.getClass().getSimpleName()
                + " to " + expectedPropertyType.getSimpleName());
    }

    private Object stripOptionalValue(Object originalValue) {
        if (!(originalValue instanceof Optional)) {
            return originalValue;
        }

        Optional optional = (Optional) originalValue;
        return optional.orNull();
    }

    public <S, T> Converter<S, T> getConverterFor(final Class<S> sourceClass, final Class<T> targetClass) {
        return new Converter<S, T>() {
            @Override
            protected T doForward(S s) {
                return assemble(s, targetClass);
            }

            @Override
            protected S doBackward(T t) {
                return disassemble(t, sourceClass);
            }
        };
    }

    /**
     * 使用ConvertibleBuilder进行装载，返回this与convertibleBuilder的绑定对象
     * 用于链式调用的开头
     *
     * @param convertibleBuilder Builder
     * @param <T>                返回类型
     * @return 绑定对象，用于链式调用
     */
    public <T, BT extends ConvertibleBuilder<T>> AssemblerWithBuilder<T, BT> useBuilder(BT convertibleBuilder) {
        return new AssemblerWithBuilder<>(this, convertibleBuilder);
    }
}
