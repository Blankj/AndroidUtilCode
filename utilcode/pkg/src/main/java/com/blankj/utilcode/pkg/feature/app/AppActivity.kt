package com.blankj.utilcode.pkg.feature.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
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
class AppActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AppActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val listener = object : OnReleasedListener {
        override fun onReleased() {
            return AppUtils.installApp(Config.TEST_APK_PATH)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_app
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_app)

        installAppBtn.setOnClickListener(this)
        installAppSilentBtn.setOnClickListener(this)
        uninstallAppBtn.setOnClickListener(this)
        uninstallAppSilentBtn.setOnClickListener(this)
        launchAppBtn.setOnClickListener(this)
        relaunchAppBtn.setOnClickListener(this)
        exitAppBtn.setOnClickListener(this)
        launchAppDetailsSettingsBtn.setOnClickListener(this)
        SpanUtils.with(aboutAppTv)
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
                .append("getApkInfo: " + AppUtils.getApkInfo(AppUtils.getAppPath())!!)
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.installAppBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips)
            } else {
                PermissionHelper.requestStorage {
                    if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                        ReleaseInstallApkTask(listener).execute()
                    } else {
                        listener.onReleased()
                        LogUtils.d("test apk existed.")
                    }
                }
            }
            R.id.installAppSilentBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips)
            } else {
                if (AppUtils.installAppSilent(Config.TEST_APK_PATH)) {
                    ToastUtils.showShort(R.string.install_successfully)
                } else {
                    ToastUtils.showShort(R.string.install_unsuccessfully)
                }
            }
            R.id.uninstallAppBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                AppUtils.uninstallApp(Config.TEST_PKG)
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips)
            }
            R.id.uninstallAppSilentBtn -> if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                if (AppUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                    ToastUtils.showShort(R.string.uninstall_successfully)
                } else {
                    ToastUtils.showShort(R.string.uninstall_unsuccessfully)
                }
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips)
            }
            R.id.launchAppBtn -> AppUtils.launchApp(this.packageName)
            R.id.relaunchAppBtn -> AppUtils.relaunchApp()
            R.id.launchAppDetailsSettingsBtn -> AppUtils.launchAppDetailsSettings()
            R.id.exitAppBtn -> AppUtils.exitApp()
        }
    }
}

class ReleaseInstallApkTask(private val mListener: OnReleasedListener?) : ThreadUtils.SimpleTask<Void>() {

    override fun doInBackground(): Void? {
        ResourceUtils.copyFileFromAssets("test_install", Config.TEST_APK_PATH)
        return null
    }

    override fun onSuccess(result: Void?) {
        mListener?.onReleased()
    }

    fun execute() {
        ThreadUtils.executeByIo(this)
    }
}

interface OnReleasedListener {
    fun onReleased()
}