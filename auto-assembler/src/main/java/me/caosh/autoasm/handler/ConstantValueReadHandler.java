package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;

/**
 * 读取{@link FieldMapping#value()}配置的{@link ReadHandler}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ConstantValueReadHandler implements ReadHandler {
    @Override
    public Object read(FieldMapping fieldMapping, Object object, String propertyName) {
        if (!fieldMapping.value().isEmpty()) {
            return fieldMapping.value();
        }
        return null;
    }
}
