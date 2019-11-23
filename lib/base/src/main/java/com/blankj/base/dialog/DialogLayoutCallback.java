package com.blankj.base.dialog;

import android.view.View;
import android.view.Window;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/12
 *     desc  :
 * </pre>
 */
public interface DialogLayoutCallback {
    int bindTheme();

    int bindLayout();

    void initView(BaseDialogFragment dialog, View contentView);

    void setWindowStyle(Window window);

    void onCancel(BaseDialogFragment dialog);

    void onDismiss(BaseDialogFragment dialog);
}
