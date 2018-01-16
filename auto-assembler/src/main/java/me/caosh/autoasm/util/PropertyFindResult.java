package me.caosh.autoasm.util;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.handler.PropertyFinder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;

/**
 * {@link PropertyFinder}的查找结果
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/13
 */
public class PropertyFindResult {
    private final Object ownObject;
    private final PropertyDescriptor propertyDescriptor;
    private final Type fieldGenericType;

    public PropertyFindResult(Object ownObject, PropertyDescriptor propertyDescriptor, Type fieldGenericType) {
        this.ownObject = ownObject;
        this.propertyDescriptor = propertyDescriptor;
        this.fieldGenericType = fieldGenericType;
    }

    public Object getOwnObject() {
        return ownObject;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public Type getFieldGenericType() {
        return fieldGenericType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PropertyFindResult.class).omitNullValues()
                .add("ownObject", ownObject)
                .add("propertyDescriptor", propertyDescriptor)
                .add("fieldGenericType", fieldGenericType)
                .toString();
    }
}
