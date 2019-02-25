package com.blankj.utilcode.pkg.feature.sdcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_sdcard.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : demo about SDCardUtils
 * ```
 */
class SDCardActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SDCardActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_sdcard)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_sdcard
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        SpanUtils.with(sdcardAboutTv)
                .appendLine("isSDCardEnableByEnvironment: " + SDCardUtils.isSDCardEnableByEnvironment())
                .appendLine("getSDCardPathByEnvironment: " + SDCardUtils.getSDCardPathByEnvironment())
                .appendLine("getSDCardInfo: " + SDCardUtils.getSDCardInfo())
                .create()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}
}
