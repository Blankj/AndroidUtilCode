package com.blankj.androidutilcode.base;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.androidutilcode.R;
import com.r0adkll.slidr.Slidr;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/27
 *     desc  : DrawerActivity基类
 * </pre>
 */
public abstract class BaseBackActivity extends BaseActivity {

    protected Toolbar mToolbar;

    @Override
    protected void setBaseView() {
        Slidr.attach(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_back, null);
        setContentView(contentView);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_view);
        frameLayout.addView(LayoutInflater.from(this).inflate(bindLayout(), frameLayout, false));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
