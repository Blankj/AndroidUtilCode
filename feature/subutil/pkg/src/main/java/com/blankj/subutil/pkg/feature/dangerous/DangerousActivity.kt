package com.blankj.subutil.pkg.feature.dangerous

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.subutil.pkg.Config
import com.blankj.subutil.pkg.R
import com.blankj.subutil.pkg.helper.PermissionHelper
import com.blankj.subutil.util.DangerousUtils
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about dangerous
 * ```
 */
class DangerousActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            PermissionHelper.requestStorageAndSms(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    val starter = Intent(context, DangerousActivity::class.java)
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
            if (DangerousUtils.installAppSilent(Config.TEST_APK_PATH)) {
                ToastUtils.showShort(R.string.dangerous_install_successfully)
            } else {
                ToastUtils.showShort(R.string.dangerous_install_unsuccessfully)
            }
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_dangerous
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.dangerous_install_silent) {
                    if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                        ToastUtils.showShort(R.string.dangerous_app_install_tips)
                    } else {
                        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                            ReleaseInstallApkTask(listener).execute()
                        } else {
                            listener.onReleased()
                        }
                    }
                },
                CommonItemClick(R.string.dangerous_uninstall_silent) {
                    if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                        if (DangerousUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                            ToastUtils.showShort(R.string.dangerous_uninstall_successfully)
                        } else {
                            ToastUtils.showShort(R.string.dangerous_uninstall_unsuccessfully)
                        }
                    } else {
                        ToastUtils.showShort(R.string.dangerous_app_uninstall_tips)
                    }
                },
                CommonItemClick(R.string.dangerous_shutdown) {
                    ToastUtils.showShort(DangerousUtils.shutdown().toString())
                },
                CommonItemClick(R.string.dangerous_reboot) {
                    ToastUtils.showShort(DangerousUtils.reboot().toString())
                },
                CommonItemClick(R.string.dangerous_reboot_to_recovery) {
                    ToastUtils.showShort(DangerousUtils.reboot2Recovery().toString())
                },
                CommonItemClick(R.string.dangerous_reboot_to_bootloader) {
                    ToastUtils.showShort(DangerousUtils.reboot2Bootloader().toString())
                },
                CommonItemSwitch(R.string.dangerous_data_enabled, Utils.Func1 {
                    NetworkUtils.getMobileDataEnabled()
                }, Utils.Func1 {
                    if (AppUtils.isAppSystem()) {
                        DangerousUtils.setMobileDataEnabled(it)
                    }
                }),
                CommonItemClick(R.string.dangerous_send_sms_silent) {
                    DangerousUtils.sendSmsSilent("10000", "sendSmsSilent")
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