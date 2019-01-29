package com.blankj.utilcode.pkg.feature.rom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.RomUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_rom.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/29
 * desc  : demo about RomUtils
 * ```
 */
class RomActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, RomActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_rom
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_rom)

        SpanUtils.with(romAboutTv)
                .append("getRomInfo: " + RomUtils.getRomInfo())
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }
}
