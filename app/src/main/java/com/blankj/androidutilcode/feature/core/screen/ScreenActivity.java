package com.blankj.androidutilcode.feature.core.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : Screen 工具类 Demo
 * </pre>
 */
public class ScreenActivity extends BaseActivity {

    ImageView ivScreenshot;
    TextView  tvAboutScreen;

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
        ivScreenshot = findViewById(R.id.iv_screenshot);
        tvAboutScreen = findViewById(R.id.tv_about_screen);
        findViewById(R.id.btn_set_fullscreen).setOnClickListener(this);
        findViewById(R.id.btn_set_landscape).setOnClickListener(this);
        findViewById(R.id.btn_set_portrait).setOnClickListener(this);
        findViewById(R.id.btn_screenshot).setOnClickListener(this);
        findViewById(R.id.btn_set_sleep_duration).setOnClickListener(this);

        updateAboutScreen();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_fullscreen:
                ScreenUtils.setFullScreen(this);
                break;
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
                updateAboutScreen();
                break;
        }
    }

    private void updateAboutScreen() {
        tvAboutScreen.setText(new SpanUtils()
                .appendLine("getScreenWidth: " + ScreenUtils.getScreenWidth())
                .appendLine("getScreenHeight: " + ScreenUtils.getScreenHeight())
                .appendLine("isLandscape: " + ScreenUtils.isLandscape())
                .appendLine("isPortrait: " + ScreenUtils.isPortrait())
                .appendLine("getScreenRotation: " + ScreenUtils.getScreenRotation(this))
                .appendLine("isScreenLock: " + ScreenUtils.isScreenLock())
                .appendLine("getSleepDuration: " + ScreenUtils.getSleepDuration())
                .append("isTablet: " + ScreenUtils.isTablet())
                .create()
        );
    }
}
