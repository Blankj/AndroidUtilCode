package com.blankj.utilcode.utils;


import org.junit.Test;

import static com.blankj.utilcode.utils.ConvertUtils.*;
import static com.blankj.utilcode.utils.TestUtils.BASEPATH;
import static com.blankj.utilcode.utils.TestUtils.SEP;
import static com.google.common.truth.Truth.assertThat;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/13
 *     desc  : ConvertUtils单元测试
 * </pre>
 */
public class ConvertUtilsTest {


    String path = BASEPATH + "convert" + SEP;
    byte[] mBytes = new byte[]{0x00, 0x08, (byte) 0xdb, 0x33, 0x45, (byte) 0xab, 0x02, 0x23};
    String hexString = "0008DB3345AB0223";

    @Test
    public void testBytes2HexString() throws Exception {
        assertThat(bytes2HexString(mBytes)).isEqualTo(hexString);
    }

    @Test
    public void testHexString2Bytes() throws Exception {
        assertThat(hexString2Bytes(hexString)).isEqualTo(mBytes);
    }

    char[] mChars1 = new char[]{'0', '1', '2'};
    byte[] mBytes1 = new byte[]{48, 49, 50};

    @Test
    public void testChars2Bytes() throws Exception {
        assertThat(chars2Bytes(mChars1)).isEqualTo(mBytes1);
    }

    @Test
    public void testBytes2Chars() throws Exception {
        assertThat(bytes2Chars(mBytes1)).isEqualTo(mChars1);
    }

    @Test
    public void testInputStream2BytesAndBytes2InputStream() throws Exception {
        String string = "this is test string";
        assertThat(new String(inputStream2Bytes(
                bytes2InputStream(string.getBytes("UTF-8")))))
                .isEqualTo(string);
    }

    @Test
    public void testInputStream2StringAndString2InputStream() throws Exception {
        String string = "this is test string";
        assertThat(inputStream2String(
                string2InputStream(string, "UTF-8")
                , "UTF-8")).isEqualTo(string);
    }
}