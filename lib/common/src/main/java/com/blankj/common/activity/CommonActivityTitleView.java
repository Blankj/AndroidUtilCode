package com.blankj.common.activity;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.blankj.common.R;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/01
 *     desc  :
 * </pre>
 */
public class CommonActivityTitleView {

    public AppCompatActivity mBaseActivity;
    public CharSequence      mTitle;
    public boolean           mIsSupportScroll;
    public CoordinatorLayout baseTitleRootLayout;
    public Toolbar           baseTitleToolbar;
    public FrameLayout       baseTitleContentView;
    public ViewStub          mViewStub;

    public CommonActivityTitleView(@NonNull AppCompatActivity activity, @StringRes int resId) {
        this(activity, activity.getString(resId), true);
    }

    public CommonActivityTitleView(@NonNull AppCompatActivity activity, @NonNull CharSequence title) {
        this(activity, title, true);
    }

    public CommonActivityTitleView(@NonNull AppCompatActivity activity, @StringRes int resId, boolean isSupportScroll) {
        this(activity, activity.getString(resId), isSupportScroll);
    }

    public CommonActivityTitleView(@NonNull AppCompatActivity activity, @NonNull CharSequence title, boolean isSupportScroll) {
        mBaseActivity = activity;
        mTitle = title;
        mIsSupportScroll = isSupportScroll;
    }

    public void setIsSupportScroll(boolean isSupportScroll) {
        mIsSupportScroll = isSupportScroll;
    }

    public int bindLayout() {
        return R.layout.common_activity_title;
    }

    public View getContentView() {
        baseTitleRootLayout = mBaseActivity.findViewById(R.id.baseTitleRootLayout);
        baseTitleToolbar = mBaseActivity.findViewById(R.id.baseTitleToolbar);
        if (mIsSupportScroll) {
            mViewStub = mBaseActivity.findViewById(R.id.baseTitleStubScroll);
        } else {
            mViewStub = mBaseActivity.findViewById(R.id.baseTitleStubNoScroll);
        }
        mViewStub.setVisibility(View.VISIBLE);
        baseTitleContentView = mBaseActivity.findViewById(R.id.commonTitleContentView);
        setTitleBar();
        BarUtils.setStatusBarColor(mBaseActivity, ColorUtils.getColor(R.color.colorPrimary));
        BarUtils.addMarginTopEqualStatusBarHeight(baseTitleRootLayout);
        return baseTitleContentView;
    }

    private void setTitleBar() {
        mBaseActivity.setSupportActionBar(baseTitleToolbar);
        ActionBar titleBar = mBaseActivity.getSupportActionBar();
        if (titleBar != null) {
            titleBar.setDisplayHomeAsUpEnabled(true);
            titleBar.setTitle(mTitle);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mBaseActivity.finish();
            return true;
        }
        return mBaseActivity.onOptionsItemSelected(item);
    }
}
