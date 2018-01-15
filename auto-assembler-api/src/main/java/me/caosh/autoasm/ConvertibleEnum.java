package me.caosh.autoasm;

/**
 * 可转换为标量值的枚举
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public interface ConvertibleEnum<T> {
    /**
     * 获取标量值
     *
     * @return 获取标量值
     */
    T getValue();
}
