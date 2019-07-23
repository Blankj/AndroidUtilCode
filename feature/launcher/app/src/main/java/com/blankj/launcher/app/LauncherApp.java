package com.blankj.launcher.app;

import android.content.Context;

import com.blankj.common.CommonApplication;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  :
 * </pre>
 */
public class LauncherApp extends CommonApplication {

    private static LauncherApp sInstance;

    public static LauncherApp getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}


