package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import kotlinx.android.synthetic.main.activity_adaptscreen.*

class AdaptScreenActivity : CommonTitleActivity() {

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

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                adaptScreenAdaptWidthBtn,
                adaptScreenAdaptHeightBtn,
                adaptScreenCloseAdaptBtn
        )
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.adaptScreenAdaptWidthBtn -> AdaptWidthActivity.start(this)
            R.id.adaptScreenAdaptHeightBtn -> AdaptHeightActivity.start(this)
            R.id.adaptScreenCloseAdaptBtn -> AdaptCloseActivity.start(this)
        }
    }
}
