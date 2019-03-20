package com.blankj.utilcode.pkg.feature.phone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.PermissionHelper
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_phone.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about PhoneUtils
 * ```
 */
class PhoneActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            PermissionHelper.requestPhoneAndSms(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    val starter = Intent(context, PhoneActivity::class.java)
                    context.startActivity(starter)
                }
            }, object : PermissionHelper.OnPermissionDeniedListener {
                override fun onPermissionDenied() {
                    start(context)
                }
            })
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_phone)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_phone
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        SpanUtils.with(phoneAboutTv)
                .appendLine("isPhone: " + PhoneUtils.isPhone())
                .appendLine("getDeviceId: " + PhoneUtils.getDeviceId())
                .appendLine("getIMEI: " + PhoneUtils.getIMEI())
                .appendLine("getMEID: " + PhoneUtils.getMEID())
                .appendLine("getIMSI: " + PhoneUtils.getIMSI())
                .appendLine("getPhoneType: " + PhoneUtils.getPhoneType())
                .appendLine("isSimCardReady: " + PhoneUtils.isSimCardReady())
                .appendLine("getSimOperatorName: " + PhoneUtils.getSimOperatorName())
                .appendLine("getSimOperatorByMnc: " + PhoneUtils.getSimOperatorByMnc())
                .append("getPhoneStatus: " + PhoneUtils.getPhoneStatus())
                .create()

        phoneDialBtn.setOnClickListener(this)
        phoneCallBtn.setOnClickListener(this)
        phoneSendSmsBtn.setOnClickListener(this)
        phoneSendSmsSilentBtn.setOnClickListener(this)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.phoneDialBtn -> PhoneUtils.dial("10000")
            R.id.phoneCallBtn -> PhoneUtils.call("10000")
            R.id.phoneSendSmsBtn -> PhoneUtils.sendSms("10000", "sendSms")
            R.id.phoneSendSmsSilentBtn -> PhoneUtils.sendSmsSilent("10000", "sendSmsSilent")
        }
    }
}
