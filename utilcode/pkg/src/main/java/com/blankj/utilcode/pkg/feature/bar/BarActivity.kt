package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
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
class BarActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_bar)

        statusBarAboutBtn.setOnClickListener(this)
        statusBarSetColorBtn.setOnClickListener(this)
        statusBarSetAlphaBtn.setOnClickListener(this)
        statusBarSetImageViewBtn.setOnClickListener(this)
        statusBarSetCustomBtn.setOnClickListener(this)
        statusBarSetFragmentBtn.setOnClickListener(this)
        statusBarSetSwipeBackBtn.setOnClickListener(this)
        statusBarSetDrawerBtn.setOnClickListener(this)
        notificationBarAboutBtn.setOnClickListener(this)
        navBarAboutBtn.setOnClickListener(this)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.statusBarAboutBtn -> BarStatusActivity.start(this)
            R.id.statusBarSetColorBtn -> BarStatusColorActivity.start(this)
            R.id.statusBarSetAlphaBtn -> BarStatusAlphaActivity.start(this)
            R.id.statusBarSetImageViewBtn -> BarStatusImageViewActivity.start(this)
            R.id.statusBarSetCustomBtn -> BarStatusCustomActivity.start(this)
            R.id.statusBarSetFragmentBtn -> BarStatusFragmentActivity.start(this)
            R.id.statusBarSetSwipeBackBtn -> BarStatusSwipeBackActivity.start(this)
            R.id.statusBarSetDrawerBtn -> BarStatusDrawerActivity.start(this)
            R.id.notificationBarAboutBtn -> BarNotificationActivity.start(this)
            R.id.navBarAboutBtn -> BarNavActivity.start(this)
        }
    }
}
