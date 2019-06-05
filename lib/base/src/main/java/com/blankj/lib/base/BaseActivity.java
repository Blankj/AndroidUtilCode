package com.blankj.lib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AntiShakeUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        initData(getIntent().getExtras());
        setRootLayout(bindLayout());
        initView(savedInstanceState, mContentView);
        doBusiness();
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
}

