package com.blankj.utilcode.utils.constant;

import android.support.annotation.IntDef;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : 时间相关常量
 * </pre>
 */
public class TimeConstant {

    /**
     * 秒与毫秒的倍数
     */
    public static final int MSEC = 1;

    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    public @interface Unit {
    }
}
