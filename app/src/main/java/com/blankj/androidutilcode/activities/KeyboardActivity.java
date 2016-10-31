package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.NetworkUtils;

public class KeyboardActivity extends Activity
        implements View.OnClickListener {

    TextView tvAboutKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);


        findViewById(R.id.btn_hide_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_show_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_toggle_soft_input).setOnClickListener(this);
        tvAboutKeyboard = (TextView) findViewById(R.id.tv_about_keyboard);

        tvAboutKeyboard.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hide_soft_input:
                KeyboardUtils.hideSoftInput(this);
                break;
            case R.id.btn_show_soft_input:
                KeyboardUtils.showSoftInput(this, (EditText) findViewById(R.id.et));
                break;
            case R.id.btn_toggle_soft_input:
                KeyboardUtils.toggleSoftInput(this);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
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
