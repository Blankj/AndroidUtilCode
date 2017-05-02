package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/29
 *     desc  : Toast工具类Demo
 * </pre>
 */
public class ToastActivity extends Activity
        implements View.OnClickListener {

    private boolean isDefaultLocation;
    private boolean isDefaultView;
    private TextView tvAboutToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        isDefaultLocation = true;
        isDefaultView = true;

        tvAboutToast = (TextView) findViewById(R.id.tv_about_toast);

        findViewById(R.id.btn_toggle_location).setOnClickListener(this);
        findViewById(R.id.btn_toggle_view).setOnClickListener(this);
        findViewById(R.id.btn_show_short_toast_safe).setOnClickListener(this);
        findViewById(R.id.btn_show_long_toast_safe).setOnClickListener(this);
        findViewById(R.id.btn_show_short_toast).setOnClickListener(this);
        findViewById(R.id.btn_show_long_toast).setOnClickListener(this);
        findViewById(R.id.btn_cancel_toast).setOnClickListener(this);

        tvAboutToast.setText("is default location: " + isDefaultLocation
                + "\ncustom view: " + isDefaultView
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggle_location:
                if (isDefaultLocation) {
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, R.dimen.toast_y_offset);
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
                        ToastUtils.showShortSafe("show_short_toast_safe");
                    }
                }).start();
                break;
            case R.id.btn_show_long_toast_safe:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showLongSafe("show_long_toast_safe");
                    }
                }).start();
                break;
            case R.id.btn_show_short_toast:
                ToastUtils.showShort("show_short_toast");
                break;
            case R.id.btn_show_long_toast:
                ToastUtils.showShort("show_long_toast");
                break;
            case R.id.btn_cancel_toast:
                ToastUtils.cancel();
                break;
        }
        tvAboutToast.setText("is default location: " + isDefaultLocation
                + "\ncustom view: " + isDefaultView
        );
    }
}
