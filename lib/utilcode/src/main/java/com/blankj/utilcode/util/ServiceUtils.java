package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about service
 * </pre>
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return all of the services are running.
     *
     * @return all of the services are running
     */
    public static Set<String> getAllRunningServices() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * Start the service.
     *
     * @param className The name of class.
     */
    public static void startService(@NonNull final String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the service.
     *
     * @param cls The service class.
     */
    public static void startService(@NonNull final Class<?> cls) {
        startService(new Intent(Utils.getApp(), cls));
    }

    /**
     * Start the service.
     *
     * @param intent The intent.
     */
    public static void startService(Intent intent) {
        try {
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Utils.getApp().startForegroundService(intent);
            } else {
                Utils.getApp().startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop the service.
     *
     * @param className The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(@NonNull final String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Stop the service.
     *
     * @param cls The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(@NonNull final Class<?> cls) {
        return stopService(new Intent(Utils.getApp(), cls));
    }

    /**
     * Stop the service.
     *
     * @param intent The intent.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(@NonNull Intent intent) {
        try {
            return Utils.getApp().stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Bind the service.
     *
     * @param className The name of class.
     * @param conn      The ServiceConnection object.
     * @param flags     Operation options for the binding.
     *                  <ul>
     *                  <li>0</li>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(@NonNull final String className,
                                   @NonNull final ServiceConnection conn,
                                   final int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bind the service.
     *
     * @param cls   The service class.
     * @param conn  The ServiceConnection object.
     * @param flags Operation options for the binding.
     *              <ul>
     *              <li>0</li>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(@NonNull final Class<?> cls,
                                   @NonNull final ServiceConnection conn,
                                   final int flags) {
        bindService(new Intent(Utils.getApp(), cls), conn, flags);
    }

    /**
     * Bind the service.
     *
     * @param intent The intent.
     * @param conn   The ServiceConnection object.
     * @param flags  Operation options for the binding.
     *               <ul>
     *               <li>0</li>
     *               <li>{@link Context#BIND_AUTO_CREATE}</li>
     *               <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *               <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *               <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *               <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *               <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *               </ul>
     */
    public static void bindService(@NonNull final Intent intent,
                                   @NonNull final ServiceConnection conn,
                                   final int flags) {
        try {
            Utils.getApp().bindService(intent, conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unbind the service.
     *
     * @param conn The ServiceConnection object.
     */
    public static void unbindService(@NonNull final ServiceConnection conn) {
        Utils.getApp().unbindService(conn);
    }

    /**
     * Return whether service is running.
     *
     * @param cls The service class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(@NonNull final Class<?> cls) {
        return isServiceRunning(cls.getName());
    }

    /**
     * Return whether service is running.
     *
     * @param className The name of class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(@NonNull final String className) {
        try {
            ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
            if (info == null || info.size() == 0) return false;
            for (RunningServiceInfo aInfo : info) {
                if (className.equals(aInfo.service.getClassName())) return true;
            }
            return false;
        } catch (Exception ignore) {
            return false;
        }
    }
}
