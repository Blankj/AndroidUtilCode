package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.HandlerUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/9/27
 *     desc : Handler工具类Demo
 * </pre>
 */
public class HandlerActivity extends Activity
        implements View.OnClickListener,
        HandlerUtils.OnReceiveMessageListener {

    private TextView                   tvAboutHandler;
    private HandlerUtils.HandlerHolder handlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tvAboutHandler = (TextView) findViewById(R.id.tv_about_handler);
        findViewById(R.id.btn_send_msg_after_3s).setOnClickListener(this);

        handlerHolder = new HandlerUtils.HandlerHolder(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_msg_after_3s:
                handlerHolder.sendEmptyMessageDelayed(0, 3000);
                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        tvAboutHandler.setText("get_msg_after_3s");
    }
}
