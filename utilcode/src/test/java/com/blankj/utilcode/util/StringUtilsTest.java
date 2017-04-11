package com.blankj.utilcode.util;

import org.junit.Test;

import static com.blankj.utilcode.util.StringUtils.*;
import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/16
 *     desc  : StringUtils单元测试
 * </pre>
 */
public class StringUtilsTest {

    @Test
    public void testIsEmpty() throws Exception {
        assertThat(isEmpty("")).isTrue();
        assertThat(isEmpty(null)).isTrue();
        assertThat(isEmpty(" ")).isFalse();
    }

    @Test
    public void testIsSpace() throws Exception {
        assertThat(isSpace("")).isTrue();
        assertThat(isSpace(null)).isTrue();
        assertThat(isSpace(" ")).isTrue();
        assertThat(isSpace("　 \n\t\r")).isTrue();
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(StringUtils.equals("blankj", "blankj")).isEqualTo(true);
        assertThat(StringUtils.equals("blankj", "Blankj")).isEqualTo(false);
    }

    @Test
    public void testEqualsIgnoreCase() throws Exception {
        assertThat(equalsIgnoreCase("blankj", "Blankj")).isEqualTo(true);
        assertThat(equalsIgnoreCase("blankj", "blankj")).isEqualTo(true);
        assertThat(equalsIgnoreCase("blankj", "blank")).isEqualTo(false);
    }

    @Test
    public void testNull2Length0() throws Exception {
        assertThat(null2Length0(null)).isEqualTo("");
    }

    @Test
    public void testLength() throws Exception {
        assertThat(length(null)).isEqualTo(0);
        assertThat(length("")).isEqualTo(0);
        assertThat(length("blankj")).isEqualTo(6);
    }

    @Test
    public void testUpperFirstLetter() throws Exception {
        assertThat(upperFirstLetter("blankj")).isEqualTo("Blankj");
        assertThat(upperFirstLetter("Blankj")).isEqualTo("Blankj");
        assertThat(upperFirstLetter("1Blankj")).isEqualTo("1Blankj");
    }

    @Test
    public void testLowerFirstLetter() throws Exception {
        assertThat(lowerFirstLetter("blankj")).isEqualTo("blankj");
        assertThat(lowerFirstLetter("Blankj")).isEqualTo("blankj");
        assertThat(lowerFirstLetter("1blankj")).isEqualTo("1blankj");
    }

    @Test
    public void testReverse() throws Exception {
        assertThat(reverse("blankj")).isEqualTo("jknalb");
        assertThat(reverse("blank")).isEqualTo("knalb");
        assertThat(reverse("测试中文")).isEqualTo("文中试测");
        assertThat(reverse(null)).isNull();
    }

    @Test
    public void testToDBC() throws Exception {
        assertThat(toDBC("　，．＆")).isEqualTo(" ,.&");
    }

    @Test
    public void testToSBC() throws Exception {
        assertThat(toSBC(" ,.&")).isEqualTo("　，．＆");
    }
}