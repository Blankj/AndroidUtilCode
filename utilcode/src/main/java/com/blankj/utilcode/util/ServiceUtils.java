package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.HashSet;
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
public final class ServiceUtils {

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
     * 启动服务
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     */
    public static void startService(Context context, String className) {
        try {
            startService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     *
     * @param context 上下文
     * @param cls     服务类
     */
    public static void startService(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
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
            return stopService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     *
     * @param context 上下文
     * @param cls     服务类
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        return context.stopService(intent);
    }

    /**
     * 绑定服务
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(Context context, String className, ServiceConnection conn, int flags) {
        try {
            bindService(context, Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     *
     * @param context 上下文
     * @param cls     服务类
     * @param conn    服务连接对象
     * @param flags   绑定选项
     *                <ul>
     *                <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                </ul>
     */
    public static void bindService(Context context, Class<?> cls, ServiceConnection conn, int flags) {
        Intent intent = new Intent(context, cls);
        context.bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     *
     * @param context 上下文
     * @param conn    服务连接对象
     */
    public static void unbindService(Context context, ServiceConnection conn) {
        context.unbindService(conn);
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
}