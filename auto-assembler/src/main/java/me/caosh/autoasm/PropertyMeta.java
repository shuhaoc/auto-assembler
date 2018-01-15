package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import java.lang.reflect.Type;

/**
 * 目标对象的字段信息
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/15
 */
public class PropertyMeta {
    private final Type fieldGenericType;
    private final FieldMapping fieldMapping;

    public PropertyMeta(Type fieldGenericType, FieldMapping fieldMapping) {
        this.fieldGenericType = fieldGenericType;
        this.fieldMapping = fieldMapping;
    }

    public Type getFieldGenericType() {
        return fieldGenericType;
    }

    public Optional<FieldMapping> getFieldMapping() {
        return Optional.fromNullable(fieldMapping);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PropertyMeta.class).omitNullValues()
                .add("fieldGenericType", fieldGenericType)
                .add("fieldMapping", fieldMapping)
                .toString();
    }
}
