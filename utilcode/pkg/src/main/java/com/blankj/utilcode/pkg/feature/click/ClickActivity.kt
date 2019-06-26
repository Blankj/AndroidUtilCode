package com.blankj.utilcode.pkg.feature.click

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ClickUtils
import kotlinx.android.synthetic.main.activity_click.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about CleanUtils
 * ```
 */
class ClickActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ClickActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_click)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_click
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        ClickUtils.applyScale(clickScaleDefaultBtn)
        ClickUtils.applyScale(arrayOf(clickScaleCustomBtn), floatArrayOf(-0.5f))
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {}
}
