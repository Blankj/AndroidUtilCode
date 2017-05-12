package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.HandlerUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/09/27
 *     desc : Handler工具类Demo
 * </pre>
 */
public class HandlerActivity extends BaseActivity
        implements Callback {

    private TextView                   tvAboutHandler;
    private HandlerUtils.HandlerHolder handlerHolder;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_handler;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvAboutHandler = (TextView) findViewById(R.id.tv_about_handler);
        findViewById(R.id.btn_send_msg_after_3s).setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context context) {
        handlerHolder = new HandlerUtils.HandlerHolder(this);
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_msg_after_3s:
                handlerHolder.sendEmptyMessageDelayed(0, 3000);
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        tvAboutHandler.setText(R.string.handler_received_msg);
        return false;
    }
}
