package com.blankj.utilcode.pkg.feature.permission

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.os.Build
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/01
 * desc  : demo about PermissionUtils
 * ```
 */
class PermissionActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PermissionActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val permissions: String

    init {
        val permissionList = PermissionUtils.getPermissions()
        if (permissionList.isEmpty()) {
            permissions = ""
        } else {
            val sb = StringBuilder()
            for (permission in permissionList) {
                sb.append("\n").append(permission.substring(permission.lastIndexOf('.') + 1))
            }
            permissions = sb.deleteCharAt(0).toString()
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_permission
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>().apply {
            add(CommonItemTitle("Permissions", permissions))
            add(CommonItemClick(R.string.permission_open_app_settings, true) { PermissionUtils.launchAppDetailsSettings() })
            add(CommonItemSwitch(
                    R.string.permission_calendar_status,
                    { PermissionUtils.isGranted(PermissionConstants.CALENDAR) },
                    { requestCalendar() }
            ))
            add(CommonItemSwitch(
                    R.string.permission_record_audio_status,
                    { PermissionUtils.isGranted(PermissionConstants.MICROPHONE) },
                    { requestRecordAudio() }
            ))
            add(CommonItemSwitch(
                    R.string.permission_calendar_and_record_audio_status,
                    { PermissionUtils.isGranted(PermissionConstants.CALENDAR, PermissionConstants.MICROPHONE) },
                    { requestCalendarAndRecordAudio() }
            ))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                add(CommonItemSwitch(
                        R.string.permission_write_settings_status,
                        { PermissionUtils.isGrantedWriteSettings() },
                        { requestWriteSettings() }
                ))
                add(CommonItemSwitch(
                        R.string.permission_write_settings_status,
                        { PermissionUtils.isGrantedDrawOverlays() },
                        { requestDrawOverlays() }
                ))
            }
        }
    }

    private fun requestCalendar() {
        PermissionUtils.permissionGroup(PermissionConstants.CALENDAR)
                .rationale { activity, shouldRequest -> PermissionHelper.showRationaleDialog(activity, shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        LogUtils.d(permissionsGranted)
                        showSnackbar(true, "Calendar is granted")
                        itemsView.updateItems(bindItems())
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        if (permissionsDeniedForever.isNotEmpty()) {
                            showSnackbar(false, "Calendar is denied forever")
                        } else {
                            showSnackbar(false, "Calendar is denied")
                        }
                        itemsView.updateItems(bindItems())
                    }
                })
                .theme { activity -> ScreenUtils.setFullScreen(activity) }
                .request()
    }

    private fun requestRecordAudio() {
        PermissionUtils.permissionGroup(PermissionConstants.MICROPHONE)
                .rationale { activity, shouldRequest -> PermissionHelper.showRationaleDialog(activity, shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        LogUtils.d(permissionsGranted)
                        showSnackbar(true, "Microphone is granted")
                        itemsView.updateItems(bindItems())
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        if (permissionsDeniedForever.isNotEmpty()) {
                            showSnackbar(false, "Microphone is denied forever")
                        } else {
                            showSnackbar(false, "Microphone is denied")
                        }
                        itemsView.updateItems(bindItems())
                    }
                })
                .request()
    }

    private fun requestCalendarAndRecordAudio() {
        PermissionUtils.permission(permission.READ_CALENDAR, permission.RECORD_AUDIO)
                .explain { activity, denied, shouldRequest -> PermissionHelper.showExplainDialog(activity, denied, shouldRequest) }
                .callback { isAllGranted, granted, deniedForever, denied ->
                    LogUtils.d(granted, deniedForever, denied)
                    itemsView.updateItems(bindItems())
                    if (isAllGranted) {
                        showSnackbar(true, "Calendar and Microphone are granted")
                        return@callback
                    }
                    if (deniedForever.isNotEmpty()) {
                        showSnackbar(false, "Calendar or Microphone is denied forever")
                    } else {
                        showSnackbar(false, "Calendar or Microphone is denied")
                    }
                }
                .request()
    }

    private fun requestWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    showSnackbar(true, "Write Settings is granted")
                    itemsView.updateItems(bindItems())
                }

                override fun onDenied() {
                    showSnackbar(false, "Write Settings is denied")
                    itemsView.updateItems(bindItems())
                }
            })
        }
    }

    private fun requestDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestDrawOverlays(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    showSnackbar(true, "Draw Overlays is granted")
                    itemsView.updateItems(bindItems())
                }

                override fun onDenied() {
                    showSnackbar(false, "Draw Overlays is denied")
                    itemsView.updateItems(bindItems())
                }
            })
        }
    }


    private fun showSnackbar(isSuccess: Boolean, msg: String) {
        SnackbarUtils.with(mContentView)
                .setDuration(SnackbarUtils.LENGTH_LONG)
                .setMessage(msg)
                .apply {
                    if (isSuccess) {
                        showSuccess()
                    } else {
                        showError()
                    }
                }
    }
}
