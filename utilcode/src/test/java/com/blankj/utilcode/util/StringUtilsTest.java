package com.blankj.utilcode.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : test StringUtils
 * </pre>
 */
public class StringUtilsTest {

    @Test
    public void isEmpty() throws Exception {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
        assertFalse(StringUtils.isEmpty(" "));
    }

    @Test
    public void isSpace() throws Exception {
        assertTrue(StringUtils.isSpace(""));
        assertTrue(StringUtils.isSpace(null));
        assertTrue(StringUtils.isSpace(" "));
        assertTrue(StringUtils.isSpace("　 \n\t\r"));
    }

    @Test
    public void equals() throws Exception {
        assertTrue(StringUtils.equals(null, null));
        assertTrue(StringUtils.equals("blankj", "blankj"));
        assertFalse(StringUtils.equals("blankj", "Blankj"));
    }

    @Test
    public void equalsIgnoreCase() throws Exception {
        assertTrue(StringUtils.equalsIgnoreCase(null, null));
        assertFalse(StringUtils.equalsIgnoreCase(null, "blankj"));
        assertTrue(StringUtils.equalsIgnoreCase("blankj", "Blankj"));
        assertTrue(StringUtils.equalsIgnoreCase("blankj", "blankj"));
        assertFalse(StringUtils.equalsIgnoreCase("blankj", "blank"));
    }

    @Test
    public void null2Length0() throws Exception {
        assertEquals("", StringUtils.null2Length0(null));
    }

    @Test
    public void length() throws Exception {
        assertEquals(0, StringUtils.length(null));
        assertEquals(0, StringUtils.length(""));
        assertEquals(6, StringUtils.length("blankj"));
    }

    @Test
    public void upperFirstLetter() throws Exception {
        assertEquals("Blankj", StringUtils.upperFirstLetter("blankj"));
        assertEquals("Blankj", StringUtils.upperFirstLetter("Blankj"));
        assertEquals("1Blankj", StringUtils.upperFirstLetter("1Blankj"));
    }

    @Test
    public void lowerFirstLetter() throws Exception {
        assertEquals("blankj", StringUtils.lowerFirstLetter("blankj"));
        assertEquals("blankj", StringUtils.lowerFirstLetter("Blankj"));
        assertEquals("1blankj", StringUtils.lowerFirstLetter("1blankj"));
    }

    @Test
    public void reverse() throws Exception {
        assertEquals("jknalb", StringUtils.reverse("blankj"));
        assertEquals("knalb", StringUtils.reverse("blank"));
        assertEquals("文中试测", StringUtils.reverse("测试中文"));
        assertNull(StringUtils.reverse(null));
    }

    @Test
    public void toDBC() throws Exception {
        assertEquals(" ,.&", StringUtils.toDBC("　，．＆"));
    }

    @Test
    public void toSBC() throws Exception {
        assertEquals("　，．＆", StringUtils.toSBC(" ,.&"));
    }
}