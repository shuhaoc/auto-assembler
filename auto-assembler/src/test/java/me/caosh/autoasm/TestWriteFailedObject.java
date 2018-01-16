package me.caosh.autoasm;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class TestWriteFailedObject {
    private final Integer id;
    private final String name;

    public TestWriteFailedObject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
