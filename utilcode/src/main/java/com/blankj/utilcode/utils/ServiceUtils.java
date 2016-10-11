package com.blankj.utilcode.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 服务相关工具类
 * </pre>
 */
public class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取服务是否开启
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isRunningService(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infoList = activityManager.getRunningServices(0x7FFFFFFF);
        if (infoList == null || infoList.size() == 0) return false;
        for (RunningServiceInfo info : infoList) {
            if (className.equals(info.service.getClassName())) return true;
        }
        return false;
    }
}