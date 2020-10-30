package com.blankj.utilcode.pkg.feature.brightness

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.common.item.CommonItemSwitch
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/02/08
 * desc  : demo about BrightnessUtils
 * ```
 */
class BrightnessActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        val starter = Intent(context, BrightnessActivity::class.java)
                        context.startActivity(starter)
                    }

                    override fun onDenied() {
                        ToastUtils.showLong("No permission of write settings.")
                    }
                })
            } else {
                val starter = Intent(context, BrightnessActivity::class.java)
                context.startActivity(starter)
            }
        }
    }


    override fun bindTitleRes(): Int {
        return R.string.demo_brightness
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemSeekBar("getBrightness", 255, object : CommonItemSeekBar.ProgressListener() {
                    override fun getCurValue(): Int {
                        return BrightnessUtils.getBrightness()
                    }

                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        BrightnessUtils.setBrightness(progress)
                    }
                }),
                CommonItemSeekBar("getWindowBrightness", 255, object : CommonItemSeekBar.ProgressListener() {
                    override fun getCurValue(): Int {
                        return BrightnessUtils.getWindowBrightness(window)
                    }

                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        BrightnessUtils.setWindowBrightness(window, progress)
                    }
                }),
                CommonItemSwitch(
                        R.string.brightness_auto_brightness,
                        { BrightnessUtils.isAutoBrightnessEnabled() },
                        { BrightnessUtils.setAutoBrightnessEnabled(it) }
                )
        )
    }
}