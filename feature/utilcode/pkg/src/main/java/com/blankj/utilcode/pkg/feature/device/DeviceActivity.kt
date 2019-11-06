package com.blankj.utilcode.pkg.feature.device

import android.content.Context
import android.content.Intent
import android.os.Build
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.DeviceUtils
import java.util.*

/**
 * ```
 * author: Blankj
 * blog : http://blankj.com
 * time : 2016/09/27
 * desc : demo about DeviceUtils
 * ```
 */
class DeviceActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, DeviceActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_device
    }

    override fun bindItems(): List<CommonItem<*>> {
        return arrayListOf<CommonItem<*>>().apply {
            add(CommonItemTitle("isRoot", DeviceUtils.isDeviceRooted().toString()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                add(CommonItemTitle("isAdbEnabled", DeviceUtils.isAdbEnabled().toString()))
            }
            add(CommonItemTitle("getSDKVersionName", DeviceUtils.getSDKVersionName()))
            add(CommonItemTitle("getSDKVersionCode", DeviceUtils.getSDKVersionCode().toString()))
            add(CommonItemTitle("getAndroidID", DeviceUtils.getAndroidID()))
            add(CommonItemTitle("getMacAddress", DeviceUtils.getMacAddress()))
            add(CommonItemTitle("getManufacturer", DeviceUtils.getManufacturer()))
            add(CommonItemTitle("getModel", DeviceUtils.getModel()))
            add(CommonItemTitle("getABIs", Arrays.asList(*DeviceUtils.getABIs()).toString()))
            add(CommonItemTitle("isTablet", DeviceUtils.isTablet().toString()))
            add(CommonItemTitle("isEmulator", DeviceUtils.isEmulator().toString()))
            add(CommonItemTitle("getUniqueDeviceId", DeviceUtils.getUniqueDeviceId("util")))
            add(CommonItemTitle("isSameDevice", DeviceUtils.isSameDevice(DeviceUtils.getUniqueDeviceId()).toString()))
        }
    }
}
