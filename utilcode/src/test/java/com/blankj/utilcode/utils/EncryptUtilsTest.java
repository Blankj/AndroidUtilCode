package com.blankj.utilcode.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.blankj.utilcode.utils.ConvertUtils.*;
import static com.blankj.utilcode.utils.EncryptUtils.*;
import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/6
 *     desc  : EncryptUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class EncryptUtilsTest {

    String blankjMD5 = "AAC25CD336E01C8655F4EC7875445A60";
    String blankjMD2 = "15435017570D8A73449E25C4622E17A4";
    String blankjSHA1 = "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C";
    String blankjSHA224 = "F4C5C0E8CF56CAC4D06DB6B523F67621859A9D79BDA4B2AC03097D5F";
    String blankjSHA256 = "8BD80AE90DFBA112786367BEBDDEE60A638EF5B82682EDF8F3D3CA8E6BFEF648";
    String blankjSHA384 = "BF831E5221FC108D6A72ACB888BA3EB0C030A5F01BA2F739856BE70681D86F992B85E0D461101C74BAEDA895BD422557";
    String blankjSHA512 = "D59D31067F614ED3586F85A31FEFDB7F33096316DA26EBE0FF440B241C8560D96650F100D78C512560C976949EFA89CB5D5589DCF68C7FAADE98F03BCFEC2B45";

    @Test
    public void testGetMD5() throws Exception {
        assertThat(getMD5("blankj")).isEqualTo(blankjMD5);
        assertThat(getMD5("blank", "j")).isEqualTo(blankjMD5);
        assertThat(getMD5("blankj".getBytes())).isEqualTo(blankjMD5);
        assertThat(getMD5("blank".getBytes(), "j".getBytes())).isEqualTo(blankjMD5);
    }

    @Test
    public void testEncryptMD5() throws Exception {
        assertThat(bytes2HexString(encryptMD5("blankj".getBytes()))).isEqualTo(blankjMD5);
    }

    @Test
    public void testGetMD2() throws Exception {
        assertThat(getMD2("blankj")).isEqualTo(blankjMD2);
        assertThat(getMD2("blankj".getBytes())).isEqualTo(blankjMD2);
    }

    @Test
    public void testEncryptMD2() throws Exception {
        assertThat(bytes2HexString(encryptMD2("blankj".getBytes()))).isEqualTo(blankjMD2);
    }

    @Test
    public void testGetSHA1() throws Exception {
        assertThat(getSHA1("blankj")).isEqualTo(blankjSHA1);
        assertThat(getSHA1("blankj".getBytes())).isEqualTo(blankjSHA1);
    }

    @Test
    public void testEncryptSHA1() throws Exception {
        assertThat(bytes2HexString(encryptSHA1("blankj".getBytes()))).isEqualTo(blankjSHA1);
    }

    @Test
    public void testGetSHA224() throws Exception {
        assertThat(getSHA224("blankj")).isEqualTo(blankjSHA224);
        assertThat(getSHA224("blankj".getBytes())).isEqualTo(blankjSHA224);
    }

    @Test
    public void testEncryptSHA224() throws Exception {
        assertThat(bytes2HexString(encryptSHA224("blankj".getBytes()))).isEqualTo(blankjSHA224);
    }

    @Test
    public void testGetSHA256() throws Exception {
        assertThat(getSHA256("blankj")).isEqualTo(blankjSHA256);
        assertThat(getSHA256("blankj".getBytes())).isEqualTo(blankjSHA256);
    }

    @Test
    public void testEncryptSHA256() throws Exception {
        assertThat(bytes2HexString(encryptSHA256("blankj".getBytes()))).isEqualTo(blankjSHA256);
    }

    @Test
    public void testGetSHA384() throws Exception {
        assertThat(getSHA384("blankj")).isEqualTo(blankjSHA384);
        assertThat(getSHA384("blankj".getBytes())).isEqualTo(blankjSHA384);
    }

    @Test
    public void testEncryptSHA384() throws Exception {
        assertThat(bytes2HexString(encryptSHA384("blankj".getBytes()))).isEqualTo(blankjSHA384);
    }

    @Test
    public void testGetSHA512() throws Exception {
        assertThat(getSHA512("blankj")).isEqualTo(blankjSHA512);
        assertThat(getSHA512("blankj".getBytes())).isEqualTo(blankjSHA512);
    }

    @Test
    public void testEncryptSHA512() throws Exception {
        assertThat(bytes2HexString(encryptSHA512("blankj".getBytes()))).isEqualTo(blankjSHA512);
    }

    @Test
    public void testGetMD5File() throws Exception {

    }

    String data = "0008DB3345AB0223";
    String key = "6801020304050607";
    String des = "1F7962581118F360";

    @Test
    public void testGetDES() throws Exception {
        byte[] bytes = hexString2Bytes(data);
        System.out.print((char) (0));
        System.out.print((char) (8));
        System.out.print((char) (219));
        System.out.print((char) (51));
        System.out.print((char) (69));
        System.out.print((char) (171));
        System.out.print((char) (2));
        System.out.print((char) (35));
        System.out.println();
        for (int i = 0; i < bytes.length; i++) {
            System.out.print((0xff & bytes[i]) + " ");
        }
        //00 08 DB 33 45 AB 02 23
        //20 08 5F 33 45 5F 02 23
        //00 08 219 51 69 171 2 35
        System.out.println();
        String d = new String(bytes);
        String k = new String(hexString2Bytes(key));
        System.out.println(d);
        System.out.println(k);
        assertThat(bytes2HexString(encryptDES(d, k).getBytes())).isEqualTo(new String(hexString2Bytes(des)));
    }

}