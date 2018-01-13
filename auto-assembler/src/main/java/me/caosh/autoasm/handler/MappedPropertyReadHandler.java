package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.util.PropertyUtils;

/**
 * 支持{@link FieldMapping#mappedProperty()}配置的{@link ReadHandler}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class MappedPropertyReadHandler implements ReadHandler {
    @Override
    public Object read(FieldMapping fieldMapping, Object object, String propertyName) {
        String propertyPath;
        String mappedProperty = fieldMapping.mappedProperty();
        if (!mappedProperty.isEmpty()) {
            propertyPath = mappedProperty;
        } else {
            propertyPath = propertyName;
        }
        return PropertyUtils.getPathPropertySoftly(object, propertyPath);
    }
}
