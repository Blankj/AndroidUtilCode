package com.blankj.utilcode.utils;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/28
 *     desc  : 判空相关工具类
 * </pre>
 */
public class EmptyUtils {

    private EmptyUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否非空
     * <p>不要问我为什么不直接调用{@code !isEmpty(obj);}</p>
     *
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String && obj.toString().length() == 0) {
            return false;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return false;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return false;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return false;
        } else if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return false;
        } else if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return false;
        } else if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return false;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return false;
            }
        }
        return true;
    }
}
