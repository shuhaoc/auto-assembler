package me.caosh.autoasm;

import org.joda.time.MonthDay;

import java.math.BigDecimal;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class TestDTO extends BaseDTO {
    private String name;
    private String generalField;
    private String setterOnly;
    private String setGetDiff;
    @FieldMapping("abc")
    private String constString;
    @FieldMapping("342")
    private Integer constInt;
    private String int2String;
    private Integer str2Int;
    private Long str2Long;
    private Float str2Float;
    private Double str2Double;
    private BigDecimal str2BigDecimal;
    @FieldMapping(mappedProperty = "domainName")
    private String dtoName;
    private MonthDay nullIgnored;
    @FieldMapping(mappedProperty = "properties.price")
    private String firstPrice;
    @FieldMapping(mappedProperty = "wrong.path")
    private String wrongPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneralField() {
        return generalField;
    }

    public void setGeneralField(String generalField) {
        this.generalField = generalField;
    }

    public String getSetGetDiff() {
        return setGetDiff;
    }

    public String getSetterOnly() {
        return setterOnly;
    }

    public void setSetterOnly(String setterOnly) {
        this.setterOnly = setterOnly;
    }

    public void setSetGetDiff(Integer setGetDiff) {
        this.setGetDiff = String.valueOf(setGetDiff);
    }

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

    public String getInt2String() {
        return int2String;
    }

    public void setInt2String(String int2String) {
        this.int2String = int2String;
    }

    public Integer getStr2Int() {
        return str2Int;
    }

    public void setStr2Int(Integer str2Int) {
        this.str2Int = str2Int;
    }

    public Long getStr2Long() {
        return str2Long;
    }

    public void setStr2Long(Long str2Long) {
        this.str2Long = str2Long;
    }

    public Float getStr2Float() {
        return str2Float;
    }

    public void setStr2Float(Float str2Float) {
        this.str2Float = str2Float;
    }

    public Double getStr2Double() {
        return str2Double;
    }

    public void setStr2Double(Double str2Double) {
        this.str2Double = str2Double;
    }

    public BigDecimal getStr2BigDecimal() {
        return str2BigDecimal;
    }

    public void setStr2BigDecimal(BigDecimal str2BigDecimal) {
        this.str2BigDecimal = str2BigDecimal;
    }

    public String getDtoName() {
        return dtoName;
    }

    public void setDtoName(String dtoName) {
        this.dtoName = dtoName;
    }

    public MonthDay getNullIgnored() {
        return nullIgnored;
    }

    public void setNullIgnored(MonthDay nullIgnored) {
        this.nullIgnored = nullIgnored;
    }

    public String getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getWrongPath() {
        return wrongPath;
    }

    public void setWrongPath(String wrongPath) {
        this.wrongPath = wrongPath;
    }
}
