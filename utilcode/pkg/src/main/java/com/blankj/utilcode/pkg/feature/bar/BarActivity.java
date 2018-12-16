package com.blankj.utilcode.pkg.feature.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.R;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarActivity extends BaseBackActivity {

    private TextView tvAboutStatus;

    public static void start(Context context) {
        Intent starter = new Intent(context, BarActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_bar);

        tvAboutStatus = findViewById(R.id.tv_about_status);
        findViewById(R.id.btn_status_bar).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_color).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_alpha).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_image_view).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_fragment).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_swipe_back).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_drawer).setOnClickListener(this);
        findViewById(R.id.btn_notification_bar).setOnClickListener(this);
        findViewById(R.id.btn_nav_bar).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_status_bar) {
            BarStatusActivity.start(this);

        } else if (i == R.id.btn_status_bar_color) {
            BarStatusColorActivity.start(this);

        } else if (i == R.id.btn_status_bar_alpha) {
            BarStatusAlphaActivity.start(this);

        } else if (i == R.id.btn_status_bar_image_view) {
            BarStatusImageViewActivity.start(this);

        } else if (i == R.id.btn_status_bar_fragment) {
            BarStatusFragmentActivity.start(this);

        } else if (i == R.id.btn_status_bar_swipe_back) {
            BarStatusSwipeBackActivity.start(this);

        } else if (i == R.id.btn_status_bar_drawer) {
            BarStatusDrawerActivity.start(this);

        } else if (i == R.id.btn_notification_bar) {
            BarNotificationActivity.start(this);

        } else if (i == R.id.btn_nav_bar) {
            BarNavActivity.start(this);

        }
    }
}
