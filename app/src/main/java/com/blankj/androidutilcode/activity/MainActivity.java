package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseDrawerActivity;

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
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void activityClick(View view) {
        startActivity(new Intent(this, ActivityActivity.class));
    }

    public void appClick(View view) {
        AppActivity.start(this);
    }

    public void barClick(View view) {
        BarActivity.start(this);
    }

    public void cleanClick(View view) {
        CleanActivity.start(this);
    }

    public void crashClick(View view) {
        throw new NullPointerException("crash test");
    }

    public void deviceClick(View view) {
        DeviceActivity.start(this);
    }

    public void fragmentClick(View view) {
        startActivity(new Intent(this, FragmentActivity.class));
    }

//    public void flashlightClick(View view) {
//        startActivity(new Intent(this, FlashlightActivity.class));
//    }

    public void imageClick(View view) {
        ImageActivity.start(this);
    }

    public void keyboardClick(View view) {
        KeyboardActivity.start(this);
    }

    public void logClick(View view) {
        LogActivity.start(this);
    }

    public void networkClick(View view) {
        NetworkActivity.start(this);
    }

//    public void permissionClick(View view) {
//        startActivity(new Intent(this, PermissionActivity.class));
//    }

    public void phoneClick(View view) {
        PhoneActivity.start(this);
    }

    public void pinyinClick(View view) {
        PinyinActivity.start(this);
    }

    public void processClick(View view) {
        ProcessActivity.start(this);
    }

    public void sdcardClick(View view) {
        SDCardActivity.start(this);
    }

    public void snackbarClick(View view) {
        SnackbarActivity.start(this);
    }

    public void spannableClick(View view) {
        SpanActivity.start(this);
    }

    public void toastClick(View view) {
        ToastActivity.start(this);
    }
}
