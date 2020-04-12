package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/04/13
 *     desc  : test NumberUtils
 * </pre>
 */
public class NumberUtilsTest {

    @Test
    public void format() {
        double val = Math.PI * 100000;// 314159.2653589793

        Assert.assertEquals("314159.27", NumberUtils.format(val, 2));
        Assert.assertEquals("314159.265", NumberUtils.format(val, 3));

        Assert.assertEquals("314159.27", NumberUtils.format(val, 2, true));
        Assert.assertEquals("314159.26", NumberUtils.format(val, 2, false));

        Assert.assertEquals("00314159.27", NumberUtils.format(val, 8, 2, true));
        Assert.assertEquals("0000314159.27", NumberUtils.format(val, 10, 2, true));

        Assert.assertEquals("314,159.27", NumberUtils.format(val, true, 2));
        Assert.assertEquals("314159.27", NumberUtils.format(val, false, 2));

        Assert.assertEquals("314159.27", NumberUtils.format(val, false, 2, true));
        Assert.assertEquals("314159.26", NumberUtils.format(val, false, 2, false));

        Assert.assertEquals("314159.27", NumberUtils.format(val, false, 2, true));
        Assert.assertEquals("314159.265", NumberUtils.format(val, false, 3, false));
    }

    @Test
    public void float2Double() {
        float val = 3.14f;
        System.out.println((double) val);
        System.out.println(NumberUtils.float2Double(val));
    }
}