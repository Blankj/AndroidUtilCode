package com.blankj.lib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.swipepanel.SwipePanel;
import com.blankj.utilcode.util.AntiShakeUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/24
 *     desc  : base about activity
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IBaseView {

    protected View     mContentView;
    protected Activity mActivity;

    public abstract boolean isSwipeBack();

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        initData(getIntent().getExtras());
        setRootLayout(bindLayout());
        findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.mediumGray));
        initView(savedInstanceState, mContentView);
        doBusiness();
        initSwipeBack();

        AppUtils.registerAppStatusChangedListener(this, new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                ToastUtils.showShort("foreground");
            }

            @Override
            public void onBackground() {
                ToastUtils.showShort("background");
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(@LayoutRes int layoutId) {
        if (layoutId <= 0) return;
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    @Override
    public void onClick(View view) {
        if (AntiShakeUtils.isValid(view)) {
            onWidgetClick(view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.unregisterAppStatusChangedListener(this);
    }

    private void initSwipeBack() {
        if (isSwipeBack()) {
            final SwipePanel swipeLayout = new SwipePanel(this);
            swipeLayout.setLeftDrawable(R.drawable.base_back);
            swipeLayout.setLeftEdgeSize(SizeUtils.dp2px(100));
            swipeLayout.wrapView(mContentView);
            swipeLayout.setOnFullSwipeListener(new SwipePanel.OnFullSwipeListener() {
                @Override
                public void onFullSwipe(int direction) {
                    finish();
                    swipeLayout.close(direction);
                }
            });
        }
    }
}

