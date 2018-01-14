package me.caosh.autoasm;

import org.joda.time.MonthDay;
import org.joda.time.YearMonth;

public class TestDTOBuilder implements ConvertibleBuilder<BasicTest.TestDTO> {
    private Integer id;
    private String name;
    private String setterOnly;
    private MonthDay nullIgnored;
    private YearMonth yearMonth;
    private Integer defaultValueField;
    private String optionalString;

    public TestDTOBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TestDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TestDTOBuilder setSetterOnly(String setterOnly) {
        this.setterOnly = setterOnly;
        return this;
    }

    public TestDTOBuilder setNullIgnored(MonthDay nullIgnored) {
        this.nullIgnored = nullIgnored;
        return this;
    }

    public TestDTOBuilder setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public TestDTOBuilder setDefaultValueField(Integer defaultValueField) {
        this.defaultValueField = defaultValueField;
        return this;
    }

    public TestDTOBuilder setOptionalString(String optionalString) {
        this.optionalString = optionalString;
        return this;
    }

    @Override
    public BasicTest.TestDTO build() {
        return new BasicTest.TestDTO(id, name, setterOnly, nullIgnored, yearMonth, defaultValueField, optionalString);
    }
}