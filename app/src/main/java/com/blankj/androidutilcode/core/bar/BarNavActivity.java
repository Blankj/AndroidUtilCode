package com.blankj.androidutilcode.core.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Activity工具类Demo
 * </pre>
 */
public class BarNavActivity extends BaseBackActivity {

    private TextView tvAboutNav;

    public static void start(Context context) {
        Intent starter = new Intent(context, BarNavActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar_nav;
    }


    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_bar));

        tvAboutNav = findViewById(R.id.tv_about_nav);
        tvAboutNav.setText("navHeight: " + BarUtils.getNavBarHeight());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        BarUtils.hideNavBar(this);
    }
}