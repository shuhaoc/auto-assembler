package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class ConvertTest {
    private final AutoAssembler autoAssembler = new AutoAssembler();

    @Test
    public void testConvert() throws Exception {
        TestConvertObject testConvertObject = new TestConvertObject();
        testConvertObject.setInt2String(1122);
        testConvertObject.setStr2Int("2233");
        testConvertObject.setStr2Long("5555566666");
        testConvertObject.setStr2Float("12.34");
        testConvertObject.setStr2Double("34.56111111111111");
        testConvertObject.setStr2BigDecimal("99.88");
        testConvertObject.setTestEnum2Int(TestEnum.B);
        testConvertObject.setInt2TestEnum(1);

        TestConvertDTO testConvertDTO = autoAssembler.assemble(testConvertObject, TestConvertDTO.class);

        assertEquals(testConvertDTO.getInt2String(), "1122");
        assertEquals(testConvertDTO.getStr2Int(), Integer.valueOf(2233));
        assertEquals(testConvertDTO.getStr2Long(), Long.valueOf(5555566666L));
        assertEquals(testConvertDTO.getStr2Float(), 12.34f);
        assertEquals(testConvertDTO.getStr2Double(), 34.56111111111111d);
        assertEquals(testConvertDTO.getStr2BigDecimal(), new BigDecimal("99.88"));
        assertEquals(testConvertDTO.getTestEnum2Int(), Integer.valueOf(1));
        assertEquals(testConvertDTO.getInt2TestEnum(), TestEnum.B);

        TestConvertObject disassembled = autoAssembler.disassemble(testConvertDTO, TestConvertObject.class);
        assertEquals(disassembled, testConvertObject);
    }

    @Test
    public void testConvertDate() throws Exception {
        TestDateObject testDateObject = new TestDateObject();
        // year 2018
        // month 1
        testDateObject.setDate2str(new Date(118, 0, 11, 10, 46, 23));
        testDateObject.setLocalDateTime2str(LocalDateTime.parse("2018-01-11T17:49:22"));
        testDateObject.setLocalDate2str(LocalDate.parse("2018-01-11"));
        testDateObject.setLocalTime2str(LocalTime.parse("17:49:22"));
        testDateObject.setStr2Date("2017-11-24 09:12:35");
        testDateObject.setStr2LocalDateTime("2018-01-11 17:49:22");
        testDateObject.setStr2LocalDate("2018-01-11");
        testDateObject.setStr2LocalTime("17:49:22");

        TestDateDTO testDateDTO = autoAssembler.assemble(testDateObject, TestDateDTO.class);
        assertEquals(testDateDTO.getDate2str(), "2018-01-11 10:46:23");
        assertEquals(testDateDTO.getLocalDateTime2str(), "2018-01-11 17:49:22");
        assertEquals(testDateDTO.getLocalDate2str(), "2018-01-11");
        assertEquals(testDateDTO.getLocalTime2str(), "17:49:22");
        assertEquals(testDateDTO.getStr2Date(), new Date(117, 10, 24, 9, 12, 35));
        assertEquals(testDateDTO.getStr2LocalDateTime(), LocalDateTime.parse("2018-01-11T17:49:22"));
        assertEquals(testDateDTO.getStr2LocalDate(), LocalDate.parse("2018-01-11"));
        assertEquals(testDateDTO.getStr2LocalTime(), LocalTime.parse("17:49:22"));

        TestDateObject disassembled = autoAssembler.disassemble(testDateDTO, TestDateObject.class);
        assertEquals(disassembled, testDateObject);
    }

    public static class TestConvertObject {
        private Integer int2String;
        private String str2Int;
        private String str2Long;
        private String str2Float;
        private String str2Double;
        private String str2BigDecimal;
        private TestEnum testEnum2Int;
        private Integer int2TestEnum;

        public Integer getInt2String() {
            return int2String;
        }

        public void setInt2String(Integer int2String) {
            this.int2String = int2String;
        }

        public String getStr2Int() {
            return str2Int;
        }

        public void setStr2Int(String str2Int) {
            this.str2Int = str2Int;
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

        public TestEnum getTestEnum2Int() {
            return testEnum2Int;
        }

        public void setTestEnum2Int(TestEnum testEnum2Int) {
            this.testEnum2Int = testEnum2Int;
        }

        public Integer getInt2TestEnum() {
            return int2TestEnum;
        }

        public void setInt2TestEnum(Integer int2TestEnum) {
            this.int2TestEnum = int2TestEnum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestConvertObject that = (TestConvertObject) o;

            if (int2String != null ? !int2String.equals(that.int2String) : that.int2String != null) return false;
            if (str2Int != null ? !str2Int.equals(that.str2Int) : that.str2Int != null) return false;
            if (str2Long != null ? !str2Long.equals(that.str2Long) : that.str2Long != null) return false;
            if (str2Float != null ? !str2Float.equals(that.str2Float) : that.str2Float != null) return false;
            if (str2Double != null ? !str2Double.equals(that.str2Double) : that.str2Double != null) return false;
            if (str2BigDecimal != null ? !str2BigDecimal.equals(that.str2BigDecimal) : that.str2BigDecimal != null)
                return false;
            if (testEnum2Int != that.testEnum2Int) return false;
            return int2TestEnum != null ? int2TestEnum.equals(that.int2TestEnum) : that.int2TestEnum == null;
        }

        @Override
        public int hashCode() {
            int result = int2String != null ? int2String.hashCode() : 0;
            result = 31 * result + (str2Int != null ? str2Int.hashCode() : 0);
            result = 31 * result + (str2Long != null ? str2Long.hashCode() : 0);
            result = 31 * result + (str2Float != null ? str2Float.hashCode() : 0);
            result = 31 * result + (str2Double != null ? str2Double.hashCode() : 0);
            result = 31 * result + (str2BigDecimal != null ? str2BigDecimal.hashCode() : 0);
            result = 31 * result + (testEnum2Int != null ? testEnum2Int.hashCode() : 0);
            result = 31 * result + (int2TestEnum != null ? int2TestEnum.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestConvertObject.class).omitNullValues()
                    .add("int2String", int2String)
                    .add("str2Int", str2Int)
                    .add("str2Long", str2Long)
                    .add("str2Float", str2Float)
                    .add("str2Double", str2Double)
                    .add("str2BigDecimal", str2BigDecimal)
                    .add("testEnum2Int", testEnum2Int)
                    .add("int2TestEnum", int2TestEnum)
                    .toString();
        }
    }

    public static class TestConvertDTO extends BaseDTO {
        private String int2String;
        private Integer str2Int;
        private Long str2Long;
        private Float str2Float;
        private Double str2Double;
        private BigDecimal str2BigDecimal;
        private Integer testEnum2Int;
        private TestEnum int2TestEnum;

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

        public Integer getTestEnum2Int() {
            return testEnum2Int;
        }

        public void setTestEnum2Int(Integer testEnum2Int) {
            this.testEnum2Int = testEnum2Int;
        }

        public TestEnum getInt2TestEnum() {
            return int2TestEnum;
        }

        public void setInt2TestEnum(TestEnum int2TestEnum) {
            this.int2TestEnum = int2TestEnum;
        }
    }

    public static class TestDateObject {
        private Date date2str;
        private LocalDateTime localDateTime2str;
        private LocalDate localDate2str;
        private LocalTime localTime2str;
        private String str2Date;
        private String str2LocalDateTime;
        private String str2LocalDate;
        private String str2LocalTime;

        public Date getDate2str() {
            return date2str;
        }

        public void setDate2str(Date date2str) {
            this.date2str = date2str;
        }

        public LocalDateTime getLocalDateTime2str() {
            return localDateTime2str;
        }

        public void setLocalDateTime2str(LocalDateTime localDateTime2str) {
            this.localDateTime2str = localDateTime2str;
        }

        public LocalDate getLocalDate2str() {
            return localDate2str;
        }

        public void setLocalDate2str(LocalDate localDate2str) {
            this.localDate2str = localDate2str;
        }

        public LocalTime getLocalTime2str() {
            return localTime2str;
        }

        public void setLocalTime2str(LocalTime localTime2str) {
            this.localTime2str = localTime2str;
        }

        public String getStr2Date() {
            return str2Date;
        }

        public void setStr2Date(String str2Date) {
            this.str2Date = str2Date;
        }

        public String getStr2LocalDateTime() {
            return str2LocalDateTime;
        }

        public void setStr2LocalDateTime(String str2LocalDateTime) {
            this.str2LocalDateTime = str2LocalDateTime;
        }

        public String getStr2LocalDate() {
            return str2LocalDate;
        }

        public void setStr2LocalDate(String str2LocalDate) {
            this.str2LocalDate = str2LocalDate;
        }

        public String getStr2LocalTime() {
            return str2LocalTime;
        }

        public void setStr2LocalTime(String str2LocalTime) {
            this.str2LocalTime = str2LocalTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestDateObject that = (TestDateObject) o;

            if (date2str != null ? !date2str.equals(that.date2str) : that.date2str != null) return false;
            if (localDateTime2str != null ? !localDateTime2str.equals(that.localDateTime2str) : that.localDateTime2str != null)
                return false;
            if (localDate2str != null ? !localDate2str.equals(that.localDate2str) : that.localDate2str != null)
                return false;
            if (localTime2str != null ? !localTime2str.equals(that.localTime2str) : that.localTime2str != null)
                return false;
            if (str2Date != null ? !str2Date.equals(that.str2Date) : that.str2Date != null) return false;
            if (str2LocalDateTime != null ? !str2LocalDateTime.equals(that.str2LocalDateTime) : that.str2LocalDateTime != null)
                return false;
            if (str2LocalDate != null ? !str2LocalDate.equals(that.str2LocalDate) : that.str2LocalDate != null)
                return false;
            return str2LocalTime != null ? str2LocalTime.equals(that.str2LocalTime) : that.str2LocalTime == null;
        }

        @Override
        public int hashCode() {
            int result = date2str != null ? date2str.hashCode() : 0;
            result = 31 * result + (localDateTime2str != null ? localDateTime2str.hashCode() : 0);
            result = 31 * result + (localDate2str != null ? localDate2str.hashCode() : 0);
            result = 31 * result + (localTime2str != null ? localTime2str.hashCode() : 0);
            result = 31 * result + (str2Date != null ? str2Date.hashCode() : 0);
            result = 31 * result + (str2LocalDateTime != null ? str2LocalDateTime.hashCode() : 0);
            result = 31 * result + (str2LocalDate != null ? str2LocalDate.hashCode() : 0);
            result = 31 * result + (str2LocalTime != null ? str2LocalTime.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestDateObject.class).omitNullValues()
                    .add("date2str", date2str)
                    .add("localDateTime2str", localDateTime2str)
                    .add("localDate2str", localDate2str)
                    .add("localTime2str", localTime2str)
                    .add("str2Date", str2Date)
                    .add("str2LocalDateTime", str2LocalDateTime)
                    .add("str2LocalDate", str2LocalDate)
                    .add("str2LocalTime", str2LocalTime)
                    .toString();
        }
    }

    public static class TestDateDTO {
        private String date2str;
        private String localDateTime2str;
        private String localDate2str;
        private String localTime2str;
        private Date str2Date;
        private LocalDateTime str2LocalDateTime;
        private LocalDate str2LocalDate;
        private LocalTime str2LocalTime;

        public String getDate2str() {
            return date2str;
        }

        public void setDate2str(String date2str) {
            this.date2str = date2str;
        }

        public String getLocalDateTime2str() {
            return localDateTime2str;
        }

        public void setLocalDateTime2str(String localDateTime2str) {
            this.localDateTime2str = localDateTime2str;
        }

        public String getLocalDate2str() {
            return localDate2str;
        }

        public void setLocalDate2str(String localDate2str) {
            this.localDate2str = localDate2str;
        }

        public String getLocalTime2str() {
            return localTime2str;
        }

        public void setLocalTime2str(String localTime2str) {
            this.localTime2str = localTime2str;
        }

        public Date getStr2Date() {
            return str2Date;
        }

        public void setStr2Date(Date str2Date) {
            this.str2Date = str2Date;
        }

        public LocalDateTime getStr2LocalDateTime() {
            return str2LocalDateTime;
        }

        public void setStr2LocalDateTime(LocalDateTime str2LocalDateTime) {
            this.str2LocalDateTime = str2LocalDateTime;
        }

        public LocalDate getStr2LocalDate() {
            return str2LocalDate;
        }

        public void setStr2LocalDate(LocalDate str2LocalDate) {
            this.str2LocalDate = str2LocalDate;
        }

        public LocalTime getStr2LocalTime() {
            return str2LocalTime;
        }

        public void setStr2LocalTime(LocalTime str2LocalTime) {
            this.str2LocalTime = str2LocalTime;
        }
    }

    public enum TestEnum implements ConvertibleEnum<Integer> {
        A(0),
        B(1),
        C(2);

        TestEnum(int value) {
            this.value = value;
        }

        private final int value;

        @Override
        public Integer getValue() {
            return value;
        }
    }
}
