package com.blankj.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/12
 *     desc  :
 * </pre>
 */
public interface DialogCallback {
    @NonNull
    Dialog bindDialog(Activity activity);

    void setWindowStyle(Window window);
}
