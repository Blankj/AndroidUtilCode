package com.blankj.utilcode.pkg.feature.phone

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PhoneUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about PhoneUtils
 * ```
 */
class PhoneActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            PermissionHelper.request(context, object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val starter = Intent(context, PhoneActivity::class.java)
                    context.startActivity(starter)
                }

                override fun onDenied() {
                }
            }, PermissionConstants.PHONE)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_phone
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("isPhone", PhoneUtils.isPhone().toString()),
                CommonItemTitle("getDeviceId", PhoneUtils.getDeviceId()),
                CommonItemTitle("getSerial", PhoneUtils.getSerial()),
                CommonItemTitle("getIMEI", PhoneUtils.getIMEI()),
                CommonItemTitle("getMEID", PhoneUtils.getMEID()),
                CommonItemTitle("getIMSI", PhoneUtils.getIMSI()),
                CommonItemTitle("getPhoneType", PhoneUtils.getPhoneType().toString()),
                CommonItemTitle("isSimCardReady", PhoneUtils.isSimCardReady().toString()),
                CommonItemTitle("getSimOperatorName", PhoneUtils.getSimOperatorName()),
                CommonItemTitle("getSimOperatorByMnc", PhoneUtils.getSimOperatorByMnc()),

                CommonItemClick(R.string.phone_dial) { PhoneUtils.dial("*10000#haha") },
                CommonItemClick(R.string.phone_call) { PhoneUtils.call("*10000#haha") },
                CommonItemClick(R.string.phone_send_sms) { PhoneUtils.sendSms("10000", "sendSms") }
        )
    }
}
