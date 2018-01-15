package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/13
 */
public class MappingTest {
    private final AutoAssembler autoAssembler = AutoAssemblers.getDefault();

    @Test
    public void testMapping() throws Exception {
        TestMappingObject testMappingObject = new TestMappingObject();
        testMappingObject.setDomainName("dn123");
        testMappingObject.setProperties(new FirstProperties("12.22"));
        testMappingObject.setSecurityInfo(new TestSecurityInfo("600000", "PFYH"));

        TestMappingDTO testMappingDTO = autoAssembler.assemble(testMappingObject, TestMappingDTO.class);

        assertEquals(testMappingDTO.getDtoName(), testMappingObject.getDomainName());
        assertEquals(testMappingDTO.getFirstPrice(), "12.22");
        assertNull(testMappingDTO.getWrongPath());
        assertEquals(testMappingDTO.getStockInfo().getCode(), testMappingObject.getSecurityInfo().getCode());
        assertEquals(testMappingDTO.getStockInfo().getName(), testMappingObject.getSecurityInfo().getName());

        TestMappingObject disassembled = autoAssembler.disassemble(testMappingDTO, TestMappingObject.class);
        assertEquals(disassembled, testMappingObject);
    }

    @Test
    public void testDisassembleWrongPath() throws Exception {
        TestMappingDTO testMappingDTO = new TestMappingDTO();
        testMappingDTO.setWrongPath("abc");

        autoAssembler.disassemble(testMappingDTO, TestMappingObject.class);
    }

    public static class TestMappingObject {
        private Integer id;
        private String name;
        private String domainName;
        private Object properties = new FirstProperties();
        private TestSecurityInfo securityInfo;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public Object getProperties() {
            return properties;
        }

        public void setProperties(Object properties) {
            this.properties = properties;
        }

        public TestSecurityInfo getSecurityInfo() {
            return securityInfo;
        }

        public void setSecurityInfo(TestSecurityInfo securityInfo) {
            this.securityInfo = securityInfo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestMappingObject that = (TestMappingObject) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (domainName != null ? !domainName.equals(that.domainName) : that.domainName != null) return false;
            if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
            return securityInfo != null ? securityInfo.equals(that.securityInfo) : that.securityInfo == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (domainName != null ? domainName.hashCode() : 0);
            result = 31 * result + (properties != null ? properties.hashCode() : 0);
            result = 31 * result + (securityInfo != null ? securityInfo.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestMappingObject.class)
                    .add("id", id)
                    .add("name", name)
                    .add("domainName", domainName)
                    .add("properties", properties)
                    .add("securityInfo", securityInfo)
                    .toString();
        }
    }

    public static class TestMappingDTO extends BaseDTO {
        private String name;
        @FieldMapping(mappedProperty = "domainName")
        private String dtoName;
        @FieldMapping(mappedProperty = "properties.price")
        private String firstPrice;
        @FieldMapping(mappedProperty = "wrong.path")
        private String wrongPath;
        @FieldMapping(mappedProperty = "securityInfo")
        private TestSecurityInfoDTO stockInfo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDtoName() {
            return dtoName;
        }

        public void setDtoName(String dtoName) {
            this.dtoName = dtoName;
        }

        public String getFirstPrice() {
            return firstPrice;
        }

        public void setFirstPrice(String firstPrice) {
            this.firstPrice = firstPrice;
        }

        public String getWrongPath() {
            return wrongPath;
        }

        public void setWrongPath(String wrongPath) {
            this.wrongPath = wrongPath;
        }

        public TestSecurityInfoDTO getStockInfo() {
            return stockInfo;
        }

        public void setStockInfo(TestSecurityInfoDTO stockInfo) {
            this.stockInfo = stockInfo;
        }
    }

    public static class FirstProperties {
        private String price;

        public FirstProperties() {
        }

        public FirstProperties(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FirstProperties that = (FirstProperties) o;

            return price != null ? price.equals(that.price) : that.price == null;
        }

        @Override
        public int hashCode() {
            return price != null ? price.hashCode() : 0;
        }
    }
}
