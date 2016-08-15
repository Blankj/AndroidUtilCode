# 常量相关
``` java
/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : 常量相关工具类
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
}
```