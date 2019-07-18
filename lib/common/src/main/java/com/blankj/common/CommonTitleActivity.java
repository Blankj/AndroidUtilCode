package com.blankj.common;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/11/16
 *     desc  : base about title activity
 * </pre>
 */
public abstract class CommonTitleActivity extends CommonBackActivity {

    public abstract CharSequence bindTitle();

    protected boolean           isSupportScroll = true;
    protected CoordinatorLayout baseTitleRootLayout;
    protected Toolbar           baseTitleToolbar;
    protected FrameLayout       baseTitleContentView;
    protected ViewStub          mViewStub;

    @Override
    public boolean isSwipeBack() {
        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(@LayoutRes int layoutId) {
        super.setRootLayout(R.layout.common_activity_title);
        baseTitleRootLayout = findViewById(R.id.baseTitleRootLayout);
        baseTitleToolbar = findViewById(R.id.baseTitleToolbar);
        if (layoutId > 0) {
            if (isSupportScroll) {
                mViewStub = findViewById(R.id.baseTitleStubScroll);
            } else {
                mViewStub = findViewById(R.id.baseTitleStubNoScroll);
            }
            mViewStub.setVisibility(View.VISIBLE);
            baseTitleContentView = findViewById(R.id.commonTitleContentView);
            LayoutInflater.from(this).inflate(layoutId, baseTitleContentView);
        }
        setTitleBar();
        BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.colorPrimary));
        BarUtils.addMarginTopEqualStatusBarHeight(baseTitleRootLayout);
    }

    private void setTitleBar() {
        setSupportActionBar(baseTitleToolbar);
        ActionBar titleBar = getSupportActionBar();
        if (titleBar != null) {
            titleBar.setDisplayHomeAsUpEnabled(true);
            titleBar.setTitle(bindTitle());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
