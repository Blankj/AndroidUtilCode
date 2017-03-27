package com.blankj.androidutilcode;

import android.app.Application;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : 工具类测试App
 * </pre>
 */
public class UtilsApp extends Application {

    public static LogUtils.Builder lBuilder;

    private static UtilsApp appContext;

    public static UtilsApp getInstance() {
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
        lBuilder = new LogUtils.Builder()
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("CMJ")// 设置log全局标签，默认为空
                                    // 当全局标签不为空时，我们输出的log全部为该tag，
                                    // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }
}
