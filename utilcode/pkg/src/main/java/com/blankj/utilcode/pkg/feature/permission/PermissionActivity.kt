package com.blankj.utilcode.pkg.feature.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_permission.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/01
 * desc  : demo about PermissionUtils
 * ```
 */
class PermissionActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PermissionActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var permissions: String

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_permission
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_permission)

        openAppSettingsBtn.setOnClickListener(this)
        requestCalendarBtn.setOnClickListener(this)
        requestRecordAudioBtn.setOnClickListener(this)
        requestCalendarAndRecordAudioBtn.setOnClickListener(this)

        val sb = StringBuilder()
        for (s in PermissionUtils.getPermissions()) {
            sb.append(s.substring(s.lastIndexOf('.') + 1)).append("\n")
        }
        permissions = sb.toString()
    }

    override fun onResume() {
        super.onResume()
        updateAboutPermission()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.openAppSettingsBtn -> PermissionUtils.launchAppDetailsSettings()
            R.id.requestCalendarBtn -> requestCalendar()
            R.id.requestRecordAudioBtn -> requestRecordAudio()
            R.id.requestCalendarAndRecordAudioBtn -> requestCalendarAndRecordAudio()
        }
    }

    private fun requestCalendar() {
        PermissionUtils.permission(PermissionConstants.CALENDAR)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        updateAboutPermission()
                        LogUtils.d(permissionsGranted)
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    }
                })
                .theme { activity -> ScreenUtils.setFullScreen(activity) }
                .request()
    }

    private fun requestRecordAudio() {
        PermissionUtils.permission(PermissionConstants.MICROPHONE)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        updateAboutPermission()
                        LogUtils.d(permissionsGranted)
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    }
                })
                .request()
    }

    private fun requestCalendarAndRecordAudio() {
        PermissionUtils.permission(PermissionConstants.CALENDAR, PermissionConstants.MICROPHONE)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        updateAboutPermission()
                        LogUtils.d(permissionsGranted)
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    }
                })
                .request()
    }

    private fun updateAboutPermission() {
        SpanUtils.with(aboutPermissionTv)
                .append(permissions).setBold()
                .appendLine("READ_CALENDAR: " + PermissionUtils.isGranted(Manifest.permission.READ_CALENDAR))
                .appendLine("RECORD_AUDIO: " + PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO))
                .create()
    }
}
