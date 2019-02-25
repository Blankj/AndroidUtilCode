package com.blankj.utilcode.pkg.feature.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.PermissionHelper
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_app.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about AppUtils
 * ```
 */
class AppActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            PermissionHelper.requestStorage(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    val starter = Intent(context, AppActivity::class.java)
                    context.startActivity(starter)
                }
            }, object : PermissionHelper.OnPermissionDeniedListener {
                override fun onPermissionDenied() {
                    start(context)
                }
            })
        }
    }

    private val listener = object : OnReleasedListener {
        override fun onReleased() {
            return AppUtils.installApp(Config.TEST_APK_PATH)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_app)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_app
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        appInstallAppBtn.setOnClickListener(this)
        appInstallAppSilentBtn.setOnClickListener(this)
        appUninstallAppBtn.setOnClickListener(this)
        appUninstallAppSilentBtn.setOnClickListener(this)
        appLaunchAppBtn.setOnClickListener(this)
        appRelaunchAppBtn.setOnClickListener(this)
        appExitAppBtn.setOnClickListener(this)
        appLaunchAppDetailsSettingsBtn.setOnClickListener(this)
        SpanUtils.with(appAboutTv)
                .appendLine("isAppRoot: " + AppUtils.isAppRoot())
                .appendLine("isAppDebug: " + AppUtils.isAppDebug())
                .appendLine("isAppSystem: " + AppUtils.isAppSystem())
                .appendLine("isAppForeground: " + AppUtils.isAppForeground())
                .append("getAppIcon: ").appendImage(AppUtils.getAppIcon(), SpanUtils.ALIGN_CENTER)
                .appendLine()
                .appendLine("getAppPackageName: " + AppUtils.getAppPackageName())
                .appendLine("getAppName: " + AppUtils.getAppName())
                .appendLine("getAppPath: " + AppUtils.getAppPath())
                .appendLine("getAppVersionName: " + AppUtils.getAppVersionName())
                .appendLine("getAppVersionCode: " + AppUtils.getAppVersionCode())
                .appendLine("getAppSignatureSHA1: " + AppUtils.getAppSignatureSHA1())
                .appendLine("getAppSignatureSHA256: " + AppUtils.getAppSignatureSHA256())
                .appendLine("getAppSignatureMD5: " + AppUtils.getAppSignatureMD5())
                .append("getApkInfo: " + AppUtils.getApkInfo(AppUtils.getAppPath()))
                .create()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.appInstallAppBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips)
            } else {
                if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                    ReleaseInstallApkTask(listener).execute()
                } else {
                    listener.onReleased()
                    LogUtils.d("test apk existed.")
                }
            }
            R.id.appInstallAppSilentBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips)
            } else {
                if (AppUtils.installAppSilent(Config.TEST_APK_PATH)) {
                    ToastUtils.showShort(R.string.install_successfully)
                } else {
                    ToastUtils.showShort(R.string.install_unsuccessfully)
                }
            }
            R.id.appUninstallAppBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                AppUtils.uninstallApp(Config.TEST_PKG)
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips)
            }
            R.id.appUninstallAppSilentBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                if (AppUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                    ToastUtils.showShort(R.string.uninstall_successfully)
                } else {
                    ToastUtils.showShort(R.string.uninstall_unsuccessfully)
                }
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips)
            }
            R.id.appLaunchAppBtn -> AppUtils.launchApp(this.packageName)
            R.id.appRelaunchAppBtn -> AppUtils.relaunchApp()
            R.id.appLaunchAppDetailsSettingsBtn -> AppUtils.launchAppDetailsSettings()
            R.id.appExitAppBtn -> AppUtils.exitApp()
        }
    }
}

class ReleaseInstallApkTask(private val mListener: OnReleasedListener) : ThreadUtils.SimpleTask<Void>() {

    override fun doInBackground(): Void? {
        ResourceUtils.copyFileFromAssets("test_install", Config.TEST_APK_PATH)
        return null
    }

    override fun onSuccess(result: Void?) {
        mListener.onReleased()
    }

    fun execute() {
        ThreadUtils.executeByIo(this)
    }
}

interface OnReleasedListener {
    fun onReleased()
}