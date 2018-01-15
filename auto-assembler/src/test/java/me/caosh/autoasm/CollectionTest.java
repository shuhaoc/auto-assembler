package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/15
 */
public class CollectionTest {

    private final AutoAssembler autoAssembler = AutoAssemblers.getDefault();

    private List testRawList;
    private List<String> testStringList;

    @Test
    public void testAssembleList() throws Exception {
        List<TestSecurityInfo> securityInfoList = Lists.newArrayList(
                new TestSecurityInfo("600000", "PFYH"),
                new TestSecurityInfo("000001", "PAYH")
        );

        List<TestSecurityInfoDTO> assembleList = autoAssembler.assembleList(securityInfoList, TestSecurityInfoDTO.class);
        assertEquals(assembleList, Lists.newArrayList(
                new TestSecurityInfoDTO("600000", "PFYH"),
                new TestSecurityInfoDTO("000001", "PAYH")
        ));

        List<TestSecurityInfo> disassembleList = autoAssembler.disassembleList(assembleList, TestSecurityInfo.class);
        assertEquals(disassembleList, securityInfoList);
    }

    @Test
    public void testAssembleEmptyList() throws Exception {
        List<TestSecurityInfoDTO> assembleList = autoAssembler.assembleList(Lists.newArrayList(), TestSecurityInfoDTO.class);
        assertTrue(assembleList.isEmpty());

        List<TestSecurityInfo> disassembleList = autoAssembler.disassembleList(Lists.newArrayList(), TestSecurityInfo.class);
        assertTrue(disassembleList.isEmpty());
    }

    @Test
    public void testGenericTypeParameter() throws Exception {
        testStringList = Lists.newArrayList();

        Type testRawListType = getClass().getDeclaredField("testRawList").getGenericType();
        assertTrue(testRawListType instanceof Class);
        assertEquals(testRawListType, List.class);

        ParameterizedType testStringListType = (ParameterizedType)
                getClass().getDeclaredField("testStringList").getGenericType();
        assertNull(testStringListType.getOwnerType());
        assertEquals(testStringListType.getRawType(), List.class);
        assertEquals(testStringListType.getActualTypeArguments().length, 1);
        assertEquals(testStringListType.getActualTypeArguments()[0], String.class);
    }

    @Test
    public void testList() throws Exception {
        TestListObject testListObject = new TestListObject();
        testListObject.setIntegerList(Lists.newArrayList(1, 2, 3));
        testListObject.setStringList(Lists.newArrayList("4", "5", "6"));
        testListObject.setSecurityInfoList(Lists.newArrayList(
                new TestSecurityInfo("600000", "PFYH"),
                new TestSecurityInfo("000001", "PAYH")
        ));

        TestListDTO assemble = autoAssembler.assemble(testListObject, TestListDTO.class);
        assertEquals(assemble.getIntegerList(), Lists.newArrayList("1", "2", "3"));
        assertEquals(assemble.getStringList(), Lists.newArrayList("4", "5", "6"));
        assertEquals(assemble.getSecurityInfoList(), Lists.newArrayList(
                new TestSecurityInfoDTO("600000", "PFYH"),
                new TestSecurityInfoDTO("000001", "PAYH")
        ));

        TestListObject disassemble = autoAssembler.disassemble(assemble, TestListObject.class);
        assertEquals(disassemble, testListObject);
    }

    public static class TestListObject {
        private List<Integer> integerList;
        private List<String> stringList;
        private List<TestSecurityInfo> securityInfoList;

        public List<Integer> getIntegerList() {
            return integerList;
        }

        public void setIntegerList(List<Integer> integerList) {
            this.integerList = integerList;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        public List<TestSecurityInfo> getSecurityInfoList() {
            return securityInfoList;
        }

        public void setSecurityInfoList(List<TestSecurityInfo> securityInfoList) {
            this.securityInfoList = securityInfoList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestListObject that = (TestListObject) o;

            if (integerList != null ? !integerList.equals(that.integerList) : that.integerList != null) return false;
            if (stringList != null ? !stringList.equals(that.stringList) : that.stringList != null) return false;
            return securityInfoList != null ? securityInfoList.equals(that.securityInfoList) : that.securityInfoList == null;
        }

        @Override
        public int hashCode() {
            int result = integerList != null ? integerList.hashCode() : 0;
            result = 31 * result + (stringList != null ? stringList.hashCode() : 0);
            result = 31 * result + (securityInfoList != null ? securityInfoList.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TestListObject.class).omitNullValues()
                    .add("integerList", integerList)
                    .add("stringList", stringList)
                    .add("securityInfoList", securityInfoList)
                    .toString();
        }
    }

    public static class TestListDTO {
        private List<String> integerList;
        private List<String> stringList;
        private List<TestSecurityInfoDTO> securityInfoList;

        public List<String> getIntegerList() {
            return integerList;
        }

        public void setIntegerList(List<String> integerList) {
            this.integerList = integerList;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        public List<TestSecurityInfoDTO> getSecurityInfoList() {
            return securityInfoList;
        }

        public void setSecurityInfoList(List<TestSecurityInfoDTO> securityInfoList) {
            this.securityInfoList = securityInfoList;
        }
    }
}
