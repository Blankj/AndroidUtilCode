package com.blankj.utilcode.app;

import android.content.Context;

import com.blankj.base.BaseApplication;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : app about utils
 * </pre>
 */
public class UtilCodeApp extends BaseApplication {

    private static UtilCodeApp sInstance;

    public static UtilCodeApp getInstance() {
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


