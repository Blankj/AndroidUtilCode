package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

import static com.blankj.utilcode.util.StringUtils.equalsIgnoreCase;
import static com.blankj.utilcode.util.StringUtils.isEmpty;
import static com.blankj.utilcode.util.StringUtils.isSpace;
import static com.blankj.utilcode.util.StringUtils.length;
import static com.blankj.utilcode.util.StringUtils.lowerFirstLetter;
import static com.blankj.utilcode.util.StringUtils.null2Length0;
import static com.blankj.utilcode.util.StringUtils.reverse;
import static com.blankj.utilcode.util.StringUtils.toDBC;
import static com.blankj.utilcode.util.StringUtils.toSBC;
import static com.blankj.utilcode.util.StringUtils.upperFirstLetter;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : StringUtils单元测试
 * </pre>
 */
public class StringUtilsTest {

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(isEmpty(""));
        Assert.assertTrue(isEmpty(null));
        Assert.assertFalse(isEmpty(" "));
    }

    @Test
    public void testIsSpace() throws Exception {
        Assert.assertTrue(isSpace(""));
        Assert.assertTrue(isSpace(null));
        Assert.assertTrue(isSpace(" "));
        Assert.assertTrue(isSpace("　 \n\t\r"));
    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertTrue(StringUtils.equals(null, null));
        Assert.assertTrue(StringUtils.equals("blankj", "blankj"));
        Assert.assertFalse(StringUtils.equals("blankj", "Blankj"));
    }

    @Test
    public void testEqualsIgnoreCase() throws Exception {
        Assert.assertTrue(equalsIgnoreCase(null, null));
        Assert.assertFalse(equalsIgnoreCase(null, "blankj"));
        Assert.assertTrue(equalsIgnoreCase("blankj", "Blankj"));
        Assert.assertTrue(equalsIgnoreCase("blankj", "blankj"));
        Assert.assertFalse(equalsIgnoreCase("blankj", "blank"));
    }

    @Test
    public void testNull2Length0() throws Exception {
        Assert.assertEquals("", null2Length0(null));
    }

    @Test
    public void testLength() throws Exception {
        Assert.assertEquals(0, length(null));
        Assert.assertEquals(0, length(""));
        Assert.assertEquals(6, length("blankj"));
    }

    @Test
    public void testUpperFirstLetter() throws Exception {
        Assert.assertEquals("Blankj", upperFirstLetter("blankj"));
        Assert.assertEquals("Blankj", upperFirstLetter("Blankj"));
        Assert.assertEquals("1Blankj", upperFirstLetter("1Blankj"));
    }

    @Test
    public void testLowerFirstLetter() throws Exception {
        Assert.assertEquals("blankj", lowerFirstLetter("blankj"));
        Assert.assertEquals("blankj", lowerFirstLetter("Blankj"));
        Assert.assertEquals("1blankj", lowerFirstLetter("1blankj"));
    }

    @Test
    public void testReverse() throws Exception {
        Assert.assertEquals("jknalb", reverse("blankj"));
        Assert.assertEquals("knalb", reverse("blank"));
        Assert.assertEquals("文中试测", reverse("测试中文"));
        Assert.assertNull(reverse(null));
    }

    @Test
    public void testToDBC() throws Exception {
        Assert.assertEquals(" ,.&", toDBC("　，．＆"));
    }

    @Test
    public void testToSBC() throws Exception {
        Assert.assertEquals("　，．＆", toSBC(" ,.&"));
    }
}