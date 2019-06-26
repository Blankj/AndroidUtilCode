package com.blankj.subutil.pkg.helper

import android.support.v7.app.AlertDialog
import com.blankj.subutil.pkg.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/10
 * desc  : helper about dialog
 * ```
 */
object DialogHelper {

    fun showRationaleDialog(shouldRequest: ShouldRequest) {
        val topActivity = ActivityUtils.getTopActivity()
        if (topActivity == null || topActivity.isFinishing) return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> shouldRequest.again(true) }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> shouldRequest.again(false) }
                .setCancelable(false)
                .create()
                .show()

    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.getTopActivity()
        if (topActivity == null || topActivity.isFinishing) return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> PermissionUtils.launchAppDetailsSettings() }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> }
                .setCancelable(false)
                .create()
                .show()
    }
}
