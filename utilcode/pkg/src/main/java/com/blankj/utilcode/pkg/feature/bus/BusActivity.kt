package com.blankj.utilcode.pkg.feature.bus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_bus.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about BusUtils
 * ```
 */
class BusActivity : CommonTitleActivity() {

    companion object {
        const val BUS_KEY = "BusActivity"

        @BusUtils.Subscribe(name = "BusActivity#start")
        fun start(context: Context) {
            val starter = Intent(context, BusActivity::class.java)
            context.startActivity(starter)
        }

        val BUNDLE = Bundle()

        init {
            BUNDLE.putString("activity", "BusActivity")
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_bus)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_bus
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        busStartRemoteBtn.setOnClickListener(this)
        busPostBtn.setOnClickListener(this)
    }

    override fun doBusiness() {
        BusUtils.subscribe(BUS_KEY, object : BusUtils.MessageCallback {
            override fun onMsgCallBack(data: Bundle?) {
                LogUtils.eTag("BusUtils", data)
            }
        })
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.busStartRemoteBtn -> BusRemoteActivity.start(this)
            R.id.busPostBtn -> {
                BusUtils.post(BUS_KEY, BUNDLE)
                BusUtils.post(BusRemoteActivity.BUS_KEY, BusRemoteActivity.BUNDLE)
            }
        }
    }
}
