package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.Config;
import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpannableStringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/10/13
 *     desc : App工具类Demo
 * </pre>
 */

public class AppActivity extends BaseActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_app;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        findViewById(R.id.btn_install_app).setOnClickListener(this);
        findViewById(R.id.btn_install_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_launch_app).setOnClickListener(this);
        findViewById(R.id.btn_get_app_details_settings).setOnClickListener(this);
        TextView tvAboutApp = (TextView) findViewById(R.id.tv_about_app);
        tvAboutApp.setText(new SpannableStringUtils.Builder().append("app icon: ")
                .appendLine("").setDrawable(AppUtils.getAppIcon(), SpannableStringUtils.ALIGN_CENTER)
                .append(AppUtils.getAppInfo().toString())
                .appendLine("isAppRoot: " + AppUtils.isAppRoot())
                .appendLine("isAppDebug: " + AppUtils.isAppDebug())
                .appendLine("AppSignatureSHA1: " + AppUtils.getAppSignatureSHA1())
                .appendLine("isAppForeground: " + AppUtils.isAppForeground())
                .create());
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install_app:
                if (AppUtils.isInstallApp("com.blankj.testinstallapk")) {
                    ToastUtils.showShort(R.string.app_install_tips);
                } else {
                    AppUtils.installApp(this, Config.getTestApkPath(), "com.blankj.androidutilcode.provider", 0);
                }
                break;
            case R.id.btn_install_app_silent:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (AppUtils.isInstallApp("com.blankj.testinstallapk")) {
                            ToastUtils.showShortSafe(R.string.app_install_tips);
                        } else {
                            if (AppUtils.installAppSilent(Config.getTestApkPath())) {
                                ToastUtils.showShortSafe(R.string.install_successfully);
                            } else {
                                ToastUtils.showShortSafe(R.string.install_unsuccessfully);
                            }
                        }
                    }
                }).start();
                break;
            case R.id.btn_uninstall_app:
                if (AppUtils.isInstallApp("com.blankj.testinstallapk")) {
                    AppUtils.uninstallApp(this, Config.TEST_PKG, 0);
                } else {
                    ToastUtils.showShort(R.string.app_uninstall_tips);
                }
                break;
            case R.id.btn_uninstall_app_silent:
                if (AppUtils.isInstallApp("com.blankj.testinstallapk")) {
                    if (AppUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                        ToastUtils.showShort(R.string.uninstall_successfully);
                    } else {
                        ToastUtils.showShort(R.string.uninstall_unsuccessfully);
                    }
                } else {
                    ToastUtils.showShort(R.string.app_uninstall_tips);
                }
                break;
            case R.id.btn_launch_app:
                AppUtils.launchApp(this.getPackageName());
                break;
            case R.id.btn_get_app_details_settings:
                AppUtils.getAppDetailsSettings();
                break;
        }
    }
}
