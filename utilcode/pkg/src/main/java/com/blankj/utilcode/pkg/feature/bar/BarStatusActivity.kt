package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
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
class BarStatusActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_bar)

        barStatusShowBtn.setOnClickListener(this)
        barStatusHideBtn.setOnClickListener(this)
        barStatusLightModeBtn.setOnClickListener(this)
        barStatusDarkModeBtn.setOnClickListener(this)
        updateAboutStatus()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barStatusShowBtn -> BarUtils.setStatusBarVisibility(this, true)
            R.id.barStatusHideBtn -> BarUtils.setStatusBarVisibility(this, false)
            R.id.barStatusLightModeBtn -> BarUtils.setStatusBarLightMode(this, true)
            R.id.barStatusDarkModeBtn -> BarUtils.setStatusBarLightMode(this, false)
        }
        updateAboutStatus()
    }

    private fun updateAboutStatus() {
        aboutStatusTv.text = SpanUtils()
                .appendLine("statusHeight: " + BarUtils.getStatusBarHeight())
                .append("isStatusVisible: " + BarUtils.isStatusBarVisible(this))
                .create()
    }
}
