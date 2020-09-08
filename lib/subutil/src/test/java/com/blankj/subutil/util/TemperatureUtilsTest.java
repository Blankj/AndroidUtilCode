package com.blankj.subutil.util;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/03/22
 *     desc  : TemperatureUtils 单元测试
 * </pre>
 */
@RunWith(JUnit4.class)
public class TemperatureUtilsTest {

    private float delta = 1e-15f;

    @Test
    public void cToF() {
        Assert.assertEquals(32f, TemperatureUtils.cToF(0f), delta);
    }

    @Test
    public void cToK() {
        Assert.assertEquals(273.15f, TemperatureUtils.cToK(0f), delta);
    }


    @Test
    public void fToC() {
        Assert.assertEquals(-17.777779f, TemperatureUtils.fToC(0f), delta);
    }

    @Test
    public void fToK() {
        Assert.assertEquals(255.3722222222f, TemperatureUtils.fToK(0f), delta);
    }


    @Test
    public void kToC() {
        Assert.assertEquals(-273.15f, TemperatureUtils.kToC(0f), delta);
    }

    @Test
    public void kToF() {
        Assert.assertEquals(-459.67f, TemperatureUtils.kToF(0f), delta);
    }

}
