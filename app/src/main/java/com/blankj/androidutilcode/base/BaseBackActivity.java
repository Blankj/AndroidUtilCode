package com.blankj.androidutilcode.base;

import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.utilcode.util.BarUtils;
import com.r0adkll.slidr.Slidr;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/27
 *     desc  : DrawerActivity 基类
 * </pre>
 */
public abstract class BaseBackActivity extends BaseActivity {

    protected CoordinatorLayout rootLayout;
    protected Toolbar           mToolbar;
    protected AppBarLayout      abl;
    protected FrameLayout       flActivityContainer;

    @Override
    protected void setBaseView(@LayoutRes int layoutId) {
        Slidr.attach(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_back, null);
        setContentView(contentView);
        rootLayout = findViewById(R.id.root_layout);
        abl = findViewById(R.id.abl);
        mToolbar = findViewById(R.id.toolbar);
        flActivityContainer = findViewById(R.id.activity_container);
        flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false));
        setSupportActionBar(mToolbar);
        getToolBar().setDisplayHomeAsUpEnabled(true);

        BarUtils.setStatusBarColor(this, ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary), 0);
        BarUtils.addMarginTopEqualStatusBarHeight(rootLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }
}
