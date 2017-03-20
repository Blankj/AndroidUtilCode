package com.blankj.utilcode.util;


import com.blankj.utilcode.constant.MemoryConstants;
import com.blankj.utilcode.constant.TimeConstants;

import org.junit.Test;

import static com.blankj.utilcode.util.ConvertUtils.*;
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

    byte[] mBytes    = new byte[]{0x00, 0x08, (byte) 0xdb, 0x33, 0x45, (byte) 0xab, 0x02, 0x23};
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
    public void testByte2Unit() throws Exception {
        assertThat(byte2MemorySize(MemoryConstants.GB, MemoryConstants.MB) - 1024).isWithin(0.001);
    }

    @Test
    public void testByte2FitSize() throws Exception {
        assertThat(byte2FitMemorySize(1024 * 1024 * 3 + 1024 * 100)).isEqualTo("3.098MB");
    }

    @Test
    public void testMillis2FitTimeSpan() throws Exception {
        long millis = TimeConstants.DAY * 6 + TimeConstants.HOUR * 6
                + TimeConstants.MIN * 6 + TimeConstants.SEC * 6 + 6;
        System.out.println(millis2FitTimeSpan(millis, 7));
        System.out.println(millis2FitTimeSpan(millis, 4));
        System.out.println(millis2FitTimeSpan(millis, 3));
        System.out.println(millis2FitTimeSpan(millis * 4, 5));
    }

    @Test
    public void testBytes2Bits() throws Exception {
        System.out.println(bytes2Bits(new byte[]{0x7F, (byte) 0xFA}));
    }

    @Test
    public void testBits2Bytes() throws Exception {
        System.out.println(bytes2HexString(bits2Bytes("111111111111010")));
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