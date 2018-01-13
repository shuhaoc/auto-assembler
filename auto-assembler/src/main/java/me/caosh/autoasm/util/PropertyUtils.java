package me.caosh.autoasm.util;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class PropertyUtils {
    public static Object getPropertySoftly(Object object, String propertyName) {
        Class<?> objectClass = object.getClass();
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(objectClass, propertyName);
        if (propertyDescriptor == null) {
            return null;
        }

        return getProperty(propertyDescriptor, object);
    }

    public static Object getProperty(PropertyDescriptor propertyDescriptor, Object object) {
        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            return null;
        }

        try {
            return readMethod.invoke(object);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Invoke read method failed: "
                    + object.getClass().getSimpleName() + "#" + readMethod.getName(), e);
        }
    }

    public static Object getPathPropertySoftly(Object object, String propertyPath) {
        Object nextProperty = null;
        Scanner scanner = new Scanner(propertyPath);
        scanner.useDelimiter("\\.");
        while (scanner.hasNext()) {
            String partialPropertyName = scanner.next();
            Object currentProperty = nextProperty == null ? object : nextProperty;
            nextProperty = getPropertySoftly(currentProperty, partialPropertyName);
            if (nextProperty == null) {
                return null;
            }
        }
        return nextProperty;
    }

    public static boolean setPropertySoftly(Object object, String propertyName, Object value) {
        Class<?> objectClass = object.getClass();
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(objectClass, propertyName);
        if (propertyDescriptor == null) {
            return false;
        }

        return setProperty(propertyDescriptor, object, value);
    }

    public static boolean setProperty(PropertyDescriptor propertyDescriptor, Object object, Object value) {
        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            return false;
        }

        try {
            writeMethod.invoke(object, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Invoke write method failed: <" + value.getClass().getSimpleName() + "> "
                    + object.getClass().getSimpleName() + "#"
                    + writeMethod.getName() + "(" + value + ")", e);
        }
    }

    private PropertyUtils() {
    }

    private static final PropertyUtils _CODE_COVERAGE = new PropertyUtils();
}
