package com.blankj.utildebug;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.ViewGroup;

import com.blankj.utilcode.util.Utils;
import com.blankj.utildebug.debug.IDebug;
import com.blankj.utildebug.debug.tool.AbsToolDebug;
import com.blankj.utildebug.debug.tool.appInfo.AppInfoDebug;
import com.blankj.utildebug.debug.tool.clearCache.ClearCacheDebug;
import com.blankj.utildebug.debug.tool.clearStorage.ClearStorageDebug;
import com.blankj.utildebug.debug.tool.deviceInfo.DeviceInfoDebug;
import com.blankj.utildebug.debug.tool.fileExplorer.FileExplorerDebug;
import com.blankj.utildebug.debug.tool.restartApp.RestartAppDebug;
import com.blankj.utildebug.helper.WindowHelper;
import com.blankj.utildebug.icon.DebugIcon;
import com.blankj.utildebug.menu.DebugMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/28
 *     desc  :
 * </pre>
 */
public class DebugUtils {

    private static boolean isInstalled;

    private DebugUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void install() {
        install(null);
    }

    public static void install(List<IDebug> debugs) {
        install(-1, debugs);
    }

    public static void install(final @DrawableRes int icon,
                               final List<IDebug> debugs) {
        if (isInstalled) return;
        isInstalled = true;
        final List<IDebug> debugList = new ArrayList<>();
        AbsToolDebug.addToolDebugs(debugList);
        if (debugs != null) {
            debugList.addAll(debugs);
        }
        DebugIcon.getInstance().setIconId(icon);
        DebugMenu.getInstance().setDebugs(debugList);
        getApp().registerActivityLifecycleCallbacks(ActivityLifecycleImpl.instance);
    }

    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        private static ActivityLifecycleImpl instance = new ActivityLifecycleImpl();

        private int                    mConfigCount = 0;
        private ViewGroup.LayoutParams mParams      = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (mConfigCount < 0) {
                ++mConfigCount;
                WindowHelper.updateWindowHeight();
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            ((ViewGroup) activity.getWindow().getDecorView()).addView(DebugIcon.getInstance(), mParams);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            ((ViewGroup) activity.getWindow().getDecorView()).removeView(DebugIcon.getInstance());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

    public static Application getApp() {
        return Utils.getApp();
    }
}
