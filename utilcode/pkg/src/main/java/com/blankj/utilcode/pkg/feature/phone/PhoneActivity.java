package com.blankj.utilcode.pkg.feature.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.pkg.helper.PermissionHelper;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about PhoneUtils
 * </pre>
 */
public class PhoneActivity extends BaseBackActivity {

    private TextView tvAboutPhone;

    public static void start(Context context) {
        Intent starter = new Intent(context, PhoneActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_phone;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_phone);

        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_call).setOnClickListener(this);
        findViewById(R.id.btn_send_sms).setOnClickListener(this);
        findViewById(R.id.btn_send_sms_silent).setOnClickListener(this);
        tvAboutPhone = findViewById(R.id.tv_about_phone);

        PermissionHelper.requestPhone(
                new PermissionHelper.OnPermissionGrantedListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionGranted() {
                        tvAboutPhone.setText(new SpanUtils()
                                .appendLine("isPhone: " + PhoneUtils.isPhone())
                                .appendLine("getDeviceId: " + PhoneUtils.getDeviceId())
                                .appendLine("getIMEI: " + PhoneUtils.getIMEI())
                                .appendLine("getMEID: " + PhoneUtils.getMEID())
                                .appendLine("getIMSI: " + PhoneUtils.getIMSI())
                                .appendLine("getPhoneType: " + PhoneUtils.getPhoneType())
                                .appendLine("isSimCardReady: " + PhoneUtils.isSimCardReady())
                                .appendLine("getSimOperatorName: " + PhoneUtils.getSimOperatorName())
                                .appendLine("getSimOperatorByMnc: " + PhoneUtils.getSimOperatorByMnc())
                                .appendLine("getPhoneStatus: " + PhoneUtils.getPhoneStatus())
                                .create());
                    }
                },
                new PermissionHelper.OnPermissionDeniedListener() {
                    @Override
                    public void onPermissionDenied() {
                        tvAboutPhone.setText(new SpanUtils()
                                .appendLine("isPhone: " + PhoneUtils.isPhone())
                                .appendLine("getDeviceId: " + "need permission")
                                .appendLine("getIMEI: " + "need permission")
                                .appendLine("getMEID: " + "need permission")
                                .appendLine("getIMSI: " + "need permission")
                                .appendLine("getPhoneType: " + PhoneUtils.getPhoneType())
                                .appendLine("isSimCardReady: " + PhoneUtils.isSimCardReady())
                                .appendLine("getSimOperatorName: " + PhoneUtils.getSimOperatorName())
                                .appendLine("getSimOperatorByMnc: " + PhoneUtils.getSimOperatorByMnc())
                                .appendLine("getPhoneStatus: " + "need permission")
                                .create());
                    }
                }
        );
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_dial) {
            PhoneUtils.dial("10000");

        } else if (i == R.id.btn_call) {
            PermissionHelper.requestPhone(new PermissionHelper.OnPermissionGrantedListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onPermissionGranted() {
                    PhoneUtils.call("10000");
                }
            });

        } else if (i == R.id.btn_send_sms) {
            PhoneUtils.sendSms("10000", "sendSms");

        } else if (i == R.id.btn_send_sms_silent) {
            PermissionHelper.requestSms(new PermissionHelper.OnPermissionGrantedListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onPermissionGranted() {
                    PhoneUtils.sendSmsSilent("10000", "sendSmsSilent");
                }
            });

        }
    }
}
