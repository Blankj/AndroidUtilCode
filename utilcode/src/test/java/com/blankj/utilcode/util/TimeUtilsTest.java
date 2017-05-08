package com.blankj.utilcode.util;

import com.blankj.utilcode.constant.TimeConstants;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/12
 *     desc  : TimeUtils单元测试
 * </pre>
 */
public class TimeUtilsTest {

    DateFormat myFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault());

    private long   timeMillis         = 1493887049000L;
    private Date   timeDate           = new Date(timeMillis);
    private String timeString         = "2017-05-04 16:37:29";
    private String myTimeString       = "2017 05 04 16:37:29";
    private String tomorrowTimeString = "2017-05-05 16:37:29";
    private String timeString0        = "2017-05-04 16:00:00";
    private String myTimeString0      = "2017 05 04 16:00:00";
    private String timeString1        = "2017-05-04 17:10:10";
    private String myTimeString1      = "2017 05 04 17:10:10";
    private long   delta              = 2;// 允许误差2ms

    @Test
    public void millis2String() throws Exception {
        assertEquals(timeString, TimeUtils.millis2String(timeMillis));
        assertEquals(myTimeString, TimeUtils.millis2String(timeMillis, myFormat));
    }

    @Test
    public void string2Millis() throws Exception {
        assertEquals(timeMillis, TimeUtils.string2Millis(timeString));
        assertEquals(timeMillis, TimeUtils.string2Millis(myTimeString, myFormat));
    }

    @Test
    public void string2Date() throws Exception {
        assertEquals(timeDate, TimeUtils.string2Date(timeString));
        assertEquals(timeDate, TimeUtils.string2Date(myTimeString, myFormat));
    }

    @Test
    public void date2String() throws Exception {
        assertEquals(timeString, TimeUtils.date2String(timeDate));
        assertEquals(myTimeString, TimeUtils.date2String(timeDate, myFormat));
    }

    @Test
    public void date2Millis() throws Exception {
        assertEquals(timeMillis, TimeUtils.date2Millis(timeDate));
    }

    @Test
    public void millis2Date() throws Exception {
        assertEquals(timeDate, TimeUtils.millis2Date(timeMillis));
    }

    @Test
    public void getTimeSpan() throws Exception {
        assertEquals(4210, TimeUtils.getTimeSpan(timeString0, timeString1, TimeConstants.SEC));
        assertEquals(4210, TimeUtils.getTimeSpan(myTimeString0, myTimeString1, myFormat, TimeConstants.SEC));
        assertEquals(10, TimeUtils.getTimeSpan(new Date(10 * TimeConstants.MIN), new Date(0), TimeConstants.MIN));
        assertEquals(6, TimeUtils.getTimeSpan(6 * TimeConstants.HOUR, 0, TimeConstants.HOUR));
    }

    @Test
    public void getFitTimeSpan() throws Exception {
        assertEquals("1小时10分钟10秒", TimeUtils.getFitTimeSpan(timeString0, timeString1, 5));
        assertEquals("1小时10分钟10秒", TimeUtils.getFitTimeSpan(myTimeString0, myTimeString1, myFormat, 5));
        assertEquals("10分钟10秒", TimeUtils.getFitTimeSpan(new Date(10 * TimeConstants.MIN + 10 * TimeConstants.SEC), new Date(0), 5));
    }

    @Test
    public void getNowMills() throws Exception {
        assertEquals(delta, TimeUtils.getNowMills(), System.currentTimeMillis());
    }

    @Test
    public void getNowString() throws Exception {
        assertEquals(delta, TimeUtils.string2Millis(TimeUtils.getNowString()), System.currentTimeMillis());
        assertEquals(10, TimeUtils.string2Millis(TimeUtils.getNowString(myFormat), myFormat), System.currentTimeMillis());
    }

    @Test
    public void getNowDate() throws Exception {
        assertEquals(delta, TimeUtils.date2Millis(TimeUtils.getNowDate()), System.currentTimeMillis());
    }

    @Test
    public void getTimeSpanByNow() throws Exception {
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowString(), TimeConstants.MSEC), delta);
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowString(myFormat), myFormat, TimeConstants.MSEC), delta);
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowMills(), TimeConstants.MSEC), delta);
        assertEquals(0, TimeUtils.getTimeSpanByNow(TimeUtils.getNowDate(), TimeConstants.MSEC), delta);
    }

    @Test
    public void getFitTimeSpanByNow() throws Exception {
        long spanMillis = 6 * TimeConstants.DAY + 6 * TimeConstants.HOUR + 6 * TimeConstants.MIN + 6 * TimeConstants.SEC;
        String spanString = "6天6小时6分钟6秒";
        assertEquals(spanString, TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis), 4));
        assertEquals(spanString, TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis, myFormat), myFormat, 4));
        assertEquals(spanString, TimeUtils.getFitTimeSpanByNow(System.currentTimeMillis() + spanMillis, 4));
        assertEquals(spanString, TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2Date(System.currentTimeMillis() + spanMillis), 4));
    }

    @Test
    public void getFriendlyTimeSpanByNow() throws Exception {
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowString()));
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowString(myFormat), myFormat));
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowMills()));
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowDate()));
    }

    @Test
    public void getMillis() throws Exception {

    }

    @Test
    public void getString() throws Exception {

    }

    @Test
    public void getDate() throws Exception {

    }

    @Test
    public void getMillisByNow() throws Exception {

    }

    @Test
    public void isSameDay() throws Exception {

    }

    @Test
    public void isLeapYear() throws Exception {

    }

    @Test
    public void getWeek() throws Exception {

    }

    @Test
    public void getWeekIndex() throws Exception {

    }

    @Test
    public void getWeekOfMonth() throws Exception {

    }

    @Test
    public void getWeekOfYear() throws Exception {

    }

    @Test
    public void getChineseZodiac() throws Exception {

    }

    @Test
    public void getZodiac() throws Exception {

    }
}