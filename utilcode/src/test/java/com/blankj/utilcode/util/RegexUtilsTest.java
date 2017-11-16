package com.blankj.utilcode.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : RegexUtils单元测试
 * </pre>
 */
public class RegexUtilsTest {

    @Test
    public void isMobileSimple() throws Exception {
        assertTrue(RegexUtils.isMobileSimple("11111111111"));
    }

    @Test
    public void isMobileExact() throws Exception {
        assertFalse(RegexUtils.isMobileExact("11111111111"));
        assertTrue(RegexUtils.isMobileExact("13888880000"));
    }

    @Test
    public void isTel() throws Exception {
        assertTrue(RegexUtils.isTel("033-88888888"));
        assertTrue(RegexUtils.isTel("033-7777777"));
        assertTrue(RegexUtils.isTel("0444-88888888"));
        assertTrue(RegexUtils.isTel("0444-7777777"));
        assertTrue(RegexUtils.isTel("033 88888888"));
        assertTrue(RegexUtils.isTel("033 7777777"));
        assertTrue(RegexUtils.isTel("0444 88888888"));
        assertTrue(RegexUtils.isTel("0444 7777777"));
        assertTrue(RegexUtils.isTel("03388888888"));
        assertTrue(RegexUtils.isTel("0337777777"));
        assertTrue(RegexUtils.isTel("044488888888"));
        assertTrue(RegexUtils.isTel("04447777777"));

        assertFalse(RegexUtils.isTel("133-88888888"));
        assertFalse(RegexUtils.isTel("033-666666"));
        assertFalse(RegexUtils.isTel("0444-999999999"));
    }

    @Test
    public void isIDCard18() throws Exception {
        assertTrue(RegexUtils.isIDCard18("33698418400112523x"));
        assertTrue(RegexUtils.isIDCard18("336984184001125233"));
        assertFalse(RegexUtils.isIDCard18("336984184021125233"));
    }

    @Test
    public void isEmail() throws Exception {
        assertTrue(RegexUtils.isEmail("blankj@qq.com"));
        assertFalse(RegexUtils.isEmail("blankj@qq"));
    }

    @Test
    public void isURL() throws Exception {
        assertTrue(RegexUtils.isURL("http://blankj.com"));
        assertFalse(RegexUtils.isURL("https:blank"));
    }

    @Test
    public void isZh() throws Exception {
        assertTrue(RegexUtils.isZh("我"));
        assertFalse(RegexUtils.isZh("wo"));
    }

    @Test
    public void isUsername() throws Exception {
        assertTrue(RegexUtils.isUsername("小明233333"));
        assertFalse(RegexUtils.isUsername("小明"));
        assertFalse(RegexUtils.isUsername("小明233333_"));
    }

    @Test
    public void isDate() throws Exception {
        assertTrue(RegexUtils.isDate("2016-08-16"));
        assertTrue(RegexUtils.isDate("2016-02-29"));
        assertFalse(RegexUtils.isDate("2015-02-29"));
        assertFalse(RegexUtils.isDate("2016-8-16"));
    }

    @Test
    public void isIP() throws Exception {
        assertTrue(RegexUtils.isIP("255.255.255.0"));
        assertFalse(RegexUtils.isIP("256.255.255.0"));
    }

    @Test
    public void isMatch() throws Exception {
        assertTrue(RegexUtils.isMatch("\\d?", "1"));
        assertFalse(RegexUtils.isMatch("\\d?", "a"));
    }

    @Test
    public void getMatches() throws Exception {
        // 贪婪
        System.out.println(RegexUtils.getMatches("b.*j", "blankj blankj"));
        // 懒惰
        System.out.println(RegexUtils.getMatches("b.*?j", "blankj blankj"));
    }

    @Test
    public void getSplits() throws Exception {
        System.out.println(Arrays.asList(RegexUtils.getSplits("1 2 3", " ")));
    }

    @Test
    public void getReplaceFirst() throws Exception {
        System.out.println(RegexUtils.getReplaceFirst("1 2 3", " ", ", "));
    }

    @Test
    public void getReplaceAll() throws Exception {
        System.out.println(RegexUtils.getReplaceAll("1 2 3", " ", ", "));
    }
}