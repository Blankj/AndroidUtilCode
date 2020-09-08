package com.blankj.subutil.util;

/**
 * <pre>
 *     author: Faramarz Afzali
 *     time  : 2020/09/05
 *     desc  : This class is intended for converting temperatures into different units.
 *             C refers to the Celsius unit
 *             F refers to the Fahrenheit unit
 *             K refers to the Kelvin unit
 * </pre>
 */
public final class TemperatureUtils {

    public static float cToF(float temp) {
        return (temp * 9) / 5 + 32;
    }

    public static float cToK(float temp) {
        return temp + 273.15f;
    }


    public static float fToC(float temp) {
        return (temp - 32) * 5 / 9;
    }

    public static float fToK(float temp) {
        return temp + 255.3722222222f;
    }


    public static float kToC(float temp) {
        return temp - 273.15f;
    }

    public static float kToF(float temp) {
        return temp - 459.67f;
    }
}
