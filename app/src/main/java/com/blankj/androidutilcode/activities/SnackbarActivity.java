package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.blankj.androidutilcode.App;
import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.SnackbarUtils;
import com.blankj.utilcode.utils.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/17
 *     desc  : Snackbar工具类测试
 * </pre>
 */
public class SnackbarActivity extends Activity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        findViewById(R.id.btn_short_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_short_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_add_view).setOnClickListener(this);
        findViewById(R.id.btn_add_view_with_action).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_short_snackbar:
                SnackbarUtils.showShortSnackbar(getWindow().getDecorView(), "short snackbar", Color.WHITE, Color.BLUE);
                break;
            case R.id.btn_short_snackbar_with_action:
                SnackbarUtils.showLongSnackbar(getWindow().getDecorView(), "short snackbar", Color.WHITE, Color.BLUE,
                        "Short", Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(App.getInstance(), "Click Short");
                            }
                        });
                break;
            case R.id.btn_long_snackbar:
                SnackbarUtils.showLongSnackbar(getWindow().getDecorView(), "long snackbar", Color.WHITE, Color.GREEN);
                break;
            case R.id.btn_long_snackbar_with_action:
                SnackbarUtils.showLongSnackbar(getWindow().getDecorView(), "long snackbar", Color.WHITE, Color.GREEN,
                        "Long", Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showLongToast(App.getInstance(), "Click Long");
                            }
                        });
                break;
            case R.id.btn_indefinite_snackbar:
                SnackbarUtils.showIndefiniteSnackbar(getWindow().getDecorView(), "Indefinite snackbar", 5000, Color.WHITE, Color.RED);
                break;
            case R.id.btn_indefinite_snackbar_with_action:
                SnackbarUtils.showIndefiniteSnackbar(getWindow().getDecorView(), "Indefinite snackbar", 5000, Color.WHITE, Color.RED,
                        "Indefinite", Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(App.getInstance(), "Click Indefinite");
                            }
                        });
                break;
            case R.id.btn_add_view:
                SnackbarUtils.showShortSnackbar(getWindow().getDecorView(), "short snackbar", Color.WHITE, Color.BLUE);
                SnackbarUtils.addView(R.layout.snackbar_add, 0);
                break;
            case R.id.btn_add_view_with_action:
                SnackbarUtils.showLongSnackbar(getWindow().getDecorView(), "short snackbar", Color.WHITE, Color.BLUE,
                        "Short", Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(App.getInstance(), "Click Short");
                            }
                        });
                SnackbarUtils.addView(R.layout.snackbar_add, 0);
                break;
        }
    }
}
