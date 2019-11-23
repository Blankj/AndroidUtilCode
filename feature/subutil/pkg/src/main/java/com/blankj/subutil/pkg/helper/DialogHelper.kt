package com.blankj.subutil.pkg.helper

import android.support.v4.app.FragmentActivity
import android.util.Pair
import android.view.View
import com.blankj.common.dialog.CommonDialogContent
import com.blankj.subutil.pkg.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest
import com.blankj.utilcode.util.StringUtils

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
        val topActivity = ActivityUtils.getTopActivity() ?: return
        CommonDialogContent().init(topActivity as FragmentActivity?,
                StringUtils.getString(android.R.string.dialog_alert_title),
                StringUtils.getString(R.string.permission_rationale_message),
                Pair(StringUtils.getString(android.R.string.ok), View.OnClickListener {
                    shouldRequest.again(true)
                }),
                Pair(StringUtils.getString(android.R.string.cancel), View.OnClickListener {
                    shouldRequest.again(false)
                }))
                .show()
    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        CommonDialogContent().init(topActivity as FragmentActivity?,
                StringUtils.getString(android.R.string.dialog_alert_title),
                StringUtils.getString(R.string.permission_denied_forever_message),
                Pair(StringUtils.getString(android.R.string.ok), View.OnClickListener {
                    PermissionUtils.launchAppDetailsSettings()
                }),
                Pair(StringUtils.getString(android.R.string.cancel), View.OnClickListener {
                }))
                .show()
    }
}
