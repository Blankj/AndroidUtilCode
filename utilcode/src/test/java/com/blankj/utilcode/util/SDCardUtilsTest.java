package com.blankj.utilcode.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/23
 *     desc  : SDCard单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SDCardUtilsTest {

    @Test
    public void testIsSDCardEnable() throws Exception {
        System.out.println(SDCardUtils.isSDCardEnable());
    }

    @Test
    public void testGetSDCardPath() throws Exception {
        System.out.println(SDCardUtils.getSDCardPath());
    }
}