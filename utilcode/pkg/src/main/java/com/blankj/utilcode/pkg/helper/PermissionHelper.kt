package com.blankj.utilcode.pkg.helper

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

    fun requestStorage(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.STORAGE)
    }

    fun requestPhone(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.PHONE)
    }

    fun requestPhone(grantedListener: OnPermissionGrantedListener,
                     deniedListener: OnPermissionDeniedListener) {
        request(grantedListener, deniedListener, PermissionConstants.PHONE)
    }

    fun requestSms(listener: OnPermissionGrantedListener) {
        request(listener, PermissionConstants.SMS)
    }

    private fun request(grantedListener: OnPermissionGrantedListener,
                        @PermissionConstants.Permission vararg permissions: String) {
        request(grantedListener, null, *permissions)
    }

    private fun request(grantedListener: OnPermissionGrantedListener,
                        deniedListener: OnPermissionDeniedListener?,
                        @PermissionConstants.Permission vararg permissions: String) {
        PermissionUtils.permission(*permissions)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        grantedListener.onPermissionGranted()
                        LogUtils.d(permissionsGranted)
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                        }
                        deniedListener?.onPermissionDenied()
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
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
