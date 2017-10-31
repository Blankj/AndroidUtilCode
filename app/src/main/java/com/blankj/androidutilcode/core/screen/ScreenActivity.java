package com.blankj.androidutilcode.core.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : Screen工具类Demo
 * </pre>
 */
public class ScreenActivity extends BaseBackActivity {

    ImageView ivScreenshot;

    public static void start(Context context) {
        Intent starter = new Intent(context, ScreenActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_screen;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_sdcard));

        findViewById(R.id.btn_set_landscape).setOnClickListener(this);
        findViewById(R.id.btn_set_portrait).setOnClickListener(this);
        findViewById(R.id.btn_screenshot).setOnClickListener(this);
        findViewById(R.id.btn_set_sleep_duration).setOnClickListener(this);
        ivScreenshot = findViewById(R.id.iv_screenshot);
        TextView tvAboutSdcard = findViewById(R.id.tv_about_screen);
        tvAboutSdcard.setText("getScreenWidth: " + ScreenUtils.getScreenWidth()
                + "\ngetScreenHeight: " + ScreenUtils.getScreenHeight()
                + "\nisLandscape: " + ScreenUtils.isLandscape()
                + "\nisPortrait: " + ScreenUtils.isPortrait()
                + "\ngetScreenRotation: " + ScreenUtils.getScreenRotation(this)
                + "\nisScreenLock: " + ScreenUtils.isScreenLock()
                + "\ngetSleepDuration: " + ScreenUtils.getSleepDuration()
                + "\nisTablet: " + ScreenUtils.isTablet()
        );


    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_landscape:
                ScreenUtils.setLandscape(this);
                break;
            case R.id.btn_set_portrait:
                ScreenUtils.setPortrait(this);
                break;
            case R.id.btn_screenshot:
                ivScreenshot.setImageBitmap(ScreenUtils.screenShot(this));
                break;
            case R.id.btn_set_sleep_duration:
                ScreenUtils.setSleepDuration(100000);
                break;
        }
    }
}
