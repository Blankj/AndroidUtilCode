package com.blankj.utilcode.util;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 * 具体经纬度参考已验证，验证地址：http://www.gpsspg.com/maps.htm
 *
 * result: 所有坐标误差均在 8m 以内
 *
 * author: sayyid
 * qq    : 943719652
 * time  : 2017/05/12
 * desc  : CoordinateConvertUtilsTest 单元测试
 */

public class CoordinateConvertUtilsTest {

    // 以下为各个坐标系的 天安门坐标
    private static final double[] locationWGS84 = new double[] { 39.9075017400,116.3912022800 };
    private static final double[] locationGCJ02 = new double[] { 39.9088600000,116.3973900000 };
    private static final double[] locationBD09 = new double[] { 39.9152478931,116.4038206839 };

    // 以下为美国纽约坐标
    private static final double[]newyorkWGS84 = new double[] { 40.7127837000,-74.0059413000 };

    @Test
    public void gcj2BD09() throws Exception {
        double[] BD09 = CoordinateConvertUtils.gcj2BD09(locationGCJ02[0], locationGCJ02[1]);
        double distance = CoordinateConvertUtils.distance(locationBD09[0], locationBD09[1],  BD09[0],  BD09[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void bd092GCJ() {
        double[] GCJ02 = CoordinateConvertUtils.bd092GCJ(locationBD09[0], locationBD09[1]);
        double distance = CoordinateConvertUtils.distance(locationGCJ02[0], locationGCJ02[1],  GCJ02[0],  GCJ02[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void bd092WGS() {
        double[] WGS84 = CoordinateConvertUtils.bd092WGS(locationBD09[0], locationBD09[1]);
        double distance = CoordinateConvertUtils.distance(locationWGS84[0], locationWGS84[1],  WGS84[0],  WGS84[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void wgs2BD09() {
        double[] BD09 = CoordinateConvertUtils.wgs2BD09(locationWGS84[0], locationWGS84[1]);
        double distance = CoordinateConvertUtils.distance(locationBD09[0], locationBD09[1],  BD09[0],  BD09[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void wgs2GCJ() {
        double[] GCJ02 = CoordinateConvertUtils.wgs2GCJ(locationWGS84[0], locationWGS84[1]);
        double distance = CoordinateConvertUtils.distance(locationGCJ02[0], locationGCJ02[1],  GCJ02[0],  GCJ02[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void gcj2WGS() {
        double[] WGS84 = CoordinateConvertUtils.gcj2WGS(locationGCJ02[0], locationGCJ02[1]);
        double distance = CoordinateConvertUtils.distance(locationWGS84[0], locationWGS84[1],  WGS84[0],  WGS84[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void gcj2WGSExactly() {
        double[] WGS84 = CoordinateConvertUtils.gcj2WGSExactly(locationGCJ02[0], locationGCJ02[1]);
        double distance = CoordinateConvertUtils.distance(locationWGS84[0], locationWGS84[1],  WGS84[0],  WGS84[1]);
        System.out.println("distance: " + distance);
        assertThat( distance < 10).isTrue();
    }

    @Test
    public void outOfChina() {
        assertThat( CoordinateConvertUtils.outOfChina(locationWGS84[0], locationWGS84[1])).isFalse();
        assertThat( CoordinateConvertUtils.outOfChina(newyorkWGS84[0], newyorkWGS84[1])).isTrue();
    }
}
