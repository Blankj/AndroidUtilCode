package com.blankj.utilcode.pkg.feature.snackbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/17
 *     desc  : demo about SnackbarUtils
 * </pre>
 */
public class SnackbarActivity extends BaseBackActivity {

    View snackBarRootView;

    public static void start(Context context) {
        Intent starter = new Intent(context, SnackbarActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_snackbar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_snackbar);

        snackBarRootView = findViewById(android.R.id.content);
        findViewById(R.id.btn_short_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_short_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_add_view).setOnClickListener(this);
        findViewById(R.id.btn_add_view_with_action).setOnClickListener(this);
        findViewById(R.id.btn_show_success).setOnClickListener(this);
        findViewById(R.id.btn_show_warning).setOnClickListener(this);
        findViewById(R.id.btn_show_error).setOnClickListener(this);
        findViewById(R.id.btn_dismiss_snackbar).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_short_snackbar) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_short))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .show();

        } else if (i == R.id.btn_short_snackbar_with_action) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_short))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShort(getString(R.string.snackbar_click));
                        }
                    })
                    .show();

        } else if (i == R.id.btn_long_snackbar) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_long))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .show();

        } else if (i == R.id.btn_long_snackbar_with_action) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_long))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShort(getString(R.string.snackbar_click));
                        }
                    })
                    .show();

        } else if (i == R.id.btn_indefinite_snackbar) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_indefinite))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .show();

        } else if (i == R.id.btn_indefinite_snackbar_with_action) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_indefinite))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShort(getString(R.string.snackbar_click));
                        }
                    })
                    .show();

        } else if (i == R.id.btn_add_view) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            SnackbarUtils.with(snackBarRootView)
                    .setBgColor(Color.TRANSPARENT)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .show();
            SnackbarUtils.addView(R.layout.snackbar_custom, params);

        } else if (i == R.id.btn_add_view_with_action) {
            ViewGroup.LayoutParams params;
            SnackbarUtils.with(snackBarRootView)
                    .setBgColor(Color.TRANSPARENT)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .show();
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            SnackbarUtils.addView(R.layout.snackbar_custom, params);
            View snackbarView = SnackbarUtils.getView();
            if (snackbarView != null) {
                TextView tvSnackbarCustom = snackbarView.findViewById(R.id.tv_snackbar_custom);
                tvSnackbarCustom.setText("点我可消失");
                snackbarView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackbarUtils.dismiss();
                    }
                });
            }

        } else if (i == R.id.btn_show_success) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_success))
                    .showSuccess();

        } else if (i == R.id.btn_show_warning) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_warning))
                    .showWarning();

        } else if (i == R.id.btn_show_error) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_error))
                    .showError();

        } else if (i == R.id.btn_dismiss_snackbar) {
            SnackbarUtils.dismiss();

        }
    }

    private SpannableStringBuilder getMsg(@StringRes int resId) {
        return new SpanUtils()
                .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                .appendSpace(32)
                .append(getString(resId)).setFontSize(24, true)
                .create();
    }
}
