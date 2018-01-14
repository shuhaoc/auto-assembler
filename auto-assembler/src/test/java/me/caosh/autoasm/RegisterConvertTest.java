package me.caosh.autoasm;

import com.google.common.base.Converter;
import com.google.common.base.MoreObjects;
import org.joda.time.YearMonth;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class RegisterConvertTest {
    private AutoAssembler autoAssembler;

    public RegisterConvertTest() {
        this.autoAssembler = new AutoAssemblerBuilder()
                .registerConverter(String.class, YearMonth.class, new Converter<String, YearMonth>() {
                    @Override
                    protected YearMonth doForward(String s) {
                        return YearMonth.parse(s);
                    }

                    @Override
                    protected String doBackward(YearMonth yearMonth) {
                        return yearMonth.toString();
                    }
                })
                .build();
    }

    @Test
    public void test() throws Exception {
        TestRegisterConvertObject testRegisterConvertObject = new TestRegisterConvertObject();
        testRegisterConvertObject.setYearMonth(YearMonth.parse("2018-01"));

        TestRegisterConvertDTO assemble = autoAssembler.assemble(testRegisterConvertObject, TestRegisterConvertDTO.class);

        assertEquals(assemble.getYearMonth(), "2018-01");

        TestRegisterConvertObject disassemble = autoAssembler.disassemble(assemble, TestRegisterConvertObject.class);
        assertEquals(disassemble, testRegisterConvertObject);
    }

    public static class TestRegisterConvertObject {
        private YearMonth yearMonth;

        public YearMonth getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(YearMonth yearMonth) {
            this.yearMonth = yearMonth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestRegisterConvertObject that = (TestRegisterConvertObject) o;

            return yearMonth != null ? yearMonth.equals(that.yearMonth) : that.yearMonth == null;
        }

        @Override
        public int hashCode() {
            return yearMonth != null ? yearMonth.hashCode() : 0;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestRegisterConvertObject.class).omitNullValues()
                    .add("yearMonth", yearMonth)
                    .toString();
        }
    }

    public static class TestRegisterConvertDTO {
        private String yearMonth;

        public String getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(String yearMonth) {
            this.yearMonth = yearMonth;
        }
    }
}
