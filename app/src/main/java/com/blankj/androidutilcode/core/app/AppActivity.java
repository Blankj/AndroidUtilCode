package com.blankj.androidutilcode.core.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.Config;
import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : App工具类Demo
 * </pre>
 */

public class AppActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AppActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_app;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_app));

        findViewById(R.id.btn_install_app).setOnClickListener(this);
        findViewById(R.id.btn_install_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_launch_app).setOnClickListener(this);
        findViewById(R.id.btn_exit_app).setOnClickListener(this);
        findViewById(R.id.btn_get_app_details_settings).setOnClickListener(this);
        TextView tvAboutApp = (TextView) findViewById(R.id.tv_about_app);
        tvAboutApp.setText(
                new SpanUtils()
                        .append("app icon: ").appendImage(AppUtils.getAppIcon(), SpanUtils.ALIGN_CENTER).appendLine()
                        .appendLine(AppUtils.getAppInfo().toString())
                        .appendLine("isAppRoot: " + AppUtils.isAppRoot())
                        .appendLine("isAppDebug: " + AppUtils.isAppDebug())
                        .appendLine("AppSignatureSHA1: " + AppUtils.getAppSignatureSHA1())
                        .append("isAppForeground: " + AppUtils.isAppForeground())
                        .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install_app:
                if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                    ToastUtils.showShort(R.string.app_install_tips);
                } else {
                    AppUtils.installApp(Config.getTestApkPath(), "com.blankj.androidutilcode.provider");
                }
                break;
            case R.id.btn_install_app_silent:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                            ToastUtils.showShort(R.string.app_install_tips);
                        } else {
                            if (AppUtils.installAppSilent(Config.getTestApkPath())) {
                                ToastUtils.showShort(R.string.install_successfully);
                            } else {
                                ToastUtils.showShort(R.string.install_unsuccessfully);
                            }
                        }
                    }
                }).start();
                break;
            case R.id.btn_uninstall_app:
                if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                    AppUtils.uninstallApp(Config.TEST_PKG);
                } else {
                    ToastUtils.showShort(R.string.app_uninstall_tips);
                }
                break;
            case R.id.btn_uninstall_app_silent:
                if (AppUtils.isInstallApp(Config.TEST_PKG)) {
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
            case R.id.btn_exit_app:
                AppUtils.exitApp();
                break;
            case R.id.btn_get_app_details_settings:
                AppUtils.getAppDetailsSettings();
                break;
        }
    }
}
