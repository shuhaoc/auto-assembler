package me.caosh.autoasm;


import org.joda.time.YearMonth;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class TestUnsupportedFieldDTO {
    @FieldMapping("2018-01")
    private YearMonth yearMonth;

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }
}
