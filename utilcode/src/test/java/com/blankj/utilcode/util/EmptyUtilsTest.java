package com.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/28
 *     desc  : EmptyUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23)
public class EmptyUtilsTest {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Test
    public void isEmpty() throws Exception {
        String string = "";
        String string1 = " ";
        int[][] arr = new int[][]{};
        int[] arr1 = null;
        LinkedList<Integer> list = new LinkedList<>();
        HashMap<String, Integer> map = new HashMap<>();
        SimpleArrayMap<String, Integer> sam = new SimpleArrayMap<>();
        SparseArray<String> sa = new SparseArray<>();
        SparseBooleanArray sba = new SparseBooleanArray();
        SparseIntArray sia = new SparseIntArray();
        SparseLongArray sla = new SparseLongArray();
        LongSparseArray<String> lsa = new LongSparseArray<>();
        android.util.LongSparseArray<String> lsaV4 = new android.util.LongSparseArray<>();

        assertTrue(EmptyUtils.isEmpty(string));
        assertFalse(EmptyUtils.isEmpty(string1));
        assertTrue(EmptyUtils.isEmpty(arr));
        assertTrue(EmptyUtils.isEmpty(arr1));
        assertTrue(EmptyUtils.isEmpty(list));
        assertTrue(EmptyUtils.isEmpty(map));
        assertTrue(EmptyUtils.isEmpty(sam));
        assertTrue(EmptyUtils.isEmpty(sa));
        assertTrue(EmptyUtils.isEmpty(sba));
        assertTrue(EmptyUtils.isEmpty(sia));
        assertTrue(EmptyUtils.isEmpty(sla));
        assertTrue(EmptyUtils.isEmpty(lsa));
        assertTrue(EmptyUtils.isEmpty(lsaV4));

        assertTrue(!EmptyUtils.isNotEmpty(string));
        assertFalse(!EmptyUtils.isNotEmpty(string1));
        assertTrue(!EmptyUtils.isNotEmpty(arr));
        assertTrue(!EmptyUtils.isNotEmpty(arr1));
        assertTrue(!EmptyUtils.isNotEmpty(list));
        assertTrue(!EmptyUtils.isNotEmpty(map));
        assertTrue(!EmptyUtils.isNotEmpty(sam));
        assertTrue(!EmptyUtils.isNotEmpty(sa));
        assertTrue(!EmptyUtils.isNotEmpty(sba));
        assertTrue(!EmptyUtils.isNotEmpty(sia));
        assertTrue(!EmptyUtils.isNotEmpty(sla));
        assertTrue(!EmptyUtils.isNotEmpty(lsa));
        assertTrue(!EmptyUtils.isNotEmpty(lsaV4));
    }
}