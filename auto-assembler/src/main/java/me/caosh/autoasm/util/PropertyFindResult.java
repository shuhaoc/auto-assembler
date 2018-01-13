package me.caosh.autoasm.util;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.handler.PropertyFinder;

import java.beans.PropertyDescriptor;

/**
 * {@link PropertyFinder}的查找结果
 *
 * @author shuhaoc@qq.com
 * @date 2018/1/13
 */
public class PropertyFindResult {
    private final Object ownObject;
    private final PropertyDescriptor propertyDescriptor;

    public PropertyFindResult(Object ownObject, PropertyDescriptor propertyDescriptor) {
        this.ownObject = ownObject;
        this.propertyDescriptor = propertyDescriptor;
    }

    public Object getOwnObject() {
        return ownObject;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PropertyFindResult.class)
                .add("ownObject", ownObject)
                .add("propertyDescriptor", propertyDescriptor)
                .toString();
    }
}
