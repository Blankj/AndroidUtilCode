package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AdaptScreenUtils
import kotlinx.android.synthetic.main.activity_adapt_height.*

class HeightActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, HeightActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapt_height)

        heightVebView.setBackgroundColor(Color.parseColor("#f0d26d"))
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1920)
    }
}
