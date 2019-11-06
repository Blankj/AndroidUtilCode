package com.blankj.utilcode.pkg.feature.app

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemImage
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.PermissionHelper
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about AppUtils
 * ```
 */
class AppActivity : CommonActivity() {

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

    override fun bindTitleRes(): Int {
        return R.string.demo_app
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
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
                CommonItemTitle("getAppSignatureSHA1", AppUtils.getAppSignatureSHA1()),
                CommonItemTitle("getAppSignatureSHA256", AppUtils.getAppSignatureSHA256()),
                CommonItemTitle("getAppSignatureMD5", AppUtils.getAppSignatureMD5()),
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