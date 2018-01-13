package me.caosh.autoasm;

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
        TestDomainObject domainObject = new TestDomainObject();
        domainObject.setInt2String(1122);
        domainObject.setStr2Int("2233");
        domainObject.setStr2Long("5555566666");
        domainObject.setStr2Float("12.34");
        domainObject.setStr2Double("34.56111111111111");
        domainObject.setStr2BigDecimal("99.88");

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getInt2String(), "1122");
        assertEquals(testDTO.getStr2Int(), Integer.valueOf(2233));
        assertEquals(testDTO.getStr2Long(), Long.valueOf(5555566666L));
        assertEquals(testDTO.getStr2Float(), 12.34f);
        assertEquals(testDTO.getStr2Double(), 34.56111111111111d);
        assertEquals(testDTO.getStr2BigDecimal(), new BigDecimal("99.88"));

        TestDomainObject domainObject1 = autoAssembler.disassemble(testDTO, TestDomainObject.class);
        assertEquals(domainObject1, domainObject);
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

        TestDateDomainObject testDateDomainObject1 = autoAssembler.disassemble(testDateDTO, TestDateDomainObject.class);
        assertEquals(testDateDomainObject1, testDateDomainObject);
    }
}
