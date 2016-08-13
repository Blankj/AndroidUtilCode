package com.blankj.utilcode.utils;


import org.junit.Test;

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

    byte[] mBytes = new byte[]{0x00, 0x08, (byte) 0xdb, 0x33, 0x45, (byte) 0xab, 0x02, 0x23};
    String hexString = "0008DB3345AB0223";

    @Test
    public void testBytes2HexString() throws Exception {
        assertThat(ConvertUtils.bytes2HexString(mBytes)).isEqualTo(hexString);
    }

    @Test
    public void testHexString2Bytes() throws Exception {
        assertThat(ConvertUtils.hexString2Bytes(hexString)).isEqualTo(mBytes);
    }
}