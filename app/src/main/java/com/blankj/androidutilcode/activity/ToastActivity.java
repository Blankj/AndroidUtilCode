package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : Toast工具类Demo
 * </pre>
 */
public class ToastActivity extends BaseActivity {

    private boolean isDefaultLocation;
    private boolean isDefaultView;
    private TextView tvAboutToast;

    @Override
    public void initData(Bundle bundle) {
        isDefaultLocation = true;
        isDefaultView = true;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        findViewById(R.id.btn_toggle_location).setOnClickListener(this);
        findViewById(R.id.btn_toggle_view).setOnClickListener(this);
        findViewById(R.id.btn_show_short_toast_safe).setOnClickListener(this);
        findViewById(R.id.btn_show_long_toast_safe).setOnClickListener(this);
        findViewById(R.id.btn_show_short_toast).setOnClickListener(this);
        findViewById(R.id.btn_show_long_toast).setOnClickListener(this);
        findViewById(R.id.btn_cancel_toast).setOnClickListener(this);
        tvAboutToast = (TextView) findViewById(R.id.tv_about_toast);
        tvAboutToast.setText("is default location: " + isDefaultLocation
                + "\nis default view: " + isDefaultView
        );
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggle_location:
                if (isDefaultLocation) {
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.offset_64));
                }
                isDefaultLocation = !isDefaultLocation;
                break;
            case R.id.btn_toggle_view:
                if (isDefaultView) {
                    ToastUtils.setView(R.layout.toast_custom);
                } else {
                    ToastUtils.setView(null);
                }
                isDefaultView = !isDefaultView;
                break;
            case R.id.btn_show_short_toast_safe:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortSafe(R.string.toast_short_safe);
                    }
                }).start();
                break;
            case R.id.btn_show_long_toast_safe:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showLongSafe(R.string.toast_long_safe);
                    }
                }).start();
                break;
            case R.id.btn_show_short_toast:
                ToastUtils.showShort(R.string.toast_short);
                break;
            case R.id.btn_show_long_toast:
                ToastUtils.showShort(R.string.toast_long);
                break;
            case R.id.btn_cancel_toast:
                ToastUtils.cancel();
                break;
        }
        tvAboutToast.setText("is default location: " + isDefaultLocation
                + "\nis default view: " + isDefaultView
        );
    }

    @Override
    protected void onDestroy() {
        ToastUtils.setView(null);
        super.onDestroy();
    }
}
