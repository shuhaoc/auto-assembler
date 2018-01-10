package me.caosh.autoasm.handler;

import me.caosh.autoasm.util.PropertyUtils;

import java.beans.PropertyDescriptor;

/**
 * 使用getter方法读取字段值
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ReflectionReadHandler implements SourceObjectReadHandler {
    @Override
    public Object read(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, String propertyName) {
        return PropertyUtils.getPropertySoftly(sourceObject, propertyName);
    }
}
