package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AdaptScreenUtils

class CloseAdaptActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CloseAdaptActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapt_close)
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.closeAdapt(super.getResources())
    }
}
