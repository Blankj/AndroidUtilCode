package com.blankj.common.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.common.R;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/01
 *     desc  :
 * </pre>
 */
public class CommonActivityDrawerView {

    public AppCompatActivity mBaseActivity;
    public DrawerLayout      mBaseDrawerRootLayout;
    public FrameLayout       mBaseDrawerContainerView;

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

    public CommonActivityDrawerView(@NonNull AppCompatActivity activity) {
        mBaseActivity = activity;
    }

    public int bindLayout() {
        return R.layout.common_activity_drawer;
    }

    public View getContentView() {
        mBaseDrawerRootLayout = mBaseActivity.findViewById(R.id.baseDrawerRootLayout);
        mBaseDrawerContainerView = mBaseActivity.findViewById(R.id.baseDrawerContainerView);
        NavigationView nav = mBaseActivity.findViewById(R.id.baseDrawerNavView);
        nav.setNavigationItemSelectedListener(mListener);
        return mBaseDrawerContainerView;
    }

    private boolean goWeb(@StringRes int id) {
        return ActivityUtils.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtils.getString(id))));
    }
}
