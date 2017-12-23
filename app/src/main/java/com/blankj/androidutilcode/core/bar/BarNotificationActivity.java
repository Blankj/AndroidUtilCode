package com.blankj.androidutilcode.core.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Activity 工具类 Demo
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
        findViewById(R.id.btn_show_nav).setOnClickListener(this);
        findViewById(R.id.btn_hide_nav).setOnClickListener(this);

        BarUtils.setNavBarVisible(this, false);
        updateAboutNav(false);

        BarUtils.registerNavBarChangedListener(this, new BarUtils.OnNavBarChangedListener() {
            @Override
            public void onNavBarChanged(boolean isVisible) {
                updateAboutNav(isVisible);
            }
        });
    }

    private void updateAboutNav(boolean isNavBarVisible) {
        tvAboutNav.setText("navHeight: " + BarUtils.getNavBarHeight()
                + "\nisNavBarVisible: " + isNavBarVisible);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_nav:
                BarUtils.setNavBarVisible(this, true);
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary), 0);
                BarUtils.addMarginTopEqualStatusBarHeight(rootLayout);
                break;
            case R.id.btn_hide_nav:
                BarUtils.setNavBarVisible(this, false);
                break;
        }
    }
}
