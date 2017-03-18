package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.AppUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/10/13
 *     desc : App工具类Demo
 * </pre>
 */

public class AppActivity extends Activity
        implements View.OnClickListener {

    private String appPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        appPath = AppUtils.getAppPath(this);

        TextView tvAboutApp = (TextView) findViewById(R.id.tv_about_app);

        findViewById(R.id.btn_install_app).setOnClickListener(this);
        findViewById(R.id.btn_install_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app).setOnClickListener(this);
        findViewById(R.id.btn_uninstall_app_silent).setOnClickListener(this);
        findViewById(R.id.btn_launch_app).setOnClickListener(this);
        findViewById(R.id.btn_get_app_details_settings).setOnClickListener(this);

        tvAboutApp.setText(AppUtils.getAppInfo(this).toString()
                + "\nisInstallWeiXin: " + AppUtils.isInstallApp(this, "com.tencent.mm")
                + "\nisAppRoot: " + AppUtils.isAppRoot()
                + "\nisAppDebug: " + AppUtils.isAppDebug(this)
                + "\nisWeiXinAppDebug: " + AppUtils.isAppDebug(this, "com.tencent.mm")
                + "\nAppSignatureSHA1: " + AppUtils.getAppSignatureSHA1(this)
                + "\nisAppForeground: " + AppUtils.isAppForeground(this)
                + "\nisWeiXinForeground: " + AppUtils.isAppForeground(this, "com.tencent.mm")
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install_app:
                AppUtils.installApp(this, appPath);
                break;
            case R.id.btn_install_app_silent:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.installAppSilent(appPath);
                    }
                }).start();
                break;
            case R.id.btn_uninstall_app:
                AppUtils.uninstallApp(this, this.getPackageName());
                break;
            case R.id.btn_uninstall_app_silent:
                AppUtils.uninstallAppSilent(this, this.getPackageName(), false);
                break;
            case R.id.btn_launch_app:
                AppUtils.launchApp(this.getPackageName());
                break;
            case R.id.btn_get_app_details_settings:
                AppUtils.getAppDetailsSettings(this);
                break;
        }
    }
}
