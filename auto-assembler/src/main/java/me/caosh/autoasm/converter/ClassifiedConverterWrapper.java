package me.caosh.autoasm.converter;

import com.google.common.base.Converter;

/**
 * 包装{@link Converter}为{@link ClassifiedConverter}的包装类
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class ClassifiedConverterWrapper<S, T> implements ClassifiedConverter<S, T> {
    private final Converter<S, T> converter;

    public ClassifiedConverterWrapper(Converter<S, T> converter) {
        this.converter = converter;
    }

    @Override
    public T convert(S value, Class<T> returnClass) {
        return converter.convert(value);
    }

    @Override
    public ClassifiedConverter<T, S> reverse() {
        return new ClassifiedConverterWrapper<>(converter.reverse());
    }
}
