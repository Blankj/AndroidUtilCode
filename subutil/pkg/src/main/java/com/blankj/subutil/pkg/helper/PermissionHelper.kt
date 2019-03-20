package com.blankj.subutil.pkg.helper

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/06
 * desc  : helper about permission
 * ```
 */
object PermissionHelper {

    fun requestLocation(listener: OnPermissionGrantedListener,
                        deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.LOCATION)
    }

    private fun request(grantedListener: OnPermissionGrantedListener?,
                        deniedListener: OnPermissionDeniedListener?,
                        @PermissionConstants.Permission vararg permissions: String) {
        PermissionUtils.permission(*permissions)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        LogUtils.d(permissionsGranted)
                        grantedListener?.onPermissionGranted()
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                            return
                        }
                        deniedListener?.onPermissionDenied()
                    }
                })
                .request()
    }

    interface OnPermissionGrantedListener {
        fun onPermissionGranted()
    }

    interface OnPermissionDeniedListener {
        fun onPermissionDenied()
    }
}
