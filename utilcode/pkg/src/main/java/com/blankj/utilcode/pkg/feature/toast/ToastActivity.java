package com.blankj.utilcode.pkg.feature.toast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : demo about ToastUtils
 * </pre>
 */
public class ToastActivity extends BaseBackActivity {

    View toastView;

    public static void start(Context context) {
        Intent starter = new Intent(context, ToastActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_toast);

        findViewById(R.id.btn_show_short_toast).setOnClickListener(this);
        findViewById(R.id.btn_show_long_toast).setOnClickListener(this);
        findViewById(R.id.btn_show_green_font).setOnClickListener(this);
        findViewById(R.id.btn_show_bg_color).setOnClickListener(this);
        findViewById(R.id.btn_show_bg_resource).setOnClickListener(this);
        findViewById(R.id.btn_show_span).setOnClickListener(this);
        findViewById(R.id.btn_show_custom_view).setOnClickListener(this);
        findViewById(R.id.btn_show_middle).setOnClickListener(this);
        findViewById(R.id.btn_cancel_toast).setOnClickListener(this);
        toastView = findViewById(R.id.btn_cancel_toast);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        resetToast();
        int i = view.getId();
        if (i == R.id.btn_show_short_toast) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(R.string.toast_short);
                }
            }).start();

        } else if (i == R.id.btn_show_long_toast) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showLong(R.string.toast_long);
                }
            }).start();

        } else if (i == R.id.btn_show_green_font) {
            ToastUtils.setMsgColor(Color.GREEN);
            ToastUtils.showLong(R.string.toast_green_font);

        } else if (i == R.id.btn_show_bg_color) {
            ToastUtils.setBgColor(ContextCompat.getColor(this, R.color.colorAccent));
            ToastUtils.showLong(R.string.toast_bg_color);

        } else if (i == R.id.btn_show_bg_resource) {
            ToastUtils.setBgResource(R.drawable.toast_shape_round_rect);
            ToastUtils.showLong(R.string.toast_custom_bg);

        } else if (i == R.id.btn_show_span) {
            ToastUtils.showLong(new SpanUtils()
                    .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                    .appendSpace(32)
                    .append(getString(R.string.toast_span)).setFontSize(24, true)
                    .create()
            );

        } else if (i == R.id.btn_show_custom_view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CustomToast.showLong(R.string.toast_custom_view);
                }
            }).start();

        } else if (i == R.id.btn_show_middle) {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showLong(R.string.toast_middle);

        } else if (i == R.id.btn_cancel_toast) {
            ToastUtils.cancel();

        }
    }

    @Override
    protected void onDestroy() {
        resetToast();
        super.onDestroy();
    }

    private void resetToast() {
        ToastUtils.setMsgColor(0xFEFFFFFF);
        ToastUtils.setBgColor(0xFEFFFFFF);
        ToastUtils.setBgResource(-1);
        ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.offset_64));
    }
}
