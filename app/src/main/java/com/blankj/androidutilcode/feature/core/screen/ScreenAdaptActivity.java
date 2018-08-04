package com.blankj.androidutilcode.feature.core.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : demo about ScreenUtils
 * </pre>
 */
public class ScreenAdaptActivity extends BaseActivity {

    private TextView tvUp;
    private TextView tvDown;

    public static void start(Context context) {
        Intent starter = new Intent(context, ScreenAdaptActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(this, 360);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_screen_adapt;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tvUp = findViewById(R.id.tv_up);
        tvDown = findViewById(R.id.tv_down);
        if (!ScreenUtils.isPortrait()) {
            updateLayout();
        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void toggleFullScreen(View view) {
        ScreenUtils.toggleFullScreen(this);
        updateLayout();
    }

    private void updateLayout() {
        int statusBarHeight = BarUtils.getStatusBarHeight();
        int statusBarHeightInDp = SizeUtils.px2dp(this, statusBarHeight);
        ViewGroup.LayoutParams upLayoutParams = tvUp.getLayoutParams();
        ViewGroup.LayoutParams downLayoutParams = tvDown.getLayoutParams();
        if (ScreenUtils.isFullScreen(this)) {
            int height = 360 / 2;
            String s = height + "dp";
            upLayoutParams.height = SizeUtils.dp2px(this, height);
            tvUp.setLayoutParams(upLayoutParams);
            tvUp.setText(s);

            downLayoutParams.height = SizeUtils.dp2px(this, height);
            tvDown.setLayoutParams(downLayoutParams);
            tvDown.setText(s);
        } else {
            int height = 360 / 2 - statusBarHeightInDp / 2;
            String s = height + "dp";
            upLayoutParams.height = SizeUtils.dp2px(this, height);
            tvUp.setLayoutParams(upLayoutParams);
            tvUp.setText(s);

            downLayoutParams.height = SizeUtils.dp2px(this, height);
            tvDown.setLayoutParams(downLayoutParams);
            tvDown.setText(s);
        }
    }
}
