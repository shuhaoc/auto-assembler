package me.caosh.autoasm.handler;

import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.util.PropertyFindResult;

import java.beans.PropertyDescriptor;

/**
 * 属性finder，根据{@link FieldMapping}和属性名在对象中查找{@link PropertyDescriptor}
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public interface PropertyFinder {
    /**
     * 根据{@link FieldMapping}和属性名在对象中查找{@link PropertyDescriptor}
     *
     * @param object 被查找对象
     * @param propertyName 属性名
     * @param fieldMapping 字段映射配置
     * @return 要写入对象的
     */
    PropertyFindResult findPropertyDescriptor(Object object, String propertyName, FieldMapping fieldMapping);
}
