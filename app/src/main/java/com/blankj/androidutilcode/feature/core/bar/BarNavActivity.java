package com.blankj.androidutilcode.feature.core.bar;

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
import com.blankj.utilcode.util.SpanUtils;

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
        findViewById(R.id.btn_immersive_nav).setOnClickListener(this);
        updateAboutNav();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_nav:
                BarUtils.setNavBarVisibility(this, true);
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary), 0);
                BarUtils.addMarginTopEqualStatusBarHeight(rootLayout);
                break;
            case R.id.btn_hide_nav:
                BarUtils.setNavBarVisibility(this, false);
                break;
            case R.id.btn_immersive_nav:
                BarUtils.setNavBarImmersive(this);
                break;
        }
        updateAboutNav();
    }

    private void updateAboutNav() {
        tvAboutNav.setText(new SpanUtils()
                .appendLine("navHeight: " + BarUtils.getNavBarHeight())
                .append("isNavBarVisible: " + BarUtils.isNavBarVisible(this))
                .create()
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        BarUtils.setNavBarVisibility(this, false);
    }
}
