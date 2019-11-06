package com.blankj.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/29
 *     desc  :
 * </pre>
 */
public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
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
