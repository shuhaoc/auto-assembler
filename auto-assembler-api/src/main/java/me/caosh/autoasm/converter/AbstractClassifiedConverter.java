package me.caosh.autoasm.converter;

/**
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public abstract class AbstractClassifiedConverter<S, T> implements ClassifiedConverter<S, T> {

    public abstract T doForward(S value, Class<T> returnClass);

    public abstract S doBackward(T value, Class<S> returnClass);

    /**
     * this的反向converter，单例化实现equals可比较性，同时节省内存
     */
    private ReversedClassifiedConverter<T, S> reversed;

    @Override
    public final T convert(S value, Class<T> returnClass) {
        if (value == null) {
            return null;
        }
        return doForward(value, returnClass);
    }

    @Override
    public final ClassifiedConverter<T, S> reverse() {
        if (reversed != null) {
            return reversed;
        }
        reversed = new ReversedClassifiedConverter<>(this);
        return reversed;
    }

    public static final class ReversedClassifiedConverter<T, S> implements ClassifiedConverter<T, S> {
        private final AbstractClassifiedConverter<S, T> original;

        public ReversedClassifiedConverter(AbstractClassifiedConverter<S, T> original) {
            this.original = original;
        }

        @Override
        public S convert(T value, Class<S> returnClass) {
            return original.doBackward(value, returnClass);
        }

        @Override
        public ClassifiedConverter<S, T> reverse() {
            return original;
        }
    }
}
