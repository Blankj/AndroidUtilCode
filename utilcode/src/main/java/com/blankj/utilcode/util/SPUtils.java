package com.blankj.utilcode.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : SP相关工具类
 * </pre>
 */
public final class SPUtils {
    private static final String DEFAULT="DEFAULT_SP";
    private static SharedPreferences sp;

    /**
     * SPUtils构造函数
     * <p>在Application中初始化</p>
     *
     */
    private SPUtils() {

    }

    public static void init(@NonNull Context context) {
        init(context,context.getPackageName());
    }

    public static void init(@NonNull Context context, String preferenceName) {
        if (context==null){
            throw new NullPointerException("context can't be null");
        }

        if (null == sp) {
            sp = context.getSharedPreferences(TextUtils.isEmpty(preferenceName) ? DEFAULT : preferenceName, Context.MODE_PRIVATE);
        }
    }



    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void save(String key, @Nullable String value) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        sp.edit().putString(key, value).apply();
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public static String getString(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return getString(key, null);
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static String getString(String key, String defaultValue) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getString(key, defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void save(String key, int value) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().putInt(key, value).apply();
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static int getInt(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static int getInt(String key, int defaultValue) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void save(String key, long value) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().putLong(key, value).apply();
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static long getLong(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static long getLong(String key, long defaultValue) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void save(String key, float value) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().putFloat(key, value).apply();
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static float getFloat(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static float getFloat(String key, float defaultValue) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void save(String key, boolean value) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().putBoolean(key, value).apply();
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public static boolean getBoolean(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP中写入String集合类型value
     *
     * @param key    键
     * @param values 值
     */
    public static void save(String key, @Nullable Set<String> values) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().putStringSet(key, values).apply();
    }

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public static Set<String> getStringSet(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return getStringSet(key, null);
    }

    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static Set<String> getStringSet(String key, @Nullable Set<String> defaultValue) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public static Map<String, ?> getAll() {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.getAll();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public static void remove(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().remove(key).apply();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean contains(String key) {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
        return sp.contains(key);
    }

    /**
     * SP中清除所有数据
     */
    public static void clear() {
        if (sp==null){
            throw new IllegalStateException("SPUtils not initialized");
        }
        
         sp.edit().clear().apply();
    }
}