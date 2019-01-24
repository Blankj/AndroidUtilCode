package com.blankj.subutil.pkg.helper;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/06
 *     desc  : helper about permission
 * </pre>
 */
public class PermissionHelper {

    public static void requestLocation(final OnPermissionGrantedListener listener,
                                       final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.LOCATION);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final OnPermissionDeniedListener deniedListener,
                                final @PermissionConstants.Permission String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        LogUtils.d(permissionsGranted);
                        if (grantedListener != null) {
                            grantedListener.onPermissionGranted();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                            return;
                        }
                        if (deniedListener != null) {
                            deniedListener.onPermissionDenied();
                        }
                    }
                })
                .request();
    }

    public interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    public interface OnPermissionDeniedListener {
        void onPermissionDenied();
    }
}
