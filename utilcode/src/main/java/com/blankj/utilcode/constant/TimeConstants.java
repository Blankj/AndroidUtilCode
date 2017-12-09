package com.blankj.utilcode.constant

import android.support.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/13
 * desc  : 时间相关常量
</pre> *
 */
object TimeConstants {

    /**
     * 毫秒与毫秒的倍数
     */
    val MSEC = 1
    /**
     * 秒与毫秒的倍数
     */
    val SEC = 1000
    /**
     * 分与毫秒的倍数
     */
    val MIN = 60000
    /**
     * 时与毫秒的倍数
     */
    val HOUR = 3600000
    /**
     * 天与毫秒的倍数
     */
    val DAY = 86400000

    @IntDef(MSEC.toLong(), SEC.toLong(), MIN.toLong(), HOUR.toLong(), DAY.toLong())
    @Retention(RetentionPolicy.SOURCE)
    annotation class Unit
}
