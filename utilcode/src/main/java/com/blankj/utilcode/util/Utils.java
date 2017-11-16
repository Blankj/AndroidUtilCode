package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 * 　　　　　　　　　瓦瓦　　　　　　　　　　　　十
 * 　　　　　　　　十齱龠己　　　　　　　　　亅瓦車己
 * 　　　　　　　　乙龍龠毋日丶　　　　　　丶乙己毋毋丶
 * 　　　　　　　　十龠馬鬼車瓦　　　　　　己十瓦毋毋
 * 　　　　　　　　　鬼馬龠馬龠十　　　　己己毋車毋瓦
 * 　　　　　　　　　毋龠龠龍龠鬼乙丶丶乙車乙毋鬼車己
 * 　　　　　　　　　乙龠龍龍鬼龍瓦　十瓦毋乙瓦龠瓦亅
 * 　　　　　　　　　　馬齱龍馬鬼十丶日己己己毋車乙丶
 * 　　　　　　　　　　己齱馬鬼車十十毋日乙己己乙乙
 * 　　　　　　　　　　　車馬齱齱日乙毋瓦己乙瓦日亅
 * 　　　　　　　　　　　亅車齺龖瓦乙車龖龍乙乙十
 * 　　　　　　　　　　　　日龠龠十亅車龍毋十十
 * 　　　　　　　　　　　　日毋己亅　己己十亅亅
 * 　　　　　　　　　　　丶己十十乙　　丶丶丶丶丶
 * 　　　　　　　　　　　亅己十龍龖瓦　　丶　丶　乙十
 * 　　　　　　　　　　　亅己十龠龖毋　丶丶　　丶己鬼鬼瓦亅
 * 　　　　　　　　　　　十日十十日亅丶亅丶　丶十日毋鬼馬馬車乙
 * 　　　　　　　　　　　十日乙十亅亅亅丶　　十乙己毋鬼鬼鬼龍齺馬乙
 * 　　　　　　　　　　　丶瓦己乙十十亅丶亅乙乙乙己毋鬼鬼鬼龍齱齺齺鬼十
 * 　　　　　　　　　　　　乙乙十十十亅乙瓦瓦己日瓦毋鬼鬼龠齱齱龍龍齱齱毋丶
 * 　　　　　　　　　　　　亅十十十十乙瓦車毋瓦瓦日車馬龠龍龍龍龍龍龠龠龠馬亅
 * 　　　　　　　　　　　　　十十十十己毋車瓦瓦瓦瓦鬼馬龠龍龠龠龍龠龠龠馬龠車
 * 　　　　　　　　　　　　　　亅十十日毋瓦日日瓦鬼鬼鬼龠龠馬馬龠龍龍龠馬馬車
 * 　　　　　　　　　　　　　　亅亅亅乙瓦瓦毋車車車馬龍龠鬼鬼馬龠龍龍龠馬馬鬼
 * 　　　　　　　　　　　　丶丶乙亅亅乙車鬼鬼鬼毋車龍龍龠鬼馬馬龠龍齱齱龍馬鬼
 * 　　　　　　　　　　　亅己十十己十日鬼鬼車瓦毋龠龍龠馬馬龠龠龠齱齺齺齱龠鬼
 * 　　　　　　　　　　　　亅乙乙乙十車馬車毋馬齱齱龍龠龠龠馬龠龍齱龍龠龠鬼瓦
 * 　　　　　　　　　　　　　　　　丶毋龠鬼車瓦車馬龠龍龠龠龍齱齱龠馬馬鬼毋日
 * 　　　　　　　　　　　　　　　　十乙己日十　　丶己鬼龍齱齺齱龍馬馬馬車毋己
 * 　　　　　　　　　　　　　　丶十己乙亅丶　　　　　　亅瓦馬龠龍龠龠馬毋瓦乙
 * 　　　　　　　　　　　　　丶十十乙亅十　　　　　　　　亅己瓦車馬龠鬼車瓦乙
 * 　　　　　　　　　　　　　丶十乙十十丶　　　　　　　　　丶丶亅十瓦鬼車瓦己
 * 　　　　　　　　　　　　　　丶亅亅丶　　　　　　　　　　　　　　　亅日瓦日
 * 　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　丶
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    static WeakReference<Activity> sTopActivityWeakRef;
    static List<Activity> sActivityList = new LinkedList<>();

    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
        app.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 获取Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    private static void setTopActivityWeakRef(Activity activity) {
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }
}
