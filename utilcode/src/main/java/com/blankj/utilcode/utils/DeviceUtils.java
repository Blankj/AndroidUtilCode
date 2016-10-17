package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.Build;
import android.provider.Settings;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/1
 *     desc  : 设备相关工具类
 * </pre>
 */
public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 关闭设备
     * 需要系统权限      <android:sharedUserId="android.uid.system"/>
     * @param context
     */
    public static void shutDownDevice(Context context) {
        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 重启设备,广播方式 需要系统权限
     * 需要系统权限      <android:sharedUserId="android.uid.system"/>
     * @param context
     */
    public static void rebootDevice(Context context, String reason) {
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        context.sendBroadcast(intent);
    }

    /**
     * 重启设备, 电源管理的方式 需要系统权限
     * 需要系统权限      <android:sharedUserId="android.uid.system"/>
     * @param context
     */
    public static void rebootDevicePM(Context context, String reason) {
        PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        try {
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            KLog.w("重启设备失败: —— " + e.getMessage());
        }
    }

    /**
     * 判断设备是否root
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isRoot() {
        return ShellUtils.execCmd("echo root", true, false).result == 0;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @param context 上下文
     * @return AndroidID
     */
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
            String macAddress = info.getMacAddress();
            if (macAddress != null) {
                return macAddress.replace(":", "");
            }
        }
        return null;
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */

    public static String getMacAddress() {
        String macAddress = null;
        LineNumberReader lnr = null;
        InputStreamReader isr = null;
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            isr = new InputStreamReader(pp.getInputStream());
            lnr = new LineNumberReader(isr);
            macAddress = lnr.readLine().replace(":", "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(lnr, isr);
        }
        return macAddress == null ? "" : macAddress;
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }
}
