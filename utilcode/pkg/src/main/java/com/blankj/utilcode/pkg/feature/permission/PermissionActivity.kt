package com.blankj.utilcode.pkg.feature.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_permission.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/01
 * desc  : demo about PermissionUtils
 * ```
 */
class PermissionActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PermissionActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var permissions: String

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_permission)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_permission
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        permissionOpenAppSettingsBtn.setOnClickListener(this)
        permissionRequestCalendarBtn.setOnClickListener(this)
        permissionRequestRecordAudioBtn.setOnClickListener(this)
        permissionRequestCalendarAndRecordAudioBtn.setOnClickListener(this)
        permissionRequestWriteSettings.setOnClickListener(this)
        permissionRequestDrawOverlays.setOnClickListener(this)

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

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.permissionOpenAppSettingsBtn -> PermissionUtils.launchAppDetailsSettings()
            R.id.permissionRequestCalendarBtn -> requestCalendar()
            R.id.permissionRequestRecordAudioBtn -> requestRecordAudio()
            R.id.permissionRequestCalendarAndRecordAudioBtn -> requestCalendarAndRecordAudio()
            R.id.permissionRequestWriteSettings -> requestWriteSettings()
            R.id.permissionRequestDrawOverlays -> requestDrawOverlays()
        }
    }

    private fun requestCalendar() {
        PermissionUtils.permission(PermissionConstants.CALENDAR)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        LogUtils.d(permissionsGranted)
                        updateAboutPermission()
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                            return
                        }
                        requestCalendar()
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
                        LogUtils.d(permissionsGranted)
                        updateAboutPermission()
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                            return
                        }
                        requestRecordAudio()
                    }
                })
                .request()
    }

    private fun requestCalendarAndRecordAudio() {
        PermissionUtils.permission(PermissionConstants.CALENDAR, PermissionConstants.MICROPHONE)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        LogUtils.d(permissionsGranted)
                        updateAboutPermission()
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog()
                            return
                        }
                        requestCalendarAndRecordAudio()
                    }
                })
                .request()
    }

    private fun requestWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    ToastUtils.showLong("Write Settings is Granted")
                }

                override fun onDenied() {
                    ToastUtils.showLong("Write Settings Denied")
                }
            })
        }

    }

    private fun requestDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestDrawOverlays(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    ToastUtils.showLong("Draw Overlays is Granted")
                }

                override fun onDenied() {
                    ToastUtils.showLong("Draw Overlays Denied")
                }
            })
        }
    }

    private fun updateAboutPermission() {
        SpanUtils.with(permissionAboutTv)
                .append(permissions).setBold()
                .appendLine("READ_CALENDAR: " + PermissionUtils.isGranted(Manifest.permission.READ_CALENDAR))
                .appendLine("RECORD_AUDIO: " + PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO))
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        appendLine("WRITE_SETTINGS: " + PermissionUtils.isGrantedWriteSettings())
                        appendLine("DRAW_OVERLAYS: " + PermissionUtils.isGrantedDrawOverlays())
                    }
                }
                .create()
    }
}
