package me.caosh.autoasm.converter;

/**
 * 需要返回类型的converter
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public interface ClassifiedConverter<S, T> {
    /**
     * 将value转换为returnClass实现
     *
     * @param value       入参值
     * @param returnClass 返回类型
     * @return 转换后returnClass实例
     */
    T convert(S value, Class<T> returnClass);

    /**
     * 获取反向converter
     *
     * @return 获取反向converter
     */
    ClassifiedConverter<T, S> reverse();
}
