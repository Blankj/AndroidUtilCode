package com.blankj.utilcode.pkg.feature.flashlight

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/04/27
 * desc  : demo about FlashlightUtils
 * ```
 */
class FlashlightActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            if (!FlashlightUtils.isFlashlightEnable()) {
                ToastUtils.showLong("Didn't support flashlight.")
                return
            }
            PermissionHelper.request(context, object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val starter = Intent(context, FlashlightActivity::class.java)
                    context.startActivity(starter)
                }

                override fun onDenied() {
                    LogUtils.e("permission denied")
                }
            }, PermissionConstants.CAMERA)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_flashlight
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>().apply {
            add(CommonItemTitle("isFlashlightEnable", FlashlightUtils.isFlashlightEnable().toString()))
            if (FlashlightUtils.isFlashlightEnable()) {
                add(CommonItemSwitch(
                        R.string.flashlight_status,
                        { FlashlightUtils.isFlashlightOn() },
                        { FlashlightUtils.setFlashlightStatus(it) }
                ))
            }
        }
    }

    override fun onDestroy() {
        FlashlightUtils.destroy()
        super.onDestroy()
    }
}
