package me.caosh.autoasm.converter;

/**
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class NotConfiguredClassifiedConverter implements ClassifiedConverter {
    @Override
    public Object convert(Object value, Class returnClass) {
        throw new IllegalArgumentException("Just non-configured value in annotation");
    }

    @Override
    public ClassifiedConverter reverse() {
        return this;
    }
}
