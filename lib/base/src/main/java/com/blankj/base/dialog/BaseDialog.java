package com.blankj.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/11
 *     desc  :
 * </pre>
 */
public abstract class BaseDialog extends Dialog {

    protected Activity mActivity;

    public abstract int bindLayout();

    public abstract void initView(BaseDialog dialog, View contentView);

    public abstract void setWindowStyle(Window window);

    public BaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        Activity activity = ActivityUtils.getActivityByContext(context);
        if (activity == null) {
            throw new IllegalArgumentException("context is not instance of Activity.");
        }
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(mActivity, bindLayout(), null);
        setContentView(contentView);
        initView(this, contentView);
        setWindowStyle(getWindow());
    }

    @Override
    public void show() {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ActivityUtils.isActivityAlive(getContext())) {
                    BaseDialog.super.show();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ActivityUtils.isActivityAlive(getContext())) {
                    BaseDialog.super.dismiss();
                }
            }
        });
    }
}