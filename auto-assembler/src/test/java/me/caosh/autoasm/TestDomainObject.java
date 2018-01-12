package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import org.joda.time.YearMonth;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class TestDomainObject {
    private final Integer id;
    private final String name;
    private String generalField;
    private String setterOnly;
    private transient Integer transientValue;
    private Integer constInt;
    private Integer int2String;
    private String str2Int;
    private String str2Long;
    private String str2Float;
    private String str2Double;
    private String str2BigDecimal;
    private String domainName;
    private YearMonth nullIgnored;

    private Object properties;

    public TestDomainObject() {
        id = 0;
        name = "";
    }

    public TestDomainObject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGeneralField() {
        return generalField;
    }

    public void setGeneralField(String generalField) {
        this.generalField = generalField;
    }

    public void setSetterOnly(String setterOnly) {
        this.setterOnly = setterOnly;
    }

    public Integer getTransientValue() {
        return transientValue;
    }

    public void setTransientValue(Integer transientValue) {
        this.transientValue = transientValue;
    }

    public Integer getConstInt() {
        return constInt;
    }

    public void setConstInt(Integer constInt) {
        this.constInt = constInt;
    }

//    public String getSetGetDiff() {
//        return "123";
//    }

    public String getStr2Int() {
        return str2Int;
    }

    public void setStr2Int(String str2Int) {
        this.str2Int = str2Int;
    }

    public Integer getInt2String() {
        return int2String;
    }

    public void setInt2String(Integer int2String) {
        this.int2String = int2String;
    }

    public String getStr2Long() {
        return str2Long;
    }

    public void setStr2Long(String str2Long) {
        this.str2Long = str2Long;
    }

    public String getStr2Float() {
        return str2Float;
    }

    public void setStr2Float(String str2Float) {
        this.str2Float = str2Float;
    }

    public String getStr2Double() {
        return str2Double;
    }

    public void setStr2Double(String str2Double) {
        this.str2Double = str2Double;
    }

    public String getStr2BigDecimal() {
        return str2BigDecimal;
    }

    public void setStr2BigDecimal(String str2BigDecimal) {
        this.str2BigDecimal = str2BigDecimal;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public YearMonth getNullIgnored() {
        return nullIgnored;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDomainObject that = (TestDomainObject) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (generalField != null ? !generalField.equals(that.generalField) : that.generalField != null) return false;
        if (setterOnly != null ? !setterOnly.equals(that.setterOnly) : that.setterOnly != null) return false;
        if (transientValue != null ? !transientValue.equals(that.transientValue) : that.transientValue != null)
            return false;
        if (constInt != null ? !constInt.equals(that.constInt) : that.constInt != null) return false;
        if (int2String != null ? !int2String.equals(that.int2String) : that.int2String != null) return false;
        if (str2Int != null ? !str2Int.equals(that.str2Int) : that.str2Int != null) return false;
        if (str2Long != null ? !str2Long.equals(that.str2Long) : that.str2Long != null) return false;
        if (str2Float != null ? !str2Float.equals(that.str2Float) : that.str2Float != null) return false;
        if (str2Double != null ? !str2Double.equals(that.str2Double) : that.str2Double != null) return false;
        if (str2BigDecimal != null ? !str2BigDecimal.equals(that.str2BigDecimal) : that.str2BigDecimal != null)
            return false;
        if (domainName != null ? !domainName.equals(that.domainName) : that.domainName != null) return false;
        if (nullIgnored != null ? !nullIgnored.equals(that.nullIgnored) : that.nullIgnored != null) return false;
        return properties != null ? properties.equals(that.properties) : that.properties == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (generalField != null ? generalField.hashCode() : 0);
        result = 31 * result + (setterOnly != null ? setterOnly.hashCode() : 0);
        result = 31 * result + (transientValue != null ? transientValue.hashCode() : 0);
        result = 31 * result + (constInt != null ? constInt.hashCode() : 0);
        result = 31 * result + (int2String != null ? int2String.hashCode() : 0);
        result = 31 * result + (str2Int != null ? str2Int.hashCode() : 0);
        result = 31 * result + (str2Long != null ? str2Long.hashCode() : 0);
        result = 31 * result + (str2Float != null ? str2Float.hashCode() : 0);
        result = 31 * result + (str2Double != null ? str2Double.hashCode() : 0);
        result = 31 * result + (str2BigDecimal != null ? str2BigDecimal.hashCode() : 0);
        result = 31 * result + (domainName != null ? domainName.hashCode() : 0);
        result = 31 * result + (nullIgnored != null ? nullIgnored.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestDomainObject.class).omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("generalField", generalField)
                .add("setterOnly", setterOnly)
                .add("transientValue", transientValue)
                .add("constInt", constInt)
                .add("int2String", int2String)
                .add("str2Int", str2Int)
                .add("str2Long", str2Long)
                .add("str2Float", str2Float)
                .add("str2Double", str2Double)
                .add("str2BigDecimal", str2BigDecimal)
                .add("domainName", domainName)
                .add("nullIgnored", nullIgnored)
                .add("properties", properties)
                .toString();
    }
}
