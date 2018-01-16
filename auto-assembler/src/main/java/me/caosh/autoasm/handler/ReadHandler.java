package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;

/**
 * 对象字段值读取handler
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public interface ReadHandler {
    /**
     * 读取对象字段值
     *
     * @param fieldMapping 字段映射配置
     * @param object 对象
     * @param propertyName 属性名
     * @return 返回字段值，可为空
     */
    Object read(FieldMapping fieldMapping, Object object, String propertyName);
}
