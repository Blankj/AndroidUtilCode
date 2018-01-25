package com.blankj.androidutilcode.feature.core.keyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.androidutilcode.helper.DialogHelper;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : Keyboard 工具类 Demo
 * </pre>
 */
public class KeyboardActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, KeyboardActivity.class);
        context.startActivity(starter);
    }

    TextView tvAboutKeyboard;
    EditText etInput;
    private AlertDialog dialog;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_keyboard;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_keyboard));

        etInput = findViewById(R.id.et_input);
        findViewById(R.id.btn_hide_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_show_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_toggle_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_keyboard_in_fragment).setOnClickListener(this);
        tvAboutKeyboard = findViewById(R.id.tv_about_keyboard);

        KeyboardUtils.registerSoftInputChangedListener(this,
                new KeyboardUtils.OnSoftInputChangedListener() {
                    @Override
                    public void onSoftInputChanged(int height) {
                        tvAboutKeyboard.setText(new SpanUtils()
                                .appendLine("isSoftInputVisible: " + KeyboardUtils.isSoftInputVisible(KeyboardActivity.this))
                                .append("height: " + height)
                                .create()
                        );
                    }
                });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hide_soft_input:
                KeyboardUtils.hideSoftInput(this);
                break;
            case R.id.btn_show_soft_input:
                KeyboardUtils.showSoftInput(this);
                break;
            case R.id.btn_toggle_soft_input:
                KeyboardUtils.toggleSoftInput();
                break;
            case R.id.btn_keyboard_in_fragment:
                DialogHelper.showKeyboardDialog();
                KeyboardUtils.showSoftInput(this);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm == null) return super.dispatchTouchEvent(ev);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
