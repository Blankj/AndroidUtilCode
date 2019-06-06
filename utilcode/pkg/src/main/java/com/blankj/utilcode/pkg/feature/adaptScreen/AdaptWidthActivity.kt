package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.blankj.lib.common.CommonBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AdaptScreenUtils
import kotlinx.android.synthetic.main.activity_adaptscreen_width.*

class AdaptWidthActivity : CommonBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AdaptWidthActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun isSwipeBack(): Boolean {
        return true
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_adaptscreen_width
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        adaptScreenWidthWebView.setBackgroundColor(Color.parseColor("#f0d26d"))
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }
}
