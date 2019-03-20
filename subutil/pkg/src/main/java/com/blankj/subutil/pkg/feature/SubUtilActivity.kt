package com.blankj.subutil.pkg.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.pkg.feature.location.LocationActivity
import com.blankj.subutil.pkg.feature.pinyin.PinyinActivity
import com.blankj.utilcode.util.BusUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
 * ```
 */
class SubUtilActivity : BaseTitleBarActivity() {

    companion object {
        @BusUtils.Subscribe(name = "SubUtilActivity#start")
        fun start(context: Context) {
            val starter = Intent(context, SubUtilActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.sub_util)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_util_sub
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {}

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    fun locationClick(view: View) {
        LocationActivity.start(this)
    }

    fun pinyinClick(view: View) {
        PinyinActivity.start(this)
    }
}
