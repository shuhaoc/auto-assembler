package me.caosh.autoasm;

import com.google.common.base.MoreObjects;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Date;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class TestDateDomainObject {
    private Date date2str;
    private LocalDateTime localDateTime2str;
    private LocalDate localDate2str;
    private LocalTime localTime2str;
    private String str2Date;
    private String str2LocalDateTime;
    private String str2LocalDate;
    private String str2LocalTime;

    public Date getDate2str() {
        return date2str;
    }

    public void setDate2str(Date date2str) {
        this.date2str = date2str;
    }

    public LocalDateTime getLocalDateTime2str() {
        return localDateTime2str;
    }

    public void setLocalDateTime2str(LocalDateTime localDateTime2str) {
        this.localDateTime2str = localDateTime2str;
    }

    public LocalDate getLocalDate2str() {
        return localDate2str;
    }

    public void setLocalDate2str(LocalDate localDate2str) {
        this.localDate2str = localDate2str;
    }

    public LocalTime getLocalTime2str() {
        return localTime2str;
    }

    public void setLocalTime2str(LocalTime localTime2str) {
        this.localTime2str = localTime2str;
    }

    public String getStr2Date() {
        return str2Date;
    }

    public void setStr2Date(String str2Date) {
        this.str2Date = str2Date;
    }

    public String getStr2LocalDateTime() {
        return str2LocalDateTime;
    }

    public void setStr2LocalDateTime(String str2LocalDateTime) {
        this.str2LocalDateTime = str2LocalDateTime;
    }

    public String getStr2LocalDate() {
        return str2LocalDate;
    }

    public void setStr2LocalDate(String str2LocalDate) {
        this.str2LocalDate = str2LocalDate;
    }

    public String getStr2LocalTime() {
        return str2LocalTime;
    }

    public void setStr2LocalTime(String str2LocalTime) {
        this.str2LocalTime = str2LocalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDateDomainObject that = (TestDateDomainObject) o;

        if (date2str != null ? !date2str.equals(that.date2str) : that.date2str != null) return false;
        if (localDateTime2str != null ? !localDateTime2str.equals(that.localDateTime2str) : that.localDateTime2str != null)
            return false;
        if (localDate2str != null ? !localDate2str.equals(that.localDate2str) : that.localDate2str != null)
            return false;
        if (localTime2str != null ? !localTime2str.equals(that.localTime2str) : that.localTime2str != null)
            return false;
        if (str2Date != null ? !str2Date.equals(that.str2Date) : that.str2Date != null) return false;
        if (str2LocalDateTime != null ? !str2LocalDateTime.equals(that.str2LocalDateTime) : that.str2LocalDateTime != null)
            return false;
        if (str2LocalDate != null ? !str2LocalDate.equals(that.str2LocalDate) : that.str2LocalDate != null)
            return false;
        return str2LocalTime != null ? str2LocalTime.equals(that.str2LocalTime) : that.str2LocalTime == null;
    }

    @Override
    public int hashCode() {
        int result = date2str != null ? date2str.hashCode() : 0;
        result = 31 * result + (localDateTime2str != null ? localDateTime2str.hashCode() : 0);
        result = 31 * result + (localDate2str != null ? localDate2str.hashCode() : 0);
        result = 31 * result + (localTime2str != null ? localTime2str.hashCode() : 0);
        result = 31 * result + (str2Date != null ? str2Date.hashCode() : 0);
        result = 31 * result + (str2LocalDateTime != null ? str2LocalDateTime.hashCode() : 0);
        result = 31 * result + (str2LocalDate != null ? str2LocalDate.hashCode() : 0);
        result = 31 * result + (str2LocalTime != null ? str2LocalTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestDateDomainObject.class).omitNullValues()
                .add("date2str", date2str)
                .add("localDateTime2str", localDateTime2str)
                .add("localDate2str", localDate2str)
                .add("localTime2str", localTime2str)
                .add("str2Date", str2Date)
                .add("str2LocalDateTime", str2LocalDateTime)
                .add("str2LocalDate", str2LocalDate)
                .add("str2LocalTime", str2LocalTime)
                .toString();
    }
}
