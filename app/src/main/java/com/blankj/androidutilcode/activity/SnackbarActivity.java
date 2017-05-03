package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.SpannableStringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/17
 *     desc  : Snackbar工具类Demo
 * </pre>
 */
public class SnackbarActivity extends Activity
        implements View.OnClickListener {

    private View snackBarRootView;
    private final int TYPE_SHORT = 0x00;
    private final int TYPE_SHORT_WITH_ACTION = 0x01;
    private final int TYPE_LONG = 0x10;
    private final int TYPE_LONG_WITH_ACTION = 0x11;
    private final int TYPE_INDEFINITE = 0x20;
    private final int TYPE_INDEFINITE_WITH_ACTION = 0x21;
    private final int TYPE_CUSTOM = 0x40;
    private final int TYPE_CUSTOM_WITH_ACTION = 0x41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        snackBarRootView = findViewById(android.R.id.content);
        findViewById(R.id.btn_short_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_short_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_add_view).setOnClickListener(this);
        findViewById(R.id.btn_add_view_with_action).setOnClickListener(this);
        findViewById(R.id.btn_cancel_snackbar).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_short_snackbar:
                showSnackbar(TYPE_SHORT);
                break;
            case R.id.btn_short_snackbar_with_action:
                showSnackbar(TYPE_SHORT_WITH_ACTION);
                break;
            case R.id.btn_long_snackbar:
                showSnackbar(TYPE_LONG);
                break;
            case R.id.btn_long_snackbar_with_action:
                showSnackbar(TYPE_LONG_WITH_ACTION);
                break;
            case R.id.btn_indefinite_snackbar:
                showSnackbar(TYPE_INDEFINITE);
                break;
            case R.id.btn_indefinite_snackbar_with_action:
                showSnackbar(TYPE_INDEFINITE_WITH_ACTION);
                break;
            case R.id.btn_add_view:
                showSnackbar(TYPE_CUSTOM);
                break;
            case R.id.btn_add_view_with_action:
                showSnackbar(TYPE_CUSTOM_WITH_ACTION);
                break;
            case R.id.btn_cancel_snackbar:
                SnackbarUtils.dismiss();
                break;
        }
    }

    private void showSnackbar(int type) {
        SpannableStringUtils.Builder builder = new SpannableStringUtils.Builder()
                .append("").setResourceId(R.mipmap.ic_launcher)
                .append("").setMargin(24);
        switch (type) {
            case TYPE_SHORT:
                SnackbarUtils.showShort(snackBarRootView,
                        builder.append(getString(R.string.snackbar_short)).create(),
                        Color.BLUE,
                        Color.LTGRAY);
                break;
            case TYPE_SHORT_WITH_ACTION:
                SnackbarUtils.showShort(snackBarRootView, builder.append(getString(R.string.snackbar_short)).create(), Color.BLUE, Color.LTGRAY,
                        "Short", Color.DKGRAY, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        });
                break;
            case TYPE_LONG:
                SnackbarUtils.showShort(snackBarRootView,
                        builder.append(getString(R.string.snackbar_long)).create(),
                        Color.BLUE,
                        Color.LTGRAY);
                break;
            case TYPE_LONG_WITH_ACTION:
                SnackbarUtils.showShort(snackBarRootView, builder.append(getString(R.string.snackbar_long)).create(), Color.BLUE, Color.LTGRAY,
                        "Short", Color.DKGRAY, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        });
                break;
            case TYPE_INDEFINITE:
                SnackbarUtils.showShort(snackBarRootView,
                        builder.append(getString(R.string.snackbar_indefinite)).create(),
                        Color.BLUE,
                        Color.LTGRAY);
                break;
            case TYPE_INDEFINITE_WITH_ACTION:
                SnackbarUtils.showShort(snackBarRootView, builder.append(getString(R.string.snackbar_indefinite)).create(), Color.BLUE, Color.LTGRAY,
                        "Short", Color.DKGRAY, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        });
                break;
            case TYPE_CUSTOM:
                SnackbarUtils.showShort(snackBarRootView,
                        "",
                        Color.BLUE,
                        Color.LTGRAY);
                SnackbarUtils.addView(R.layout.snackbar_add, 0);
                break;
            case TYPE_CUSTOM_WITH_ACTION:
                SnackbarUtils.showShort(snackBarRootView,
                        "",
                        Color.BLUE,
                        Color.LTGRAY,
                        "Short",
                        Color.DKGRAY, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(getString(R.string.snackbar_click));
                            }
                        });
                SnackbarUtils.addView(R.layout.snackbar_add, 0);
                break;

        }
    }
}
