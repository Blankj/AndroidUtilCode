package com.blankj.utilcode.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/04/09
 *     desc  :
 * </pre>
 */
public class PathUtilsTest extends BaseTest {

    @Test
    public void join() {
        assertEquals(PathUtils.join("", ""), "");
        assertEquals(PathUtils.join("", "data"), "/data");

        assertEquals(PathUtils.join("", "//data"), "/data");
        assertEquals(PathUtils.join("", "data//"), "/data");
        assertEquals(PathUtils.join("", "//data//"), "/data");

        assertEquals(PathUtils.join("/sdcard", "data"), "/sdcard/data");
        assertEquals(PathUtils.join("/sdcard/", "data"), "/sdcard/data");
    }
}