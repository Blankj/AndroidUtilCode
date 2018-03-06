package com.blankj.androidutilcode.feature.core.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.Config;
import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.androidutilcode.helper.AssertHelper;
import com.blankj.androidutilcode.helper.PermissionHelper;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : App 工具类 Demo
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
        findViewById(R.id.btn_launch_app_details_settings).setOnClickListener(this);
        TextView tvAboutApp = findViewById(R.id.tv_about_app);
//        "pkg name: " + getPackageName() +
//                "\napp name: " + getName() +
//                "\napp path: " + getPackagePath() +
//                "\napp v name: " + getVersionName() +
//                "\napp v code: " + getVersionCode() +
//                "\nis system: " + isSystem();
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
                .append("getAppSignatureSHA1: " + AppUtils.getAppSignatureSHA1())
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install_app:
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    ToastUtils.showShort(R.string.app_install_tips);
                } else {
                    PermissionHelper.requestStorage(new PermissionHelper.OnPermissionGrantedListener() {
                        @Override
                        public void onPermissionGranted() {
                            AssertHelper.releaseInstallApk(new AssertHelper.OnReleasedListener() {
                                @Override
                                public void onReleased() {
                                    AppUtils.installApp(
                                            Config.TEST_APK_PATH,
                                            "com.blankj.androidutilcode.provider"
                                    );
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.btn_install_app_silent:
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    ToastUtils.showShort(R.string.app_install_tips);
                } else {
                    if (AppUtils.installAppSilent(Config.TEST_APK_PATH)) {
                        ToastUtils.showShort(R.string.install_successfully);
                    } else {
                        ToastUtils.showShort(R.string.install_unsuccessfully);
                    }
                }
                break;
            case R.id.btn_uninstall_app:
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    AppUtils.uninstallApp(Config.TEST_PKG);
                } else {
                    ToastUtils.showShort(R.string.app_uninstall_tips);
                }
                break;
            case R.id.btn_uninstall_app_silent:
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
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
            case R.id.btn_launch_app_details_settings:
                AppUtils.launchAppDetailsSettings();
                break;
            case R.id.btn_exit_app:
                AppUtils.exitApp();
                break;
        }
    }
}
