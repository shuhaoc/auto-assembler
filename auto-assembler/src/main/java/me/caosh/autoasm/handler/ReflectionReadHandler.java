package me.caosh.autoasm.handler;

import me.caosh.autoasm.util.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

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

    @Override
    public boolean write(PropertyDescriptor targetPropertyDescriptor, Object sourceObject, Object value) {
        String propertyName = targetPropertyDescriptor.getName();
        Class<?> sourceClass = sourceObject.getClass();

        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(sourceClass, propertyName);
        if (propertyDescriptor == null) {
            return false;
        }

        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            return false;
        }

        try {
            writeMethod.invoke(sourceObject, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Invoke write method failed: <" + value.getClass().getSimpleName() + "> "
                    + sourceClass.getSimpleName() + "#"
                    + writeMethod.getName() + "(" + value + ")", e);
        }
    }
}
