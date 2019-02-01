package com.blankj.utilcode.pkg.feature.brightness

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BrightnessUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_brightness.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/02/08
 * desc  : demo about BrightnessUtils
 * ```
 */
class BrightnessActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        val starter = Intent(context, BrightnessActivity::class.java)
                        context.startActivity(starter)
                    }

                    override fun onDenied() {
                        start(context)
                    }
                })
            } else {
                val starter = Intent(context, BrightnessActivity::class.java)
                context.startActivity(starter)
            }
        }
    }

    private val brightnessChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            BrightnessUtils.setBrightness(progress)
            updateBrightness()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private val windowBrightnessChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            BrightnessUtils.setWindowBrightness(window, progress)
            updateWindowBrightness()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_brightness)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_brightness
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        brightnessChangeSb.progress = BrightnessUtils.getBrightness()
        brightnessChangeSb.setOnSeekBarChangeListener(brightnessChangeListener)
        updateBrightness()

        brightnessWindowChangeSb.progress = BrightnessUtils.getWindowBrightness(window)
        brightnessWindowChangeSb.setOnSeekBarChangeListener(windowBrightnessChangeListener)
        updateWindowBrightness()

        brightnessSetAutoCb.setOnCheckedChangeListener { buttonView, isChecked ->
            BrightnessUtils.setAutoBrightnessEnabled(isChecked)
        }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    private fun updateBrightness() {
        SpanUtils.with(brightnessAboutTv)
                .append("getBrightness: " + BrightnessUtils.getBrightness())
                .create()
    }

    private fun updateWindowBrightness() {
        SpanUtils.with(brightnessWindowAboutTv)
                .append("getWindowBrightness: " + BrightnessUtils.getWindowBrightness(window))
                .create()
    }
}
