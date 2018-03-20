package me.caosh.autoasm.builder;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * 注解配置无效值，不可实例化
 *
 * @author shuhaoc@qq.com
 * @date 2018/2/4
 */
public class NotConfiguredConvertibleBuilder implements ConvertibleBuilder {
    @Override
    public Object build() {
        throw new IllegalArgumentException("Just non-configured value in annotation");
    }

    private NotConfiguredConvertibleBuilder() {
    }
}
