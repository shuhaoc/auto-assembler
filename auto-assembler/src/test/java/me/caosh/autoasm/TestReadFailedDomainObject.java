package me.caosh.autoasm;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class TestReadFailedDomainObject {
    public String getCorruptedField() {
        throw new IllegalArgumentException("corrupted field");
    }
}
