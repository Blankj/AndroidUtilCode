package com.blankj.utilcode.pkg.feature.bus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Keep
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.Utils
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

    @BusUtils.Bus(tag = TAG_BUS)
    fun test(param: String) {
        busAboutTv.text = param
    }

    @BusUtils.Bus(tag = TAG_STICKY_BUS, sticky = true)
    fun testSticky(callback: Callback?) {
        busAboutTv.text = callback?.call()
    }

    @BusUtils.Bus(tag = TAG_IO, threadMode = BusUtils.ThreadMode.IO)
    fun testIo() {
        val currentThread = Thread.currentThread().toString()
        Utils.runOnUiThread(Runnable {
            busAboutTv.text = currentThread
        })
    }

    companion object {
        const val TAG_BUS = "tag_bus"
        const val TAG_STICKY_BUS = "tag_sticky_bus"
        const val TAG_IO = "tag_io"

        fun start(context: Context) {
            val starter = Intent(context, BusActivity::class.java)
            context.startActivity(starter)
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
        applyDebouncingClickListener(
                busRegister,
                busUnregister,
                busPost,
                busPostSticky,
                busPost2IoThread,
                busRemoveSticky,
                busStartCompare
        )
    }

    override fun doBusiness() {

    }

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.busRegister -> {
                BusUtils.register(this)
            }
            R.id.busUnregister -> {
                BusUtils.unregister(this)
                busAboutTv.text = ""
            }
            R.id.busPost -> {
                BusUtils.post(TAG_BUS, TAG_BUS)
            }
            R.id.busPostSticky -> {
                BusUtils.postSticky(TAG_STICKY_BUS, object : Callback {
                    override fun call(): String {
                        return TAG_STICKY_BUS
                    }
                })
            }
            R.id.busPost2IoThread -> {
                BusUtils.post(TAG_IO)
            }
            R.id.busRemoveSticky -> {
                BusUtils.removeSticky(TAG_STICKY_BUS)
            }
            R.id.busStartCompare -> {
                BusCompareActivity.start(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BusUtils.removeSticky(TAG_STICKY_BUS)
        BusUtils.unregister(this)
    }

    @Keep
    interface Callback {
        fun call(): String
    }
}
