package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_bar_nav.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about BarUtils
 * ```
 */
class BarNavActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarNavActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_nav
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        (contentView.parent as View).setBackgroundColor(Color.GRAY)
        setTitle(R.string.demo_bar)

        barNavShowBtn.setOnClickListener(this)
        barNavHideBtn.setOnClickListener(this)
        barNavSetColorBtn.setOnClickListener(this)
        updateAboutNav()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barNavShowBtn -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                BarUtils.setNavBarVisibility(this, true)
            }
            R.id.barNavHideBtn -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                BarUtils.setNavBarVisibility(this, false)
            }
            R.id.barNavSetColorBtn -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BarUtils.setNavBarColor(this, (Math.random() * 0xFFFFFFFF).toInt())
            }
        }
        updateAboutNav()
    }

    private fun updateAboutNav() {
        SpanUtils.with(barNavAboutTv)
                .appendLine("navHeight: " + BarUtils.getNavBarHeight())
                .appendLine("isNavBarVisible: " + BarUtils.isNavBarVisible(this))
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        appendLine("getNavBarColor: #" + Integer.toHexString(BarUtils.getNavBarColor(mActivity)))
                    }
                }
                .append("isSupportNavBar: " + BarUtils.isSupportNavBar())
                .create();
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        updateAboutNav()
    }
}
