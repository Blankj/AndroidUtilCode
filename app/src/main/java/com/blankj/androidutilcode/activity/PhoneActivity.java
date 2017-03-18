package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.PhoneUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Phone工具类Demo
 * </pre>
 */
public class PhoneActivity extends Activity implements
        View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        TextView tvAboutPhone = (TextView) findViewById(R.id.tv_about_phone);

        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_call).setOnClickListener(this);
        findViewById(R.id.btn_send_sms).setOnClickListener(this);
        findViewById(R.id.btn_send_sms_silent).setOnClickListener(this);

        tvAboutPhone.setText("isPhone: " + PhoneUtils.isPhone()
                + "\ngetIMEI: " + PhoneUtils.getIMEI()
                + "\ngetIMSI: " + PhoneUtils.getIMSI()
                + "\ngetPhoneType: " + PhoneUtils.getPhoneType()
                + "\nisSimCardReady: " + PhoneUtils.isSimCardReady()
                + "\ngetSimOperatorName: " + PhoneUtils.getSimOperatorName()
                + "\ngetSimOperatorByMnc: " + PhoneUtils.getSimOperatorByMnc()
                + "\n获取手机状态信息: " + PhoneUtils.getPhoneStatus()
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dial:
                PhoneUtils.dial("10000");
                break;
            case R.id.btn_call:
                PhoneUtils.call("10000");
                break;
            case R.id.btn_send_sms:
                PhoneUtils.sendSms("10000", "test");
                break;
            case R.id.btn_send_sms_silent:
                PhoneUtils.sendSmsSilent("10000", "test");
                break;
        }
    }
}
