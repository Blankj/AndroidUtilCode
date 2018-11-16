package com.blankj.base;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/27
 *     desc  : base about drawer activity
 * </pre>
 */
public abstract class BaseDrawerActivity extends BaseActivity {

    protected DrawerLayout rootLayout;
    protected FrameLayout  flActivityContainer;

    NavigationView.OnNavigationItemSelectedListener mListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
            if (i == R.id.action_git_hub) {
                ActivityUtils.startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtils.getString(R.string.github)))
                );
            } else if (i == R.id.action_blog) {
                ActivityUtils.startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtils.getString(R.string.blog)))
                );
            }
            return false;
        }
    };

    @Override
    protected void setBaseView(@LayoutRes int layoutId) {
        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_drawer, null);
        setContentView(mContentView);
        rootLayout = findViewById(R.id.root_layout);
        flActivityContainer = findViewById(R.id.activity_container);
        flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false));
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mListener);
    }
}
