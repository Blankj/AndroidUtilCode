package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.PhoneUtils;

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

        tvAboutPhone.setText("isPhone: " + PhoneUtils.isPhone(this) +
                "\ngetIMEI: " + PhoneUtils.getIMEI(this) +
                "\ngetIMSI: " + PhoneUtils.getIMSI(this) +
                "\ngetPhoneType: " + PhoneUtils.getPhoneType(this) +
                "\nisSimCardReady: " + PhoneUtils.isSimCardReady(this) +
                "\ngetSimOperatorName: " + PhoneUtils.getSimOperatorName(this) +
                "\ngetSimOperatorByMnc: " + PhoneUtils.getSimOperatorByMnc(this) +
                "\n获取手机状态信息: " + PhoneUtils.getPhoneStatus(this)
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dial:
                PhoneUtils.dial(this, "10000");
                break;
            case R.id.btn_call:
                PhoneUtils.call(this, "10000");
                break;
            case R.id.btn_send_sms:
                PhoneUtils.sendSms(this, "10000", "test");
                break;
            case R.id.btn_send_sms_silent:
                PhoneUtils.sendSmsSilent(this, "10000", "test");
                break;
        }
    }
}
