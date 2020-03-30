package com.blankj.base.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/11
 *     desc  :
 * </pre>
 */
public class BaseDialogFragment extends DialogFragment {

    protected DialogLayoutCallback mDialogLayoutCallback;
    protected DialogCallback       mDialogCallback;

    protected FragmentActivity mActivity;
    protected View             mContentView;

    public BaseDialogFragment init(Context context, DialogLayoutCallback listener) {
        mActivity = getFragmentActivity(context);
        mDialogLayoutCallback = listener;
        return this;
    }

    public BaseDialogFragment init(Context context, DialogCallback dialogCallback) {
        mActivity = getFragmentActivity(context);
        mDialogCallback = dialogCallback;
        return this;
    }

    private FragmentActivity getFragmentActivity(Context context) {
        Activity activity = ActivityUtils.getActivityByContext(context);
        if (activity == null) return null;
        if (activity instanceof FragmentActivity) {
            return (FragmentActivity) activity;
        }
        LogUtils.w(context + "not instanceof FragmentActivity");
        return null;
    }

    @Override
    public int getTheme() {
        if (mDialogLayoutCallback != null) {
            int theme = mDialogLayoutCallback.bindTheme();
            if (theme != View.NO_ID) {
                return theme;
            }
        }
        return super.getTheme();
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog;
        if (mDialogCallback != null) {
            dialog = mDialogCallback.bindDialog(mActivity);
        } else {
            dialog = super.onCreateDialog(savedInstanceState);
        }
        Window window = dialog.getWindow();
        if (mDialogCallback != null) {
            mDialogCallback.setWindowStyle(window);
        } else if (mDialogLayoutCallback != null) {
            mDialogLayoutCallback.setWindowStyle(window);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mDialogLayoutCallback != null) {
            return inflater.inflate(mDialogLayoutCallback.bindLayout(), container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (mDialogLayoutCallback != null) {
            mDialogLayoutCallback.initView(this, view);
            return;
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mDialogLayoutCallback != null) {
            mDialogLayoutCallback.onCancel(this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogLayoutCallback != null) {
            mDialogLayoutCallback.onDismiss(this);
        }
    }

    public void show() {
        show(getClass().getSimpleName());
    }

    public void show(final String tag) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @SuppressLint("CommitTransaction")
            @Override
            public void run() {
                if (ActivityUtils.isActivityAlive(mActivity)) {
                    FragmentManager fm = mActivity.getSupportFragmentManager();
                    Fragment prev = fm.findFragmentByTag(tag);
                    if (prev != null) {
                        fm.beginTransaction().remove(prev);
                    }
                    BaseDialogFragment.super.show(fm, tag);
                }
            }
        });
    }

    @Override
    public void dismiss() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ActivityUtils.isActivityAlive(mActivity)) {
                    BaseDialogFragment.super.dismissAllowingStateLoss();
                }
            }
        });
    }
}


