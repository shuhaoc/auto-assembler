package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import org.testng.annotations.Test;

import java.util.Objects;

import static org.testng.Assert.*;

/**
 * @author shuhaoc@qq.com
 * @date 2018/1/13
 */
public class RuntimeTypeTest {
    private AutoAssembler autoAssembler = AutoAssemblers.getDefault();

    @Test
    public void test() throws Exception {
        TestConditionOrder testConditionOrder = new TestConditionOrder();
        FirstExternalProperties externalProperties = new FirstExternalProperties();
        externalProperties.setX(123);
        testConditionOrder.setExternalProperties(externalProperties);

        TestConditionOrderDTO testConditionOrderDTO = autoAssembler.assemble(testConditionOrder,
                                                                             TestConditionOrderDTO.class);
        assertEquals(((FirstExternalPropertiesDTO) testConditionOrderDTO.getExternalProperties()).getX(),
                     ((FirstExternalProperties) testConditionOrder.getExternalProperties()).getX());

        TestConditionOrder disassemble = autoAssembler.disassemble(testConditionOrderDTO, TestConditionOrder.class);
        assertEquals(((FirstExternalProperties) disassemble.getExternalProperties()).getX(),
                     ((FirstExternalPropertiesDTO) testConditionOrderDTO.getExternalProperties()).getX());
    }

    @Test
    public void testSameTypeUsingInterface() throws Exception {
        TestConditionOrder testConditionOrder = new TestConditionOrder();
        FirstExternalProperties externalProperties = new FirstExternalProperties();
        externalProperties.setX(123);
        testConditionOrder.setExternalProperties(externalProperties);

        TestConditionOrder assemble = autoAssembler.assemble(testConditionOrder, TestConditionOrder.class);
        assertEquals(assemble, testConditionOrder);

        TestConditionOrder disassemble = autoAssembler.disassemble(assemble, TestConditionOrder.class);
        assertEquals(disassemble, assemble);
    }

    @Test
    public void testNoPojoType() throws Exception {
        TestConditionOrder testConditionOrder = new TestConditionOrder();
        testConditionOrder.setExternalProperties(new SecondExternalProperties());

        TestConditionOrderDTO assemble = autoAssembler.assemble(testConditionOrder, TestConditionOrderDTO.class);
        assertNull(assemble.getExternalProperties());

        TestConditionOrderDTO testConditionOrderDTO = new TestConditionOrderDTO();
        testConditionOrderDTO.setExternalProperties(new ThirdExternalPropertiesDTO());

        TestConditionOrder disassemble = autoAssembler.disassemble(testConditionOrderDTO, TestConditionOrder.class);
        assertNull(disassemble.getExternalProperties());
    }

    @Test
    public void testRuntimeTypeTarget() throws Exception {
        FirstExternalProperties externalProperties = new FirstExternalProperties();
        externalProperties.setX(123);

        ExternalPropertiesDTO assemble = autoAssembler.assemble(externalProperties, ExternalPropertiesDTO.class);
        assertTrue(assemble instanceof FirstExternalPropertiesDTO);
        assertEquals(((FirstExternalPropertiesDTO) assemble).getX(), externalProperties.getX());

        ExternalProperties disassemble = autoAssembler.disassemble(assemble, ExternalProperties.class);
        assertTrue(disassemble instanceof FirstExternalProperties);
        assertEquals(disassemble, externalProperties);
    }

    @Test
    public void testDisassembleMultiPropertiesToOne() throws Exception {
        TestConditionOrderDTO conditionOrderDTO = new TestConditionOrderDTO();
        FourthExternalPropertiesDTO externalProperties = new FourthExternalPropertiesDTO();
        externalProperties.setNotExternalString("hello");
        conditionOrderDTO.setExternalProperties(externalProperties);
        conditionOrderDTO.setExternalString("world");

        TestConditionOrderBuilder sourceBuilder = new TestConditionOrderBuilder();
        sourceBuilder.setExternalProperties(new FourthExternalPropertiesBuilder());
        TestConditionOrder conditionOrder = autoAssembler.disassemble(conditionOrderDTO, sourceBuilder).build();
        assertTrue(conditionOrder.getExternalProperties() instanceof FourthExternalProperties);
        assertEquals(((FourthExternalProperties) conditionOrder.getExternalProperties()).getExternalString(), "world");

        TestConditionOrderDTO assemble = autoAssembler.assemble(conditionOrder, TestConditionOrderDTO.class);
        assertTrue(assemble.getExternalProperties() instanceof FourthExternalPropertiesDTO);
        assertNull(((FourthExternalPropertiesDTO) assemble.getExternalProperties()).getNotExternalString());
        assertEquals(assemble.getExternalString(), conditionOrderDTO.getExternalString());
    }

    public static class TestConditionOrder {
        private ExternalProperties externalProperties;

        public ExternalProperties getExternalProperties() {
            return externalProperties;
        }

        public void setExternalProperties(ExternalProperties externalProperties) {
            this.externalProperties = externalProperties;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestConditionOrder that = (TestConditionOrder) o;

            return externalProperties != null ? externalProperties.equals(that.externalProperties) : that.externalProperties == null;
        }

        @Override
        public int hashCode() {
            return externalProperties != null ? externalProperties.hashCode() : 0;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestConditionOrder.class)
                              .add("externalProperties", externalProperties)
                              .toString();
        }
    }

    public static class TestConditionOrderBuilder implements ConvertibleBuilder<TestConditionOrder> {
        private ConvertibleBuilder<? extends ExternalProperties> externalProperties;

        public ConvertibleBuilder<? extends ExternalProperties> getExternalProperties() {
            return externalProperties;
        }

        public void setExternalProperties(ConvertibleBuilder<? extends ExternalProperties> externalProperties) {
            this.externalProperties = externalProperties;
        }

        @Override
        public TestConditionOrder build() {
            TestConditionOrder testConditionOrder = new TestConditionOrder();
            testConditionOrder.setExternalProperties(externalProperties.build());
            return testConditionOrder;
        }
    }

    public interface ExternalProperties {
    }

    public static class FirstExternalProperties implements ExternalProperties {
        private Integer x;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FirstExternalProperties that = (FirstExternalProperties) o;

            return x != null ? x.equals(that.x) : that.x == null;
        }

        @Override
        public int hashCode() {
            return x != null ? x.hashCode() : 0;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(FirstExternalProperties.class)
                              .add("x", x)
                              .toString();
        }
    }

    public static class SecondExternalProperties implements ExternalProperties {
        private Integer y;

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(SecondExternalProperties.class).omitNullValues()
                              .add("y", y)
                              .toString();
        }
    }

    public static class FourthExternalProperties implements ExternalProperties {
        private String externalString;

        public FourthExternalProperties(String externalString) {
            this.externalString = externalString;
        }

        public String getExternalString() {
            return externalString;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FourthExternalProperties that = (FourthExternalProperties) o;
            return Objects.equals(externalString, that.externalString);
        }

        @Override
        public int hashCode() {
            return Objects.hash(externalString);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(FourthExternalProperties.class).omitNullValues()
                              .add("notExternalString", externalString)
                              .toString();
        }
    }

    public static class FourthExternalPropertiesBuilder implements ConvertibleBuilder<FourthExternalProperties> {
        private String externalString;

        public FourthExternalPropertiesBuilder setExternalString(String externalString) {
            this.externalString = externalString;
            return this;
        }

        @Override
        public FourthExternalProperties build() {
            return new FourthExternalProperties(externalString);
        }
    }

    public static class TestConditionOrderDTO {
        private ExternalPropertiesDTO externalProperties;
        @FieldMapping(mappedProperty = "externalProperties.externalString")
        private String externalString;

        public ExternalPropertiesDTO getExternalProperties() {
            return externalProperties;
        }

        public void setExternalProperties(ExternalPropertiesDTO externalProperties) {
            this.externalProperties = externalProperties;
        }

        public String getExternalString() {
            return externalString;
        }

        public void setExternalString(String externalString) {
            this.externalString = externalString;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestConditionOrderDTO that = (TestConditionOrderDTO) o;
            return Objects.equals(externalProperties, that.externalProperties) &&
                    Objects.equals(externalString, that.externalString);
        }

        @Override
        public int hashCode() {
            return Objects.hash(externalProperties, externalString);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestConditionOrderDTO.class).omitNullValues()
                              .add("externalProperties", externalProperties)
                              .add("externalString", externalString)
                              .toString();
        }
    }

    @RuntimeType({
            FirstExternalPropertiesDTO.class,
            FourthExternalPropertiesDTO.class
    })
    public interface ExternalPropertiesDTO {
    }

    @MappedClass(FirstExternalProperties.class)
    public static class FirstExternalPropertiesDTO implements ExternalPropertiesDTO {
        private Integer x;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FirstExternalPropertiesDTO that = (FirstExternalPropertiesDTO) o;

            return x != null ? x.equals(that.x) : that.x == null;
        }

        @Override
        public int hashCode() {
            return x != null ? x.hashCode() : 0;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(FirstExternalPropertiesDTO.class)
                              .add("x", x)
                              .toString();
        }
    }

    public static class ThirdExternalPropertiesDTO implements ExternalPropertiesDTO {
        private Integer z;

        public Integer getZ() {
            return z;
        }

        public void setZ(Integer z) {
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ThirdExternalPropertiesDTO that = (ThirdExternalPropertiesDTO) o;

            return z != null ? z.equals(that.z) : that.z == null;
        }

        @Override
        public int hashCode() {
            return z != null ? z.hashCode() : 0;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(ThirdExternalPropertiesDTO.class).omitNullValues()
                              .add("z", z)
                              .toString();
        }
    }

    @MappedClass(value = FourthExternalProperties.class, builderClass = FourthExternalPropertiesBuilder.class)
    public static class FourthExternalPropertiesDTO implements ExternalPropertiesDTO {
        private String notExternalString;

        public String getNotExternalString() {
            return notExternalString;
        }

        public void setNotExternalString(String notExternalString) {
            this.notExternalString = notExternalString;
        }
    }
}