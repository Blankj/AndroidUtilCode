package com.blankj.utilcode.utils;

import android.text.TextUtils;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.ShadowLog;

import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/6
 *     desc  : EncryptUtils单元测试
 * </pre>
 */
public class EncryptUtilsTest {

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
    }

    static String blankjMD5 = "AAC25CD336E01C8655F4EC7875445A60";
    static String blankjSHA = "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C";

    @Test
    public void testGetMD5() throws Exception {
        assertThat(EncryptUtils.getMD5("blankj")).isEqualTo(blankjMD5);
        assertThat(EncryptUtils.getMD5("blank", "j")).isEqualTo(blankjMD5);
        assertThat(EncryptUtils.getMD5("blankj".getBytes())).isEqualTo(blankjMD5);
        assertThat(EncryptUtils.getMD5("blank".getBytes(), "j".getBytes())).isEqualTo(blankjMD5);
    }

    @Test
    public void testEncryptMD5() throws Exception {
        assertThat(EncryptUtils.bytes2Hex(EncryptUtils.encryptMD5("blankj".getBytes()))).isEqualTo(blankjMD5);
    }

    @Test
    public void testGetMD5File() throws Exception {
        System.out.println(TextUtils.htmlEncode(""));
    }

    @Test
    public void testGetMD5File1() throws Exception {

    }

    @Test
    public void testGetSHA() throws Exception {
        assertThat(EncryptUtils.getSHA("blankj")).isEqualTo(blankjSHA);
        assertThat(EncryptUtils.getSHA("blankj".getBytes())).isEqualTo(blankjSHA);
    }

    @Test
    public void testEncryptSHA() throws Exception {
        assertThat(EncryptUtils.bytes2Hex(EncryptUtils.encryptSHA("blankj".getBytes()))).isEqualTo(blankjSHA);
    }

    @Test
    public void testBytes2Hex() throws Exception {
        assertThat(EncryptUtils.bytes2Hex(new byte[]{(byte) 0xff, (byte) 0x11})).isEqualTo("FF11");
    }
}