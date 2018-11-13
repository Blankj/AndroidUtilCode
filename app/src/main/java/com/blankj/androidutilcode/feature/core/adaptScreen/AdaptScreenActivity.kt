package com.blankj.androidutilcode.feature.core.adaptScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import kotlinx.android.synthetic.main.activity_adapt_screen.*

class AdaptScreenActivity : BaseBackActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AdaptScreenActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_adapt_screen;
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        adaptWidthBtn.setOnClickListener { WidthActivity.start(this) }
        adaptHeightBtn.setOnClickListener { HeightActivity.start(this) }
        closeAdaptBtn.setOnClickListener { CloseAdaptActivity.start(this) }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View?) {}
}
