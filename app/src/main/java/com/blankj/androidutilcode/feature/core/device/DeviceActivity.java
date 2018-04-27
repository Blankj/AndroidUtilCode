package com.blankj.androidutilcode.feature.core.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SpanUtils;

import java.util.Arrays;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/09/27
 *     desc : Device 工具类 Demo
 * </pre>
 */
public class DeviceActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DeviceActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_device;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        getToolBar().setTitle(getString(R.string.demo_device));

        findViewById(R.id.btn_shutdown).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_recovery).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_bootloader).setOnClickListener(this);
        TextView tvAboutDevice = findViewById(R.id.tv_about_device);
        tvAboutDevice.setText(new SpanUtils()
                .appendLine("isRoot: " + DeviceUtils.isDeviceRooted())
                .appendLine("getSDKVersionName: " + DeviceUtils.getSDKVersionName())
                .appendLine("getSDKVersionCode: " + DeviceUtils.getSDKVersionCode())
                .appendLine("getAndroidID: " + DeviceUtils.getAndroidID())
                .appendLine("getMacAddress: " + DeviceUtils.getMacAddress())
                .appendLine("getManufacturer: " + DeviceUtils.getManufacturer())
                .appendLine("getModel: " + DeviceUtils.getModel())
                .append("getABIs: " + Arrays.asList(DeviceUtils.getABIs()))
                .create()
        );
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shutdown:
                DeviceUtils.shutdown();
                break;
            case R.id.btn_reboot:
                DeviceUtils.reboot();
                break;
            case R.id.btn_reboot_to_recovery:
                DeviceUtils.reboot2Recovery();
                break;
            case R.id.btn_reboot_to_bootloader:
                DeviceUtils.reboot2Bootloader();
                break;
        }
    }
}
