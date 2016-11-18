package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.DeviceUtils;
import com.blankj.utilcode.utils.HandlerUtils;
import com.blankj.utilcode.utils.LogUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/9/27
 *     desc : Handler工具类测试
 * </pre>
 */
public class HandlerActivity extends Activity
        implements View.OnClickListener, HandlerUtils.OnReceiveMessageListener {

    private TextView tvAboutHandler0;
    private TextView tvAboutHandler1;
    private HandlerUtils.HandlerHolder handlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tvAboutHandler0 = (TextView) findViewById(R.id.tv_about_handler0);
        tvAboutHandler1 = (TextView) findViewById(R.id.tv_about_handler1);
        findViewById(R.id.btn_send_msg_after_3s).setOnClickListener(this);

        handlerHolder = new HandlerUtils.HandlerHolder(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_msg_after_3s:
                handlerHolder.sendEmptyMessageDelayed(0, 30000);
                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        tvAboutHandler1.setText("get_msg_after_3s");
    }
}
