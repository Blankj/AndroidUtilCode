package com.blankj.subutil.pkg.feature.flashlight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.subutil.pkg.R;
import com.blankj.subutil.pkg.helper.PermissionHelper;
import com.blankj.subutil.util.FlashlightUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/27
 *     desc  : demo about FlashlightUtils
 * </pre>
 */
public class FlashlightActivity extends BaseBackActivity {

    TextView tvAboutFlashlight;

    public static void start(Context context) {
        Intent starter = new Intent(context, FlashlightActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_flashlight;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tvAboutFlashlight = findViewById(R.id.tv_about_flashlight);
        findViewById(R.id.btn_set_flashlight_on).setOnClickListener(this);
        findViewById(R.id.btn_set_flashlight_off).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
        if (FlashlightUtils.isFlashlightEnable()) {
            FlashlightUtils.getInstance().register();
        }
        updateAboutFlashlight();
    }

    @Override
    public void onWidgetClick(View view) {
        if (!FlashlightUtils.isFlashlightEnable()) return;
        int i = view.getId();
        if (i == R.id.btn_set_flashlight_on) {
            PermissionHelper.requestCamera(new PermissionHelper.OnPermissionGrantedListener() {
                @Override
                public void onPermissionGranted() {
                    FlashlightUtils.getInstance().setFlashlightOn();
                }
            });

        } else if (i == R.id.btn_set_flashlight_off) {
            PermissionHelper.requestCamera(new PermissionHelper.OnPermissionGrantedListener() {
                @Override
                public void onPermissionGranted() {
                    FlashlightUtils.getInstance().setFlashlightOff();
                }
            });

        }
        updateAboutFlashlight();
    }

    @Override
    protected void onDestroy() {
        FlashlightUtils.getInstance().unregister();
        super.onDestroy();
    }

    private void updateAboutFlashlight() {
        PermissionHelper.requestCamera(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                tvAboutFlashlight.setText(new SpanUtils()
                        .appendLine("isFlashlightEnable: " + FlashlightUtils.isFlashlightEnable())
                        .appendLine("isFlashlightOn: " + FlashlightUtils.getInstance().isFlashlightOn())
                        .create());
            }
        });
    }
}
