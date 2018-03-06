package com.blankj.utilcode.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.Arrays;

import static com.blankj.utilcode.util.TestConfig.PATH_ENCRYPT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/06
 *     desc  : test EncryptUtils
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23)
public class EncryptUtilsTest {

    static {
        TestUtils.init();
    }

    @Test
    public void encryptMD2() throws Exception {
        String blankjMD2 = "15435017570D8A73449E25C4622E17A4";
        assertEquals(
                blankjMD2,
                EncryptUtils.encryptMD2ToString("blankj")
        );
        assertEquals(
                blankjMD2,
                EncryptUtils.encryptMD2ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjMD2),
                        EncryptUtils.encryptMD2("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptMD5() throws Exception {
        String blankjMD5 = "AAC25CD336E01C8655F4EC7875445A60";
        assertEquals(
                blankjMD5,
                EncryptUtils.encryptMD5ToString("blankj")
        );
        assertEquals(
                blankjMD5,
                EncryptUtils.encryptMD5ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjMD5),
                        EncryptUtils.encryptMD5("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA1() throws Exception {
        String blankjSHA1 = "C606ACCB1FEB669E19D080ADDDDBB8E6CDA5F43C";
        assertEquals(
                blankjSHA1,
                EncryptUtils.encryptSHA1ToString("blankj")
        );
        assertEquals(
                blankjSHA1,
                EncryptUtils.encryptSHA1ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjSHA1),
                        EncryptUtils.encryptSHA1("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA224() throws Exception {
        String blankjSHA224 = "F4C5C0E8CF56CAC4D06DB6B523F67621859A9D79BDA4B2AC03097D5F";
        assertEquals(
                blankjSHA224,
                EncryptUtils.encryptSHA224ToString("blankj")
        );
        assertEquals(
                blankjSHA224,
                EncryptUtils.encryptSHA224ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjSHA224),
                        EncryptUtils.encryptSHA224("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA256() throws Exception {
        String blankjSHA256 = "8BD80AE90DFBA112786367BEBDDEE60A638EF5B82682EDF8F3D3CA8E6BFEF648";
        assertEquals(
                blankjSHA256,
                EncryptUtils.encryptSHA256ToString("blankj")
        );
        assertEquals(
                blankjSHA256,
                EncryptUtils.encryptSHA256ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjSHA256),
                        EncryptUtils.encryptSHA256("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA384() throws Exception {
        String blankjSHA384 = "BF831E5221FC108D6A72ACB888BA3EB0C030A5F01BA2F739856BE70681D86F992B85E0D461101C74BAEDA895BD422557";
        assertEquals(
                blankjSHA384,
                EncryptUtils.encryptSHA384ToString("blankj")
        );
        assertEquals(
                blankjSHA384,
                EncryptUtils.encryptSHA384ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjSHA384),
                        EncryptUtils.encryptSHA384("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA512() throws Exception {
        String blankjSHA512 = "D59D31067F614ED3586F85A31FEFDB7F33096316DA26EBE0FF440B241C8560D96650F100D78C512560C976949EFA89CB5D5589DCF68C7FAADE98F03BCFEC2B45";
        assertEquals(
                blankjSHA512,
                EncryptUtils.encryptSHA512ToString("blankj")
        );
        assertEquals(
                blankjSHA512,
                EncryptUtils.encryptSHA512ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjSHA512),
                        EncryptUtils.encryptSHA512("blankj".getBytes())
                )
        );
    }

    private String blankjHmacSHA512 =
            "FC55AD54B95F55A8E32EA1BAD7748C157F80679F5561EC95A3EAD975316BA85363CB4AF6462D695F742F469EDC2D577272BE359A7F9E9C7018FDF4C921E1B3CF";
    private String blankjHmackey    = "blankj";

    @Test
    public void encryptHmacMD5() throws Exception {
        String blankjHmacMD5 = "2BA3FDABEE222522044BEC0CE5D6B490";
        assertEquals(
                blankjHmacMD5,
                EncryptUtils.encryptHmacMD5ToString("blankj", blankjHmackey)
        );
        assertEquals(
                blankjHmacMD5,
                EncryptUtils.encryptHmacMD5ToString("blankj".getBytes(), blankjHmackey.getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjHmacMD5),
                        EncryptUtils.encryptHmacMD5("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA1() throws Exception {
        String blankjHmacSHA1 = "88E83EFD915496860C83739BE2CF4752B2AC105F";
        assertEquals(
                blankjHmacSHA1,
                EncryptUtils.encryptHmacSHA1ToString("blankj", blankjHmackey)
        );
        assertEquals(
                blankjHmacSHA1,
                EncryptUtils.encryptHmacSHA1ToString("blankj".getBytes(), blankjHmackey.getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjHmacSHA1),
                        EncryptUtils.encryptHmacSHA1("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA224() throws Exception {
        String blankjHmacSHA224 = "E392D83D1030323FB2E062E8165A3AD38366E53DF19EA3290961E153";
        assertEquals(
                blankjHmacSHA224,
                EncryptUtils.encryptHmacSHA224ToString("blankj", blankjHmackey)
        );
        assertEquals(
                blankjHmacSHA224,
                EncryptUtils.encryptHmacSHA224ToString("blankj".getBytes(), blankjHmackey.getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjHmacSHA224),
                        EncryptUtils.encryptHmacSHA224("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA256() throws Exception {
        String blankjHmacSHA256 = "A59675F13FC9A6E06D8DC90D4DC01DB9C991B0B95749D2471E588BF311DA2C67";
        assertEquals(
                blankjHmacSHA256,
                EncryptUtils.encryptHmacSHA256ToString("blankj", blankjHmackey)
        );
        assertEquals(
                blankjHmacSHA256,
                EncryptUtils.encryptHmacSHA256ToString("blankj".getBytes(), blankjHmackey.getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjHmacSHA256),
                        EncryptUtils.encryptHmacSHA256("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA384() throws Exception {
        String blankjHmacSHA384 = "9FC2F49C7EDE698EA59645B3BEFBBE67DCC7D6623E03D4D03CDA1324F7B6445BC428AB42F6A962CF79AFAD1302C3223D";
        assertEquals(
                blankjHmacSHA384,
                EncryptUtils.encryptHmacSHA384ToString("blankj", blankjHmackey)
        );
        assertEquals(
                blankjHmacSHA384,
                EncryptUtils.encryptHmacSHA384ToString("blankj".getBytes(), blankjHmackey.getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjHmacSHA384),
                        EncryptUtils.encryptHmacSHA384("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA512() throws Exception {
        assertEquals(
                blankjHmacSHA512,
                EncryptUtils.encryptHmacSHA512ToString("blankj", blankjHmackey)
        );
        assertEquals(
                blankjHmacSHA512,
                EncryptUtils.encryptHmacSHA512ToString("blankj".getBytes(), blankjHmackey.getBytes())
        );
        assertTrue(
                Arrays.equals(
                        ConvertUtils.hexString2Bytes(blankjHmacSHA512),
                        EncryptUtils.encryptHmacSHA512("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }


    private String dataDES      = "0008DB3345AB0223";
    private String keyDES       = "6801020304050607";
    private String resDES       = "1F7962581118F360";
    private byte[] bytesDataDES = ConvertUtils.hexString2Bytes(dataDES);
    private byte[] bytesKeyDES  = ConvertUtils.hexString2Bytes(keyDES);
    private byte[] bytesResDES  = ConvertUtils.hexString2Bytes(resDES);

    @Test
    public void encryptDES() throws Exception {
        assertTrue(
                Arrays.equals(
                        bytesResDES,
                        EncryptUtils.encryptDES(
                                bytesDataDES,
                                bytesKeyDES,
                                "DES/ECB/NoPadding",
                                null
                        )
                )
        );
        assertEquals(
                resDES,
                EncryptUtils.encryptDES2HexString(
                        bytesDataDES,
                        bytesKeyDES,
                        "DES/ECB/NoPadding",
                        null
                )
        );
        assertTrue(
                Arrays.equals(
                        EncodeUtils.base64Encode(bytesResDES),
                        EncryptUtils.encryptDES2Base64(
                                bytesDataDES,
                                bytesKeyDES,
                                "DES/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    @Test
    public void decryptDES() throws Exception {
        assertTrue(
                Arrays.equals(
                        bytesDataDES,
                        EncryptUtils.decryptDES(
                                bytesResDES,
                                bytesKeyDES,
                                "DES/ECB/NoPadding",
                                null
                        )
                )
        );
        assertTrue(
                Arrays.equals(
                        bytesDataDES,
                        EncryptUtils.decryptHexStringDES(
                                resDES,
                                bytesKeyDES,
                                "DES/ECB/NoPadding",
                                null
                        )
                )
        );
        assertTrue(
                Arrays.equals(
                        bytesDataDES,
                        EncryptUtils.decryptBase64DES(
                                EncodeUtils.base64Encode(bytesResDES),
                                bytesKeyDES,
                                "DES/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    private String data3DES      = "1111111111111111";
    private String key3DES       = "111111111111111111111111111111111111111111111111";
    private String res3DES       = "F40379AB9E0EC533";
    private byte[] bytesDataDES3 = ConvertUtils.hexString2Bytes(data3DES);
    private byte[] bytesKeyDES3  = ConvertUtils.hexString2Bytes(key3DES);
    private byte[] bytesResDES3  = ConvertUtils.hexString2Bytes(res3DES);

    @Test
    public void encrypt3DES() throws Exception {
        assertTrue(
                Arrays.equals(
                        bytesResDES3,
                        EncryptUtils.encrypt3DES(
                                bytesDataDES3,
                                bytesKeyDES3,
                                "DESede/ECB/NoPadding",
                                null
                        )
                )
        );
        assertEquals(
                res3DES,
                EncryptUtils.encrypt3DES2HexString(
                        bytesDataDES3,
                        bytesKeyDES3,
                        "DESede/ECB/NoPadding",
                        null
                )
        );
        assertTrue(
                Arrays.equals(
                        EncodeUtils.base64Encode(bytesResDES3),
                        EncryptUtils.encrypt3DES2Base64(
                                bytesDataDES3,
                                bytesKeyDES3,
                                "DESede/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    @Test
    public void decrypt3DES() throws Exception {
        assertTrue(
                Arrays.equals(
                        bytesDataDES3,
                        EncryptUtils.decrypt3DES(
                                bytesResDES3,
                                bytesKeyDES3,
                                "DESede/ECB/NoPadding",
                                null
                        )
                )
        );
        assertTrue(
                Arrays.equals(
                        bytesDataDES3,
                        EncryptUtils.decryptHexString3DES(
                                res3DES,
                                bytesKeyDES3,
                                "DESede/ECB/NoPadding",
                                null
                        )
                )
        );
        assertTrue(
                Arrays.equals(
                        bytesDataDES3,
                        EncryptUtils.decryptBase64_3DES(
                                EncodeUtils.base64Encode(bytesResDES3),
                                bytesKeyDES3,
                                "DESede/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    private String dataAES      = "11111111111111111111111111111111";
    private String keyAES       = "11111111111111111111111111111111";
    private String resAES       = "E56E26F5608B8D268F2556E198A0E01B";
    private byte[] bytesDataAES = ConvertUtils.hexString2Bytes(dataAES);
    private byte[] bytesKeyAES  = ConvertUtils.hexString2Bytes(keyAES);
    private byte[] bytesResAES  = ConvertUtils.hexString2Bytes(resAES);

    @Test
    public void encryptAES() throws Exception {
//        EncryptUtils.encryptAES(
//                bytesDataAES,
//                bytesKeyAES,
//                "AES/ECB/NoPadding",
//                null
//        );
        assertTrue(
                Arrays.equals(
                        bytesResAES,
                        EncryptUtils.encryptAES(
                                bytesDataAES,
                                bytesKeyAES,
                                "AES/ECB/NoPadding",
                                null
                        )
                )
        );
        assertEquals(
                resAES,
                EncryptUtils.encryptAES2HexString(
                        bytesDataAES,
                        bytesKeyAES,
                        "AES/ECB/NoPadding",
                        null
                )
        );
        assertTrue(
                Arrays.equals(
                        EncodeUtils.base64Encode(bytesResAES),
                        EncryptUtils.encryptAES2Base64(
                                bytesDataAES,
                                bytesKeyAES,
                                "AES/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    @Test
    public void decryptAES() throws Exception {
        assertTrue(
                Arrays.equals(
                        bytesDataAES,
                        EncryptUtils.decryptAES(bytesResAES, bytesKeyAES, "AES/ECB/NoPadding", null)
                )
        );
        assertTrue(
                Arrays.equals(
                        bytesDataAES,
                        EncryptUtils.decryptHexStringAES(
                                resAES,
                                bytesKeyAES,
                                "AES/ECB/NoPadding",
                                null
                        )
                )
        );
        assertTrue(
                Arrays.equals(
                        bytesDataAES,
                        EncryptUtils.decryptBase64AES(
                                EncodeUtils.base64Encode(bytesResAES),
                                bytesKeyAES,
                                "AES/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    @Test
    public void encryptMD5File() throws Exception {
        String fileMd5 = "7f138a09169b250e9dcb378140907378";
        assertEquals(
                fileMd5.toUpperCase(),
                EncryptUtils.encryptMD5File2String(new File(PATH_ENCRYPT + "MD5.txt"))
        );
    }
}