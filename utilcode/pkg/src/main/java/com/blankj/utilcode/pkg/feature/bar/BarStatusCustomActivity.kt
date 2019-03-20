package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/14
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusCustomActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusCustomActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun isSwipeBack(): Boolean {
        return true
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_custom
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {}

    override fun doBusiness() {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT).setBackgroundResource(R.drawable.bar_status_custom)
    }

    override fun onWidgetClick(view: View) {}
}
