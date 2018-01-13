package me.caosh.autoasm.converter;

import com.google.common.base.Converter;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.LinkedList;

/**
 * 源类型、目标类型组合键至{@link com.google.common.base.Converter}的映射
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class ConverterMapping {
    private LinkedList<ConverterItem> converterItems = new LinkedList<>();

    /**
     * 注册源类型与目标类型之间互转的converter
     *
     * @param sourceClass 源类型class
     * @param targetClass 目标类型class
     * @param converter   guava converter
     * @param <S>         源类型参数
     * @param <T>         目标类型参数
     */
    public <S, T> void register(Class<S> sourceClass, Class<T> targetClass, Converter<S, T> converter) {
        ConverterItem<S, T> converterItem = new ConverterItem<>(sourceClass, targetClass, converter);
        converterItems.addFirst(converterItem);

        ConverterItem reversedConverterItem = converterItem.reverse();
        converterItems.addFirst(reversedConverterItem);
    }

    /**
     * 根据源类型、目标类型查找converter，不存在返回null
     *
     * @param sourceClass 源类型class
     * @param targetClass 目标类型class
     * @param <S>         源类型参数
     * @param <T>         目标类型参数
     * @return 源类型转换为目标类型的converter
     */
    public <S, T> Converter<S, T> find(final Class<S> sourceClass, final Class<T> targetClass) {
        Optional<ConverterItem> converter = Iterables.tryFind(converterItems, new Predicate<ConverterItem>() {
                    @Override
                    public boolean apply(ConverterItem converterItem) {
                        return converterItem.getSourceClass().equals(sourceClass)
                                && converterItem.getTargetClass().equals(targetClass);
                    }
                }
        );
        if (converter.isPresent()) {
            return converter.get().getConverter();
        }
        return null;
    }

    private static class ConverterItem<A, B> {
        private final Class<A> sourceClass;
        private final Class<B> targetClass;
        private final Converter<A, B> converter;

        public ConverterItem(Class<A> sourceClass, Class<B> targetClass, Converter<A, B> converter) {
            Preconditions.checkNotNull(sourceClass, "sourceClass cannot be null");
            Preconditions.checkNotNull(targetClass, "targetClass cannot be null");
            Preconditions.checkNotNull(converter, "converter cannot be null");
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
            this.converter = converter;
        }

        public ConverterItem<B, A> reverse() {
            return new ConverterItem<>(targetClass, sourceClass, converter.reverse());
        }

        public Class<A> getSourceClass() {
            return sourceClass;
        }

        public Class<B> getTargetClass() {
            return targetClass;
        }

        public Converter<A, B> getConverter() {
            return converter;
        }
    }
}
