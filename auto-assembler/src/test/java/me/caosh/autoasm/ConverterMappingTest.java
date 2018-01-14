package me.caosh.autoasm;

import com.google.common.base.Converter;
import me.caosh.autoasm.converter.CommonConverters;
import me.caosh.autoasm.converter.DefaultConverterMapping;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author shuhaoc@qq.com
 * @date 2018/1/14
 */
public class ConverterMappingTest {
    @Test
    public void testPriority() throws Exception {
        DefaultConverterMapping converterMapping = new DefaultConverterMapping();
        Converter<MyDate, String> myDateStringConverter = converterMapping.find(MyDate.class, String.class);
        assertNotNull(myDateStringConverter);

        MyDate myDate = new MyDate(118, 0, 14);
        assertEquals(myDateStringConverter.convert(myDate), "2018-01-14 00:00:00");

        Converter<String, MyDate> customStringMyDateConverter = CommonConverters.stringDateConverter()
                .andThen(new Converter<Date, MyDate>() {
                    @Override
                    protected MyDate doForward(Date date) {
                        return new MyDate(date.getTime());
                    }

                    @Override
                    protected Date doBackward(MyDate myDate) {
                        return myDate;
                    }
                });
        Converter<MyDate, String> customMyDateStringConverter = customStringMyDateConverter.reverse();
        converterMapping.register(MyDate.class, String.class, customMyDateStringConverter);
        Converter<MyDate, String> myDateStringConverter2 = converterMapping.find(MyDate.class, String.class);
        assertEquals(myDateStringConverter2, customMyDateStringConverter);
        assertEquals(myDateStringConverter2.convert(myDate), "2018-01-14 00:00:00");
    }

    public static class MyDate extends Date {
        public MyDate(long date) {
            super(date);
        }

        public MyDate(int year, int month, int date) {
            super(year, month, date);
        }
    }
}
