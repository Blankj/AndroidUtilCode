package com.blankj.utilcode.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 加密解密相关的工具类
 * </pre>
 */
public final class EncryptUtils {

    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // 哈希加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * MD2 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptMD2ToString(final String data) {
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * MD2 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptMD2ToString(final byte[] data) {
        return bytes2HexString(encryptMD2(data));
    }

    /**
     * MD2 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD2(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * MD5 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptMD5ToString(final String data) {
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5 加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 16 进制加盐密文
     */
    public static String encryptMD5ToString(final String data, final String salt) {
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptMD5ToString(final byte[] data) {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 16 进制加盐密文
     */
    public static String encryptMD5ToString(final byte[] data, final byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5 加密文件
     *
     * @param filePath 文件路径
     * @return 文件的 16 进制密文
     */
    public static String encryptMD5File2String(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File2String(file);
    }

    /**
     * MD5 加密文件
     *
     * @param filePath 文件路径
     * @return 文件的 MD5 校验码
     */
    public static byte[] encryptMD5File(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * MD5 加密文件
     *
     * @param file 文件
     * @return 文件的 16 进制密文
     */
    public static String encryptMD5File2String(final File file) {
        return bytes2HexString(encryptMD5File(file));
    }

    /**
     * MD5 加密文件
     *
     * @param file 文件
     * @return 文件的 MD5 校验码
     */
    public static byte[] encryptMD5File(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fis);
        }
    }

    /**
     * SHA1 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptSHA1ToString(final String data) {
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptSHA1ToString(final byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA1(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * SHA224 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptSHA224ToString(final String data) {
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptSHA224ToString(final byte[] data) {
        return bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA224(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA256 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptSHA256ToString(final String data) {
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptSHA256ToString(final byte[] data) {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA256(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * SHA384 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptSHA384ToString(final String data) {
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptSHA384ToString(final byte[] data) {
        return bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA384(final byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * SHA512 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptSHA512ToString(final String data) {
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptSHA512ToString(final byte[] data) {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA512(final byte[] data) {
        return hashTemplate(data, "SHA512");
    }

    /**
     * hash 加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * HmacMD5 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacMD5ToString(final String data, final String key) {
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacMD5 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacMD5ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacMD5(data, key));
    }

    /**
     * HmacMD5 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacMD5(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * HmacSHA1 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA1ToString(final String data, final String key) {
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA1 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA1ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA1(data, key));
    }

    /**
     * HmacSHA1 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA1(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * HmacSHA224 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA224ToString(final String data, final String key) {
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA224ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA224(data, key));
    }

    /**
     * HmacSHA224 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA224(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * HmacSHA256 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA256ToString(final String data, final String key) {
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA256 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA256ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA256(data, key));
    }

    /**
     * HmacSHA256 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA256(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * HmacSHA384 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA384ToString(final String data, final String key) {
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA384 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA384ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA384(data, key));
    }

    /**
     * HmacSHA384 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA384(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * HmacSHA512 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA512ToString(final String data, final String key) {
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA512 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16 进制密文
     */
    public static String encryptHmacSHA512ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA512(data, key));
    }

    /**
     * HmacSHA512 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA512(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * Hmac 加密模板
     *
     * @param data      数据
     * @param key       秘钥
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hmacTemplate(final byte[] data,
                                       final byte[] key,
                                       final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DES 加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * DES 转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    private static final String DES_Algorithm = "DES";

    /**
     * DES 加密后转为 Base64 编码
     *
     * @param data           明文
     * @param key            8 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return Base64 密文
     */
    public static byte[] encryptDES2Base64(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密后转为 16 进制
     *
     * @param data           明文
     * @param key            8 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 16 进制密文
     */
    public static String encryptDES2HexString(final byte[] data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv) {
        return bytes2HexString(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密
     *
     * @param data           明文
     * @param key            8 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 密文
     */
    public static byte[] encryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return desTemplate(data, key, DES_Algorithm, transformation, iv, true);
    }

    /**
     * DES 解密 Base64 编码密文
     *
     * @param data           Base64 编码密文
     * @param key            8 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptBase64DES(final byte[] data,
                                          final byte[] key,
                                          final String transformation,
                                          final byte[] iv) {
        return decryptDES(base64Decode(data), key, transformation, iv);
    }

    /**
     * DES 解密 16 进制密文
     *
     * @param data           16 进制密文
     * @param key            8 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptHexStringDES(final String data,
                                             final byte[] key,
                                             final String transformation,
                                             final byte[] iv) {
        return decryptDES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * DES 解密
     *
     * @param data           密文
     * @param key            8 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return desTemplate(data, key, DES_Algorithm, transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES 加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 3DES 转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    private static final String TripleDES_Algorithm = "DESede";


    /**
     * 3DES 加密后转为 Base64 编码
     *
     * @param data           明文
     * @param key            24 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return Base64 密文
     */
    public static byte[] encrypt3DES2Base64(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv) {
        return base64Encode(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密后转为 16 进制
     *
     * @param data           明文
     * @param key            24 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 16 进制密文
     */
    public static String encrypt3DES2HexString(final byte[] data,
                                               final byte[] key,
                                               final String transformation,
                                               final byte[] iv) {
        return bytes2HexString(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密
     *
     * @param data           明文
     * @param key            24 字节密钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 密文
     */
    public static byte[] encrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv) {
        return desTemplate(data, key, TripleDES_Algorithm, transformation, iv, true);
    }

    /**
     * 3DES 解密 Base64 编码密文
     *
     * @param data           Base64 编码密文
     * @param key            24 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptBase64_3DES(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv) {
        return decrypt3DES(base64Decode(data), key, transformation, iv);
    }

    /**
     * 3DES 解密 16 进制密文
     *
     * @param data           16 进制密文
     * @param key            24 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptHexString3DES(final String data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv) {
        return decrypt3DES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * 3DES 解密
     *
     * @param data           密文
     * @param key            24 字节密钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv) {
        return desTemplate(data, key, TripleDES_Algorithm, transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AES 加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * AES 转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    private static final String AES_Algorithm = "AES";


    /**
     * AES 加密后转为 Base64 编码
     *
     * @param data           明文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return Base64 密文
     */
    public static byte[] encryptAES2Base64(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return base64Encode(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES 加密后转为 16 进制
     *
     * @param data           明文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 16 进制密文
     */
    public static String encryptAES2HexString(final byte[] data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv) {
        return bytes2HexString(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES 加密
     *
     * @param data           明文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 密文
     */
    public static byte[] encryptAES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return desTemplate(data, key, AES_Algorithm, transformation, iv, true);
    }

    /**
     * AES 解密 Base64 编码密文
     *
     * @param data           Base64 编码密文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptBase64AES(final byte[] data,
                                          final byte[] key,
                                          final String transformation,
                                          final byte[] iv) {
        return decryptAES(base64Decode(data), key, transformation, iv);
    }

    /**
     * AES 解密 16 进制密文
     *
     * @param data           16 进制密文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptHexStringAES(final String data,
                                             final byte[] key,
                                             final String transformation,
                                             final byte[] iv) {
        return decryptAES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * AES 解密
     *
     * @param data           密文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptAES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return desTemplate(data, key, AES_Algorithm, transformation, iv, false);
    }

    /**
     * DES 加密模板
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation 转变
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于 DES，3DES，AES
     */
    private static byte[] desTemplate(final byte[] data,
                                      final byte[] key,
                                      final String algorithm,
                                      final String transformation,
                                      final byte[] iv,
                                      final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
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
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
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
}
