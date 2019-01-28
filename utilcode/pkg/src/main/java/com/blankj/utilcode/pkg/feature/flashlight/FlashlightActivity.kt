package com.blankj.utilcode.pkg.feature.flashlight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.PermissionHelper
import com.blankj.utilcode.util.FlashlightUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_flashlight.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/04/27
 * desc  : demo about FlashlightUtils
 * ```
 */
class FlashlightActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            if (!FlashlightUtils.isFlashlightEnable()) {
                ToastUtils.showLong("Didn't support flashlight.")
                return
            }
            PermissionHelper.requestCamera(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    val starter = Intent(context, FlashlightActivity::class.java)
                    context.startActivity(starter)
                }
            }, object : PermissionHelper.OnPermissionDeniedListener {
                override fun onPermissionDenied() {
                    start(context)
                }
            })
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_flashlight
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_flashlight)

        flashlightStatusCb.isChecked = FlashlightUtils.isFlashlightOn()
        flashlightStatusCb.setOnCheckedChangeListener { buttonView, isChecked ->
            FlashlightUtils.setFlashlightStatus(isChecked)
        }
    }

    override fun doBusiness() {
        updateAboutFlashlight()
    }

    override fun onWidgetClick(view: View) {

    }

    private fun updateAboutFlashlight() {
        SpanUtils.with(flashlightAboutTv)
                .append("isFlashlightEnable: " + FlashlightUtils.isFlashlightEnable())
                .create()
    }

    override fun onDestroy() {
        FlashlightUtils.destroy()
        super.onDestroy()
    }
}
