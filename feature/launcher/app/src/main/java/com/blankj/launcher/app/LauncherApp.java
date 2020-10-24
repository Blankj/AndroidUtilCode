package com.blankj.launcher.app;

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
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}


