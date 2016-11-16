package com.blankj.androidutilcode;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.utils.CrashUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : App
 * </pre>
 */
public class App extends Application {

    private static App ourInstance;

    public static App getInstance() {
        return ourInstance;
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
        ourInstance = this;
        CrashUtils.getInstance().init(this);
        LogUtils.getBuilder(this).setTag("MyTag").setLog2FileSwitch(true).create();
    }
}
