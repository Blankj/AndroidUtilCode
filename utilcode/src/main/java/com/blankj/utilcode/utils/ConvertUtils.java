package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static com.blankj.utilcode.utils.ConstUtils.BYTE;
import static com.blankj.utilcode.utils.ConstUtils.GB;
import static com.blankj.utilcode.utils.ConstUtils.KB;
import static com.blankj.utilcode.utils.ConstUtils.MB;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/13
 *     desc  : 转换相关工具类
 * </pre>
 */
public class ConvertUtils {

    static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes byte数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        char[] ret = new char[bytes.length << 1];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * hexString转byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >>> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar转int
     *
     * @param hexChar hex单个字节
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArr转byteArr
     *
     * @param chars 字符数组
     * @return 字节数组
     */
    public static byte[] chars2Bytes(char[] chars) {
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * byteArr转charArr
     *
     * @param bytes 字节数组
     * @return 字符数组
     */
    public static char[] bytes2Chars(byte[] bytes) {
        int len = bytes.length;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * 字节数转以unit为单位的size
     *
     * @param byteNum 字节数
     * @param unit    <ul>
     *                <li>{@link ConstUtils.MemoryUnit#BYTE}: 字节</li>
     *                <li>{@link ConstUtils.MemoryUnit#KB}  : 千字节</li>
     *                <li>{@link ConstUtils.MemoryUnit#MB}  : 兆</li>
     *                <li>{@link ConstUtils.MemoryUnit#GB}  : GB</li>
     *                </ul>
     * @return 以unit为单位的size
     */
    public static double byte2Size(long byteNum, ConstUtils.MemoryUnit unit) {
        if (byteNum < 0) return -1;
        switch (unit) {
            default:
            case BYTE:
                return (double) byteNum / BYTE;
            case KB:
                return (double) byteNum / KB;
            case MB:
                return (double) byteNum / MB;
            case GB:
                return (double) byteNum / GB;
        }
    }

    /**
     * 以unit为单位的size转字节数
     *
     * @param size 大小
     * @param unit <ul>
     *             <li>{@link ConstUtils.MemoryUnit#BYTE}: 字节</li>
     *             <li>{@link ConstUtils.MemoryUnit#KB}  : 千字节</li>
     *             <li>{@link ConstUtils.MemoryUnit#MB}  : 兆</li>
     *             <li>{@link ConstUtils.MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return 字节数
     */
    public static long size2Byte(long size, ConstUtils.MemoryUnit unit) {
        if (size < 0) return -1;
        switch (unit) {
            default:
            case BYTE:
                return size * BYTE;
            case KB:
                return size * KB;
            case MB:
                return size * MB;
            case GB:
                return size * GB;
        }
    }

    /**
     * 字节数转合适大小
     * <p>保留3位小数</p>
     *
     * @param byteNum 字节数
     * @return 1...1024 unit
     */
    public static String byte2FitSize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < KB) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < MB) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / KB);
        } else if (byteNum < GB) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / MB);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / GB);
        }
    }

    /**
     * inputStream转outputStream
     *
     * @param is 输入流
     * @return outputStream子类
     */
    public static ByteArrayOutputStream input2OutputStream(InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[KB];
            int len;
            while ((len = is.read(b, 0, KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            FileUtils.closeIO(is);
        }
    }

    /**
     * outputStream转inputStream
     *
     * @param out 输出流
     * @return inputStream子类
     */
    public ByteArrayInputStream output2InputStream(OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * inputStream转byteArr
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        return input2OutputStream(is).toByteArray();
    }

    /**
     * byteArr转inputStream
     *
     * @param bytes 字节数组
     * @return 输入流
     */
    public static InputStream bytes2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream转byteArr
     *
     * @param out 输出流
     * @return 字节数组
     */
    public static byte[] outputStream2Bytes(OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * outputStream转byteArr
     *
     * @param bytes 字节数组
     * @return 字节数组
     */
    public static OutputStream bytes2OutputStream(byte[] bytes) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            FileUtils.closeIO(os);
        }
    }

    /**
     * inputStream转string按编码
     *
     * @param is          输入流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String inputStream2String(InputStream is, String charsetName) {
        if (is == null || StringUtils.isSpace(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转inputStream按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static InputStream string2InputStream(String string, String charsetName) {
        if (string == null || StringUtils.isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream转string按编码
     *
     * @param out         输出流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String outputStream2String(OutputStream out, String charsetName) {
        if (out == null) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转outputStream按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static OutputStream string2OutputStream(String string, String charsetName) {
        if (string == null || StringUtils.isSpace(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byteArr转bitmap
     *
     * @param bytes 字节数组
     * @return bitmap对象
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap对象
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * bitmap转drawable
     *
     * @param res    resources对象
     * @param bitmap bitmap对象
     * @return drawable对象
     */
    public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(res, bitmap);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format) {
        return bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * byteArr转drawable
     *
     * @param res   resources对象
     * @param bytes 字节数组
     * @return drawable对象
     */
    public static Drawable bytes2Drawable(Resources res, byte[] bytes) {
        return bitmap2Drawable(res, bytes2Bitmap(bytes));
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }





    public static String[] pystr = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian",
            "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che",
            "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan",
            "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du",
            "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang",
            "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang",
            "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian",
            "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken",
            "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng",
            "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan", "lue", "lun", "luo", "ma", "mai",
            "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai",
            "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan",
            "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",
            "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re",
            "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha",
            "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun",
            "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao",
            "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi",
            "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi",
            "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha",
            "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui",
            "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};
    private static int[] pyvalue = new int[]{-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036, -20032,
            -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741, -19739, -19728,
            -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261,
            -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961,
            -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518, -18501, -18490,
            -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183, -18181, -18012, -17997, -17988,
            -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692,
            -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733,
            -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419,
            -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158, -16155, -15959,
            -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659, -15652, -15640, -15631,
            -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180,
            -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941,
            -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871, -14857,
            -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384, -14379, -14368, -14355, -14353,
            -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094,
            -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831,
            -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329,
            -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875, -12871, -12860,
            -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556, -12359, -12346, -12320, -12300,
            -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358,
            -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014,
            -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309,
            -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};
    private static CharacterParser characterParser = new CharacterParser();
    private String resource;

    public static CharacterParser getInstance() {
        return characterParser;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    private int getChsAscii(String chs) {
        int asc = 0;
        try {
            byte[] bytes = chs.getBytes("gb2312");

            if (bytes.length == 1) {
                asc = bytes[0];
            } else if (bytes.length == 2) {
                int highByte = 256 + bytes[0];
                int lowByte = 256 + bytes[1];
                asc = (256 * highByte + lowByte) - 256 * 256;
            } else
                throw new RuntimeException("illegel resource string");

        } catch (Exception e) {
            System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
        }
        return asc;
    }

    /**
     * 单个字解析
     *
     * @param str 被解析的字
     * @return 解析出的拼音
     */
    public String convert(String str) {
        String result = null;
        int ascii = getChsAscii(str);
        if (ascii > 0 && ascii < 160) {
            result = String.valueOf((char) ascii);
        } else {
            for (int i = (pyvalue.length - 1); i >= 0; i--) {
                if (pyvalue[i] <= ascii) {
                    result = pystr[i];
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 词组解析
     *
     * @param chs 被解析的词组
     * @return 解析出的拼音
     */
    public String getSpelling(String chs) {
        String key, value;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chs.length(); i++) {
            key = chs.substring(i, i + 1);
            if (key.getBytes().length >= 2) {
                value = convert(key);
                if (value == null) {
                    value = "unknown";
                }
            } else {
                value = key;
            }
            builder.append(value);
        }
        return builder.toString();
    }

}
