package com.blankj.utilcode.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.util.Collection;
import java.util.Collections;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/26
 *     desc  : utils about container
 * </pre>
 */
public final class ContainerUtils {

    private ContainerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(final Collection obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final java.util.Map obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final SimpleArrayMap obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final SparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final SparseBooleanArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final SparseIntArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final android.support.v4.util.LongSparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isEmpty(final android.util.LongSparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isEmpty(final SparseLongArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static <V> boolean isNotEmpty(V[] obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final Collection obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final java.util.Map obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SimpleArrayMap obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseBooleanArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseIntArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final android.support.v4.util.LongSparseArray obj) {
        return !isEmpty(obj);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isNotEmpty(final android.util.LongSparseArray obj) {
        return !isEmpty(obj);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isNotEmpty(final SparseLongArray obj) {
        return !isEmpty(obj);
    }


    public static final class Array {

        private Array() {
            throw new UnsupportedOperationException("u can't instantiate me...");
        }

        public static <T> int getSize(final T[] arr) {
            if (isEmpty(arr)) return 0;
            return arr.length;
        }

        public static <T> void reverse(final T[] arr) {
            int size = getSize(arr);
            if (size <= 1) return;
            int mid = size >> 1;
            T tmp;
            for (int i = 0; i < mid; i++) {
                tmp = arr[i];
                arr[i] = arr[size - i - 1];
                arr[size - i - 1] = tmp;
            }
        }
    }


    public static final class List {

        private List() {
            throw new UnsupportedOperationException("u can't instantiate me...");
        }

        public static <T> int getSize(java.util.List<T> list) {
            if (isEmpty(list)) return 0;
            return list.size();
        }

        public static <V> void reverse(java.util.List<V> list) {
            if (getSize(list) <= 1) return;
            Collections.reverse(list);
        }

    }


    public static final class Map {

        private Map() {
            throw new UnsupportedOperationException("u can't instantiate me...");
        }

        public static <K, V> int getSize(java.util.Map<K, V> map) {
            if (isEmpty(map)) return 0;
            return map.size();
        }


    }


}
