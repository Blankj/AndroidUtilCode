package com.blankj.utilcode.pkg.feature.device;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SpanUtils;

import java.util.Arrays;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/09/27
 *     desc : demo about DeviceUtils
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
        setTitle(R.string.demo_device);

        findViewById(R.id.btn_shutdown).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_recovery).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_bootloader).setOnClickListener(this);
        TextView tvAboutDevice = findViewById(R.id.tv_about_device);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tvAboutDevice.setText(new SpanUtils()
                    .appendLine("isRoot: " + DeviceUtils.isDeviceRooted())
                    .appendLine("isAdbEnabled: " + DeviceUtils.isAdbEnabled())
                    .appendLine("getSDKVersionName: " + DeviceUtils.getSDKVersionName())
                    .appendLine("getSDKVersionCode: " + DeviceUtils.getSDKVersionCode())
                    .appendLine("getAndroidID: " + DeviceUtils.getAndroidID())
                    .appendLine("getMacAddress: " + DeviceUtils.getMacAddress())
                    .appendLine("getManufacturer: " + DeviceUtils.getManufacturer())
                    .appendLine("getModel: " + DeviceUtils.getModel())
                    .append("getABIs: " + Arrays.asList(DeviceUtils.getABIs()))
                    .create()
            );
        } else {
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
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_shutdown) {
            DeviceUtils.shutdown();

        } else if (i == R.id.btn_reboot) {
            DeviceUtils.reboot();

        } else if (i == R.id.btn_reboot_to_recovery) {
            DeviceUtils.reboot2Recovery();

        } else if (i == R.id.btn_reboot_to_bootloader) {
            DeviceUtils.reboot2Bootloader();

        }
    }
}
