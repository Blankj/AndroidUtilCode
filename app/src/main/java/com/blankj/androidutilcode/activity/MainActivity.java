package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : MainActivity
 * </pre>
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
        startActivity(new Intent(this, AppActivity.class));
    }

    public void barClick(View view) {
        startActivity(new Intent(this, BarActivity.class));
    }

    public void cleanClick(View view) {
        startActivity(new Intent(this, CleanActivity.class));
    }

    public void crashClick(View view) {
        throw new NullPointerException("crash test");
    }

    public void deviceClick(View view) {
        startActivity(new Intent(this, DeviceActivity.class));
    }

    public void fragmentClick(View view) {
        startActivity(new Intent(this, FragmentActivity.class));
    }

//    public void flashlightClick(View view) {
//        startActivity(new Intent(this, FlashlightActivity.class));
//    }

    public void imageClick(View view) {
        startActivity(new Intent(this, ImageActivity.class));
    }

    public void keyboardClick(View view) {
        startActivity(new Intent(this, KeyboardActivity.class));
    }

    public void locationClick(View view) {
        startActivity(new Intent(this, LocationActivity.class));
    }

    public void logClick(View view) {
        startActivity(new Intent(this, LogActivity.class));
    }

    public void networkClick(View view) {
        startActivity(new Intent(this, NetworkActivity.class));
    }

//    public void permissionClick(View view) {
//        startActivity(new Intent(this, PermissionActivity.class));
//    }

    public void phoneClick(View view) {
        startActivity(new Intent(this, PhoneActivity.class));
    }

    public void pinyinClick(View view) {
        startActivity(new Intent(this, PinyinActivity.class));
    }

    public void processClick(View view) {
        startActivity(new Intent(this, ProcessActivity.class));
    }

    public void sdcardClick(View view) {
        startActivity(new Intent(this, SDCardActivity.class));
    }

    public void snackbarClick(View view) {
        startActivity(new Intent(this, SnackbarActivity.class));
    }

    public void spannableClick(View view) {
        startActivity(new Intent(this, SpanActivity.class));
    }

    public void toastClick(View view) {
        startActivity(new Intent(this, ToastActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_git_hub:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Blankj/AndroidUtilCode")));
                break;
            case R.id.action_blog:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/u/46702d5c6978")));
                break;
        }
        return false;
    }
}
