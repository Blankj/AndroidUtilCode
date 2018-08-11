package com.blankj.androidutilcode.feature.core.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.ScreenUtils;

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
            ScreenUtils.adaptScreen4VerticalSlide(this, 720);
        } else {
            ScreenUtils.setFullScreen(this);
            ScreenUtils.adaptScreen4HorizontalSlide(this, 720);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_screen_adapt;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        ScreenUtils.cancelAdaptScreen(this);
        super.onDestroy();
    }
}
