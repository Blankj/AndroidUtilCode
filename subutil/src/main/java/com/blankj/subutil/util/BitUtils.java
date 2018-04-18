package com.blankj.subutil.util;

import android.util.Log;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/03/21
 *     desc  : 位运算工具类
 * </pre>
 */
public final class BitUtils {

    private BitUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取运算数指定位置的值<br>
     * 例如： 0000 1011 获取其第 0 位的值为 1, 第 2 位 的值为 0<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0...7)
     * @return 指定位置的值(0 or 1)
     */
    public static byte getBitValue(byte source, int pos) {
        return (byte) ((source >> pos) & 1);

    }


    /**
     * 将运算数指定位置的值置为指定值<br>
     * 例: 0000 1011 需要更新为 0000 1111, 即第 2 位的值需要置为 1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @param value  只能取值为 0, 或 1, 所有大于0的值作为1处理, 所有小于0的值作为0处理
     * @return 运算后的结果数
     */
    public static byte setBitValue(byte source, int pos, byte value) {

        byte mask = (byte) (1 << pos);
        if (value > 0) {
            source |= mask;

        } else {
            source &= (~mask);

        }
        return source;
    }


    /**
     * 将运算数指定位置取反值<br>
     * 例： 0000 1011 指定第 3 位取反, 结果为 0000 0011; 指定第2位取反, 结果为 0000 1111<br>
     *
     * @param source
     * @param pos    指定位置 (0<=pos<=7)
     * @return 运算后的结果数
     */
    public static byte reverseBitValue(byte source, int pos) {
        byte mask = (byte) (1 << pos);
        return (byte) (source ^ mask);

    }


    /**
     * 检查运算数的指定位置是否为1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @return true 表示指定位置值为1, false 表示指定位置值为 0
     */
    public static boolean checkBitValue(byte source, int pos) {

        source = (byte) (source >>> pos);

        return (source & 1) == 1;

    }

    /**
     * 入口函数做测试<br>
     *
     * @param args
     */
    public static void main(String[] args) {
        // 取十进制 11 (二级制 0000 1011) 为例子
        byte source = 11;
        // 取第2位值并输出, 结果应为 0000 1011
        for (byte i = 7; i >= 0; i--) {
            Log.d("BitUtils", getBitValue(source, i) + "");

        }
        // 将第6位置为1并输出 , 结果为 75 (0100 1011)
        Log.d("BitUtils", setBitValue(source, 6, (byte) 1) + "");

        // 将第6位取反并输出, 结果应为75(0100 1011)
        Log.d("BitUtils", reverseBitValue(source, 6) + "");

        // 检查第6位是否为1，结果应为false
        Log.d("BitUtils", checkBitValue(source, 6) + "");

        // 输出为1的位, 结果应为 0 1 3
        for (byte i = 0; i < 8; i++) {
            if (checkBitValue(source, i)) {
                Log.d("BitUtils", i + "");
            }
        }
    }
}
