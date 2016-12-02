package com.blankj.utilcode.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.blankj.utilcode.utils.ConstUtils.DAY;
import static com.blankj.utilcode.utils.ConstUtils.HOUR;
import static com.blankj.utilcode.utils.ConstUtils.MIN;
import static com.blankj.utilcode.utils.ConstUtils.MSEC;
import static com.blankj.utilcode.utils.ConstUtils.SEC;
import static com.blankj.utilcode.utils.ConstUtils.TimeUnit;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 时间相关工具类
 * </pre>
 */
public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
     * 格式的意义如下： 日期和时间模式 <br>
     * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * </p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>时区 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>时区 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     * 注意SimpleDateFormat不是线程安全的
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";


    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        return millis2String(millis, DEFAULT_PATTERN);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, String pattern) {
        return new Date(string2Millis(time, pattern));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_PATTERN);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @return unit时间戳
     */
    private static long millis2Unit(long millis, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return millis / MSEC;
            case SEC:
                return millis / SEC;
            case MIN:
                return millis / MIN;
            case HOUR:
                return millis / HOUR;
            case DAY:
                return millis / DAY;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit  单位类型
     *              <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(String time0, String time1, TimeUnit unit) {
        return getTimeSpan(time0, time1, unit, DEFAULT_PATTERN);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0   时间字符串1
     * @param time1   时间字符串2
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                <li>{@link TimeUnit#SEC }: 秒</li>
     *                <li>{@link TimeUnit#MIN }: 分</li>
     *                <li>{@link TimeUnit#HOUR}: 小时</li>
     *                <li>{@link TimeUnit#DAY }: 天</li>
     *                </ul>
     * @param pattern 时间格式
     * @return unit时间戳
     */
    public static long getTimeSpan(String time0, String time1, TimeUnit unit, String pattern) {
        return millis2Unit(Math.abs(string2Millis(time0, pattern) - string2Millis(time1, pattern)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2都为Date类型</p>
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit  单位类型
     *              <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(Date time0, Date time1, TimeUnit unit) {
        return millis2Unit(Math.abs(date2Millis(time1) - date2Millis(time0)), unit);
    }

    /**
     * 获取当前时间戳
     *
     * @return 毫秒时间戳
     */
    public static long getNowTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getNowTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String getNowTimeString(String pattern) {
        return date2String(new Date(), pattern);
    }

    /**
     * 获取当前时间Date
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getNowTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit 单位类型
     *             <ul>
     *             <li>{@link TimeUnit#MSEC}:毫秒</li>
     *             <li>{@link TimeUnit#SEC }:秒</li>
     *             <li>{@link TimeUnit#MIN }:分</li>
     *             <li>{@link TimeUnit#HOUR}:小时</li>
     *             <li>{@link TimeUnit#DAY }:天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(String time, TimeUnit unit) {
        return getTimeSpanByNow(time, unit, DEFAULT_PATTERN);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time    时间字符串
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                <li>{@link TimeUnit#SEC }: 秒</li>
     *                <li>{@link TimeUnit#MIN }: 分</li>
     *                <li>{@link TimeUnit#HOUR}: 小时</li>
     *                <li>{@link TimeUnit#DAY }: 天</li>
     *                </ul>
     * @param pattern 时间格式
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(String time, TimeUnit unit, String pattern) {
        return getTimeSpan(getNowTimeString(), time, unit, pattern);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time为Date类型</p>
     *
     * @param time Date类型时间
     * @param unit 单位类型
     *             <ul>
     *             <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *             <li>{@link TimeUnit#SEC }: 秒</li>
     *             <li>{@link TimeUnit#MIN }: 分</li>
     *             <li>{@link TimeUnit#HOUR}: 小时</li>
     *             <li>{@link TimeUnit#DAY }: 天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(Date time, TimeUnit unit) {
        return getTimeSpan(getNowTimeDate(), time, unit);
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param time 时间字符串
     * @return <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_PATTERN);
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param time 时间字符串
     * @return <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String time, String pattern) {
        return getFriendlyTimeSpanByNow(string2Millis(time, pattern));
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param time Date类型时间
     * @return <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(Date time) {
        return getFriendlyTimeSpanByNow(time.getTime());
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) return String.format("%tc", new Date(millis));
        // 获取当天00:00
        long wee = (now / ConstUtils.DAY) * ConstUtils.DAY;
        if (span < 1000) {
            return "刚刚";
        } else if (span < ConstUtils.MIN) {
            return String.format("%d秒前", span / ConstUtils.SEC);
        } else if (span < ConstUtils.HOUR) {
            return String.format("%d分钟前", span / ConstUtils.MIN);
        } else if (millis >= wee) {
            return String.format("今天%tR", new Date(millis));
        } else if (millis >= wee - ConstUtils.DAY) {
            return String.format("昨天%tR", new Date(millis));
        } else {
            return String.format("%tF", new Date(millis));
        }
    }

    /**
     * 判断是否同一天
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(long millis) {
        long wee = (System.currentTimeMillis() / ConstUtils.DAY) * ConstUtils.DAY;
        return millis >= wee && millis < wee + ConstUtils.DAY;
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time));
    }

    /**
     * 获取星期
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 星期
     */
    public static String getWeek(String time, String pattern) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, pattern));
    }

    /**
     * 获取星期
     *
     * @param time Date类型时间
     * @return 星期
     */
    public static String getWeek(Date time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(time);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        Date date = string2Date(time);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, String pattern) {
        Date date = string2Date(time, pattern);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        Date date = string2Date(time);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, String pattern) {
        Date date = string2Date(time, pattern);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        Date date = string2Date(time);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, String pattern) {
        Date date = string2Date(time, pattern);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}