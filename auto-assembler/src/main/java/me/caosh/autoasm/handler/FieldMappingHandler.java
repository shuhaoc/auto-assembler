package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;

import java.beans.PropertyDescriptor;

/**
 * 使用{@link FieldMapping}的源对象字段值读取handler
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public interface FieldMappingHandler {
    /**
     * 读取源对象字段值
     *
     * @param targetPropertyDescriptor 目标字段的{@link PropertyDescriptor}
     * @param fieldMapping             字段映射注解配置
     * @param sourceObject             源对象
     * @param propertyPath             映射的源对象属性路径
     * @return 返回字段值，可为空
     */
    Object read(PropertyDescriptor targetPropertyDescriptor, FieldMapping fieldMapping, Object sourceObject, String propertyPath);
}
