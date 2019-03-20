package com.blankj.utilcode.util;

import android.util.Base64;

import org.junit.Assert;
import org.junit.Test;

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
public class EncryptUtilsTest extends BaseTest {
    @Test
    public void encryptMD2() {
        String blankjMD2 = "15435017570D8A73449E25C4622E17A4";
        Assert.assertEquals(
                blankjMD2,
                EncryptUtils.encryptMD2ToString("blankj")
        );
        assertEquals(
                blankjMD2,
                EncryptUtils.encryptMD2ToString("blankj".getBytes())
        );
        assertTrue(
                Arrays.equals(
                        hexString2Bytes(blankjMD2),
                        EncryptUtils.encryptMD2("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptMD5() {
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
                        hexString2Bytes(blankjMD5),
                        EncryptUtils.encryptMD5("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptMD5File() {
        String fileMd5 = "7f138a09169b250e9dcb378140907378";
        assertEquals(
                fileMd5.toUpperCase(),
                EncryptUtils.encryptMD5File2String(new File(PATH_ENCRYPT + "MD5.txt"))
        );
    }

    @Test
    public void encryptSHA1() {
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
                        hexString2Bytes(blankjSHA1),
                        EncryptUtils.encryptSHA1("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA224() {
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
                        hexString2Bytes(blankjSHA224),
                        EncryptUtils.encryptSHA224("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA256() {
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
                        hexString2Bytes(blankjSHA256),
                        EncryptUtils.encryptSHA256("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA384() {
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
                        hexString2Bytes(blankjSHA384),
                        EncryptUtils.encryptSHA384("blankj".getBytes())
                )
        );
    }

    @Test
    public void encryptSHA512() {
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
                        hexString2Bytes(blankjSHA512),
                        EncryptUtils.encryptSHA512("blankj".getBytes())
                )
        );
    }

    private String blankjHmacSHA512 =
            "FC55AD54B95F55A8E32EA1BAD7748C157F80679F5561EC95A3EAD975316BA85363CB4AF6462D695F742F469EDC2D577272BE359A7F9E9C7018FDF4C921E1B3CF";
    private String blankjHmackey    = "blankj";

    @Test
    public void encryptHmacMD5() {
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
                        hexString2Bytes(blankjHmacMD5),
                        EncryptUtils.encryptHmacMD5("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA1() {
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
                        hexString2Bytes(blankjHmacSHA1),
                        EncryptUtils.encryptHmacSHA1("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA224() {
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
                        hexString2Bytes(blankjHmacSHA224),
                        EncryptUtils.encryptHmacSHA224("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA256() {
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
                        hexString2Bytes(blankjHmacSHA256),
                        EncryptUtils.encryptHmacSHA256("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA384() {
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
                        hexString2Bytes(blankjHmacSHA384),
                        EncryptUtils.encryptHmacSHA384("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }

    @Test
    public void encryptHmacSHA512() {
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
                        hexString2Bytes(blankjHmacSHA512),
                        EncryptUtils.encryptHmacSHA512("blankj".getBytes(), blankjHmackey.getBytes())
                )
        );
    }


    private String dataDES      = "0008DB3345AB0223";
    private String keyDES       = "6801020304050607";
    private String resDES       = "1F7962581118F360";
    private byte[] bytesDataDES = hexString2Bytes(dataDES);
    private byte[] bytesKeyDES  = hexString2Bytes(keyDES);
    private byte[] bytesResDES  = hexString2Bytes(resDES);

    @Test
    public void encryptDES() {
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
                        base64Encode(bytesResDES),
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
    public void decryptDES() {
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
                                base64Encode(bytesResDES),
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
    private byte[] bytesDataDES3 = hexString2Bytes(data3DES);
    private byte[] bytesKeyDES3  = hexString2Bytes(key3DES);
    private byte[] bytesResDES3  = hexString2Bytes(res3DES);

    @Test
    public void encrypt3DES() {
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
                        base64Encode(bytesResDES3),
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
    public void decrypt3DES() {
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
                                base64Encode(bytesResDES3),
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
    private byte[] bytesDataAES = hexString2Bytes(dataAES);
    private byte[] bytesKeyAES  = hexString2Bytes(keyAES);
    private byte[] bytesResAES  = hexString2Bytes(resAES);

    @Test
    public void encryptAES() {
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
                        base64Encode(bytesResAES),
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
    public void decryptAES() {
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
                                base64Encode(bytesResAES),
                                bytesKeyAES,
                                "AES/ECB/NoPadding",
                                null
                        )
                )
        );
    }

    private String publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWuAuSCrzUXC1l4ixXBeBfotUtkALrAjLM5UHiVfOFHrRJHM41HSeHVm56UZHgJlwk80R8juu1ykuhkgrilTv7H+3MpZdIunvndDElgdgk8aI2Ip4GUlemUDvCtWd3ychWEh4kYQ8CeInQvNM08imoLFldvbjWt/IkGK+BcGzamQIDAQAB";
    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJa4C5IKvNRcLWXiLFcF4F+i1S2QAusCMszlQeJV84UetEkczjUdJ4dWbnpRkeAmXCTzRHyO67XKS6GSCuKVO/sf7cyll0i6e+d0MSWB2CTxojYingZSV6ZQO8K1Z3fJyFYSHiRhDwJ4idC80zTyKagsWV29uNa38iQYr4FwbNqZAgMBAAECgYAxV1k6W1eMMg0OsKeRabQVuwoNG3tJEnQtDdSu0zKg3vdohAyh6MR7EvmiA7g86HH8CsPd/y/9WJe/8j6sBO0Ye9gt7eyQ2NiwWvlTuwNmngcSTapVvVI6NEyJFMfQt9PB1EHLNAXlz8jtJUyA7C48jReQD9p/SzAP0VxG7lwyMQJBAOjE7hAZ/6fyP3DB1fG7jr9gONZcz3TUaqx6BUn4GKZnckW08ht9Xqcqft5Hthu8BbLM9ptQ0U8QZekrJwD6ya0CQQClwstZMPu8jLhsgugVwodcG1mPEOiw9Yjnmt9+WTI07Ll2uFv//hRXBnahBBnZbucUYEbUY3kqUX9b3e9TmEodAkEAybPMbxt4VDoxCy6Mi/pxChkBZ4/pHV3sSiU6bAyWn6vIc+sGWRfca5MBePA/N+1IKtY9Y/02QwL8rH5+P/URyQJAL/hdjORGFdzLimuf6pwvPBKWKncEQCHuisghIZmClBpl2duklELddAnkztg2+tvDd/wcw14+NGb9aoKhvhl2aQJAbvcgoPU+xs0CjeexH+TS2S/jKkTRpvP2CpPK/k71m13xWdE8RtMkYY1measRmlIwOfWze7ll/PGT4dxWf31FNg==";
    private String dataRSA    = "BlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBla12345678";

    @Test
    public void encryptDecryptRSA() {
        assertTrue(
                Arrays.equals(
                        EncryptUtils.decryptRSA(
                                EncryptUtils.encryptRSA(
                                        dataRSA.getBytes(),
                                        base64Decode(publicKey.getBytes()),
                                        true,
                                        "RSA/ECB/PKCS1Padding"
                                ),
                                base64Decode(privateKey.getBytes()),
                                false,
                                "RSA/ECB/PKCS1Padding"
                        ),
                        dataRSA.getBytes()
                )
        );
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }
}