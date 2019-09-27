package com.blankj.utildebug.helper;

import com.blankj.utilcode.util.SPUtils;

import java.util.HashSet;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/09
 *     desc  :
 * </pre>
 */
public class SpHelper {

    public static String getSpClassName(Class cls) {
        if (cls == null) return "null";
        if (Boolean.class.equals(cls)) {
            return "boolean";
        }
        if (String.class.equals(cls)) {
            return "String";
        }
        if (Integer.class.equals(cls)) {
            return "int";
        }
        if (Float.class.equals(cls)) {
            return "float";
        }
        if (Long.class.equals(cls)) {
            return "long";
        }
        if (HashSet.class.equals(cls)) {
            return "set";
        }
        return "unknown";
    }

    public static boolean putValue(SPUtils spUtils, String key, String value, Class cls) {
        if (cls == null) return false;
        if (String.class.equals(cls)) {
            spUtils.put(key, value);
            return true;
        }
        if (Integer.class.equals(cls)) {
            try {
                int val = Integer.parseInt(value);
                spUtils.put(key, val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (Float.class.equals(cls)) {
            try {
                float val = Float.parseFloat(value);
                spUtils.put(key, val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (Long.class.equals(cls)) {
            try {
                long val = Long.parseLong(value);
                spUtils.put(key, val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
