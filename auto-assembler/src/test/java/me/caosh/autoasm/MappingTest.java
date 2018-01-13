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
    private final AutoAssembler autoAssembler = new AutoAssembler();

    @Test
    public void testMapping() throws Exception {
        TestDomainObject domainObject = new TestDomainObject();
        domainObject.setDomainName("dn123");
        domainObject.setProperties(new FirstProperties("12.22"));

        TestDTO testDTO = autoAssembler.assemble(domainObject, TestDTO.class);

        assertEquals(testDTO.getDtoName(), domainObject.getDomainName());
        assertEquals(testDTO.getFirstPrice(), "12.22");
        assertNull(testDTO.getWrongPath());
    }

    public static class TestDomainObject {
        private Integer id;
        private String name;
        private String domainName;
        private Object properties;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestDomainObject that = (TestDomainObject) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (domainName != null ? !domainName.equals(that.domainName) : that.domainName != null) return false;
            return properties != null ? properties.equals(that.properties) : that.properties == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (domainName != null ? domainName.hashCode() : 0);
            result = 31 * result + (properties != null ? properties.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestDomainObject.class).omitNullValues()
                    .add("id", id)
                    .add("name", name)
                    .add("domainName", domainName)
                    .add("properties", properties)
                    .toString();
        }
    }

    public static class TestDTO extends BaseDTO {
        private String name;
        @FieldMapping(mappedProperty = "domainName")
        private String dtoName;
        @FieldMapping(mappedProperty = "properties.price")
        private String firstPrice;
        @FieldMapping(mappedProperty = "wrong.path")
        private String wrongPath;

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
    }

    /**
     * @author caosh/shuhaoc@qq.com
     * @date 2018/1/11
     */
    public static class FirstProperties {
        private String price;

        public FirstProperties(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
