package com.blankj.utilcode.utils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : 单位相关工具类
 * </pre>
 */
public class ConstUtils {

    private ConstUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /******************** 存储相关常量 ********************/
    /**
     * Byte与Byte的倍数
     */
    public static final long BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final long KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final long MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final long GB = 1073741824;

    /******************** 时间相关常量 ********************/
    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

    /******************** DES加密相关常量 ********************/
    /**
     * DES加密为ECB、无填充
     */
    public static final String DES_ECB_NO_PADDING = "DES/ECB/NoPadding";
    /**
     * DES加密为CBC、无填充
     */
    public static final String DES_CBC_NO_PADDING = "DES/CBC/NoPadding";
    /**
     * DES加密为CFB、无填充
     */
    public static final String DES_CFB_NO_PADDING = "DES/CFB/NoPadding";
    /**
     * DES加密为OFB、无填充
     */
    public static final String DES_OFB_NO_PADDING = "DES/OFB/NoPadding";
    /**
     * DES加密为ECB、零填充
     */
    public static final String DES_ECB_ZEROS_PADDING = "DES/ECB/ZerosPadding";
    /**
     * DES加密为CBC、零填充
     */
    public static final String DES_CBC_ZEROS_PADDING = "DES/CBC/ZerosPadding";
    /**
     * DES加密为CFB、零填充
     */
    public static final String DES_CFB_ZEROS_PADDING = "DES/CFB/ZerosPadding";
    /**
     * DES加密为OFB、零填充
     */
    public static final String DES_OFB_ZEROS_PADDING = "DES/OFB/ZerosPadding";
    /**
     * DES加密为ECB、PKCS5Padding填充
     */
    public static final String DES_ECB_PKCS5_PADDING = "DES/ECB/PKCS5Padding";
    /**
     * DES加密为CBC、PKCS5Padding填充
     */
    public static final String DES_CBC_PKCS5_PADDING = "DES/CBC/PKCS5Padding";
    /**
     * DES加密为CFB、PKCS5Padding填充
     */
    public static final String DES_CFB_PKCS5_PADDING = "DES/CFB/PKCS5Padding";
    /**
     * DES加密为OFB、PKCS5Padding填充
     */
    public static final String DES_OFB_PKCS5_PADDING = "DES/OFB/PKCS5Padding";
}
