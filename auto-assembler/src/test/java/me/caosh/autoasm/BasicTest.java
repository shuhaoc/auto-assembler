package me.caosh.autoasm;

import org.joda.time.YearMonth;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/10
 */
public class BasicTest {
    private final AutoAssembler autoAssembler = new AutoAssembler();

    @Test
    public void testBasic() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        // transient value, DTO does not have
        domainObject.setTransientValue(233);
        // general field no converted
        domainObject.setYearMonth(YearMonth.now());

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getId(), domainObject.getId());
        assertEquals(testDTO.getName(), domainObject.getName());
        assertEquals(testDTO.getYearMonth(), domainObject.getYearMonth());
        assertNull(testDTO.getSetterOnly());
        assertNull(testDTO.getNullIgnored());
    }

    @Test
    public void testBasicDisassemble() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(0, "");
        domainObject.setGeneralField("abc333");

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getGeneralField(), domainObject.getGeneralField());

        TestDomainObject disassembled = autoAssembler.disassemble(testDTO, TestDomainObject.class);
        assertEquals(disassembled, domainObject);
    }

    @Test
    public void testConst() throws Exception {
        TestDomainObject domainObject = new TestDomainObject(12, "abc23");
        // no use
        domainObject.setConstInt(341);

        TestConstDTO testConstDTO = autoAssembler.assemble(domainObject, TestConstDTO.class);

        assertEquals(testConstDTO.getConstString(), "abc");
        assertEquals(testConstDTO.getConstInt(), Integer.valueOf(342));

        TestDomainObject disassembled = autoAssembler.disassemble(testConstDTO, TestDomainObject.class);
        assertEquals(disassembled.getConstInt(), testConstDTO.getConstInt());
    }
}
