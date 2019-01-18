package com.blankj.utilcode.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/01/15
 *     desc  : test ColorUtils
 * </pre>
 */
public class ColorUtilsTest extends BaseTest {

    @Test
    public void setAlphaComponent() {
        assertEquals(0x80ffffff, ColorUtils.setAlphaComponent(0xffffffff, 0x80));
        assertEquals(0x80ffffff, ColorUtils.setAlphaComponent(0xffffffff, 0.5f));
    }

    @Test
    public void setRedComponent() {
        assertEquals(0xff80ffff, ColorUtils.setRedComponent(0xffffffff, 0x80));
        assertEquals(0xff80ffff, ColorUtils.setRedComponent(0xffffffff, 0.5f));
    }

    @Test
    public void setGreenComponent() {
        assertEquals(0xffff80ff, ColorUtils.setGreenComponent(0xffffffff, 0x80));
        assertEquals(0xffff80ff, ColorUtils.setGreenComponent(0xffffffff, 0.5f));
    }

    @Test
    public void setBlueComponent() {
        assertEquals(0xffffff80, ColorUtils.setBlueComponent(0xffffffff, 0x80));
        assertEquals(0xffffff80, ColorUtils.setBlueComponent(0xffffffff, 0.5f));
    }

    @Test
    public void string2Int() {
        assertEquals(0xffffffff, ColorUtils.string2Int("#ffffff"));
        assertEquals(0xffffffff, ColorUtils.string2Int("#ffffffff"));
        assertEquals(0xffffffff, ColorUtils.string2Int("white"));
    }

    @Test
    public void int2RgbString() {
        assertEquals("#000001", ColorUtils.int2RgbString(1));
        assertEquals("#ffffff", ColorUtils.int2RgbString(0xffffff));
    }

    @Test
    public void int2ArgbString() {
        assertEquals("#ff000001", ColorUtils.int2ArgbString(1));
        assertEquals("#ffffffff", ColorUtils.int2ArgbString(0xffffff));
    }
}