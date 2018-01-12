package me.caosh.autoasm.handler;

import com.google.common.base.Preconditions;
import me.caosh.autoasm.util.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 使用getter/setter方法（如果存在）读取、写入字段值
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class TargetObjectReflectionHandler implements TargetObjectHandler {

    @Override
    public boolean write(PropertyDescriptor propertyDescriptor, Object targetObject, Object value) {
        Method writeMethod = propertyDescriptor.getWriteMethod();
        Preconditions.checkNotNull(writeMethod);

        try {
            writeMethod.invoke(targetObject, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Invoke write method failed: <" + value.getClass().getSimpleName() + "> "
                    + targetObject.getClass().getSimpleName() + "#"
                    + writeMethod.getName() + "(" + value + ")", e);
        }
    }

    @Override
    public Object read(PropertyDescriptor propertyDescriptor, Object targetObject, String propertyName) {
        return PropertyUtils.getPropertySoftly(targetObject, propertyName);
    }
}
