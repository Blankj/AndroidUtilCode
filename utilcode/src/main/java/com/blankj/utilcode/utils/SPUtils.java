package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : SP读写工具类
 * </pre>
 */
public class SPUtils {

    private SPUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * SP的name值
     * <p>可通过修改PREFERENCE_NAME变量修改SP的name值</p>
     */
    public static String PREFERENCE_NAME = "ANCROID_UTIL_CODE";

    /**
     * SP中写入String类型value
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putString(Context context, String key, String value) {
        return getSP(context).edit().putString(key, value).commit();
    }

    /**
     * SP中读取String
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值null
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * SP中读取String
     *
     * @param context      上下文
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static String getString(Context context, String key, String defaultValue) {
        return getSP(context).getString(key, defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putInt(Context context, String key, int value) {
        return getSP(context).edit().putInt(key, value).commit();
    }

    /**
     * SP中读取int
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * SP中读取int
     *
     * @param context      上下文
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static int getInt(Context context, String key, int defaultValue) {
        return getSP(context).getInt(key, defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putLong(Context context, String key, long value) {
        return getSP(context).edit().putLong(key, value).commit();
    }

    /**
     * SP中读取long
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * SP中读取long
     *
     * @param context      上下文
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static long getLong(Context context, String key, long defaultValue) {
        return getSP(context).getLong(key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putFloat(Context context, String key, float value) {
        return getSP(context).edit().putFloat(key, value).commit();
    }

    /**
     * SP中读取float
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * SP中读取float
     *
     * @param context      上下文
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        return getSP(context).getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        return getSP(context).edit().putBoolean(key, value).commit();
    }

    /**
     * SP中读取boolean
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值false
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param context      上下文
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSP(context).getBoolean(key, defaultValue);
    }

    /**
     * 获取name为PREFERENCE_NAME的SP对象
     *
     * @param context 上下文
     * @return SP
     */
    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }


}
