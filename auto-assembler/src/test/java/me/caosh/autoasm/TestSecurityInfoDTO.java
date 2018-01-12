package me.caosh.autoasm;

import com.google.common.base.MoreObjects;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/12
 */
@Convertible
public class TestSecurityInfoDTO {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestSecurityInfoDTO that = (TestSecurityInfoDTO) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestSecurityInfoDTO.class).omitNullValues()
                .add("code", code)
                .add("name", name)
                .toString();
    }
}
