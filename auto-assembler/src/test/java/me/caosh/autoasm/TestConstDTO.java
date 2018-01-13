package me.caosh.autoasm;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class TestConstDTO {
    @FieldMapping("abc")
    private String constString;
    @FieldMapping("342")
    private Integer constInt;

    public String getConstString() {
        return constString;
    }

    public void setConstString(String constString) {
        this.constString = constString;
    }

    public Integer getConstInt() {
        return constInt;
    }

    public void setConstInt(Integer constInt) {
        this.constInt = constInt;
    }
}
