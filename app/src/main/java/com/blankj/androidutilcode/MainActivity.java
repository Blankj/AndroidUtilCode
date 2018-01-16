package com.blankj.androidutilcode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.androidutilcode.base.BaseDrawerActivity;
import com.blankj.androidutilcode.feature.core.CoreUtilActivity;
import com.blankj.androidutilcode.feature.sub.SubUtilActivity;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : MainActivity
 * </pre>
 */
public class MainActivity extends BaseDrawerActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        View fakeStatusBar = findViewById(R.id.fake_status_bar);
        CollapsingToolbarLayout ctl = findViewById(R.id.ctl);
        ctl.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                rootLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        rootLayout.addDrawerListener(toggle);
        toggle.syncState();

        BarUtils.setStatusBarAlpha4Drawer(this, rootLayout, fakeStatusBar, 0, false);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void coreUtilClick(View view) {
        CoreUtilActivity.start(this);
    }

    public void subUtilClick(View view) {
        SubUtilActivity.start(this);
    }
}
