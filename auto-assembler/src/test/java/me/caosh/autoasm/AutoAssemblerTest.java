package me.caosh.autoasm;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.YearMonth;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class AutoAssemblerTest {

    private final AutoAssembler autoAssembler = new AutoAssembler();

    @Test
    public void testBasic() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        // transient value, DTO does not have
        domainObject.setTransientValue(233);

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getId(), domainObject.getId());
        assertEquals(testDTO.getName(), domainObject.getName());
        assertNull(testDTO.getSetterOnly());
        assertNull(testDTO.getNullIgnored());
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Invoke write method failed: <String> TestWriteFailedDTO#setName\\(abc23\\)")
    public void testWriteFailed() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        autoAssembler.assemble(domainObject, TestWriteFailedDTO.class);
    }

    @Test
    public void testConst() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        // no use
        domainObject.setConstInt(341);

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getConstString(), "abc");
        assertEquals(testDTO.getConstInt(), Integer.valueOf(342));
    }

    @Test
    public void testConvert() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        domainObject.setInt2String(1122);
        domainObject.setStr2Int("2233");
        domainObject.setStr2Long("5555566666");
        domainObject.setStr2Float("12.34");
        domainObject.setStr2Double("34.5611111111111111");
        domainObject.setStr2BigDecimal("99.88");

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getInt2String(), "1122");
        assertEquals(testDTO.getStr2Int(), Integer.valueOf(2233));
        assertEquals(testDTO.getStr2Long(), Long.valueOf(5555566666L));
        assertEquals(testDTO.getStr2Float(), 12.34f);
        assertEquals(testDTO.getStr2Double(), 34.5611111111111111d);
        assertEquals(testDTO.getStr2BigDecimal(), new BigDecimal("99.88"));
    }

    @Test
    public void testConvertDate() throws Exception {
        TestDateDomainObject testDateDomainObject = new TestDateDomainObject();
        // year 2018
        // month 1
        testDateDomainObject.setDate2str(new Date(118, 0, 11, 10, 46, 23));
        testDateDomainObject.setLocalDateTime2str(LocalDateTime.parse("2018-01-11T17:49:22"));
        testDateDomainObject.setLocalDate2str(LocalDate.parse("2018-01-11"));
        testDateDomainObject.setLocalTime2str(LocalTime.parse("17:49:22"));
        testDateDomainObject.setStr2Date("2017-11-24 09:12:35");
        testDateDomainObject.setStr2LocalDateTime("2018-01-11 17:49:22");
        testDateDomainObject.setStr2LocalDate("2018-01-11");
        testDateDomainObject.setStr2LocalTime("17:49:22");

        TestDateDTO testDateDTO = autoAssembler.assemble(testDateDomainObject, TestDateDTO.class);
        assertEquals(testDateDTO.getDate2str(), "2018-01-11 10:46:23");
        assertEquals(testDateDTO.getLocalDateTime2str(), "2018-01-11 17:49:22");
        assertEquals(testDateDTO.getLocalDate2str(), "2018-01-11");
        assertEquals(testDateDTO.getLocalTime2str(), "17:49:22");
        assertEquals(testDateDTO.getStr2Date(), new Date(117, 10, 24, 9, 12, 35));
        assertEquals(testDateDTO.getStr2LocalDateTime(), LocalDateTime.parse("2018-01-11T17:49:22"));
        assertEquals(testDateDTO.getStr2LocalDate(), LocalDate.parse("2018-01-11"));
        assertEquals(testDateDTO.getStr2LocalTime(), LocalTime.parse("17:49:22"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Non supported target type: org.joda.time.YearMonth")
    public void testUnsupportedFiledConvert() throws Exception {
        autoAssembler.assemble(new Object(), TestUnsupportedFieldDTO.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Type mismatch and cannot convert: YearMonth to MonthDay")
    public void testUnsupportedTypeConvert() throws Exception {
        autoAssembler.assemble(new TestUnsupportedTypeDomainObject(YearMonth.now()), TestUnsupportedTypeDTO.class);
    }

    @Test
    public void testReadFailed() {
        TestReadFailedDTO dto = autoAssembler.assemble(new TestReadFailedDomainObject(), TestReadFailedDTO.class);
        assertNull(dto.getCorruptedField());
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Create target object <TestNewInstanceFailedDTO> using non-argument-constructor failed")
    public void testNewInstanceFailed() throws Exception {
        autoAssembler.assemble(new Object(), TestNewInstanceFailedDTO.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Get declared field \\(mockField\\) from property declaring class <TestGetFieldFailedDTO> failed")
    public void testGetFieldFailed() throws Exception {
        autoAssembler.assemble(new Object(), TestGetFieldFailedDTO.class);
    }

    @Test
    public void testMapping() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        domainObject.setDomainName("dn123");
        domainObject.setProperties(new FirstProperties("12.22"));

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getDtoName(), domainObject.getDomainName());
        assertEquals(testDTO.getFirstPrice(), "12.22");
        assertNull(testDTO.getWrongPath());
    }
}
