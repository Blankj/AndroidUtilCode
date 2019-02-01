package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import kotlinx.android.synthetic.main.activity_bar.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_bar)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_bar
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        barStatusAboutBtn.setOnClickListener(this)
        barStatusSetColorBtn.setOnClickListener(this)
        barStatusSetAlphaBtn.setOnClickListener(this)
        barStatusSetImageViewBtn.setOnClickListener(this)
        barStatusSetCustomBtn.setOnClickListener(this)
        barStatusSetFragmentBtn.setOnClickListener(this)
        barStatusSetSwipeBackBtn.setOnClickListener(this)
        barStatusSetDrawerBtn.setOnClickListener(this)
        barNotificationAboutBtn.setOnClickListener(this)
        barNavAboutBtn.setOnClickListener(this)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barStatusAboutBtn -> BarStatusActivity.start(this)
            R.id.barStatusSetColorBtn -> BarStatusColorActivity.start(this)
            R.id.barStatusSetAlphaBtn -> BarStatusAlphaActivity.start(this)
            R.id.barStatusSetImageViewBtn -> BarStatusImageViewActivity.start(this)
            R.id.barStatusSetCustomBtn -> BarStatusCustomActivity.start(this)
            R.id.barStatusSetFragmentBtn -> BarStatusFragmentActivity.start(this)
            R.id.barStatusSetSwipeBackBtn -> BarStatusSwipeBackActivity.start(this)
            R.id.barStatusSetDrawerBtn -> BarStatusDrawerActivity.start(this)
            R.id.barNotificationAboutBtn -> BarNotificationActivity.start(this)
            R.id.barNavAboutBtn -> BarNavActivity.start(this)
        }
    }
}
