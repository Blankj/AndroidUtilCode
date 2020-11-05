package com.blankj.utilcode.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : test StringUtils
 * </pre>
 */
public class StringUtilsTest extends BaseTest {

    @Test
    public void isEmpty() {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
        assertFalse(StringUtils.isEmpty(" "));
    }

    @Test
    public void isTrimEmpty() {
        assertTrue(StringUtils.isTrimEmpty(""));
        assertTrue(StringUtils.isTrimEmpty(null));
        assertTrue(StringUtils.isTrimEmpty(" "));
    }

    @Test
    public void isSpace() {
        assertTrue(StringUtils.isSpace(""));
        assertTrue(StringUtils.isSpace(null));
        assertTrue(StringUtils.isSpace(" "));
        assertTrue(StringUtils.isSpace("　 \n\t\r"));
    }

    @Test
    public void equals() {
        assertTrue(StringUtils.equals(null, null));
        assertTrue(StringUtils.equals("blankj", "blankj"));
        assertFalse(StringUtils.equals("blankj", "Blankj"));
    }

    @Test
    public void equalsIgnoreCase() {
        assertTrue(StringUtils.equalsIgnoreCase(null, null));
        assertFalse(StringUtils.equalsIgnoreCase(null, "blankj"));
        assertTrue(StringUtils.equalsIgnoreCase("blankj", "Blankj"));
        assertTrue(StringUtils.equalsIgnoreCase("blankj", "blankj"));
        assertFalse(StringUtils.equalsIgnoreCase("blankj", "blank"));
    }

    @Test
    public void null2Length0() {
        assertEquals("", StringUtils.null2Length0(null));
    }

    @Test
    public void length() {
        assertEquals(0, StringUtils.length(null));
        assertEquals(0, StringUtils.length(""));
        assertEquals(6, StringUtils.length("blankj"));
    }

    @Test
    public void upperFirstLetter() {
        assertEquals("Blankj", StringUtils.upperFirstLetter("blankj"));
        assertEquals("Blankj", StringUtils.upperFirstLetter("Blankj"));
        assertEquals("1Blankj", StringUtils.upperFirstLetter("1Blankj"));
    }

    @Test
    public void lowerFirstLetter() {
        assertEquals("blankj", StringUtils.lowerFirstLetter("blankj"));
        assertEquals("blankj", StringUtils.lowerFirstLetter("Blankj"));
        assertEquals("1blankj", StringUtils.lowerFirstLetter("1blankj"));
    }

    @Test
    public void reverse() {
        assertEquals("jknalb", StringUtils.reverse("blankj"));
        assertEquals("knalb", StringUtils.reverse("blank"));
        assertEquals("文中试测", StringUtils.reverse("测试中文"));
        assertEquals("", StringUtils.reverse(null));
    }

    @Test
    public void toDBC() {
        assertEquals(" ,.&", StringUtils.toDBC("　，．＆"));
    }

    @Test
    public void toSBC() {
        assertEquals("　，．＆", StringUtils.toSBC(" ,.&"));
    }
}