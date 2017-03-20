package com.blankj.utilcode.util;

import com.blankj.utilcode.constant.TimeConstants;

import org.junit.Test;

import java.util.Date;

import static com.blankj.utilcode.util.TimeUtils.*;
import static com.google.common.truth.Truth.assertThat;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/12
 *     desc  : TimeUtils单元测试
 * </pre>
 */
public class TimeUtilsTest {

    String myPattern = "yyyy-MM-dd HH:mm:ss zzzz";
    long millis = 1470991049000L;
    Date timeDate = new Date(millis);
    String timeString = "2016-08-12 16:37:29";
    String myTimeString = "2016-08-12 16:37:29 中国标准时间";
    String timeString0 = "2016-08-12 16:00:00";
    String timeString1 = "2016-08-12 17:10:10";
    String myTimeString0 = "2016-08-12 16:00:00 中国标准时间";
    String myTimeString1 = "2016-08-12 17:10:10 中国标准时间";

    @Test
    public void testMillis2String() throws Exception {
        assertThat(millis2String(millis)).isEqualTo(timeString);
        assertThat(millis2String(millis, myPattern)).isEqualTo(myTimeString);
    }

    @Test
    public void testString2Millis() throws Exception {
        assertThat(string2Millis(timeString)).isEqualTo(millis);
        assertThat(string2Millis(myTimeString, myPattern)).isEqualTo(millis);
    }

    @Test
    public void testString2Date() throws Exception {
        assertThat(string2Date(timeString)).isEqualTo(timeDate);
        assertThat(string2Date(myTimeString, myPattern)).isEqualTo(timeDate);
    }

    @Test
    public void testDate2String() throws Exception {
        assertThat(date2String(timeDate)).isEqualTo(timeString);
        assertThat(date2String(timeDate, myPattern)).isEqualTo(myTimeString);
    }

    @Test
    public void testDate2Millis() throws Exception {
        assertThat(date2Millis(timeDate)).isEqualTo(millis);
    }

    @Test
    public void testMillis2Date() throws Exception {
        assertThat(millis2Date(millis)).isEqualTo(timeDate);
    }

    @Test
    public void testGetTimeSpan() throws Exception {
        assertThat(getTimeSpan(timeString0, timeString1, TimeConstants.SEC)).isEqualTo(4210);
        assertThat(getTimeSpan(myTimeString0, myTimeString1, TimeConstants.SEC, myPattern)).isEqualTo(4210);
        assertThat(getTimeSpan(new Date(4210000), new Date(0), TimeConstants.SEC)).isEqualTo(4210);
    }

    @Test
    public void testGetCurTimeMills() throws Exception {
        long interval = getNowTimeMills() - System.currentTimeMillis();
        assertThat(interval).isLessThan(10L);
    }

    @Test
    public void testGetCurTimeString() throws Exception {
        System.out.println(getNowTimeString());
        System.out.println(getNowTimeString(myPattern));
    }

    @Test
    public void testGetFriendlyTimeSpanByNow() throws Exception {
        System.out.println(getFriendlyTimeSpanByNow(System.currentTimeMillis()));
        System.out.println(getFriendlyTimeSpanByNow(System.currentTimeMillis() - 6 * TimeConstants.SEC));
        System.out.println(getFriendlyTimeSpanByNow(System.currentTimeMillis() - 6 * TimeConstants.MIN));
        System.out.println(getFriendlyTimeSpanByNow(System.currentTimeMillis() - 6 * TimeConstants.HOUR));
        System.out.println(getFriendlyTimeSpanByNow(System.currentTimeMillis() - TimeConstants.DAY));
        System.out.println(getFriendlyTimeSpanByNow(System.currentTimeMillis() - 2 * TimeConstants.DAY));
    }

    @Test
    public void testIsSameDay() throws Exception {
        System.out.println(isSameDay(System.currentTimeMillis()));
        System.out.println(isSameDay((System.currentTimeMillis() / TimeConstants.DAY) * TimeConstants.DAY - 8 * TimeConstants.HOUR));
        System.out.println(isSameDay((System.currentTimeMillis() / TimeConstants.DAY) * TimeConstants.DAY + 16 * TimeConstants.HOUR));
    }

    @Test
    public void testIsLeapYear() throws Exception {
        assertThat(isLeapYear(2012)).isEqualTo(true);
        assertThat(isLeapYear(2000)).isEqualTo(true);
        assertThat(isLeapYear(1900)).isEqualTo(false);
    }

    @Test
    public void testGetWeek() throws Exception {
        assertThat(getWeek(timeString)).isEqualTo("星期五");
    }

    @Test
    public void testGetWeekIndex() throws Exception {
        assertThat(getWeekIndex(timeString)).isEqualTo(6);
    }

    @Test
    public void testGetOfMonth() throws Exception {
        assertThat(getWeekOfMonth(timeString)).isEqualTo(2);
    }

    @Test
    public void testGetOfYear() throws Exception {
        assertThat(getWeekOfYear(timeString)).isEqualTo(33);
    }

    @Test
    public void testGetChineseZodiac() throws Exception {
        System.out.println(getChineseZodiac(System.currentTimeMillis()));
    }

    @Test
    public void testGetZodiac() throws Exception {
        assertThat(getZodiac("2016-08-16 16:37:29")).isEqualTo("狮子座");
    }
}