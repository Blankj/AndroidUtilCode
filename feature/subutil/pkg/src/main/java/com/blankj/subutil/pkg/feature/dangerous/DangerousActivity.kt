package com.blankj.subutil.pkg.feature.dangerous

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.subutil.pkg.Config
import com.blankj.subutil.pkg.R
import com.blankj.subutil.pkg.helper.PermissionHelper
import com.blankj.subutil.util.DangerousUtils
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_dangerous.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about dangerous
 * ```
 */
class DangerousActivity : CommonTitleActivity() {

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

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_dangerous)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_dangerous
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                dangerousInstallAppSilentBtn,
                dangerousUninstallAppSilentBtn,
                dangerousShutdownBtn,
                dangerousRebootBtn,
                dangerousReboot2RecoveryBtn,
                dangerousReboot2BootloaderBtn,
                dangerousSendSmsSilentBtn
        )

        if (AppUtils.isAppSystem()) {
            dangerousMobileDataEnabledCb.setOnCheckedChangeListener { buttonView, isChecked ->
                DangerousUtils.setMobileDataEnabled(isChecked)
            }
        } else {
            dangerousMobileDataEnabledCb.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
        dangerousMobileDataEnabledCb.isChecked = NetworkUtils.getMobileDataEnabled()
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.dangerousInstallAppSilentBtn -> {
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    ToastUtils.showShort(R.string.dangerous_app_install_tips)
                } else {
                    if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
                        ReleaseInstallApkTask(listener).execute()
                    } else {
                        listener.onReleased()
                    }
                }
            }
            R.id.dangerousUninstallAppSilentBtn -> {
                if (AppUtils.isAppInstalled(Config.TEST_PKG)) {
                    if (DangerousUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                        ToastUtils.showShort(R.string.dangerous_uninstall_successfully)
                    } else {
                        ToastUtils.showShort(R.string.dangerous_uninstall_unsuccessfully)
                    }
                } else {
                    ToastUtils.showShort(R.string.dangerous_app_uninstall_tips)
                }
            }
            R.id.dangerousShutdownBtn -> DangerousUtils.shutdown()
            R.id.dangerousRebootBtn -> DangerousUtils.reboot()
            R.id.dangerousReboot2RecoveryBtn -> DangerousUtils.reboot2Recovery()
            R.id.dangerousReboot2BootloaderBtn -> DangerousUtils.reboot2Bootloader()
            R.id.dangerousReboot2BootloaderBtn -> DangerousUtils.reboot2Bootloader()
            R.id.dangerousSendSmsSilentBtn -> DangerousUtils.sendSmsSilent("10000", "sendSmsSilent")
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