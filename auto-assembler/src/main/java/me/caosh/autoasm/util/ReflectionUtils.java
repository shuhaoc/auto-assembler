package me.caosh.autoasm.util;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class ReflectionUtils {
    public static <T> T newInstance(Class<T> objectClass) {
        T object;
        try {
            object = objectClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Create object <" + objectClass.getSimpleName()
                    + "> using non-argument-constructor failed", e);
        }
        return object;
    }

    private ReflectionUtils() {
    }

    private static final ReflectionUtils _CODE_COVERAGE = new ReflectionUtils();
}
