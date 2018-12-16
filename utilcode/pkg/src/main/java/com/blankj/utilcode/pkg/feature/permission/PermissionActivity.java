package com.blankj.utilcode.pkg.feature.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.pkg.helper.DialogHelper;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SpanUtils;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/01
 *     desc  : demo about PermissionUtils
 * </pre>
 */
public class PermissionActivity extends BaseBackActivity {

    TextView tvAboutPermission;
    String   permissions;

    public static void start(Context context) {
        Intent starter = new Intent(context, PermissionActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_permission;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_permission);

        tvAboutPermission = findViewById(R.id.tv_about_permission);
        findViewById(R.id.btn_open_app_settings).setOnClickListener(this);
        findViewById(R.id.btn_request_calendar).setOnClickListener(this);
        findViewById(R.id.btn_request_record_audio).setOnClickListener(this);
        findViewById(R.id.btn_request_calendar_and_record_audio).setOnClickListener(this);

        StringBuilder sb = new StringBuilder();
        for (String s : PermissionUtils.getPermissions()) {
            sb.append(s.substring(s.lastIndexOf('.') + 1)).append("\n");
        }
        permissions = sb.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAboutPermission();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_open_app_settings) {
            PermissionUtils.launchAppDetailsSettings();

        } else if (i == R.id.btn_request_calendar) {
            PermissionUtils.permission(PermissionConstants.CALENDAR)
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(final ShouldRequest shouldRequest) {
                            DialogHelper.showRationaleDialog(shouldRequest);
                        }
                    })
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            updateAboutPermission();
                            LogUtils.d(permissionsGranted);
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever,
                                             List<String> permissionsDenied) {
                            if (!permissionsDeniedForever.isEmpty()) {
                                DialogHelper.showOpenAppSettingDialog();
                            }
                            LogUtils.d(permissionsDeniedForever, permissionsDenied);
                        }
                    })
                    .theme(new PermissionUtils.ThemeCallback() {
                        @Override
                        public void onActivityCreate(Activity activity) {
                            ScreenUtils.setFullScreen(activity);
                        }
                    })
                    .request();

        } else if (i == R.id.btn_request_record_audio) {
            PermissionUtils.permission(PermissionConstants.MICROPHONE)
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(final ShouldRequest shouldRequest) {
                            DialogHelper.showRationaleDialog(shouldRequest);
                        }
                    })
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            updateAboutPermission();
                            LogUtils.d(permissionsGranted);
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever,
                                             List<String> permissionsDenied) {
                            if (!permissionsDeniedForever.isEmpty()) {
                                DialogHelper.showOpenAppSettingDialog();
                            }
                            LogUtils.d(permissionsDeniedForever, permissionsDenied);
                        }
                    })
                    .request();

        } else if (i == R.id.btn_request_calendar_and_record_audio) {
            PermissionUtils.permission(PermissionConstants.CALENDAR, PermissionConstants.MICROPHONE)
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(final ShouldRequest shouldRequest) {
                            DialogHelper.showRationaleDialog(shouldRequest);
                        }
                    })
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            updateAboutPermission();
                            LogUtils.d(permissionsGranted);
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever,
                                             List<String> permissionsDenied) {
                            if (!permissionsDeniedForever.isEmpty()) {
                                DialogHelper.showOpenAppSettingDialog();
                            }
                            LogUtils.d(permissionsDeniedForever, permissionsDenied);
                        }
                    })
                    .request();

        }
    }

    private void updateAboutPermission() {
        tvAboutPermission.setText(new SpanUtils()
                .append(permissions).setBold()
                .appendLine("READ_CALENDAR: " + PermissionUtils.isGranted(Manifest.permission.READ_CALENDAR))
                .appendLine("RECORD_AUDIO: " + PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO))
                .create());
    }
}
