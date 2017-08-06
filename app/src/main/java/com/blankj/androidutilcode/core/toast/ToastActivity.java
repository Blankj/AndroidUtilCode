package com.blankj.androidutilcode.core.toast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : Toast工具类Demo
 * </pre>
 */
public class ToastActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ToastActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_toast));

        view.findViewById(R.id.btn_show_short_toast_safe).setOnClickListener(this);
        view.findViewById(R.id.btn_show_short_toast).setOnClickListener(this);
        view.findViewById(R.id.btn_show_long_toast_safe).setOnClickListener(this);
        view.findViewById(R.id.btn_show_long_toast).setOnClickListener(this);
        view.findViewById(R.id.btn_show_green_font).setOnClickListener(this);
        view.findViewById(R.id.btn_show_custom_bg).setOnClickListener(this);
        view.findViewById(R.id.btn_show_span).setOnClickListener(this);
        view.findViewById(R.id.btn_show_custom_view).setOnClickListener(this);
        view.findViewById(R.id.btn_show_middle).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_toast).setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        resetToast();
        switch (view.getId()) {
            case R.id.btn_show_short_toast_safe:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortSafe(R.string.toast_short_safe);
                    }
                }).start();
                break;
            case R.id.btn_show_short_toast:
                ToastUtils.showShort(R.string.toast_short);
                break;
            case R.id.btn_show_long_toast_safe:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showLongSafe(R.string.toast_long_safe);
                    }
                }).start();
                break;
            case R.id.btn_show_long_toast:
                ToastUtils.showLong(R.string.toast_long);
                break;
            case R.id.btn_show_green_font:
                ToastUtils.setMessageColor(Color.GREEN);
                ToastUtils.showLong(R.string.toast_green_font);
                break;
            case R.id.btn_show_custom_bg:
                ToastUtils.setBgResource(R.drawable.shape_round_rect);
                ToastUtils.showLong(R.string.toast_custom_bg);
                break;
            case R.id.btn_show_span:
                ToastUtils.showLong(new SpanUtils()
                        .appendLine(getString(R.string.toast_span))
                        .setFontSize(24, true)
                        .setIconMargin(R.mipmap.ic_launcher, 32, SpanUtils.ALIGN_CENTER)
                        .append(" ").setFontSize(0)
                        .create());
                break;
            case R.id.btn_show_custom_view:
                ToastUtils.showCustomLong(R.layout.toast_custom);
                break;
            case R.id.btn_show_middle:
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                ToastUtils.showLong(R.string.toast_middle);
                break;
            case R.id.btn_cancel_toast:
                ToastUtils.cancel();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        resetToast();
        super.onDestroy();
    }

    private void resetToast() {
        ToastUtils.setMessageColor(0x12000000);
        ToastUtils.setBgResource(-1);
        ToastUtils.setView(null);
        ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.offset_64));
    }
}
