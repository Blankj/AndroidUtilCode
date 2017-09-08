package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.blankj.utilcode.util.RegexUtils.getMatches;
import static com.blankj.utilcode.util.RegexUtils.getReplaceAll;
import static com.blankj.utilcode.util.RegexUtils.getReplaceFirst;
import static com.blankj.utilcode.util.RegexUtils.getSplits;
import static com.blankj.utilcode.util.RegexUtils.isDate;
import static com.blankj.utilcode.util.RegexUtils.isEmail;
import static com.blankj.utilcode.util.RegexUtils.isIDCard18;
import static com.blankj.utilcode.util.RegexUtils.isIP;
import static com.blankj.utilcode.util.RegexUtils.isMatch;
import static com.blankj.utilcode.util.RegexUtils.isMobileExact;
import static com.blankj.utilcode.util.RegexUtils.isMobileSimple;
import static com.blankj.utilcode.util.RegexUtils.isTel;
import static com.blankj.utilcode.util.RegexUtils.isURL;
import static com.blankj.utilcode.util.RegexUtils.isUsername;
import static com.blankj.utilcode.util.RegexUtils.isZh;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : RegularUtils单元测试
 * </pre>
 */
public class RegexUtilsTest {

    @Test
    public void testIsMobileSimple() throws Exception {
        Assert.assertTrue(isMobileSimple("11111111111"));
    }

    @Test
    public void testIsMobileExact() throws Exception {
        Assert.assertFalse(isMobileExact("11111111111"));
        Assert.assertTrue(isMobileExact("13888880000"));
    }

    @Test
    public void testIsTel() throws Exception {
        Assert.assertTrue(isTel("033-88888888"));
        Assert.assertTrue(isTel("033-7777777"));
        Assert.assertTrue(isTel("0444-88888888"));
        Assert.assertTrue(isTel("0444-7777777"));
        Assert.assertTrue(isTel("033 88888888"));
        Assert.assertTrue(isTel("033 7777777"));
        Assert.assertTrue(isTel("0444 88888888"));
        Assert.assertTrue(isTel("0444 7777777"));
        Assert.assertTrue(isTel("03388888888"));
        Assert.assertTrue(isTel("0337777777"));
        Assert.assertTrue(isTel("044488888888"));
        Assert.assertTrue(isTel("04447777777"));

        Assert.assertFalse(isTel("133-88888888"));
        Assert.assertFalse(isTel("033-666666"));
        Assert.assertFalse(isTel("0444-999999999"));
    }

    @Test
    public void testIsIDCard() throws Exception {
        Assert.assertTrue(isIDCard18("33698418400112523x"));
        Assert.assertTrue(isIDCard18("336984184001125233"));
        Assert.assertFalse(isIDCard18("336984184021125233"));
    }

    @Test
    public void testIsEmail() throws Exception {
        Assert.assertTrue(isEmail("blankj@qq.com"));
        Assert.assertFalse(isEmail("blankj@qq"));
    }

    @Test
    public void testIsURL() throws Exception {
        Assert.assertTrue(isURL("http://blankj.com"));
        Assert.assertFalse(isURL("https:blank"));
    }

    @Test
    public void testIsChz() throws Exception {
        Assert.assertTrue(isZh("我"));
        Assert.assertFalse(isZh("wo"));
    }

    @Test
    public void testIsUsername() throws Exception {
        Assert.assertTrue(isUsername("小明233333"));
        Assert.assertFalse(isUsername("小明"));
        Assert.assertFalse(isUsername("小明233333_"));
    }

    @Test
    public void testIsDate() throws Exception {
        Assert.assertTrue(isDate("2016-08-16"));
        Assert.assertTrue(isDate("2016-02-29"));
        Assert.assertFalse(isDate("2015-02-29"));
        Assert.assertFalse(isDate("2016-8-16"));
    }

    @Test
    public void testIsIP() throws Exception {
        Assert.assertTrue(isIP("255.255.255.0"));
        Assert.assertFalse(isIP("256.255.255.0"));
    }

    @Test
    public void testIsMatch() throws Exception {
        Assert.assertTrue(isMatch("\\d?", "1"));
        Assert.assertFalse(isMatch("\\d?", "a"));
    }

    @Test
    public void testGetMatches() throws Exception {
        // 贪婪
        System.out.println(getMatches("b.*j", "blankj blankj"));
        // 懒惰
        System.out.println(getMatches("b.*?j", "blankj blankj"));
    }

    @Test
    public void testGetSplits() throws Exception {
        System.out.println(Arrays.asList(getSplits("1 2 3", " ")));
    }

    @Test
    public void testGetReplace() throws Exception {
        System.out.println(getReplaceFirst("1 2 3", " ", ", "));
    }

    @Test
    public void testGetReplaceAll() throws Exception {
        System.out.println(getReplaceAll("1 2 3", " ", ", "));
    }
}