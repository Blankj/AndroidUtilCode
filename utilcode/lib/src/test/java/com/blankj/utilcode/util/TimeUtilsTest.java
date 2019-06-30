package com.blankj.utilcode.util;

import com.blankj.utilcode.constant.TimeConstants;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/12
 *     desc  : test TimeUtils
 * </pre>
 */
public class TimeUtilsTest {

    private final DateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final DateFormat mFormat       = new SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault());

    private final long   timeMillis               = 1493887049000L;// 2017-05-04 16:37:29
    private final Date   timeDate                 = new Date(timeMillis);
    private final String timeString               = defaultFormat.format(timeDate);
    private final String timeStringFormat         = mFormat.format(timeDate);
    private final long   tomorrowTimeMillis       = 1493973449000L;
    private final Date   tomorrowTimeDate         = new Date(tomorrowTimeMillis);
    private final String tomorrowTimeString       = defaultFormat.format(tomorrowTimeDate);
    private final String tomorrowTimeStringFormat = mFormat.format(tomorrowTimeDate);
    private final long   delta                    = 10;// 允许误差 10ms

    @Test
    public void millis2String() {
        assertEquals(timeString, TimeUtils.millis2String(timeMillis));
        assertEquals(timeStringFormat, TimeUtils.millis2String(timeMillis, mFormat));
        assertEquals(timeStringFormat, TimeUtils.millis2String(timeMillis, "yyyy MM dd HH:mm:ss"));
    }

    @Test
    public void string2Millis() {
        assertEquals(timeMillis, TimeUtils.string2Millis(timeString));
        assertEquals(timeMillis, TimeUtils.string2Millis(timeStringFormat, mFormat));
        assertEquals(timeMillis, TimeUtils.string2Millis(timeStringFormat, "yyyy MM dd HH:mm:ss"));
    }

    @Test
    public void string2Date() {
        assertEquals(timeDate, TimeUtils.string2Date(timeString));
        assertEquals(timeDate, TimeUtils.string2Date(timeStringFormat, mFormat));
        assertEquals(timeDate, TimeUtils.string2Date(timeStringFormat, "yyyy MM dd HH:mm:ss"));
    }

    @Test
    public void date2String() {
        assertEquals(timeString, TimeUtils.date2String(timeDate));
        assertEquals(timeStringFormat, TimeUtils.date2String(timeDate, mFormat));
        assertEquals(timeStringFormat, TimeUtils.date2String(timeDate, "yyyy MM dd HH:mm:ss"));
    }

    @Test
    public void date2Millis() {
        assertEquals(timeMillis, TimeUtils.date2Millis(timeDate));
    }

    @Test
    public void millis2Date() {
        assertEquals(timeDate, TimeUtils.millis2Date(timeMillis));
    }

    @Test
    public void getTimeSpan() {
        long testTimeMillis = timeMillis + 120 * TimeConstants.SEC;
        String testTimeString = TimeUtils.millis2String(testTimeMillis);
        String testTimeStringFormat = TimeUtils.millis2String(testTimeMillis, mFormat);
        Date testTimeDate = TimeUtils.millis2Date(testTimeMillis);
        assertEquals(-120, TimeUtils.getTimeSpan(timeString, testTimeString, TimeConstants.SEC));
        assertEquals(2, TimeUtils.getTimeSpan(testTimeStringFormat, timeStringFormat, mFormat, TimeConstants.MIN));
        assertEquals(-2, TimeUtils.getTimeSpan(timeDate, testTimeDate, TimeConstants.MIN));
        assertEquals(120, TimeUtils.getTimeSpan(testTimeMillis, timeMillis, TimeConstants.SEC));
    }

    @Test
    public void getFitTimeSpan() {
        long testTimeMillis = timeMillis + 10 * TimeConstants.DAY + 10 * TimeConstants.MIN + 10 * TimeConstants.SEC;
        String testTimeString = TimeUtils.millis2String(testTimeMillis);
        String testTimeStringFormat = TimeUtils.millis2String(testTimeMillis, mFormat);
        Date testTimeDate = TimeUtils.millis2Date(testTimeMillis);
        assertEquals("-10天10分钟10秒", TimeUtils.getFitTimeSpan(timeString, testTimeString, 5));
        assertEquals("10天10分钟10秒", TimeUtils.getFitTimeSpan(testTimeStringFormat, timeStringFormat, mFormat, 5));
        assertEquals("-10天10分钟10秒", TimeUtils.getFitTimeSpan(timeDate, testTimeDate, 5));
        assertEquals("10天10分钟10秒", TimeUtils.getFitTimeSpan(testTimeMillis, timeMillis, 5));
    }

    @Test
    public void getNowMills() {
        assertEquals(System.currentTimeMillis(), TimeUtils.getNowMills(), delta);
    }

    @Test
    public void getNowString() {
        assertEquals(System.currentTimeMillis(), TimeUtils.string2Millis(TimeUtils.getNowString()), delta);
        assertEquals(System.currentTimeMillis(), TimeUtils.string2Millis(TimeUtils.getNowString(mFormat), mFormat), delta);
    }

    @Test
    public void getNowDate() {
        assertEquals(System.currentTimeMillis(), TimeUtils.date2Millis(TimeUtils.getNowDate()), delta);
    }

    @Test
    public void getTimeSpanByNow() {
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowString(), TimeConstants.MSEC), delta);
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowString(mFormat), mFormat, TimeConstants.MSEC), delta);
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowDate(), TimeConstants.MSEC), delta);
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowMills(), TimeConstants.MSEC), delta);
    }

    @Test
    public void getFitTimeSpanByNow() {
//        long spanMillis = 6 * TimeConstants.DAY + 6 * TimeConstants.HOUR + 6 * TimeConstants.MIN + 6 * TimeConstants.SEC;
//        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis), 4));
//        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis, mFormat), mFormat, 4));
//        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2Date(System.currentTimeMillis() + spanMillis), 4));
//        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(System.currentTimeMillis() + spanMillis, 4));
    }

    @Test
    public void getFriendlyTimeSpanByNow() {
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowString()));
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowString(mFormat), mFormat));
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowDate()));
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowMills()));
        assertEquals("1秒前", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowMills() - TimeConstants.SEC));
        assertEquals("1分钟前", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowMills() - TimeConstants.MIN));
    }

    @Test
    public void getMillis() {
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeMillis, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeString, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeStringFormat, mFormat, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeDate, 1, TimeConstants.DAY));
    }

    @Test
    public void getString() {
        assertEquals(tomorrowTimeString, TimeUtils.getString(timeMillis, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeStringFormat, TimeUtils.getString(timeMillis, mFormat, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeString, TimeUtils.getString(timeString, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeStringFormat, TimeUtils.getString(timeStringFormat, mFormat, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeString, TimeUtils.getString(timeDate, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeStringFormat, TimeUtils.getString(timeDate, mFormat, 1, TimeConstants.DAY));
    }

    @Test
    public void getDate() {
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeMillis, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeString, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeStringFormat, mFormat, 1, TimeConstants.DAY));
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeDate, 1, TimeConstants.DAY));
    }

    @Test
    public void getMillisByNow() {
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, TimeUtils.getMillisByNow(1, TimeConstants.DAY), delta);
    }

    @Test
    public void getStringByNow() {
        long tomorrowMillis = TimeUtils.string2Millis(TimeUtils.getStringByNow(1, TimeConstants.DAY));
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, tomorrowMillis, delta);
        tomorrowMillis = TimeUtils.string2Millis(TimeUtils.getStringByNow(1, mFormat, TimeConstants.DAY), mFormat);
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, tomorrowMillis, delta);
    }

    @Test
    public void getDateByNow() {
        long tomorrowMillis = TimeUtils.date2Millis(TimeUtils.getDateByNow(1, TimeConstants.DAY));
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, TimeUtils.getMillisByNow(1, TimeConstants.DAY), delta);
    }

    @Test
    public void isToday() {
        long todayTimeMillis = System.currentTimeMillis();
        String todayTimeString = TimeUtils.millis2String(todayTimeMillis);
        String todayTimeStringFormat = TimeUtils.millis2String(todayTimeMillis, mFormat);
        Date todayTimeDate = TimeUtils.millis2Date(todayTimeMillis);
        long tomorrowTimeMillis = todayTimeMillis + TimeConstants.DAY;
        String tomorrowTimeString = TimeUtils.millis2String(tomorrowTimeMillis);
        Date tomorrowTimeDate = TimeUtils.millis2Date(tomorrowTimeMillis);
        assertTrue(TimeUtils.isToday(todayTimeString));
        assertTrue(TimeUtils.isToday(todayTimeStringFormat, mFormat));
        assertTrue(TimeUtils.isToday(todayTimeDate));
        assertTrue(TimeUtils.isToday(todayTimeMillis));
        assertFalse(TimeUtils.isToday(tomorrowTimeString));
        assertFalse(TimeUtils.isToday(tomorrowTimeStringFormat, mFormat));
        assertFalse(TimeUtils.isToday(tomorrowTimeDate));
        assertFalse(TimeUtils.isToday(tomorrowTimeMillis));
    }

    @Test
    public void isLeapYear() {
        assertFalse(TimeUtils.isLeapYear(timeString));
        assertFalse(TimeUtils.isLeapYear(timeStringFormat, mFormat));
        assertFalse(TimeUtils.isLeapYear(timeDate));
        assertFalse(TimeUtils.isLeapYear(timeMillis));
        assertTrue(TimeUtils.isLeapYear(2016));
        assertFalse(TimeUtils.isLeapYear(2017));
    }

    @Test
    public void getChineseWeek() {
        assertEquals("星期四", TimeUtils.getChineseWeek(timeString));
        assertEquals("星期四", TimeUtils.getChineseWeek(timeStringFormat, mFormat));
        assertEquals("星期四", TimeUtils.getChineseWeek(timeDate));
        assertEquals("星期四", TimeUtils.getChineseWeek(timeMillis));
    }

    @Test
    public void getUSWeek() {
        assertEquals("Thursday", TimeUtils.getUSWeek(timeString));
        assertEquals("Thursday", TimeUtils.getUSWeek(timeStringFormat, mFormat));
        assertEquals("Thursday", TimeUtils.getUSWeek(timeDate));
        assertEquals("Thursday", TimeUtils.getUSWeek(timeMillis));
    }

    @Test
    public void getWeekIndex() {
        assertEquals(5, TimeUtils.getValueByCalendarField(timeString, Calendar.DAY_OF_WEEK));
        assertEquals(5, TimeUtils.getValueByCalendarField(timeString, Calendar.DAY_OF_WEEK));
        assertEquals(5, TimeUtils.getValueByCalendarField(timeStringFormat, mFormat, Calendar.DAY_OF_WEEK));
        assertEquals(5, TimeUtils.getValueByCalendarField(timeDate, Calendar.DAY_OF_WEEK));
        assertEquals(5, TimeUtils.getValueByCalendarField(timeMillis, Calendar.DAY_OF_WEEK));
    }

    @Test
    public void getWeekOfMonth() {
        assertEquals(1, TimeUtils.getValueByCalendarField(timeString, Calendar.WEEK_OF_MONTH));
        assertEquals(1, TimeUtils.getValueByCalendarField(timeStringFormat, mFormat, Calendar.WEEK_OF_MONTH));
        assertEquals(1, TimeUtils.getValueByCalendarField(timeDate, Calendar.WEEK_OF_MONTH));
        assertEquals(1, TimeUtils.getValueByCalendarField(timeMillis, Calendar.WEEK_OF_MONTH));
    }

    @Test
    public void getWeekOfYear() {
        assertEquals(18, TimeUtils.getValueByCalendarField(timeString, Calendar.WEEK_OF_YEAR));
        assertEquals(18, TimeUtils.getValueByCalendarField(timeStringFormat, mFormat, Calendar.WEEK_OF_YEAR));
        assertEquals(18, TimeUtils.getValueByCalendarField(timeDate, Calendar.WEEK_OF_YEAR));
        assertEquals(18, TimeUtils.getValueByCalendarField(timeMillis, Calendar.WEEK_OF_YEAR));
    }

    @Test
    public void getChineseZodiac() {
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeString));
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeStringFormat, mFormat));
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeDate));
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeMillis));
        assertEquals("鸡", TimeUtils.getChineseZodiac(2017));
    }

    @Test
    public void getZodiac() {
        assertEquals("金牛座", TimeUtils.getZodiac(timeString));
        assertEquals("金牛座", TimeUtils.getZodiac(timeStringFormat, mFormat));
        assertEquals("金牛座", TimeUtils.getZodiac(timeDate));
        assertEquals("金牛座", TimeUtils.getZodiac(timeMillis));
        assertEquals("狮子座", TimeUtils.getZodiac(8, 16));
    }
}