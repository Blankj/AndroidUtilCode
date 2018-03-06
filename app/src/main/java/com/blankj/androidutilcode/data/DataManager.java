package com.blankj.androidutilcode.data;

import com.blankj.utilcode.util.SPUtils;

import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/08
 *     desc  : 数据管理器
 * </pre>
 */
public class DataManager {

    private static final SPUtils SP_UTILS = SPUtils.getInstance("demo");

    public static void putString() {
        SP_UTILS.put("STRING", "string");
    }

    public static String getString() {
        return SP_UTILS.getString("STRING");
    }

    public static void putInt() {
        SP_UTILS.put("INT", 21);
    }

    public static String getInt() {
        return String.valueOf(SP_UTILS.getInt("INT"));
    }

    public static void putLong() {
        SP_UTILS.put("LONG", Long.MAX_VALUE);
    }

    public static String getLong() {
        return String.valueOf(SP_UTILS.getLong("LONG"));
    }

    public static void putFloat() {
        SP_UTILS.put("FLOAT", (float) Math.PI);
    }

    public static String getFloat() {
        return String.valueOf(SP_UTILS.getFloat("FLOAT"));
    }

    public static void putBoolean() {
        SP_UTILS.put("BOOLEAN", true);
    }

    public static String getBoolean() {
        return String.valueOf(SP_UTILS.getBoolean("BOOLEAN"));
    }

    public static void clear() {
        SP_UTILS.clear();
    }

    public static String sp2String() {
        StringBuilder sb = new StringBuilder();
        Map<String, ?> map = SP_UTILS.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return sb.toString();
    }

}
