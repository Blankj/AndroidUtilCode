package com.blankj.utilcode.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/7
 *     desc  : 编码解码相关工具类
 * </pre>
 */
public class EncodeUtils {

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 以UTF-8编码字符串
     * <p>若想自己指定字符集,可以使用encode(String string, String charset)方法</p>
     *
     * @param string 要编码的字符
     * @return 编码为UTF-8的字符串
     */
    public static String encodeUTF8(String string) {
        return encode(string, "UTF-8");
    }

    /**
     * 字符编码
     * <p>若系统不支持指定的编码字符集,则直接将string原样返回</p>
     *
     * @param string  要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public static String encode(String string, String charset) {
        try {
            return URLEncoder.encode(string, charset);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    /**
     * 以UTF-8解码字符串
     * <p>若想自己指定字符集,可以使用# {decode(String string, String charset)}方法</p>
     *
     * @param string 要解码的字符
     * @return 解码为UTF-8的字符串
     */
    public static String decodeUTF8(String string) {
        return decode(string, "UTF-8");
    }

    /**
     * 字符解码
     * <p>若系统不支持指定的解码字符集,则直接将string原样返回</p>
     *
     * @param string  要解码的字符
     * @param charset 字符集
     * @return 解码为字符集的字符串
     */
    public static String decode(String string, String charset) {
        try {
            return URLDecoder.decode(string, charset);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }
}
