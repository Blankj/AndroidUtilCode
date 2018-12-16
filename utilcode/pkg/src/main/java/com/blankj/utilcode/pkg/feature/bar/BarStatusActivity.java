package com.blankj.utilcode.pkg.feature.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarStatusActivity extends BaseBackActivity {

    private TextView tvAboutStatus;

    public static void start(Context context) {
        Intent starter = new Intent(context, BarStatusActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar_status;
    }


    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_bar);

        tvAboutStatus = findViewById(R.id.tv_about_status);
        findViewById(R.id.btn_show_status).setOnClickListener(this);
        findViewById(R.id.btn_hide_status).setOnClickListener(this);
        findViewById(R.id.btn_light_mode).setOnClickListener(this);
        findViewById(R.id.btn_dark_mode).setOnClickListener(this);
        updateAboutStatus();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_show_status) {
            BarUtils.setStatusBarVisibility(this, true);

        } else if (i == R.id.btn_hide_status) {
            BarUtils.setStatusBarVisibility(this, false);

        } else if (i == R.id.btn_light_mode) {
            BarUtils.setStatusBarLightMode(this, true);

        } else if (i == R.id.btn_dark_mode) {
            BarUtils.setStatusBarLightMode(this, false);

        }
        updateAboutStatus();
    }

    private void updateAboutStatus() {
        tvAboutStatus.setText(new SpanUtils()
                .appendLine("statusHeight: " + BarUtils.getStatusBarHeight())
                .append("isStatusVisible: " + BarUtils.isStatusBarVisible(this))
                .create());
    }
}
