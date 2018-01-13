package me.caosh.autoasm;

import org.joda.time.YearMonth;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNull;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class ExceptionTest {

    private final AutoAssembler autoAssembler = new AutoAssembler();

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Invoke write method failed: <String> TestWriteFailedDTO#setName\\(abc23\\)")
    public void testWriteFailed() throws Exception {
        TestWriteFailedObject domainObject = new TestWriteFailedObject(12, "abc23");
        autoAssembler.assemble(domainObject, TestWriteFailedDTO.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Type mismatch and cannot convert: String to YearMonth")
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
            expectedExceptionsMessageRegExp = "Create object <TestNewInstanceFailedDTO> using non-argument-constructor failed")
    public void testNewInstanceFailed() throws Exception {
        autoAssembler.assemble(new Object(), TestNewInstanceFailedDTO.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Create object <TestNewInstanceFailedDomainObject> using non-argument-constructor failed")
    public void testNewInstanceDomainFailed() throws Exception {
        autoAssembler.disassemble(new Object(), TestNewInstanceFailedDomainObject.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Get declared field \\(mockField\\) from property declaring class <TestGetFieldFailedDTO> failed")
    public void testGetFieldFailed() throws Exception {
        autoAssembler.assemble(new Object(), TestGetFieldFailedDTO.class);
    }
}
