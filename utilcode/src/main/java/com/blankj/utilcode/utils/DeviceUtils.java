package com.blankj.utilcode.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/*********************************************
 * author: Blankj on 2016/8/1 19:43
 * blog:   http://blankj.com
 * e-mail: blankj@qq.com
 *********************************************/
public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getMacAddress(Context context) {
        String macAddress;
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        if (null == macAddress) {
            return "";
        }
        macAddress = macAddress.replace(":", "");
        return macAddress;
    }

    /**
     * 获取设备厂商，如Xiaomi
     */
    public static String getManufacturer() {
        String MANUFACTURER = Build.MANUFACTURER;
        return MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
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

    /**
     * 获取设备SD卡是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取设备SD卡路径
     * <p>一般是/storage/emulated/0/
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }
}
