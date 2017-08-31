package com.blankj.androidutilcode.core.toast;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/31
 *     desc  :
 * </pre>
 */
public class MyToast {

    public static void showMyToast(@NonNull final String message){
        View toastView = ToastUtils.showCustomLongSafe(R.layout.toast_custom);
        TextView tvMessage = (TextView) toastView.findViewById(R.id.tv_toast_message);
        tvMessage.setText(message);
    }

}
