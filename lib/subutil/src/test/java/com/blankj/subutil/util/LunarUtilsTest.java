package com.blankj.subutil.util;

import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/08
 *     desc  : LunarUtils 单元测试
 * </pre>
 */
public class LunarUtilsTest {

    @Test
    public void lunarYear2GanZhi() throws Exception {
        System.out.println(LunarUtils.lunarYear2GanZhi(2018));
    }

    @Test
    public void lunar2Solar() throws Exception {
        System.out.println(LunarUtils.lunar2Solar(new LunarUtils.Lunar(2018, 2, 23, false)));
    }

    @Test
    public void solar2Lunar() throws Exception {
        System.out.println(LunarUtils.solar2Lunar(new LunarUtils.Solar(2018, 4, 8)));
    }

}