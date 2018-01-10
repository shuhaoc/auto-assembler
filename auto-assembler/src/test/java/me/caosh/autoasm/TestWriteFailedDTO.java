package me.caosh.autoasm;


/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class TestWriteFailedDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        throw new UnsupportedOperationException("for test");
    }
}
