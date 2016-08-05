package com.blankj.androidframework.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/*********************************************
 * author: Blankj on 2016/8/2 21:24
 * blog:   http://blankj.com
 * e-mail: blankj@qq.com
 *********************************************/
public class UnclassifiedUtils {

    private UnclassifiedUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 获取服务是否开启
     * @param className 完整包名的服务类名
     */
    public static boolean isRunningService(String className, Context context) {
        // 进程的管理者,活动的管理者
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的服务，最多获取1000个
        List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
        // 遍历集合
        for (RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName service = runningServiceInfo.service;
            if (className.equals(service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获取浮点数保留几位小数后的字符串
     * @param number 原始数字，如float, double 等类型的数字
     * @param place 保留的位数
     */
    public static String getNumberString(Object number, int place) {
        try {
            return String.format("%." + place + "f", number);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
}
