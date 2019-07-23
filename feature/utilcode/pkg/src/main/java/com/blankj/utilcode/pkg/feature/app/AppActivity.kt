package com.blankj.utilcode.pkg.feature.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
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
class AppActivity : CommonTitleActivity() {

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

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                appInstallAppBtn,
                appUninstallAppBtn,
                appLaunchAppBtn,
                appRelaunchAppBtn,
                appExitAppBtn,
                appLaunchAppDetailsSettingsBtn
        )
        SpanUtils.with(appAboutTv)
                .appendLine("isAppRoot: " + AppUtils.isAppRoot())
                .appendLine("isAppDebug: " + AppUtils.isAppDebug())
                .appendLine("isAppSystem: " + AppUtils.isAppSystem())
                .appendLine("isAppForeground: " + AppUtils.isAppForeground("com.blankj.androidutilcode"))
                .appendLine("isAppRunning: " + AppUtils.isAppRunning("com.blankj.androidutilcode"))
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
                .appendLine("getAppUid: " + AppUtils.getAppUid())
                .append("getApkInfo: " + AppUtils.getApkInfo(AppUtils.getAppPath()))
                .create()
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.appInstallAppBtn -> {
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    ToastUtils.showShort(R.string.app_install_tips)
                } else {
                    if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                        ReleaseInstallApkTask(listener).execute()
                    } else {
                        listener.onReleased()
                    }
                }
            }
            R.id.appUninstallAppBtn -> {
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    AppUtils.uninstallApp(Config.TEST_PKG)
                } else {
                    ToastUtils.showShort(R.string.app_uninstall_tips)
                }
            }
            R.id.appLaunchAppBtn -> AppUtils.launchApp(this.packageName)
            R.id.appRelaunchAppBtn -> AppUtils.relaunchApp()
            R.id.appLaunchAppDetailsSettingsBtn -> AppUtils.launchAppDetailsSettings()
            R.id.appExitAppBtn -> AppUtils.exitApp()
        }
    }
}

class ReleaseInstallApkTask(private val mListener: OnReleasedListener) : ThreadUtils.SimpleTask<Unit>() {

    override fun doInBackground() {
        ResourceUtils.copyFileFromAssets("test_install", Config.TEST_APK_PATH)
    }

    override fun onSuccess(result: Unit) {
        mListener.onReleased()
    }

    fun execute() {
        ThreadUtils.executeByIo(this)
    }
}

interface OnReleasedListener {
    fun onReleased()
}