package com.blankj.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blankj.base.dialog.BaseDialog;
import com.blankj.common.R;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/29
 *     desc  :
 * </pre>
 */
public class CommonLoadingDialog extends BaseDialog {

    public CommonLoadingDialog(@NonNull Context context, @Nullable OnCancelListener listener) {
        super(context, R.style.DialogTransparent);

        setContentView(R.layout.common_dialog_loading);
        setCanceledOnTouchOutside(false);
        if (listener == null) {
            setCancelable(false);
        } else {
            setCancelable(true);
            setOnCancelListener(listener);
        }
    }
}
