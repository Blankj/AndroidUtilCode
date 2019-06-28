package com.blankj.launcher.app;

import android.content.Context;

import com.blankj.lib.common.CommonApplication;
import com.blankj.utilcode.util.LanguageUtils;

import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : app about utils
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
        LanguageUtils.applyLanguage(getBaseContext(), Locale.SIMPLIFIED_CHINESE);
    }
}


