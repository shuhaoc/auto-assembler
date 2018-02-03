package me.caosh.autoasm;

/**
 * 适用于AutoAssembler的Builder接口
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public interface ConvertibleBuilder<T> {
    /**
     * 构建对象，由AutoAssembler调用
     *
     * @return 对象实例
     */
    T build();
}
