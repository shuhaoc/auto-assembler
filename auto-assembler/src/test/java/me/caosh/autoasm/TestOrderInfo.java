package me.caosh.autoasm;

import com.google.common.base.MoreObjects;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/12
 */
public class TestOrderInfo {
    private TestSecurityInfo securityInfo;

    public TestSecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(TestSecurityInfo securityInfo) {
        this.securityInfo = securityInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestOrderInfo that = (TestOrderInfo) o;

        return securityInfo != null ? securityInfo.equals(that.securityInfo) : that.securityInfo == null;
    }

    @Override
    public int hashCode() {
        return securityInfo != null ? securityInfo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestOrderInfo.class).omitNullValues()
                .add("securityInfo", securityInfo)
                .toString();
    }
}
