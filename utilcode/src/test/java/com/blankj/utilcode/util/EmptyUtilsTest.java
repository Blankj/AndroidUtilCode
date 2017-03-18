package com.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.os.Build;
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

import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/28
 *     desc  : EmptyUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE , sdk = 23)
public class EmptyUtilsTest {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Test
    public void isEmpty() throws Exception {
        String string = "";
        String string1 = " ";
        int[][] arr = new int[][]{};
        int[] arr1 = null;
        LinkedList<Integer> list = new LinkedList<>();
        HashMap<String,Integer> map = new HashMap<>();
        SparseArray<String> sa = new SparseArray<>();
        SparseBooleanArray sba = new SparseBooleanArray();
        SparseIntArray sia = new SparseIntArray();
        SparseLongArray sla = new SparseLongArray();

        assertThat(EmptyUtils.isEmpty(string)).isTrue();
        assertThat(EmptyUtils.isEmpty(string1)).isFalse();
        assertThat(EmptyUtils.isEmpty(arr)).isTrue();
        assertThat(EmptyUtils.isEmpty(arr1)).isTrue();
        assertThat(EmptyUtils.isEmpty(list)).isTrue();
        assertThat(EmptyUtils.isEmpty(map)).isTrue();
        assertThat(EmptyUtils.isEmpty(sa)).isTrue();
        assertThat(EmptyUtils.isEmpty(sba)).isTrue();
        assertThat(EmptyUtils.isEmpty(sia)).isTrue();
        assertThat(EmptyUtils.isEmpty(sla)).isTrue();

        assertThat(!EmptyUtils.isNotEmpty(string)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(string1)).isFalse();
        assertThat(!EmptyUtils.isNotEmpty(arr)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(arr1)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(list)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(map)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(sa)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(sba)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(sia)).isTrue();
        assertThat(!EmptyUtils.isNotEmpty(sla)).isTrue();
    }
}