package me.caosh.autoasm;

/**
 * 将源字段转换为目标类型值的converter
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public interface ClassifiedConverter<S, T> {
    /**
     * 将源字段转换为目标类型值
     *
     * @param source      源字段值，不能为空
     * @param targetClass 目标类型
     * @return 目标值
     */
    T convert(S source, Class<T> targetClass);
}
