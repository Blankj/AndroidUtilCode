package com.blankj.androidutilcode.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/06
 *     desc  : 权限帮助类
 * </pre>
 */
public class PermissionHelper {

    public static void requestStorage(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.STORAGE);
    }

    public static void requestPhone(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.PHONE);
    }

    public static void requestPhone(final OnPermissionGrantedListener grantedListener,
                                    final OnPermissionDeniedListener deniedListener) {
        request(grantedListener, deniedListener, PermissionConstants.PHONE);
    }

    public static void requestSms(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.SMS);
    }

    public static void requestLocation(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.LOCATION);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final @PermissionConstants.Permission String... permissions) {
        request(grantedListener, null, permissions);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final OnPermissionDeniedListener deniedListener,
                                final @PermissionConstants.Permission String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        if (grantedListener != null) {
                            grantedListener.onPermissionGranted();
                        }
                        LogUtils.d(permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            showOpenAppSettingDialog();
                        }
                        if (deniedListener != null) {
                            deniedListener.onPermissionDenied();
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .request();
    }

    public static void showRationaleDialog(final ShouldRequest shouldRequest) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(true);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(false);
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }

    public static void showOpenAppSettingDialog() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.openAppSettings();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }

    public interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    public interface OnPermissionDeniedListener {
        void onPermissionDenied();
    }
}
