package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AdaptScreenUtils
import kotlinx.android.synthetic.main.activity_adaptscreen_height.*

class HeightActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, HeightActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun isSwipeBack(): Boolean {
        return true
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_adaptscreen_height
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        adaptScreenHeightWebView.setBackgroundColor(Color.parseColor("#f0d26d"))
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1920)
    }
}
