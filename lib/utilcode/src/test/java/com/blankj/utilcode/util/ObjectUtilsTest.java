package com.blankj.utilcode.util;

import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/24
 *     desc  : test ObjectUtils
 * </pre>
 */
public class ObjectUtilsTest extends BaseTest {

    @Test
    public void isEmpty() {
        StringBuilder sb = new StringBuilder("");
        StringBuilder sb1 = new StringBuilder(" ");
        String string = "";
        String string1 = " ";
        int[][] arr = new int[][]{};
        LinkedList<Integer> list = new LinkedList<>();
        HashMap<String, Integer> map = new HashMap<>();
        SimpleArrayMap<String, Integer> sam = new SimpleArrayMap<>();
        SparseArray<String> sa = new SparseArray<>();
        SparseBooleanArray sba = new SparseBooleanArray();
        SparseIntArray sia = new SparseIntArray();
        SparseLongArray sla = new SparseLongArray();
        LongSparseArray<String> lsa = new LongSparseArray<>();
        android.util.LongSparseArray<String> lsaV4 = new android.util.LongSparseArray<>();

        assertTrue(ObjectUtils.isEmpty(sb));
        assertFalse(ObjectUtils.isEmpty(sb1));
        assertTrue(ObjectUtils.isEmpty(string));
        assertFalse(ObjectUtils.isEmpty(string1));
        assertTrue(ObjectUtils.isEmpty(arr));
        assertTrue(ObjectUtils.isEmpty(list));
        assertTrue(ObjectUtils.isEmpty(map));
        assertTrue(ObjectUtils.isEmpty(sam));
        assertTrue(ObjectUtils.isEmpty(sa));
        assertTrue(ObjectUtils.isEmpty(sba));
        assertTrue(ObjectUtils.isEmpty(sia));
        assertTrue(ObjectUtils.isEmpty(sla));
        assertTrue(ObjectUtils.isEmpty(lsa));
        assertTrue(ObjectUtils.isEmpty(lsaV4));

        assertTrue(!ObjectUtils.isNotEmpty(sb));
        assertFalse(!ObjectUtils.isNotEmpty(sb1));
        assertTrue(!ObjectUtils.isNotEmpty(string));
        assertFalse(!ObjectUtils.isNotEmpty(string1));
        assertTrue(!ObjectUtils.isNotEmpty(arr));
        assertTrue(!ObjectUtils.isNotEmpty(list));
        assertTrue(!ObjectUtils.isNotEmpty(map));
        assertTrue(!ObjectUtils.isNotEmpty(sam));
        assertTrue(!ObjectUtils.isNotEmpty(sa));
        assertTrue(!ObjectUtils.isNotEmpty(sba));
        assertTrue(!ObjectUtils.isNotEmpty(sia));
        assertTrue(!ObjectUtils.isNotEmpty(sla));
        assertTrue(!ObjectUtils.isNotEmpty(lsa));
        assertTrue(!ObjectUtils.isNotEmpty(lsaV4));
    }

    @Test
    public void equals() {
        assertTrue(ObjectUtils.equals(1, 1));
        assertTrue(ObjectUtils.equals("str", "str"));
        assertTrue(ObjectUtils.equals(null, null));

        assertFalse(ObjectUtils.equals(null, 1));
        assertFalse(ObjectUtils.equals(null, ""));
    }

}