package com.blankj.common.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.base.dialog.BaseDialogFragment;
import com.blankj.base.dialog.DialogLayoutCallback;
import com.blankj.common.R;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/29
 *     desc  :
 * </pre>
 */
public class CommonDialogLoading extends BaseDialogFragment {

    public CommonDialogLoading init(Context context, final Runnable onCancelListener) {
        super.init(context, new DialogLayoutCallback() {
            @Override
            public int bindTheme() {
                return R.style.CommonLoadingDialogStyle;
            }

            @Override
            public int bindLayout() {
                return R.layout.common_dialog_loading;
            }

            @Override
            public void initView(BaseDialogFragment dialog, View contentView) {
                if (onCancelListener == null) {
                    setCancelable(false);
                } else {
                    setCancelable(true);
                }
            }

            @Override
            public void setWindowStyle(final Window window) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                BarUtils.setStatusBarColor(window, Color.TRANSPARENT);
            }

            @Override
            public void onCancel(BaseDialogFragment dialog) {
                if (onCancelListener != null) {
                    onCancelListener.run();
                }
            }

            @Override
            public void onDismiss(BaseDialogFragment dialog) {

            }
        });
        return this;
    }
}
