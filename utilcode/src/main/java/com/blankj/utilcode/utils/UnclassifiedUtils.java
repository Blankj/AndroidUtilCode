package com.blankj.utilcode.utils;

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
}
