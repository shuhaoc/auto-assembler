package me.caosh.autoasm;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class MappingTest {
    private final AutoAssembler autoAssembler = new AutoAssembler();

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
