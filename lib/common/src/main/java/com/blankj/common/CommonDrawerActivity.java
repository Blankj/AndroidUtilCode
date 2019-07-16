package com.blankj.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
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
public abstract class CommonDrawerActivity extends CommonBackActivity {

    protected DrawerLayout mBaseDrawerRootLayout;
    protected FrameLayout  mBaseDrawerContainerView;

    private NavigationView.OnNavigationItemSelectedListener mListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.baseDrawerActionGitHub) {
                return goWeb(R.string.github);
            } else if (id == R.id.baseDrawerActionBlog) {
                return goWeb(R.string.blog);
            }
            return false;
        }
    };

    @Override
    public boolean isSwipeBack() {
        return false;
    }

    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(@LayoutRes int layoutId) {
        super.setRootLayout(R.layout.common_activity_drawer);
        mBaseDrawerRootLayout = findViewById(R.id.baseDrawerRootLayout);
        mBaseDrawerContainerView = findViewById(R.id.baseDrawerContainerView);
        if (layoutId > 0) {
            LayoutInflater.from(this).inflate(layoutId, mBaseDrawerContainerView);
        }
        NavigationView nav = findViewById(R.id.baseDrawerNavView);
        nav.setNavigationItemSelectedListener(mListener);
    }

    private boolean goWeb(@StringRes int id) {
        return ActivityUtils.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtils.getString(id))));
    }
}
