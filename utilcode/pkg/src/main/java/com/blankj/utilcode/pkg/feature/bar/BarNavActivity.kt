package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
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
class BarNavActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarNavActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_bar)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_bar_nav
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        contentView.setBackgroundColor(Color.GRAY)
        if (!BarUtils.isSupportNavBar()) {
            barNavVisibilityCb.visibility = View.GONE
            barNavSetColorBtn.visibility = View.GONE
        } else {
            barNavVisibilityCb.setOnCheckedChangeListener { buttonView, isChecked ->
                BarUtils.setNavBarVisibility(this, isChecked)
            }
            barNavSetColorBtn.setOnClickListener(this)
        }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barNavSetColorBtn -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BarUtils.setNavBarColor(this, ColorUtils.getRandomColor())
            }
        }
        updateAboutNav()
    }

    private fun updateAboutNav() {
        SpanUtils.with(barNavAboutTv)
                .appendLine("navHeight: " + BarUtils.getNavBarHeight())
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        appendLine("getNavBarColor: " + ColorUtils.int2ArgbString(BarUtils.getNavBarColor(mActivity)))
                    }
                }
                .append("isSupportNavBar: " + BarUtils.isSupportNavBar())
                .create();
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        barNavVisibilityCb.isChecked = BarUtils.isNavBarVisible(this)
        updateAboutNav()
    }
}
