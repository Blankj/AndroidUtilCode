package com.blankj.utilcode.pkg.feature.vibrate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.VibrateUtils
import kotlinx.android.synthetic.main.activity_vibrate.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/12/29
 * desc  : demo about VibrateUtils
 * ```
 */
class VibrateActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, VibrateActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_vibrate)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_vibrate
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        vibrate1000msBtn.setOnClickListener { VibrateUtils.vibrate(1000) }
        vibrateCustomBtn.setOnClickListener {
            VibrateUtils.vibrate(longArrayOf(0, 1000, 1000, 2000, 2000, 1000), 1)
        }
        vibrateCancelBtn.setOnClickListener { VibrateUtils.cancel() }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun onDestroy() {
        super.onDestroy()
        VibrateUtils.cancel()
    }
}
