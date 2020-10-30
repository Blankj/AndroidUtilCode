package com.blankj.utilcode.pkg.feature.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.*
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about AppUtils
 * ```
 */
class AppActivity : CommonActivity(), Utils.OnAppStatusChangedListener {

    var isRegisterAppStatusChangedListener: Boolean = false

    companion object {
        fun start(context: Context) {
            PermissionHelper.request(context, object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val starter = Intent(context, AppActivity::class.java)
                    context.startActivity(starter)
                }

                override fun onDenied() {
                }
            }, PermissionConstants.STORAGE)
        }
    }

    private val listener = object : OnReleasedListener {
        override fun onReleased() {
            return AppUtils.installApp(Config.TEST_APK_PATH)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_app
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtils.e(requestCode, resultCode)
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemSwitch("registerAppStatusChangedListener", { isRegisterAppStatusChangedListener }, {
                    isRegisterAppStatusChangedListener = it
                    if (it) {
                        AppUtils.registerAppStatusChangedListener(this)
                    } else {
                        AppUtils.unregisterAppStatusChangedListener(this)
                    }
                }),
                CommonItemTitle("isAppRoot", AppUtils.isAppRoot().toString()),
                CommonItemTitle("isAppDebug", AppUtils.isAppDebug().toString()),
                CommonItemTitle("isAppSystem", AppUtils.isAppSystem().toString()),
                CommonItemTitle("isAppForeground", AppUtils.isAppForeground(AppUtils.getAppPackageName()).toString()),
                CommonItemTitle("isAppRunning", AppUtils.isAppRunning(AppUtils.getAppPackageName()).toString()),
                CommonItemImage("getAppIcon") {
                    it.setImageDrawable(AppUtils.getAppIcon())
                },
                CommonItemTitle("getAppPackageName", AppUtils.getAppPackageName()),
                CommonItemTitle("getAppName", AppUtils.getAppName()),
                CommonItemTitle("getAppPath", AppUtils.getAppPath()),
                CommonItemTitle("getAppVersionName", AppUtils.getAppVersionName()),
                CommonItemTitle("getAppVersionCode", AppUtils.getAppVersionCode().toString()),
                CommonItemTitle("getAppSignaturesSHA1", AppUtils.getAppSignaturesSHA1().toString()),
                CommonItemTitle("getAppSignaturesSHA256", AppUtils.getAppSignaturesSHA256().toString()),
                CommonItemTitle("getAppSignaturesMD5", AppUtils.getAppSignaturesMD5().toString()),
                CommonItemTitle("getAppUid", AppUtils.getAppUid().toString()),
                CommonItemTitle("getApkInfo", AppUtils.getApkInfo(AppUtils.getAppPath()).toString()),

                CommonItemClick(R.string.app_install) {
                    if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                        ToastUtils.showShort(R.string.app_install_tips)
                    } else {
                        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                            ReleaseInstallApkTask(listener).execute()
                        } else {
                            listener.onReleased()
                        }
                    }
                },
                CommonItemClick(R.string.app_uninstall) {
                    if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                        AppUtils.uninstallApp(Config.TEST_PKG)
                    } else {
                        ToastUtils.showShort(R.string.app_uninstall_tips)
                    }
                },
                CommonItemClick(R.string.app_launch) {
                    AppUtils.launchApp(this.packageName)
                },
                CommonItemClick(R.string.app_relaunch) {
                    AppUtils.relaunchApp()
                },
                CommonItemClick(R.string.app_launch_details_settings, true) {
                    AppUtils.launchAppDetailsSettings()
                },
                CommonItemClick(R.string.app_exit) {
                    AppUtils.exitApp()
                }
        )
    }

    override fun onForeground(activity: Activity) {
        ToastUtils.showShort("onForeground\n${activity.javaClass.simpleName}")
    }

    override fun onBackground(activity: Activity) {
        ToastUtils.showShort("onBackground\n${activity.javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRegisterAppStatusChangedListener) {
            AppUtils.unregisterAppStatusChangedListener(this)
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