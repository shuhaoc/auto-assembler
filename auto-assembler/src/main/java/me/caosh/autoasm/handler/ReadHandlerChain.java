package me.caosh.autoasm.handler;

import com.google.common.collect.ImmutableList;
import me.caosh.autoasm.FieldMapping;

import java.util.List;

/**
 * 依次调用所有handlers直到任意一个handler返回非空
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ReadHandlerChain implements ReadHandler {
    private final List<ReadHandler> readHandlerList;

    public ReadHandlerChain(ReadHandler... handlers) {
        this.readHandlerList = ImmutableList.copyOf(handlers);
    }

    @Override
    public Object read(FieldMapping fieldMapping, Object object, String propertyName) {
        for (ReadHandler readHandler : readHandlerList) {
            Object value = readHandler.read(fieldMapping, object, propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
