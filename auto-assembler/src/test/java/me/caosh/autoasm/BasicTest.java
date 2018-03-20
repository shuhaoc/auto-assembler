package me.caosh.autoasm;

import com.google.common.base.Converter;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import org.joda.time.MonthDay;
import org.joda.time.YearMonth;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class BasicTest {
    private final AutoAssembler autoAssembler = AutoAssemblers.getDefault();

    @Test
    public void testBasic() throws Exception {
        TestBasicObject testBasicObject = new TestBasicObject();
        testBasicObject.setId(12);
        testBasicObject.setName("ccc");
        testBasicObject.setSetterOnly("domain has setter only");
        // transient value, DTO does not have
        testBasicObject.setTransientValue(233);
        // general field no converted
        testBasicObject.setYearMonth(YearMonth.now());

        TestDTO testDTO = autoAssembler.assemble(testBasicObject, TestDTO.class);

        assertEquals(testDTO.getId(), testBasicObject.getId());
        assertEquals(testDTO.getName(), testBasicObject.getName());
        assertNull(testDTO.getSetterOnly());
        assertNull(testDTO.getNullIgnored());
        assertEquals(testDTO.getYearMonth(), testBasicObject.getYearMonth());
        assertNull(testDTO.getOptionalString());
    }

    @Test
    public void testBasicDisassemble() throws Exception {
        TestBasicObject testBasicObject = new TestBasicObject();
        testBasicObject.setId(12);
        testBasicObject.setName("ccc");
        testBasicObject.setYearMonth(YearMonth.now());

        TestDTO testDTO = autoAssembler.assemble(testBasicObject, TestDTO.class);
        TestBasicObject disassembled = autoAssembler.disassemble(testDTO, TestBasicObject.class);
        assertEquals(disassembled, testBasicObject);
    }

    @Test
    public void testGetConverterFor() throws Exception {
        Converter<TestBasicObject, TestDTO> converter = autoAssembler.getConverterFor(TestBasicObject.class, TestDTO.class);

        TestBasicObject testBasicObject = new TestBasicObject();
        testBasicObject.setId(12);
        testBasicObject.setName("ccc");
        testBasicObject.setYearMonth(YearMonth.now());

        TestDTO testDTO = converter.convert(testBasicObject);
        TestBasicObject convertBack = converter.reverse().convert(testDTO);
        assertEquals(convertBack, testBasicObject);
    }

    @Test
    public void testConst() throws Exception {
        TestBasicObject domainObject = new TestBasicObject();
        // no use
        domainObject.setConstInt(341);

        TestConstDTO testConstDTO = autoAssembler.assemble(domainObject, TestConstDTO.class);

        assertEquals(testConstDTO.getConstString(), "abc");
        assertEquals(testConstDTO.getConstInt(), Integer.valueOf(342));

        TestBasicObject disassembled = autoAssembler.disassemble(testConstDTO, TestBasicObject.class);
        assertEquals(disassembled.getConstInt(), testConstDTO.getConstInt());
    }

    @Test
    public void testDefaultValue() throws Exception {
        TestDefaultValueObject testDefaultValueObject = new TestDefaultValueObject();

        TestDTO testDTO = autoAssembler.assemble(testDefaultValueObject, TestDTO.class);
        assertNull(testDTO.getDefaultValueField());

        TestDefaultValueObject disassemble = autoAssembler.disassemble(testDTO, TestDefaultValueObject.class);
        assertEquals(disassemble.getDefaultValueField(), Integer.valueOf(123));
    }

    @Test
    public void testOptional() throws Exception {
        TestBasicObject testBasicObject = new TestBasicObject();
        testBasicObject.setOptionalString("aaa");

        TestDTO assemble = autoAssembler.assemble(testBasicObject, TestDTO.class);
        assertNotNull(assemble.getOptionalString());
        assertEquals(assemble.getOptionalString(), testBasicObject.getOptionalString().orNull());

        testBasicObject.setOptionalString(null);
        assemble = autoAssembler.assemble(testBasicObject, TestDTO.class);
        assertNull(assemble.getOptionalString());
    }

    @Test
    public void testBasicUsingBuilder() throws Exception {
        TestBasicObject testBasicObject = new TestBasicObject();
        testBasicObject.setId(12);
        testBasicObject.setName("ccc");

        TestDTO assemble = autoAssembler.assemble(testBasicObject, new TestDTOBuilder()).build();

        assertEquals(assemble.getId(), testBasicObject.getId());
        assertEquals(assemble.getName(), testBasicObject.getName());

        TestDTO assemble2 = autoAssembler.useBuilder(new TestDTOBuilder())
                .assemble(testBasicObject)
                .build();
        assertEquals(assemble2.getId(), assemble.getId());
        assertEquals(assemble2.getName(), assemble.getName());

        TestBasicObject disassembled = autoAssembler.disassemble(assemble, new TestBasicObjectBuilder()).build();
        assertEquals(disassembled, testBasicObject);
    }

    @Test
    public void testSkipped() throws Exception {
        TestBasicObject testBasicObject = new TestBasicObject();
        testBasicObject.setSkippedField(1223);

        TestDTO assemble = autoAssembler.assemble(testBasicObject, TestDTO.class);
        assertNull(assemble.getSkippedField());

        TestDTO testDTO = new TestDTO();
        testDTO.setSkippedField(2333);
        TestBasicObject disassemble = autoAssembler.disassemble(assemble, TestBasicObject.class);
        assertNull(disassemble.getSkippedField());
    }

    @Test
    public void testWriteOnlyReadonly() throws Exception {
        TestBasicObject testBasicObject = new TestBasicObject();

        TestDTO assemble = autoAssembler.assemble(testBasicObject, TestDTO.class);
        assertEquals(assemble.getReadonlyInt(), assemble.getReadonlyInt());
        assertNull(assemble.getWriteOnlyInt());

        TestDTO testDTO = new TestDTO();
        testDTO.setWriteOnlyInt(11);
        testDTO.setReadonlyInt(22);
        // see stdout
        autoAssembler.disassemble(testDTO, TestBasicObject.class);
    }


    @Test
    public void testGetFieldFailed() throws Exception {
        TestBasicObject sourceObject = new TestBasicObject();
        sourceObject.setName("hello");
        TestGetFieldFailedDTO assemble = autoAssembler.assemble(sourceObject, TestGetFieldFailedDTO.class);
        assertEquals(assemble.mockField, sourceObject.getName());
    }

    public static class TestBasicObject {
        private Integer id;
        private String name;
        private String setterOnly;
        private transient Integer transientValue;
        private YearMonth nullIgnored;
        private YearMonth yearMonth;
        private Integer constInt;
        private String optionalString;
        private Integer skippedField;

        public TestBasicObject() {
        }

        public TestBasicObject(Integer id, String name, String setterOnly, Integer transientValue, YearMonth nullIgnored,
                               YearMonth yearMonth, Integer constInt, String optionalString) {
            this.id = id;
            this.name = name;
            this.setterOnly = setterOnly;
            this.transientValue = transientValue;
            this.nullIgnored = nullIgnored;
            this.yearMonth = yearMonth;
            this.constInt = constInt;
            this.optionalString = optionalString;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public YearMonth getNullIgnored() {
            return nullIgnored;
        }

        public YearMonth getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(YearMonth yearMonth) {
            this.yearMonth = yearMonth;
        }

        public Integer getConstInt() {
            return constInt;
        }

        public void setConstInt(Integer constInt) {
            this.constInt = constInt;
        }

        public void setOptionalString(String optionalString) {
            this.optionalString = optionalString;
        }

        public Optional<String> getOptionalString() {
            return Optional.fromNullable(optionalString);
        }

        public Integer getSkippedField() {
            return skippedField;
        }

        public void setSkippedField(Integer skippedField) {
            this.skippedField = skippedField;
        }

        public void setWriteOnlyInt(Integer x) {
            System.out.println("setWriteOnlyInt: " + x);
        }

        public Integer getReadonlyInt() {
            return 333;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestBasicObject that = (TestBasicObject) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (setterOnly != null ? !setterOnly.equals(that.setterOnly) : that.setterOnly != null) return false;
            if (transientValue != null ? !transientValue.equals(that.transientValue) : that.transientValue != null)
                return false;
            if (nullIgnored != null ? !nullIgnored.equals(that.nullIgnored) : that.nullIgnored != null) return false;
            if (yearMonth != null ? !yearMonth.equals(that.yearMonth) : that.yearMonth != null) return false;
            if (constInt != null ? !constInt.equals(that.constInt) : that.constInt != null) return false;
            if (optionalString != null ? !optionalString.equals(that.optionalString) : that.optionalString != null)
                return false;
            return skippedField != null ? skippedField.equals(that.skippedField) : that.skippedField == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (setterOnly != null ? setterOnly.hashCode() : 0);
            result = 31 * result + (transientValue != null ? transientValue.hashCode() : 0);
            result = 31 * result + (nullIgnored != null ? nullIgnored.hashCode() : 0);
            result = 31 * result + (yearMonth != null ? yearMonth.hashCode() : 0);
            result = 31 * result + (constInt != null ? constInt.hashCode() : 0);
            result = 31 * result + (optionalString != null ? optionalString.hashCode() : 0);
            result = 31 * result + (skippedField != null ? skippedField.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestBasicObject.class).omitNullValues()
                    .add("id", id)
                    .add("name", name)
                    .add("setterOnly", setterOnly)
                    .add("transientValue", transientValue)
                    .add("nullIgnored", nullIgnored)
                    .add("yearMonth", yearMonth)
                    .add("constInt", constInt)
                    .add("optionalString", optionalString)
                    .add("skippedField", skippedField)
                    .toString();
        }
    }

    public static class TestDefaultValueObject {
        private Integer defaultValueField;

        public Integer getDefaultValueField() {
            return defaultValueField;
        }

        public void setDefaultValueField(Integer defaultValueField) {
            this.defaultValueField = defaultValueField;
        }
    }

    public static class TestDTO extends BaseDTO {
        private String name;
        private String setterOnly;
        private MonthDay nullIgnored;
        private YearMonth yearMonth;
        @FieldMapping(defaultValue = "123")
        private Integer defaultValueField;
        private String optionalString;
        @SkippedField
        private Integer skippedField;
        private Integer writeOnlyInt;
        private Integer readonlyInt;

        public TestDTO() {
        }

        public TestDTO(Integer id, String name, String setterOnly, MonthDay nullIgnored, YearMonth yearMonth,
                       Integer defaultValueField, String optionalString) {
            setId(id);
            this.name = name;
            this.setterOnly = setterOnly;
            this.nullIgnored = nullIgnored;
            this.yearMonth = yearMonth;
            this.defaultValueField = defaultValueField;
            this.optionalString = optionalString;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSetterOnly() {
            return setterOnly;
        }

        public void setSetterOnly(String setterOnly) {
            this.setterOnly = setterOnly;
        }

        public MonthDay getNullIgnored() {
            return nullIgnored;
        }

        public void setNullIgnored(MonthDay nullIgnored) {
            this.nullIgnored = nullIgnored;
        }

        public YearMonth getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(YearMonth yearMonth) {
            this.yearMonth = yearMonth;
        }

        public Integer getDefaultValueField() {
            return defaultValueField;
        }

        public void setDefaultValueField(Integer defaultValueField) {
            this.defaultValueField = defaultValueField;
        }

        public String getOptionalString() {
            return optionalString;
        }

        public void setOptionalString(String optionalString) {
            this.optionalString = optionalString;
        }

        public Integer getSkippedField() {
            return skippedField;
        }

        public void setSkippedField(Integer skippedField) {
            this.skippedField = skippedField;
        }

        public Integer getWriteOnlyInt() {
            return writeOnlyInt;
        }

        public void setWriteOnlyInt(Integer writeOnlyInt) {
            this.writeOnlyInt = writeOnlyInt;
        }

        public Integer getReadonlyInt() {
            return readonlyInt;
        }

        public void setReadonlyInt(Integer readonlyInt) {
            this.readonlyInt = readonlyInt;
        }
    }

    public static class TestConstDTO {
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
}
