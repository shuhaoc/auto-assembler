package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;

/**
 * Assemble操作中（源对象转换为目标对象），支持{@link FieldMapping}配置的{@link ReadHandler}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class FieldMappingAssembleReadHandler implements ReadHandler {
    private final ReadHandler fieldMappingReadHandler;

    public FieldMappingAssembleReadHandler() {
        fieldMappingReadHandler = new ReadHandlerChain(
                new ConstantValueReadHandler(),
                new MappedPropertyReadHandler()
        );
    }

    @Override
    public Object read(FieldMapping fieldMapping, Object object, String propertyName) {
        if (fieldMapping == null) {
            return null;
        }
        return fieldMappingReadHandler.read(fieldMapping, object, propertyName);
    }
}
