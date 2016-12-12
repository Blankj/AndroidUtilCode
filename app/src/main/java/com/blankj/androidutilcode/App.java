package com.blankj.androidutilcode;

import android.app.Application;

import com.blankj.utilcode.utils.CrashUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.Utils;
import com.squareup.leakcanary.LeakCanary;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : App
 * </pre>
 */
public class App extends Application {

    private static App appContext;

    public static App getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 内存泄露检查工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        appContext = this;
        Utils.init(appContext);
        CrashUtils.getInstance().init();
        LogUtils.getBuilder().setTag("MyTag").setLog2FileSwitch(true).create();
    }
}
