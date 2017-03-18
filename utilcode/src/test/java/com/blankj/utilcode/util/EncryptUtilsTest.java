package com.blankj.utilcode.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import static com.blankj.utilcode.util.ConvertUtils.*;
import static com.blankj.utilcode.util.EncodeUtils.*;
import static com.blankj.utilcode.util.EncryptUtils.*;
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
@Config(manifest = Config.NONE ,sdk = 23)
public class EncryptUtilsTest {

    String blankjMD2 = "15435017570D8A73449E25C4622E17A4";
    String blankjMD5 = "AAC25CD336E01C8655F4EC7875445A60";
    String blankjSHA1 = "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C";
    String blankjSHA224 = "F4C5C0E8CF56CAC4D06DB6B523F67621859A9D79BDA4B2AC03097D5F";
    String blankjSHA256 = "8BD80AE90DFBA112786367BEBDDEE60A638EF5B82682EDF8F3D3CA8E6BFEF648";
    String blankjSHA384 =
            "BF831E5221FC108D6A72ACB888BA3EB0C030A5F01BA2F739856BE70681D86F992B85E0D461101C74BAEDA895BD422557";
    String blankjSHA512 =
            "D59D31067F614ED3586F85A31FEFDB7F33096316DA26EBE0FF440B241C8560D96650F100D78C512560C976949EFA89CB5D5589DCF68C7FAADE98F03BCFEC2B45";

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

    //use this site to test https://www.freeformatter.com/hmac-generator.html
    String blankjHmacMD5 = "2BA3FDABEE222522044BEC0CE5D6B490";
    String blankjHmacSHA1 = "88E83EFD915496860C83739BE2CF4752B2AC105F";
    String blankjHmacSHA224 = "E392D83D1030323FB2E062E8165A3AD38366E53DF19EA3290961E153";
    String blankjHmacSHA256 = "A59675F13FC9A6E06D8DC90D4DC01DB9C991B0B95749D2471E588BF311DA2C67";
    String blankjHmacSHA384 =
            "9FC2F49C7EDE698EA59645B3BEFBBE67DCC7D6623E03D4D03CDA1324F7B6445BC428AB42F6A962CF79AFAD1302C3223D";
    String blankjHmacSHA512 =
            "FC55AD54B95F55A8E32EA1BAD7748C157F80679F5561EC95A3EAD975316BA85363CB4AF6462D695F742F469EDC2D577272BE359A7F9E9C7018FDF4C921E1B3CF";
    String blankjHmackey = "blankj";

    @Test
    public void testEncryptHmacMD5() throws Exception {
        assertThat(encryptHmacMD5ToString("blankj", blankjHmackey)).isEqualTo(blankjHmacMD5);
        assertThat(encryptHmacMD5ToString("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(blankjHmacMD5);
        assertThat(encryptHmacMD5("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(hexString2Bytes(blankjHmacMD5));
    }

    @Test
    public void testEncryptHmacSHA1() throws Exception {
        assertThat(encryptHmacSHA1ToString("blankj", blankjHmackey)).isEqualTo(blankjHmacSHA1);
        assertThat(encryptHmacSHA1ToString("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(blankjHmacSHA1);
        assertThat(encryptHmacSHA1("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(hexString2Bytes(blankjHmacSHA1));
    }

    @Test
    public void testEncryptHmacSHA224() throws Exception {
        assertThat(encryptHmacSHA224ToString("blankj", blankjHmackey)).isEqualTo(blankjHmacSHA224);
        assertThat(encryptHmacSHA224ToString("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(blankjHmacSHA224);
        assertThat(encryptHmacSHA224("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(hexString2Bytes(blankjHmacSHA224));
    }

    @Test
    public void testEncryptHmacSHA256() throws Exception {
        assertThat(encryptHmacSHA256ToString("blankj", blankjHmackey)).isEqualTo(blankjHmacSHA256);
        assertThat(encryptHmacSHA256ToString("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(blankjHmacSHA256);
        assertThat(encryptHmacSHA256("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(hexString2Bytes(blankjHmacSHA256));
    }

    @Test
    public void testEncryptHmacSHA384() throws Exception {
        assertThat(encryptHmacSHA384ToString("blankj", blankjHmackey)).isEqualTo(blankjHmacSHA384);
        assertThat(encryptHmacSHA384ToString("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(blankjHmacSHA384);
        assertThat(encryptHmacSHA384("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(hexString2Bytes(blankjHmacSHA384));
    }

    @Test
    public void testEncryptHmacSHA512() throws Exception {
        assertThat(encryptHmacSHA512ToString("blankj", blankjHmackey)).isEqualTo(blankjHmacSHA512);
        assertThat(encryptHmacSHA512ToString("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(blankjHmacSHA512);
        assertThat(encryptHmacSHA512("blankj".getBytes(), blankjHmackey.getBytes())).isEqualTo(hexString2Bytes(blankjHmacSHA512));
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
        assertThat(encryptDES2Base64(bytesDataDES, bytesKeyDES)).isEqualTo(base64Encode
                (bytesResDES));
    }

    @Test
    public void testDecryptDES() throws Exception {
        assertThat(decryptDES(bytesResDES, bytesKeyDES)).isEqualTo(bytesDataDES);
        assertThat(decryptHexStringDES(resDES, bytesKeyDES)).isEqualTo(bytesDataDES);
        assertThat(decryptBase64DES(base64Encode(bytesResDES), bytesKeyDES)).isEqualTo
                (bytesDataDES);
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
        assertThat(encrypt3DES2Base64(bytesDataDES3, bytesKeyDES3)).isEqualTo(base64Encode
                (bytesResDES3));
    }

    @Test
    public void testDecrypt3DES() throws Exception {
        assertThat(decrypt3DES(bytesResDES3, bytesKeyDES3)).isEqualTo(bytesDataDES3);
        assertThat(decryptHexString3DES(res3DES, bytesKeyDES3)).isEqualTo(bytesDataDES3);
        assertThat(decryptBase64_3DES(base64Encode(bytesResDES3), bytesKeyDES3)).isEqualTo
                (bytesDataDES3);
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
        assertThat(encryptAES2Base64(bytesDataAES, bytesKeyAES)).isEqualTo(base64Encode
                (bytesResAES));
    }

    @Test
    public void testDecryptAES() throws Exception {
        assertThat(decryptAES(bytesResAES, bytesKeyAES)).isEqualTo(bytesDataAES);
        assertThat(decryptHexStringAES(resAES, bytesKeyAES)).isEqualTo(bytesDataAES);
        assertThat(decryptBase64AES(base64Encode(bytesResAES), bytesKeyAES)).isEqualTo
                (bytesDataAES);
    }

    String path = TestUtils.BASEPATH + "encrypt" + TestUtils.SEP;
    String md5 = "7F138A09169B250E9DCB378140907378";

    @Test
    public void testEncryptMD5File() throws Exception {
        assertThat(encryptMD5File2String(new File(path + "MD5.txt"))).isEqualTo(md5);
    }
}