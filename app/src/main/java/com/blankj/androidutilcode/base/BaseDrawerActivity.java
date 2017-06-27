package com.blankj.androidutilcode.base;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.androidutilcode.R;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/27
 *     desc  : DrawerActivity基类
 * </pre>
 */
public abstract class BaseDrawerActivity extends BaseActivity {

    NavigationView.OnNavigationItemSelectedListener mListener = new NavigationView.OnNavigationItemSelectedListener() {
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
    };

    @Override
    public void setBaseView() {
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_drawer, null);
        setContentView(contentView);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_view);
        frameLayout.addView(LayoutInflater.from(this).inflate(bindLayout(), frameLayout, false));
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(mListener);
    }
}
