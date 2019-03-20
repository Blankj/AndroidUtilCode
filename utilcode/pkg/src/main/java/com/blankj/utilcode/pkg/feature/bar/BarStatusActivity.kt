package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_bar_status.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_bar)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        barStatusVisibilityCb.isChecked = BarUtils.isStatusBarVisible(this)
        barStatusVisibilityCb.setOnCheckedChangeListener { buttonView, isChecked ->
            BarUtils.setStatusBarVisibility(this, isChecked)
        }

        barStatusLightModeCb.isChecked = BarUtils.isStatusBarLightMode(this)
        barStatusLightModeCb.setOnCheckedChangeListener { buttonView, isChecked ->
            BarUtils.setStatusBarLightMode(this, isChecked)
        }
    }

    override fun doBusiness() {}

    override fun onResume() {
        super.onResume()
        updateAboutStatus()
    }

    override fun onWidgetClick(view: View) {}

    private fun updateAboutStatus() {
        SpanUtils.with(barStatusAboutTv)
                .append("getStatusBarHeight: " + BarUtils.getStatusBarHeight())
                .create()
    }
}
