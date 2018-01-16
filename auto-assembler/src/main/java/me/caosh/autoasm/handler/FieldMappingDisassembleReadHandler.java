package me.caosh.autoasm.handler;

import com.google.common.base.Strings;
import me.caosh.autoasm.FieldMapping;

/**
 * Disassemble操作中（目标对象转换为源对象），支持{@link FieldMapping}配置的{@link ReadHandler}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class FieldMappingDisassembleReadHandler implements ReadHandler {
    @Override
    public Object read(FieldMapping fieldMapping, Object object, String propertyName) {
        if (fieldMapping == null) {
            return null;
        }
        String defaultValue = fieldMapping.defaultValue();
        return Strings.emptyToNull(defaultValue);
    }
}
