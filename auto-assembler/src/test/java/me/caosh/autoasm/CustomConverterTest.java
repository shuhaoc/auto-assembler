package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.converter.AbstractClassifiedConverter;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class CustomConverterTest {
    private final AutoAssembler autoAssembler = new AutoAssembler();

    @Test
    public void testNull() throws Exception {
        TestCustomConverterObject testCustomConverterObject = new TestCustomConverterObject();

        TestCustomConverterDTO assemble = autoAssembler.assemble(testCustomConverterObject, TestCustomConverterDTO.class);
        assertNull(assemble.getDeviationCtrlFlag());
        assertNull(assemble.getDeviationCtrlLimit());
    }

    @Test
    public void testDisabled() throws Exception {
        TestCustomConverterObject testCustomConverterObject = new TestCustomConverterObject();
        testCustomConverterObject.setDeviationCtrl(new DisabledDeviationCtrl());

        TestCustomConverterDTO assemble = autoAssembler.assemble(testCustomConverterObject, TestCustomConverterDTO.class);
        assertEquals(assemble.getDeviationCtrlFlag(), Integer.valueOf(0));
        assertNull(assemble.getDeviationCtrlLimit());
    }

    @Test
    public void testEnabled() throws Exception {
        TestCustomConverterObject testCustomConverterObject = new TestCustomConverterObject();
        testCustomConverterObject.setDeviationCtrl(new EnabledDeviationCtrl(new BigDecimal("10.5")));

        TestCustomConverterDTO assemble = autoAssembler.assemble(testCustomConverterObject, TestCustomConverterDTO.class);
        assertEquals(assemble.getDeviationCtrlFlag(), Integer.valueOf(1));
        assertEquals(assemble.getDeviationCtrlLimit(), new BigDecimal("10.5"));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testDisassembleUnsupported() throws Exception {
        TestCustomConverterDTO testCustomConverterDTO = new TestCustomConverterDTO();
        testCustomConverterDTO.setDeviationCtrlFlag(0);

        autoAssembler.disassemble(testCustomConverterDTO, TestCustomConverterObject.class);
    }

    public static class TestCustomConverterObject {
        private DeviationCtrl deviationCtrl;

        public DeviationCtrl getDeviationCtrl() {
            return deviationCtrl;
        }

        public void setDeviationCtrl(DeviationCtrl deviationCtrl) {
            this.deviationCtrl = deviationCtrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestCustomConverterObject that = (TestCustomConverterObject) o;

            return deviationCtrl != null ? deviationCtrl.equals(that.deviationCtrl) : that.deviationCtrl == null;
        }

        @Override
        public int hashCode() {
            return deviationCtrl != null ? deviationCtrl.hashCode() : 0;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestCustomConverterObject.class).omitNullValues()
                    .add("deviationCtrl", deviationCtrl)
                    .toString();
        }
    }

    public interface DeviationCtrl {
        boolean isEnabled();
    }

    public static class DisabledDeviationCtrl implements DeviationCtrl {
        @Override
        public boolean isEnabled() {
            return false;
        }
    }

    public static class EnabledDeviationCtrl implements DeviationCtrl {
        private final BigDecimal limit;

        public EnabledDeviationCtrl(BigDecimal limit) {
            this.limit = limit;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        public BigDecimal getLimit() {
            return limit;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(EnabledDeviationCtrl.class).omitNullValues()
                    .add("limit", getLimit())
                    .toString();
        }
    }

    public static class TestCustomConverterDTO {
        @FieldMapping(mappedProperty = "deviationCtrl",
                customConverterClass = DeviationCtrlFlagConverter.class)
        private Integer deviationCtrlFlag;
        @FieldMapping(mappedProperty = "deviationCtrl.limit")
        private BigDecimal deviationCtrlLimit;

        public Integer getDeviationCtrlFlag() {
            return deviationCtrlFlag;
        }

        public void setDeviationCtrlFlag(Integer deviationCtrlFlag) {
            this.deviationCtrlFlag = deviationCtrlFlag;
        }

        public BigDecimal getDeviationCtrlLimit() {
            return deviationCtrlLimit;
        }

        public void setDeviationCtrlLimit(BigDecimal deviationCtrlLimit) {
            this.deviationCtrlLimit = deviationCtrlLimit;
        }
    }

    public static class DeviationCtrlFlagConverter extends AbstractClassifiedConverter<DeviationCtrl, Integer> {
        @Override
        public Integer doForward(DeviationCtrl value, Class<Integer> returnClass) {
            if (value instanceof EnabledDeviationCtrl) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public DeviationCtrl doBackward(Integer value, Class<DeviationCtrl> returnClass) {
            throw new UnsupportedOperationException(returnClass.getSimpleName());
        }
    }
}
