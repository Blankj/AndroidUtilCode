package com.blankj.utilcode.pkg.feature.bus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Keep
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_bus.*
import kotlin.random.Random


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about BusUtils
 * ```
 */
class BusActivity : CommonTitleActivity() {

    @BusUtils.Bus(tag = TAG_BASIC_TYPE)
    fun test(param: Int) {
        busAboutTv.text = param.toString()
    }

    @BusUtils.Bus(tag = TAG_BUS)
    fun test(param: String) {
        busAboutTv.text = param
    }

    @SuppressLint("SetTextI18n")
    @BusUtils.Bus(tag = TAG_BUS, priority = 1)
    fun testSameTag(param: String) {
        if (busAboutTv.text.toString().equals(TAG_BUS)) {
            busAboutTv.text = "${busAboutTv.text} * 2"
        }
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

    @BusUtils.Bus(tag = "_PusNotifyData", sticky = true, threadMode = BusUtils.ThreadMode.MAIN)
    fun onPusNotifyData(pusNotifyData: PusNotifyData) {
        LogUtils.e("haha")
    }

    companion object {
        const val TAG_BASIC_TYPE = "tag_basic_type"
        const val TAG_BUS = "tag_bus"
        const val TAG_STICKY_BUS = "tag_sticky_bus"
        const val TAG_IO = "tag_io"

        fun start(context: Context) {
            val starter = Intent(context, BusActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(com.blankj.utilcode.pkg.R.string.demo_bus)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return com.blankj.utilcode.pkg.R.layout.activity_bus
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                busRegister,
                busUnregister,
                busPost,
                busPostBasicType,
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
            com.blankj.utilcode.pkg.R.id.busRegister -> {
                BusUtils.register(this)
            }
            com.blankj.utilcode.pkg.R.id.busUnregister -> {
                BusUtils.unregister(this)
                busAboutTv.text = ""
            }
            com.blankj.utilcode.pkg.R.id.busPost -> {
                BusUtils.post(TAG_BUS, TAG_BUS)
            }
            com.blankj.utilcode.pkg.R.id.busPostBasicType -> {
                BusUtils.post(TAG_BASIC_TYPE, Random(System.currentTimeMillis()).nextInt())
            }
            com.blankj.utilcode.pkg.R.id.busPostSticky -> {
                BusUtils.postSticky(TAG_STICKY_BUS, object : Callback {
                    override fun call(): String {
                        return TAG_STICKY_BUS
                    }
                })
            }
            com.blankj.utilcode.pkg.R.id.busPost2IoThread -> {
                BusUtils.post(TAG_IO)
            }
            com.blankj.utilcode.pkg.R.id.busRemoveSticky -> {
                BusUtils.removeSticky(TAG_STICKY_BUS)
            }
            com.blankj.utilcode.pkg.R.id.busStartCompare -> {
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
