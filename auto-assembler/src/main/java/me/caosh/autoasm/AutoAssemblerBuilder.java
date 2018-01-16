package me.caosh.autoasm;

import com.google.common.base.Converter;
import me.caosh.autoasm.converter.ClassifiedConverter;
import me.caosh.autoasm.converter.ConverterMapping;
import me.caosh.autoasm.converter.DefaultConverterMapping;

/**
 * 支持自定义AutoAssembler的Builder
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/14
 */
public class AutoAssemblerBuilder {
    private ConverterMapping converterMapping = new DefaultConverterMapping();

    public <S, T> AutoAssemblerBuilder registerConverter(Class<S> sourceClass, Class<T> targetClass, Converter<S, T> converter) {
        converterMapping.register(sourceClass, targetClass, converter);
        return this;
    }

    public <S, T> AutoAssemblerBuilder registerConverter(Class<S> sourceClass, Class<T> targetClass, ClassifiedConverter<S, T> converter) {
        converterMapping.register(sourceClass, targetClass, converter);
        return this;
    }

    public AutoAssembler build() {
        return new AutoAssembler(converterMapping);
    }
}