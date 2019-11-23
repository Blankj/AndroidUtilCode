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

class AdaptCloseActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AdaptCloseActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindLayout(): Int {
        return R.layout.adaptscreen_close_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.closeAdapt(super.getResources())
    }
}
