package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.blankj.common.activity.CommonActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AdaptScreenUtils
import kotlinx.android.synthetic.main.adaptscreen_width_activity.*

class AdaptWidthActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AdaptWidthActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindLayout(): Int {
        return R.layout.adaptscreen_width_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        adaptScreenWidthWebView.setBackgroundColor(Color.parseColor("#f0d26d"))
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }
}
