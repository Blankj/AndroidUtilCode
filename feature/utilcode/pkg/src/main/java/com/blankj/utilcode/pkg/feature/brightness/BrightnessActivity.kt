package com.blankj.utilcode.pkg.feature.brightness

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
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
                CommonItemSeekBar("getBrightness", 255, BrightnessUtils.getBrightness(), object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        BrightnessUtils.setBrightness(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                }),
                CommonItemSeekBar("getWindowBrightness", 255, BrightnessUtils.getWindowBrightness(window), object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        BrightnessUtils.setWindowBrightness(window, progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                }),
                CommonItemSwitch(
                        R.string.brightness_auto_brightness,
                        Utils.Func1 {
                            BrightnessUtils.isAutoBrightnessEnabled()
                        },
                        Utils.Func1 {
                            BrightnessUtils.setAutoBrightnessEnabled(it)
                        }
                )
        )
    }
}