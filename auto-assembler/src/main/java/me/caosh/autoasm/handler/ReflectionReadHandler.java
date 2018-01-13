package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.util.PropertyUtils;

/**
 * 使用getter方法（如果存在）读取字段值
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ReflectionReadHandler implements ReadHandler {
    @Override
    public Object read(FieldMapping fieldMapping, Object object, String propertyName) {
        return PropertyUtils.getPropertySoftly(object, propertyName);
    }
}
