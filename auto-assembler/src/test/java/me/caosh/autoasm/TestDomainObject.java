package me.caosh.autoasm;

import org.joda.time.YearMonth;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class TestDomainObject {
    private final Integer id;
    private final String name;
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
}
