package com.blankj.utilcode.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
     * 获取所有运行的服务
     *
     * @param context 上下文
     * @return 服务名集合
     */
    public static Set getAllRunningService(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = activityManager.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (infos == null || infos.size() == 0) return null;
        for (RunningServiceInfo info : infos) {
            names.add(info.service.getClassName());
        }
        return names;
    }

    /**
     * 判断服务是否运行
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = activityManager.getRunningServices(0x7FFFFFFF);
        if (infos == null || infos.size() == 0) return false;
        for (RunningServiceInfo info : infos) {
            if (className.equals(info.service.getClassName())) return true;
        }
        return false;
    }

    /**
     * 停止服务
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(Context context, String className) {
        try {
            Intent intent = new Intent(context, Class.forName(className));
            return context.stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}