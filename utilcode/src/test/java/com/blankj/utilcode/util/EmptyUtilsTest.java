package com.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.LinkedList;

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
        android.util.LongSparseArray<String> lsa4 = new android.util.LongSparseArray<>();

        Assert.assertTrue(EmptyUtils.isEmpty(string));
        Assert.assertFalse(EmptyUtils.isEmpty(string1));
        Assert.assertTrue(EmptyUtils.isEmpty(arr));
        Assert.assertTrue(EmptyUtils.isEmpty(arr1));
        Assert.assertTrue(EmptyUtils.isEmpty(list));
        Assert.assertTrue(EmptyUtils.isEmpty(map));
        Assert.assertTrue(EmptyUtils.isEmpty(sam));
        Assert.assertTrue(EmptyUtils.isEmpty(sa));
        Assert.assertTrue(EmptyUtils.isEmpty(sba));
        Assert.assertTrue(EmptyUtils.isEmpty(sia));
        Assert.assertTrue(EmptyUtils.isEmpty(sla));
        Assert.assertTrue(EmptyUtils.isEmpty(lsa));
        Assert.assertTrue(EmptyUtils.isEmpty(lsa4));

        Assert.assertTrue(!EmptyUtils.isNotEmpty(string));
        Assert.assertFalse(!EmptyUtils.isNotEmpty(string1));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(arr));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(arr1));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(list));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(map));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(sam));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(sa));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(sba));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(sia));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(sla));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(lsa));
        Assert.assertTrue(!EmptyUtils.isNotEmpty(lsa4));
    }
}