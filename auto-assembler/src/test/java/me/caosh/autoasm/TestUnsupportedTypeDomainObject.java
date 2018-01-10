package me.caosh.autoasm;

import org.joda.time.YearMonth;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class TestUnsupportedTypeDomainObject {
    private YearMonth f1;

    public TestUnsupportedTypeDomainObject(YearMonth f1) {
        this.f1 = f1;
    }

    public YearMonth getF1() {
        return f1;
    }

    public void setF1(YearMonth f1) {
        this.f1 = f1;
    }
}
