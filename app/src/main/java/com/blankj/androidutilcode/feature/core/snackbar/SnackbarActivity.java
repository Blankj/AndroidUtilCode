package com.blankj.androidutilcode.feature.core.snackbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/17
 *     desc  : Snackbar 工具类 Demo
 * </pre>
 */
public class SnackbarActivity extends BaseBackActivity {

    View snackBarRootView;

    public static void start(Context context) {
        Intent starter = new Intent(context, SnackbarActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_snackbar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_snackbar));

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
        switch (view.getId()) {
            case R.id.btn_short_snackbar:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_short))
                        .setMessageColor(Color.WHITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .show();
                break;

            case R.id.btn_short_snackbar_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_short))
                        .setMessageColor(Color.WHITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        })
                        .show();
                break;

            case R.id.btn_long_snackbar:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_long))
                        .setMessageColor(Color.WHITE)
                        .setDuration(SnackbarUtils.LENGTH_LONG)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .show();
                break;

            case R.id.btn_long_snackbar_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_long))
                        .setMessageColor(Color.WHITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .setDuration(SnackbarUtils.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        })
                        .show();
                break;

            case R.id.btn_indefinite_snackbar:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_indefinite))
                        .setMessageColor(Color.WHITE)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .show();
                break;

            case R.id.btn_indefinite_snackbar_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_indefinite))
                        .setMessageColor(Color.WHITE)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        })
                        .show();
                break;

            case R.id.btn_add_view:
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show();
                SnackbarUtils.addView(R.layout.snackbar_custom, params);
                break;

            case R.id.btn_add_view_with_action:
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
                break;

            case R.id.btn_show_success:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_success))
                        .showSuccess();
                break;

            case R.id.btn_show_warning:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_warning))
                        .showWarning();
                break;

            case R.id.btn_show_error:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage(getMsg(R.string.snackbar_error))
                        .showError();
                break;

            case R.id.btn_dismiss_snackbar:
                SnackbarUtils.dismiss();
                break;
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
