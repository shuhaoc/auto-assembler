package me.caosh.autoasm;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/12
 */
public class NestedObjectTest {
    private AutoAssembler autoAssembler = new AutoAssembler();

    @Test
    public void test() throws Exception {
        TestOrderInfo orderInfo = new TestOrderInfo();
        orderInfo.setSecurityInfo(new TestSecurityInfo("600000", "PFYH"));

        TestOrderInfoDTO orderInfoDTO = autoAssembler.assemble(orderInfo, TestOrderInfoDTO.class);
        TestSecurityInfoDTO securityInfo = orderInfoDTO.getSecurityInfo();

        assertNotNull(securityInfo);
        assertEquals(securityInfo.getCode(), orderInfo.getSecurityInfo().getCode());
        assertEquals(securityInfo.getName(), orderInfo.getSecurityInfo().getName());

        TestOrderInfo orderInfo1 = autoAssembler.disassemble(orderInfoDTO, TestOrderInfo.class);
        assertEquals(orderInfo1, orderInfo);
    }
}
