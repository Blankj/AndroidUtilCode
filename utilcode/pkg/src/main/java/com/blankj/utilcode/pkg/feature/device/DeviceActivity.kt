package com.blankj.utilcode.pkg.feature.device

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_device.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog : http://blankj.com
 * time : 2016/09/27
 * desc : demo about DeviceUtils
 * ```
 */
class DeviceActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, DeviceActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_device
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_device)

        shutdownBtn.setOnClickListener(this)
        rebootBtn.setOnClickListener(this)
        reboot2RecoveryBtn.setOnClickListener(this)
        reboot2BootloaderBtn.setOnClickListener(this)

        SpanUtils.with(aboutDeviceTv)
                .appendLine("isRoot: " + DeviceUtils.isDeviceRooted())
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        appendLine("isAdbEnabled: " + DeviceUtils.isAdbEnabled())
                    }
                }
                .appendLine("getSDKVersionName: " + DeviceUtils.getSDKVersionName())
                .appendLine("getSDKVersionCode: " + DeviceUtils.getSDKVersionCode())
                .appendLine("getAndroidID: " + DeviceUtils.getAndroidID())
                .appendLine("getMacAddress: " + DeviceUtils.getMacAddress())
                .appendLine("getManufacturer: " + DeviceUtils.getManufacturer())
                .appendLine("getModel: " + DeviceUtils.getModel())
                .append("getABIs: " + Arrays.asList(*DeviceUtils.getABIs()))
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.shutdownBtn -> DeviceUtils.shutdown()
            R.id.rebootBtn -> DeviceUtils.reboot()
            R.id.reboot2RecoveryBtn -> DeviceUtils.reboot2Recovery()
            R.id.reboot2BootloaderBtn -> DeviceUtils.reboot2Bootloader()
        }
    }
}
