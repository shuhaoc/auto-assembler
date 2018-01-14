package me.caosh.autoasm;

import org.joda.time.YearMonth;

public class TestBasicObjectBuilder implements ConvertibleBuilder<BasicTest.TestBasicObject> {
    private Integer id;
    private String name;
    private String setterOnly;
    private Integer transientValue;
    private YearMonth nullIgnored;
    private YearMonth yearMonth;
    private Integer constInt;
    private String optionalString;

    public TestBasicObjectBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TestBasicObjectBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TestBasicObjectBuilder setSetterOnly(String setterOnly) {
        this.setterOnly = setterOnly;
        return this;
    }

    public TestBasicObjectBuilder setTransientValue(Integer transientValue) {
        this.transientValue = transientValue;
        return this;
    }

    public TestBasicObjectBuilder setNullIgnored(YearMonth nullIgnored) {
        this.nullIgnored = nullIgnored;
        return this;
    }

    public TestBasicObjectBuilder setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public TestBasicObjectBuilder setConstInt(Integer constInt) {
        this.constInt = constInt;
        return this;
    }

    public TestBasicObjectBuilder setOptionalString(String optionalString) {
        this.optionalString = optionalString;
        return this;
    }

    @Override
    public BasicTest.TestBasicObject build() {
        return new BasicTest.TestBasicObject(id, name, setterOnly, transientValue, nullIgnored, yearMonth, constInt,
                optionalString);
    }
}