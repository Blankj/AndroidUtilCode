package com.blankj.utilcode.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import static com.blankj.utilcode.utils.ConvertUtils.*;
import static com.blankj.utilcode.utils.EncodeUtils.*;
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

    String blankjMD2 = "15435017570D8A73449E25C4622E17A4";
    String blankjMD5 = "AAC25CD336E01C8655F4EC7875445A60";
    String blankjSHA1 = "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C";
    String blankjSHA224 = "F4C5C0E8CF56CAC4D06DB6B523F67621859A9D79BDA4B2AC03097D5F";
    String blankjSHA256 = "8BD80AE90DFBA112786367BEBDDEE60A638EF5B82682EDF8F3D3CA8E6BFEF648";
    String blankjSHA384 = "BF831E5221FC108D6A72ACB888BA3EB0C030A5F01BA2F739856BE70681D86F992B85E0D461101C74BAEDA895BD422557";
    String blankjSHA512 = "D59D31067F614ED3586F85A31FEFDB7F33096316DA26EBE0FF440B241C8560D96650F100D78C512560C976949EFA89CB5D5589DCF68C7FAADE98F03BCFEC2B45";

    @Test
    public void testEncryptMD2() throws Exception {
        assertThat(encryptMD2ToString("blankj")).isEqualTo(blankjMD2);
        assertThat(encryptMD2ToString("blankj".getBytes())).isEqualTo(blankjMD2);
        assertThat(encryptMD2("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjMD2));
    }

    @Test
    public void testEncryptMD5() throws Exception {
        assertThat(encryptMD5ToString("blankj")).isEqualTo(blankjMD5);
        assertThat(encryptMD5ToString("blankj".getBytes())).isEqualTo(blankjMD5);
        assertThat(encryptMD5("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjMD5));
    }

    @Test
    public void testEncryptSHA1() throws Exception {
        assertThat(encryptSHA1ToString("blankj")).isEqualTo(blankjSHA1);
        assertThat(encryptSHA1ToString("blankj".getBytes())).isEqualTo(blankjSHA1);
        assertThat(encryptSHA1("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjSHA1));
    }

    @Test
    public void testEncryptSHA224() throws Exception {
        assertThat(encryptSHA224ToString("blankj")).isEqualTo(blankjSHA224);
        assertThat(encryptSHA224ToString("blankj".getBytes())).isEqualTo(blankjSHA224);
        assertThat(encryptSHA224("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjSHA224));
    }

    @Test
    public void testEncryptSHA256() throws Exception {
        assertThat(encryptSHA256ToString("blankj")).isEqualTo(blankjSHA256);
        assertThat(encryptSHA256ToString("blankj".getBytes())).isEqualTo(blankjSHA256);
        assertThat(encryptSHA256("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjSHA256));
    }

    @Test
    public void testEncryptSHA384() throws Exception {
        assertThat(encryptSHA384ToString("blankj")).isEqualTo(blankjSHA384);
        assertThat(encryptSHA384ToString("blankj".getBytes())).isEqualTo(blankjSHA384);
        assertThat(encryptSHA384("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjSHA384));
    }

    @Test
    public void testEncryptSHA512() throws Exception {
        assertThat(encryptSHA512ToString("blankj")).isEqualTo(blankjSHA512);
        assertThat(encryptSHA512ToString("blankj".getBytes())).isEqualTo(blankjSHA512);
        assertThat(encryptSHA512("blankj".getBytes())).isEqualTo(hexString2Bytes(blankjSHA512));
    }

    String dataDES = "0008DB3345AB0223";
    String keyDES = "6801020304050607";
    String resDES = "1F7962581118F360";
    byte[] bytesDataDES = hexString2Bytes(dataDES);
    byte[] bytesKeyDES = hexString2Bytes(keyDES);
    byte[] bytesResDES = hexString2Bytes(resDES);

    @Test
    public void testEncryptDES() throws Exception {
        assertThat(encryptDES(bytesDataDES, bytesKeyDES)).isEqualTo(bytesResDES);
        assertThat(encryptDES2HexString(bytesDataDES, bytesKeyDES)).isEqualTo(resDES);
        assertThat(encryptDES2Base64(bytesDataDES, bytesKeyDES)).isEqualTo(base64Encode(bytesResDES));
    }

    @Test
    public void testDecryptDES() throws Exception {
        assertThat(decryptDES(bytesResDES, bytesKeyDES)).isEqualTo(bytesDataDES);
        assertThat(decryptHexStringDES(resDES, bytesKeyDES)).isEqualTo(bytesDataDES);
        assertThat(decryptBase64DES(base64Encode(bytesResDES), bytesKeyDES)).isEqualTo(bytesDataDES);
    }

    String data3DES = "1111111111111111";
    String key3DES = "111111111111111111111111111111111111111111111111";
    String res3DES = "F40379AB9E0EC533";
    byte[] bytesDataDES3 = hexString2Bytes(data3DES);
    byte[] bytesKeyDES3 = hexString2Bytes(key3DES);
    byte[] bytesResDES3 = hexString2Bytes(res3DES);

    @Test
    public void testEncrypt3DES() throws Exception {
        assertThat(encrypt3DES(bytesDataDES3, bytesKeyDES3)).isEqualTo(bytesResDES3);
        assertThat(encrypt3DES2HexString(bytesDataDES3, bytesKeyDES3)).isEqualTo(res3DES);
        assertThat(encrypt3DES2Base64(bytesDataDES3, bytesKeyDES3)).isEqualTo(base64Encode(bytesResDES3));
    }

    @Test
    public void testDecrypt3DES() throws Exception {
        assertThat(decrypt3DES(bytesResDES3, bytesKeyDES3)).isEqualTo(bytesDataDES3);
        assertThat(decryptHexString3DES(res3DES, bytesKeyDES3)).isEqualTo(bytesDataDES3);
        assertThat(decryptBase64_3DES(base64Encode(bytesResDES3), bytesKeyDES3)).isEqualTo(bytesDataDES3);
    }

    String dataAES = "11111111111111111111111111111111";
    String keyAES = "11111111111111111111111111111111";
    String resAES = "E56E26F5608B8D268F2556E198A0E01B";
    byte[] bytesDataAES = hexString2Bytes(dataAES);
    byte[] bytesKeyAES = hexString2Bytes(keyAES);
    byte[] bytesResAES = hexString2Bytes(resAES);

    @Test
    public void testEncryptAES() throws Exception {
        assertThat(encryptAES(bytesDataAES, bytesKeyAES)).isEqualTo(bytesResAES);
        assertThat(encryptAES2HexString(bytesDataAES, bytesKeyAES)).isEqualTo(resAES);
        assertThat(encryptAES2Base64(bytesDataAES, bytesKeyAES)).isEqualTo(base64Encode(bytesResAES));
    }

    @Test
    public void testDecryptAES() throws Exception {
        assertThat(decryptAES(bytesResAES, bytesKeyAES)).isEqualTo(bytesDataAES);
        assertThat(decryptHexStringAES(resAES, bytesKeyAES)).isEqualTo(bytesDataAES);
        assertThat(decryptBase64AES(base64Encode(bytesResAES), bytesKeyAES)).isEqualTo(bytesDataAES);
    }

    String path = System.getProperty("user.dir") + "\\src\\test\\res\\";
    String md5 = "7F138A09169B250E9DCB378140907378";

    @Test
    public void testEncryptMD5File() throws Exception {
        assertThat(encryptMD5File2String(new File(path + File.separator + "MD5.txt"))).isEqualTo(md5);
    }
}