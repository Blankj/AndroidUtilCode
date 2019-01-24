package com.blankj.utilcode.pkg.feature.flashlight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.subutil.util.FlashlightUtils
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.PermissionHelper
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
            val starter = Intent(context, FlashlightActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_flashlight
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        flashlightSetOnBtn.setOnClickListener(this)
        flashlightSetOffBtn.setOnClickListener(this)
    }

    override fun doBusiness() {
        if (!FlashlightUtils.isFlashlightEnable()) {
            ToastUtils.showLong("Didn't support flashlight.")
            finish()
            return
        }
        register()
    }

    private fun register() {
        PermissionHelper.requestCamera(object : PermissionHelper.OnPermissionGrantedListener {
            override fun onPermissionGranted() {
                FlashlightUtils.getInstance().register()
                updateAboutFlashlight()
            }
        }, object : PermissionHelper.OnPermissionDeniedListener {
            override fun onPermissionDenied() {
                register()
            }
        })
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.flashlightSetOnBtn -> FlashlightUtils.getInstance().setFlashlightOn()
            R.id.flashlightSetOffBtn -> FlashlightUtils.getInstance().setFlashlightOff()
        }
        updateAboutFlashlight()
    }

    private fun updateAboutFlashlight() {
        SpanUtils.with(flashlightAboutTv)
                .appendLine("isFlashlightEnable: " + FlashlightUtils.isFlashlightEnable())
                .appendLine("isFlashlightOn: " + FlashlightUtils.getInstance().isFlashlightOn)
                .create()
    }

    override fun onDestroy() {
        FlashlightUtils.getInstance().unregister()
        super.onDestroy()
    }
}
