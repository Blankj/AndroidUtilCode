package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleActivity
import com.blankj.utilcode.pkg.R
import kotlinx.android.synthetic.main.activity_adaptscreen.*

class AdaptScreenActivity : BaseTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AdaptScreenActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_adapt_screen)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_adaptscreen;
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        adaptScreenAdaptWidthBtn.setOnClickListener(this)
        adaptScreenAdaptHeightBtn.setOnClickListener(this)
        adaptScreenCloseAdaptBtn.setOnClickListener(this)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.adaptScreenAdaptWidthBtn -> WidthActivity.start(this)
            R.id.adaptScreenAdaptHeightBtn -> HeightActivity.start(this)
            R.id.adaptScreenCloseAdaptBtn -> CloseAdaptActivity.start(this)
        }
    }
}
