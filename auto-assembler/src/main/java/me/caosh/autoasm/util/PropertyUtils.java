package me.caosh.autoasm.util;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class PropertyUtils {
    public static Object getPropertySoftly(Object object, String propertyName) {
        Class<?> srcClass = object.getClass();
        PropertyDescriptor srcPropertyDescriptor = BeanUtils.getPropertyDescriptor(srcClass, propertyName);
        if (srcPropertyDescriptor == null) {
            return null;
        }

        Method readMethod = srcPropertyDescriptor.getReadMethod();
        if (readMethod == null) {
            return null;
        }

        try {
            return readMethod.invoke(object);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private PropertyUtils() {
    }

    private static final PropertyUtils _CODE_COVERAGE = new PropertyUtils();
}
