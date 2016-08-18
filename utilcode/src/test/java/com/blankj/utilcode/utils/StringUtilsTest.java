package com.blankj.utilcode.utils;

import org.junit.Test;

import static com.blankj.utilcode.utils.StringUtils.*;
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
        assertThat(isSpace("　")).isFalse();
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
    public void testToDBC() throws Exception {
        assertThat(toDBC("　，．＆")).isEqualTo(" ,.&");
    }

    @Test
    public void testToSBC() throws Exception {
        assertThat(toSBC(" ,.&")).isEqualTo("　，．＆");
    }
}