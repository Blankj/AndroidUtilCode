package com.blankj.utilcode.pkg.feature.bus

import android.content.Context
import android.content.Intent
import android.support.annotation.Keep
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.Utils
import kotlin.random.Random


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about BusUtils
 * ```
 */
class BusActivity : CommonActivity() {

    private val titleItem: CommonItemTitle = CommonItemTitle("", true);

    @BusUtils.Bus(tag = TAG_BASIC_TYPE)
    fun test(param: Int) {
        titleItem.title = param.toString()
    }

    @BusUtils.Bus(tag = TAG_BUS, priority = 5)
    fun test(param: String) {
        titleItem.title = param
    }

    @BusUtils.Bus(tag = TAG_BUS, priority = 1)
    fun testSameTag(param: String) {
        if (titleItem.title.toString() == TAG_BUS) {
            titleItem.title = "${titleItem.title} * 2"
        }
    }

    @BusUtils.Bus(tag = TAG_STICKY_BUS, sticky = true)
    fun testSticky(callback: Callback) {
        titleItem.title = callback.call()
    }

    @BusUtils.Bus(tag = TAG_IO, threadMode = BusUtils.ThreadMode.IO)
    fun testIo() {
        val currentThread = Thread.currentThread().toString()
        Utils.runOnUiThread(Runnable {
            titleItem.title = currentThread
        })
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

    override fun bindTitleRes(): Int {
        return R.string.demo_bus
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                titleItem,
                CommonItemClick(R.string.bus_register) {
                    BusUtils.register(this)
                },
                CommonItemClick(R.string.bus_unregister) {
                    BusUtils.unregister(this)
                },
                CommonItemClick(R.string.bus_post) {
                    BusUtils.post(TAG_BUS, TAG_BUS)
                },
                CommonItemClick(R.string.bus_post_basic_type) {
                    BusUtils.post(TAG_BASIC_TYPE, Random(System.currentTimeMillis()).nextInt())
                },
                CommonItemClick(R.string.bus_post_sticky) {
                    BusUtils.postSticky(TAG_STICKY_BUS, object : Callback {
                        override fun call(): String {
                            return TAG_STICKY_BUS
                        }
                    })
                },
                CommonItemClick(R.string.bus_post_to_io_thread) {
                    BusUtils.post(TAG_IO)
                },
                CommonItemClick(R.string.bus_remove_sticky) {
                    BusUtils.removeSticky(TAG_STICKY_BUS)
                },
                CommonItemClick(R.string.bus_start_compare, true) {
                    BusCompareActivity.start(this)
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        BusUtils.removeSticky(TAG_STICKY_BUS)
        BusUtils.unregister(this)
    }
}

@Keep
interface Callback {
    fun call(): String
}
