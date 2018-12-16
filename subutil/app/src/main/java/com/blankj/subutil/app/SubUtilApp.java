package com.blankj.subutil.app;

import android.content.Context;

import com.blankj.lib.base.BaseApplication;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : app about utils
 * </pre>
 */
public class SubUtilApp extends BaseApplication {

    private static SubUtilApp sInstance;

    public static SubUtilApp getInstance() {
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


