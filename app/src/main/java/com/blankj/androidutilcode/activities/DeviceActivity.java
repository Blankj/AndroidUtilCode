package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.App;
import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.DeviceUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog : http://blankj.com
 *     time : 2016/9/27
 *     desc : Device工具类Demo
 * </pre>
 */
public class DeviceActivity extends Activity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        TextView tvAboutDevice = (TextView) findViewById(R.id.tv_about_device);

        findViewById(R.id.btn_shutdown).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_recovery).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_bootloader).setOnClickListener(this);

        tvAboutDevice.setText("isRoot: " + DeviceUtils.isDeviceRoot() +
                "\ngetSDKVersion: " + DeviceUtils.getSDKVersion() +
                "\ngetAndroidID: " + DeviceUtils.getAndroidID(App.getInstance()) +
                "\ngetMacAddress: " + DeviceUtils.getMacAddress(App.getInstance()) +
                "\ngetManufacturer: " + DeviceUtils.getManufacturer() +
                "\ngetModel: " + DeviceUtils.getModel()
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shutdown:
                DeviceUtils.shutdown();
                break;
            case R.id.btn_reboot:
                DeviceUtils.reboot();
            case R.id.btn_reboot_to_recovery:
                DeviceUtils.reboot2Recovery();
            case R.id.btn_reboot_to_bootloader:
                DeviceUtils.reboot2Bootloader();
                break;
        }
    }
}