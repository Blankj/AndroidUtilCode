package com.blankj.subutil.util;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Create by Faramarz Afzali on 2020/9/5
 */


@RunWith(JUnit4.class)
public class TestTempConversion {

    private float delta = 1e-15f;

    @Test
    public void testCToF() {
        Assert.assertEquals(32f, TemperatureUtils.cToF(0f), delta);
    }

    @Test
    public void testCToK() {
        Assert.assertEquals(273.15f, TemperatureUtils.cToK(0f), delta);
    }


    @Test
    public void testFToC() {
        Assert.assertEquals(-17.777779f, TemperatureUtils.fToC(0f), delta);
    }

    @Test
    public void testFToK() {
        Assert.assertEquals(255.3722222222f, TemperatureUtils.fToK(0f), delta);
    }


    @Test
    public void testKToC() {
        Assert.assertEquals(-273.15f, TemperatureUtils.kToC(0f), delta);
    }

    @Test
    public void testKToF() {
        Assert.assertEquals(-459.67f, TemperatureUtils.kToF(0f), delta);
    }

}
