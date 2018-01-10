package me.caosh.autoasm;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Date;

/**
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/11
 */
public class TestDateDTO {
    private String date2str;
    private String localDateTime2str;
    private String localDate2str;
    private String localTime2str;
    private Date str2Date;
    private LocalDateTime str2LocalDateTime;
    private LocalDate str2LocalDate;
    private LocalTime str2LocalTime;

    public String getDate2str() {
        return date2str;
    }

    public void setDate2str(String date2str) {
        this.date2str = date2str;
    }

    public String getLocalDateTime2str() {
        return localDateTime2str;
    }

    public void setLocalDateTime2str(String localDateTime2str) {
        this.localDateTime2str = localDateTime2str;
    }

    public String getLocalDate2str() {
        return localDate2str;
    }

    public void setLocalDate2str(String localDate2str) {
        this.localDate2str = localDate2str;
    }

    public String getLocalTime2str() {
        return localTime2str;
    }

    public void setLocalTime2str(String localTime2str) {
        this.localTime2str = localTime2str;
    }

    public Date getStr2Date() {
        return str2Date;
    }

    public void setStr2Date(Date str2Date) {
        this.str2Date = str2Date;
    }

    public LocalDateTime getStr2LocalDateTime() {
        return str2LocalDateTime;
    }

    public void setStr2LocalDateTime(LocalDateTime str2LocalDateTime) {
        this.str2LocalDateTime = str2LocalDateTime;
    }

    public LocalDate getStr2LocalDate() {
        return str2LocalDate;
    }

    public void setStr2LocalDate(LocalDate str2LocalDate) {
        this.str2LocalDate = str2LocalDate;
    }

    public LocalTime getStr2LocalTime() {
        return str2LocalTime;
    }

    public void setStr2LocalTime(LocalTime str2LocalTime) {
        this.str2LocalTime = str2LocalTime;
    }
}
