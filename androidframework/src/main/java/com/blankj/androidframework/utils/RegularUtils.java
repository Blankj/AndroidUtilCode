package com.blankj.androidframework.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/*********************************************
 * author: Blankj on 2016/8/2 21:19
 * blog:   http://blankj.com
 * e-mail: blankj@qq.com
 *********************************************/
public class RegularUtils {

    private RegularUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    // 验证手机号
    private static final String REGEX_MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    // 验证座机号,正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx
    private static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    // 验证邮箱
    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    // 验证url
    private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    // 验证汉字
    private static final String REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$";
    // 验证用户名,取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
    private static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    // 验证IP地址
    private static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    //If u want more please visit http://toutiao.com/i6231678548520731137/

    /**
     * @param string 待验证文本
     * @return 是否符合手机号格式
     */
    public static boolean isMobile(String string) {
        return isMatch(REGEX_MOBILE, string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合座机号码格式
     */
    public static boolean isTel(String string) {
        return isMatch(REGEX_TEL, string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合邮箱格式
     */
    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合网址格式
     */
    public static boolean isURL(String string) {
        return isMatch(REGEX_URL, string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合汉字
     */
    public static boolean isChz(String string) {
        return isMatch(REGEX_CHZ, string);
    }

    /**
     * @param string 待验证文本
     * @return 是否符合用户名
     */
    public static boolean isUsername(String string) {
        return isMatch(REGEX_USERNAME, string);
    }

    /**
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean isMatch(String regex, String string) {
        return !TextUtils.isEmpty(string) && Pattern.matches(regex, string);
    }
}
