package com.blankj.utilcode.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*********************************************
 * author: Blankj on 2016/8/6 14:02
 * blog:   http://blankj.com
 * e-mail: blankj@qq.com
 *********************************************/
public class EncryptUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetMD5() throws Exception {
        Assert.assertEquals(EncryptUtils.getMD5("blankj"), "AAC25CD336E01C8655F4EC7875445A60");
    }

    @Test
    public void testGetMD51() throws Exception {
        Assert.assertEquals(EncryptUtils.getMD5("blank", "j"), "AAC25CD336E01C8655F4EC7875445A60");
    }

    @Test
    public void testGetMD52() throws Exception {
        Assert.assertEquals(EncryptUtils.getMD5("blankj".getBytes()), "AAC25CD336E01C8655F4EC7875445A60");
    }

    @Test
    public void testGetMD53() throws Exception {
        Assert.assertEquals(EncryptUtils.getMD5("blank".getBytes(), "j".getBytes()), "AAC25CD336E01C8655F4EC7875445A60");
    }

    @Test
    public void testEncryptMD5() throws Exception {
        Assert.assertEquals(EncryptUtils.bytes2Hex(EncryptUtils.encryptMD5("blankj".getBytes())), "AAC25CD336E01C8655F4EC7875445A60");
    }

    @Test
    public void testGetMD5File() throws Exception {

    }

    @Test
    public void testGetMD5File1() throws Exception {

    }

    @Test
    public void testGetSHA() throws Exception {
        Assert.assertEquals(EncryptUtils.getSHA("blankj"), "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C");
    }

    @Test
    public void testGetSHA1() throws Exception {
        Assert.assertEquals(EncryptUtils.getSHA("blankj".getBytes()), "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C");
    }

    @Test
    public void testEncryptSHA() throws Exception {
        Assert.assertEquals(EncryptUtils.bytes2Hex(EncryptUtils.encryptSHA("blankj".getBytes())), "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C");
    }

    @Test
    public void testBytes2Hex() throws Exception {
        Assert.assertEquals(EncryptUtils.bytes2Hex(new byte[]{(byte) 0xff, (byte) 0x11}), "FF11");
    }
}