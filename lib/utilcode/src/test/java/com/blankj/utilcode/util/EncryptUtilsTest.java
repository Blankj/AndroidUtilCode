package com.blankj.utilcode.util;

import android.util.Base64;
import android.util.Pair;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.blankj.utilcode.util.TestConfig.PATH_ENCRYPT;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


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
        assertArrayEquals(
                hexString2Bytes(blankjMD2),
                EncryptUtils.encryptMD2("blankj".getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjMD5),
                EncryptUtils.encryptMD5("blankj".getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjSHA1),
                EncryptUtils.encryptSHA1("blankj".getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjSHA224),
                EncryptUtils.encryptSHA224("blankj".getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjSHA256),
                EncryptUtils.encryptSHA256("blankj".getBytes()));

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
        assertArrayEquals(
                hexString2Bytes(blankjSHA384),
                EncryptUtils.encryptSHA384("blankj".getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjSHA512),
                EncryptUtils.encryptSHA512("blankj".getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjHmacMD5),
                EncryptUtils.encryptHmacMD5("blankj".getBytes(), blankjHmackey.getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjHmacSHA1),
                EncryptUtils.encryptHmacSHA1("blankj".getBytes(), blankjHmackey.getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjHmacSHA224),
                EncryptUtils.encryptHmacSHA224("blankj".getBytes(), blankjHmackey.getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjHmacSHA256),
                EncryptUtils.encryptHmacSHA256("blankj".getBytes(), blankjHmackey.getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjHmacSHA384),
                EncryptUtils.encryptHmacSHA384("blankj".getBytes(), blankjHmackey.getBytes())
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
        assertArrayEquals(
                hexString2Bytes(blankjHmacSHA512),
                EncryptUtils.encryptHmacSHA512("blankj".getBytes(), blankjHmackey.getBytes())
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
        assertArrayEquals(
                bytesResDES,
                EncryptUtils.encryptDES(bytesDataDES, bytesKeyDES, "DES/ECB/NoPadding", null)
        );
        assertEquals(
                resDES,
                EncryptUtils.encryptDES2HexString(bytesDataDES, bytesKeyDES, "DES/ECB/NoPadding", null)
        );
        assertArrayEquals(
                base64Encode(bytesResDES),
                EncryptUtils.encryptDES2Base64(bytesDataDES, bytesKeyDES, "DES/ECB/NoPadding", null)
        );
    }

    @Test
    public void decryptDES() {
        assertArrayEquals(
                bytesDataDES,
                EncryptUtils.decryptDES(bytesResDES, bytesKeyDES, "DES/ECB/NoPadding", null)
        );
        assertArrayEquals(
                bytesDataDES,
                EncryptUtils.decryptHexStringDES(resDES, bytesKeyDES, "DES/ECB/NoPadding", null)
        );
        assertArrayEquals(
                bytesDataDES,
                EncryptUtils.decryptBase64DES(base64Encode(bytesResDES), bytesKeyDES, "DES/ECB/NoPadding", null)
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
        assertArrayEquals(
                bytesResDES3,
                EncryptUtils.encrypt3DES(bytesDataDES3, bytesKeyDES3, "DESede/ECB/NoPadding", null)
        );
        assertEquals(
                res3DES,
                EncryptUtils.encrypt3DES2HexString(bytesDataDES3, bytesKeyDES3, "DESede/ECB/NoPadding", null)
        );
        assertArrayEquals(
                base64Encode(bytesResDES3),
                EncryptUtils.encrypt3DES2Base64(bytesDataDES3, bytesKeyDES3, "DESede/ECB/NoPadding", null)
        );
    }

    @Test
    public void decrypt3DES() {
        assertArrayEquals(
                bytesDataDES3,
                EncryptUtils.decrypt3DES(bytesResDES3, bytesKeyDES3, "DESede/ECB/NoPadding", null)
        );
        assertArrayEquals(
                bytesDataDES3,
                EncryptUtils.decryptHexString3DES(res3DES, bytesKeyDES3, "DESede/ECB/NoPadding", null)
        );
        assertArrayEquals(
                bytesDataDES3,
                EncryptUtils.decryptBase64_3DES(base64Encode(bytesResDES3), bytesKeyDES3, "DESede/ECB/NoPadding", null)
        );
    }

    private String dataAES      = "111111111111111111111111111111111";
    private String keyAES       = "11111111111111111111111111111111";
    private String resAES       = "393FBBBC2C774BE50A106A50393E623AC3790781D015BB854359587256581F6D";
    private byte[] bytesDataAES = hexString2Bytes(dataAES);
    private byte[] bytesKeyAES  = hexString2Bytes(keyAES);
    private byte[] bytesResAES  = hexString2Bytes(resAES);

    @Test
    public void encryptAES() {
        assertArrayEquals(
                bytesResAES,
                EncryptUtils.encryptAES(bytesDataAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertEquals(
                resAES,
                EncryptUtils.encryptAES2HexString(bytesDataAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(
                base64Encode(bytesResAES),
                EncryptUtils.encryptAES2Base64(bytesDataAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
    }

    @Test
    public void decryptAES() {
        assertArrayEquals(
                bytesDataAES,
                EncryptUtils.decryptAES(bytesResAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(
                bytesDataAES,
                EncryptUtils.decryptHexStringAES(resAES, bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
        assertArrayEquals(bytesDataAES,
                EncryptUtils.decryptBase64AES(base64Encode(bytesResAES), bytesKeyAES, "AES/ECB/PKCS5Padding", null)
        );
    }

    @Test
    public void encryptDecryptRSA() throws Exception {
        int keySize = 1024;
        Pair<String, String> publicPrivateKey = genKeyPair(keySize);

        String publicKey = publicPrivateKey.first;
        String privateKey = publicPrivateKey.second;
        String dataRSA = "BlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBl";
        System.out.println("publicKeyBase64:" + publicKey);
        System.out.println("privateKeyBase64:" + privateKey);

        System.out.println(EncryptUtils.encryptRSA2HexString(
                dataRSA.getBytes(),
                base64Decode(publicKey.getBytes()),
                keySize,
                "RSA/None/PKCS1Padding"
        ));

        assertArrayEquals(EncryptUtils.decryptRSA(
                EncryptUtils.encryptRSA(
                        dataRSA.getBytes(),
                        base64Decode(publicKey.getBytes()),
                        keySize,
                        "RSA/None/PKCS1Padding"
                ),
                base64Decode(privateKey.getBytes()),
                keySize,
                "RSA/None/PKCS1Padding"
        ), dataRSA.getBytes());
    }

    private String dataRc4 = "111111111111111111111";
    private String keyRc4  = "111111111111";

    @Test
    public void rc4() throws Exception {
        System.out.println(new String(EncryptUtils.rc4(EncryptUtils.rc4(dataRc4.getBytes(), keyRc4.getBytes()), keyRc4.getBytes())));
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

    private Pair<String, String> genKeyPair(int size) throws NoSuchAlgorithmException {

        if (size == 1024) {
            return Pair.create(
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHGvdORdwsK5i+s9rKaMPL1O5eDK2XwNHRUWaxmGB/cxLxeinJrrqdAN+mME7XtGN9bklnOR3MUBQLVnWIn/IU0pnIJY9DpPTVc7x+1zFb8UUq1N0BBo/NpUG5olxuQULuAAHZOg28pnP/Pcb5XVEvpNKL0HaWjN8pu/Dzf8gZwIDAQAB",
                    "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJgca905F3CwrmL6z2spow8vU7l4MrZfA0dFRZrGYYH9zEvF6Kcmuup0A36YwTte0Y31uSWc5HcxQFAtWdYif8hTSmcglj0Ok9NVzvH7XMVvxRSrU3QEGj82lQbmiXG5BQu4AAdk6Dbymc/89xvldUS+k0ovQdpaM3ym78PN/yBnAgMBAAECgYAFdX+pgNMGiFC53KZ1AhmIAfrPPTEUunQzqpjE5Tm6oJEkZwXiedFbeK5nbLQCnXSH07nBT9AjNvFH71i6BqLvT1l3/ezPq9pmRPriHfWQQ3/J3ASf1O9F9CkYbq/s/qqkXEFcl8PdYQV0xU/kS4jZPP+60Lv3sPkLg2DpkhM+AQJBANTl+/v6sBqqQSS0Anl5nE15Ck3XGBcq0nvATHfFkJYtG9rrXz3ZoRATLxF1iJYwGSAtirhev9W7qFayjci0ztcCQQC25/kkFbeMEWT6/kyV8wcPIog1mKy8RVB9+2l6C8AzbWBPZYtLlB7uaGSJeZBTEGfvRYzpFm5xO0JqwCfDddjxAkBmxtgM3wqg9MwaAeSn6/Nu2x4EUfBJTtzp7P19XJzeQsyNtM73ttYwQnKYhRr5FiMrC5FKTENj1QIBSJV17QNlAkAL5cUAAuWgl9UQuo/yxQ81fdKMYfUCfiPBPiRbSv5imf/Eyl8oOGdWrLW1d5HaxVttZgHHe60NcoRce0la3oSRAkAe8OqLsm9ryXNvBtZxSG+1JUvePVxpRSlJdZIAUKxN6XQE0S9aEe/IkNDBgVeiUEtop76R2NkkGtGTwzbzl0gm"
            );
        } else if (size == 2048) {
            return Pair.create(
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjLLeJZIO7dfQKb6tHE+TlhvD1m3UdTefKvl4uNQboDXy2ztgPcksjLDXxsT+znxMBh4RpXxfVPgnrcSLewGVhTb3uXh9sWo6tvvshNaMKBTebaZePhE7grq+LHH3NILscVssK24rDSvIquZ4nUbDipF/Iscge4LwnypcCuun/3RCn4HYzXW+0YFFZC8Vq4zabIxtzzkvgZlAlvuD6tT76Uuo5kD8b36yYNALI+ZStOj283wlL8PgyyitRGaqCH+MjWYqDb5C0DN31kcoSU7ARTGWgNNAoexAdNujkBvVRFyR2cH9FpjJDu18Oa8v9uSjlRftVWPj0OQXE7vRUsrrawIDAQAB",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMst4lkg7t19Apvq0cT5OWG8PWbdR1N58q+Xi41BugNfLbO2A9ySyMsNfGxP7OfEwGHhGlfF9U+CetxIt7AZWFNve5eH2xajq2++yE1owoFN5tpl4+ETuCur4scfc0guxxWywrbisNK8iq5nidRsOKkX8ixyB7gvCfKlwK66f/dEKfgdjNdb7RgUVkLxWrjNpsjG3POS+BmUCW+4Pq1PvpS6jmQPxvfrJg0Asj5lK06PbzfCUvw+DLKK1EZqoIf4yNZioNvkLQM3fWRyhJTsBFMZaA00Ch7EB026OQG9VEXJHZwf0WmMkO7Xw5ry/25KOVF+1VY+PQ5BcTu9FSyutrAgMBAAECggEAHJQ4i2kfnzA3GEOi5h1D3TnGjcfBYA3sRs5ltyVedyx+KAnngqVaZzmEmtto5ohY6OUysGqS8q91X9aMfm/T7zs7FnFjFqZ9Rq3lXRY3YezbQWqJuhHGBMfp2R1NGV1+qYfbcPbvx70dBZnK5id5kKv9JxNLhcsTFUGFcLJtbXXixY2CGiS/dIbFvFHGMbAz3+9l9HXaL4AS7KQXvnauwJW1a5vIAVFYZVBj0qY9Viy2vq6ShH+9pdxOSsWBt08WpxIhjkTr+ZkFck67la2Jn0SBlClB0FIygTqbAmsM3p1nqcR55jdx3hfs31rIfM1Rx5epMm48KYErb2ktowngAQKBgQDL9FEumMMagPy4+EjR1puFHNvADlAi8tIUNt1W5zKKnd+T6gYGn8nqiiy5pvwLLUp8JISmq50tMC3cgAPw+G4kIe5zoBO2EU9X6aPhMd/ScUlVdk0IzEMXa3kMAOjOInWvoevJ4cwWcBPH2aRuDg5wZdh3TpB9LQP4uQ0QHwmE3wKBgQCwmkL6rJDrNo1GNUsjw+WIsXkuS3PYJahbg/uhRdGSsX2BRIPQVCRJP7MkgaUMhZRilt1ROfQy4d2BPxTxvUiGJcKfpsW8xi39PrYWZC5TvEA839q39Uak+ISCsYtZaHk5dvzmE9nF5gv0ivjCr81N2/1KwXO8VmNofzWUqNd+9QKBgQCs39QICRgm2Ppd1qXyp1N/SuzBJ+CpHuUOmUqXpLRkZljiSVT+PGar1J8AZhfxaVxfSZzeoUxCxzm4UxIEKK9DFTfG7gKHKrj0LWfpM5siB0A/nlzBflHIAiLCF+s8/lx+mGMB5dBVnH5HwaTsXCHFB66pwgAa+hMJueDmr0gkRQKBgDKhd1Rwxvd4Y1ZejxVI43SmFOzt2t98JGFgXHLnFmdtFWNLJlNC3EhXx99Of+gwH9OIFxljeRxhXuTgFfwcXT+AceTdplExrBuvr/qJbDK7hNsu/oDBBCjlyu/BQQc4CZEtCOJZjJTNGF5avWjrh/urd1nITosPZV6fIdhl86pFAoGAfOwK0Wte6gO5glAHP9RNktDeyFJCfFH1KUFiAG7XUww6bRpL2fEAqBIcDVgsS565ihxDSbUjgQgg/Ckh2+iBrwf1K9ViO4XUuwWqRS26rn4Is/W5kbPtnC4HS5cQIH1aWi3xUMJcWxV4ZrwiMVdw91leYWC0IbXC/yrc/PBW+sE="
            );
        }

        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(size, secureRandom);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Key publicKey = keyPair.getPublic();

        Key privateKey = keyPair.getPrivate();

        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        String publicKeyBase64 = EncodeUtils.base64Encode2String(publicKeyBytes);
        String privateKeyBase64 = EncodeUtils.base64Encode2String(privateKeyBytes);

        return Pair.create(publicKeyBase64, privateKeyBase64);
    }
}