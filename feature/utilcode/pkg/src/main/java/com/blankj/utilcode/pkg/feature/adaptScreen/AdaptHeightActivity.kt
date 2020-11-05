package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.blankj.common.activity.CommonActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils

class AdaptHeightActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AdaptHeightActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindLayout(): Int {
        return R.layout.adaptscreen_height_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e(BarUtils.getStatusBarHeight())
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1920)
    }
}
