package com.blankj.utilcode.util;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : 震动相关工具类
 * </pre>
 */
public final class VibrationUtils {

//    private VibrationUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }
//
//    /**
//     * 震动
//     * <p>需添加权限 {@code <uses-permission android:name="android.permission.VIBRATE"/>}</p>
//     *
//     * @param context      上下文
//     * @param milliseconds 振动时长
//     */
//    public static void vibrate(final Context context, final long milliseconds) {
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(milliseconds);
//    }
//
//    /**
//     * 指定手机以pattern模式振动
//     *
//     * @param context
//     * @param pattern new long[]{400,800,1200,1600}，就是指定在400ms、800ms、1200ms、1600ms这些时间点交替启动、关闭手机振动器
//     * @param repeat  指定pattern数组的索引，指定pattern数组中从repeat索引开始的振动进行循环。-1表示只振动一次，非-1表示从 pattern的指定下标开始重复振动。
//     */
//    public static void vibrate(final Context context, final long[] pattern, final int repeat) {
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(pattern, repeat);
//    }
//
//    /**
//     * 取消振动
//     *
//     * @param context 上下文
//     */
//    public static void cancel(final Context context) {
//        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).cancel();
//    }
}
