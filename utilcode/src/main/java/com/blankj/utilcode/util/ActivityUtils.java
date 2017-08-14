package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : Activity相关工具类
 * </pre>
 */
public final class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断是否存在Activity
     *
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(@NonNull final String packageName,
                                           @NonNull final String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getApp().getPackageManager()) == null ||
                Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 启动Activity
     *
     * @param cls activity类
     */
    public static void startActivity(@NonNull final Class<?> cls) {
        Context context = Utils.getApp();
        startActivity(context, null, context.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param cls     activity类
     * @param options 跳转动画
     */
    public static void startActivity(@NonNull final Class<?> cls,
                                     @NonNull final Bundle options) {
        Context context = Utils.getApp();
        startActivity(context, null, context.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param cls      activity类
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> cls) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param cls      activity类
     * @param options  跳转动画
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动Activity
     *
     * @param extras extras
     * @param cls    activity类
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> cls) {
        Context context = Utils.getApp();
        startActivity(context, extras, context.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param extras  extras
     * @param cls     activity类
     * @param options 跳转动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<?> cls,
                                     @NonNull final Bundle options) {
        Context context = Utils.getApp();
        startActivity(context, extras, context.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param cls      activity类
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> cls) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param cls      activity类
     * @param options  跳转动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param extras    extras
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动Activity
     *
     * @param pkg 包名
     * @param cls 全类名
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(Utils.getApp(), null, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(Utils.getApp(), null, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param pkg      包名
     * @param cls      全类名
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, null, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param pkg      包名
     * @param cls      全类名
     * @param options  动画
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, null, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param activity  activity
     * @param pkg       包名
     * @param cls       全类名
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, pkg, cls, null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动Activity
     *
     * @param extras extras
     * @param pkg    包名
     * @param cls    全类名
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(Utils.getApp(), extras, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param extras  extras
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(Utils.getApp(), extras, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param extras   extras
     * @param pkg      包名
     * @param cls      全类名
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, extras, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param pkg      包名
     * @param cls      全类名
     * @param options  动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, extras, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param extras    extras
     * @param pkg       包名
     * @param cls       全类名
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, pkg, cls, null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(@NonNull final String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }


    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public static Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                activities = (HashMap) activitiesField.get(activityThread);
            } else {
                activities = (ArrayMap) activitiesField.get(activityThread);
            }
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
