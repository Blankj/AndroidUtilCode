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
     * URL编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String string, String charset)}方法</p>
     *
     * @param string 要编码的字符
     * @return 编码为UTF-8的字符串
     */
    public static String urlEncode(String string) {
        return urlEncode(string, "UTF-8");
    }

    /**
     * URL编码
     * <p>若系统不支持指定的编码字符集,则直接将string原样返回</p>
     *
     * @param string  要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(String string, String charset) {
        try {
            return URLEncoder.encode(string, charset);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    /**
     * URL解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String string, String charset)}方法</p>
     *
     * @param string 要解码的字符串
     * @return URL解码后的字符串
     */
    public static String urlDecode(String string) {
        return urlDecode(string, "UTF-8");
    }

    /**
     * URL解码
     * <p>若系统不支持指定的解码字符集,则直接将string原样返回</p>
     *
     * @param string  要解码的字符串
     * @param charset 字符集
     * @return URL解码为指定字符集的字符串
     */
    public static String urlDecode(String string, String charset) {
        try {
            return URLDecoder.decode(string, charset);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

}
