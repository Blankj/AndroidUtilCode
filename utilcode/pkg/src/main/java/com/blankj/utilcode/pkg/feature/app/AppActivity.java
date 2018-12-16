package com.blankj.utilcode.pkg.feature.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.Config;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.pkg.helper.PermissionHelper;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about AppUtils
 * </pre>
 */

public class AppActivity extends BaseBackActivity {

    private final OnReleasedListener listener = new OnReleasedListener() {
        @Override
        public void onReleased() {
            AppUtils.installApp(Config.TEST_APK_PATH);
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, AppActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        AppUtils.registerAppStatusChangedListener(this, new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                ToastUtils.showShort("foreground");
            }

            @Override
            public void onBackground() {
                ToastUtils.showShort("background");
            }
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_app;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_app);

        findViewById(R.id.btn_install_app).setOnClickListener(this);
        findViewById(R.id.btn_install_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_launch_app).setOnClickListener(this);
        findViewById(R.id.btn_relaunch_app).setOnClickListener(this);
        findViewById(R.id.btn_exit_app).setOnClickListener(this);
        findViewById(R.id.btn_launch_app_details_settings).setOnClickListener(this);
        TextView tvAboutApp = findViewById(R.id.tv_about_app);
        tvAboutApp.setText(new SpanUtils()
                .appendLine("isAppRoot: " + AppUtils.isAppRoot())
                .appendLine("isAppDebug: " + AppUtils.isAppDebug())
                .appendLine("isAppSystem: " + AppUtils.isAppSystem())
                .appendLine("isAppForeground: " + AppUtils.isAppForeground())
                .append("getAppIcon: ").appendImage(AppUtils.getAppIcon(), SpanUtils.ALIGN_CENTER)
                .appendLine()
                .appendLine("getAppPackageName: " + AppUtils.getAppPackageName())
                .appendLine("getAppName: " + AppUtils.getAppName())
                .appendLine("getAppPath: " + AppUtils.getAppPath())
                .appendLine("getAppVersionName: " + AppUtils.getAppVersionName())
                .appendLine("getAppVersionCode: " + AppUtils.getAppVersionCode())
                .appendLine("getAppSignatureSHA1: " + AppUtils.getAppSignatureSHA1())
                .appendLine("getAppSignatureSHA256: " + AppUtils.getAppSignatureSHA256())
                .append("getAppSignatureMD5: " + AppUtils.getAppSignatureMD5())
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_install_app) {
            if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips);
            } else {
                PermissionHelper.requestStorage(new PermissionHelper.OnPermissionGrantedListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                            new ReleaseInstallApkTask(listener).execute();
                        } else {
                            listener.onReleased();
                            LogUtils.d("test apk existed.");
                        }
                    }
                });
            }

        } else if (i == R.id.btn_install_app_silent) {
            if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips);
            } else {
                if (AppUtils.installAppSilent(Config.TEST_APK_PATH)) {
                    ToastUtils.showShort(R.string.install_successfully);
                } else {
                    ToastUtils.showShort(R.string.install_unsuccessfully);
                }
            }

        } else if (i == R.id.btn_uninstall_app) {
            if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                AppUtils.uninstallApp(Config.TEST_PKG);
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips);
            }

        } else if (i == R.id.btn_uninstall_app_silent) {
            if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                if (AppUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                    ToastUtils.showShort(R.string.uninstall_successfully);
                } else {
                    ToastUtils.showShort(R.string.uninstall_unsuccessfully);
                }
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips);
            }

        } else if (i == R.id.btn_launch_app) {
            AppUtils.launchApp(this.getPackageName());

        } else if (i == R.id.btn_relaunch_app) {
            AppUtils.relaunchApp();

        } else if (i == R.id.btn_launch_app_details_settings) {
            AppUtils.launchAppDetailsSettings();

        } else if (i == R.id.btn_exit_app) {
            AppUtils.exitApp();

        }
    }

    @Override
    protected void onDestroy() {
        AppUtils.unregisterAppStatusChangedListener(this);
        super.onDestroy();
    }
}
