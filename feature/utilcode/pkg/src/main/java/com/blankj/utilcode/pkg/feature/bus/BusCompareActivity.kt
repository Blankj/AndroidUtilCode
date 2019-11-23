package com.blankj.utilcode.pkg.feature.bus

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ThreadUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/07/14
 * desc  : demo about BusUtils
 * ```
 */
class BusCompareActivity : CommonActivity() {

    private val titleItem: CommonItemTitle = CommonItemTitle("", true)

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BusCompareActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_bus
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                titleItem,
                CommonItemClick(R.string.bus_compare_register_10000_times) {
                    compareRegister10000Times()
                },
                CommonItemClick(R.string.bus_compare_post_to_1_subscriber_1000000_times) {
                    comparePostTo1Subscriber1000000Times()
                },
                CommonItemClick(R.string.bus_compare_post_to_100_subscriber_100000_times) {
                    comparePostTo100Subscribers100000Times()
                },
                CommonItemClick(R.string.bus_compare_unregister_10000_times) {
                    compareUnregister10000Times()
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ThreadUtils.cancel(ThreadUtils.getCpuPool())
    }

    /**
     * 注册 10000 个订阅者，共执行 10 次取平均值
     */
    private fun compareRegister10000Times() {
        val eventBusTests = CopyOnWriteArrayList<BusEvent>()
        val busUtilsTests = CopyOnWriteArrayList<BusEvent>()

        compareWithEventBus("Register 10000 times.", 10, 10000, object : CompareCallback {
            override fun runEventBus() {
                val test = BusEvent()
                EventBus.getDefault().register(test)
                eventBusTests.add(test)
            }

            override fun runBusUtils() {
                val test = BusEvent()
                BusUtils.register(test)
                busUtilsTests.add(test)
            }

            override fun restState() {
                for (test in eventBusTests) {
                    EventBus.getDefault().unregister(test)
                }
                eventBusTests.clear()

                for (test in busUtilsTests) {
                    BusUtils.unregister(test)
                }
                busUtilsTests.clear()
            }
        }, object : OnFinishCallback {
            override fun onFinish() {
                for (test in eventBusTests) {
                    EventBus.getDefault().unregister(test)
                }
                eventBusTests.clear()

                for (test in busUtilsTests) {
                    BusUtils.unregister(test)
                }
                busUtilsTests.clear()
            }
        })
    }

    /**
     * 向 1 个订阅者发送 * 1000000 次，共执行 10 次取平均值
     */
    private fun comparePostTo1Subscriber1000000Times() {
        comparePostTemplate("Post to 1 subscriber 1000000 times.", 1, 1000000)
    }

    /**
     * 向 100 个订阅者发送 * 100000 次，共执行 10 次取平均值
     */
    private fun comparePostTo100Subscribers100000Times() {
        comparePostTemplate("Post to 100 subscribers 100000 times.", 100, 100000)
    }

    private fun comparePostTemplate(name: String, subscribeNum: Int, postTimes: Int) {
        val tests = java.util.ArrayList<BusEvent>()
        for (i in 0 until subscribeNum) {
            val test = BusEvent()
            EventBus.getDefault().register(test)
            BusUtils.register(test)
            tests.add(test)
        }

        compareWithEventBus(name, 10, postTimes, object : CompareCallback {
            override fun runEventBus() {
                EventBus.getDefault().post("EventBus")
            }

            override fun runBusUtils() {
                BusUtils.post("busUtilsFun", "BusUtils")
            }

            override fun restState() {

            }
        }, object : OnFinishCallback {
            override fun onFinish() {
                for (test in tests) {
                    EventBus.getDefault().unregister(test)
                    BusUtils.unregister(test)
                }
            }
        })
    }

    /**
     * 注销 10000 个订阅者，共执行 10 次取平均值
     */
    private fun compareUnregister10000Times() {
        val tests = ArrayList<BusEvent>()
        for (i in 0..9999) {
            val test = BusEvent()
            EventBus.getDefault().register(test)
            BusUtils.register(test)
            tests.add(test)
        }

        compareWithEventBus("Unregister 10000 times.", 10, 1, object : CompareCallback {
            override fun runEventBus() {
                for (test in tests) {
                    EventBus.getDefault().unregister(test)
                }
            }

            override fun runBusUtils() {
                for (test in tests) {
                    BusUtils.unregister(test)
                }
            }

            override fun restState() {
                for (test in tests) {
                    EventBus.getDefault().register(test)
                    BusUtils.register(test)
                }
            }
        }, object : OnFinishCallback {
            override fun onFinish() {
                for (test in tests) {
                    EventBus.getDefault().unregister(test)
                    BusUtils.unregister(test)
                }
            }
        })
    }

    /**
     * @param name             传入的测试函数名
     * @param sampleSize       样本数
     * @param times            每次执行的次数
     * @param callback         比较的回调函数
     * @param onFinishCallback 执行结束的回调
     */
    private fun compareWithEventBus(name: String, sampleSize: Int, times: Int,
                                    callback: CompareCallback, onFinishCallback: OnFinishCallback) {
        showLoading()
        ThreadUtils.executeByCpu(object : ThreadUtils.Task<String>() {
            override fun doInBackground(): String {
                val dur = Array(2) { LongArray(sampleSize) }
                for (i in 0 until sampleSize) {
                    var cur = System.currentTimeMillis()
                    for (j in 0 until times) {
                        callback.runEventBus()
                    }
                    dur[0][i] = System.currentTimeMillis() - cur
                    cur = System.currentTimeMillis()
                    for (j in 0 until times) {
                        callback.runBusUtils()
                    }
                    dur[1][i] = System.currentTimeMillis() - cur
                    callback.restState()
                }
                var eventBusAverageTime: Long = 0
                var busUtilsAverageTime: Long = 0
                for (i in 0 until sampleSize) {
                    eventBusAverageTime += dur[0][i]
                    busUtilsAverageTime += dur[1][i]
                }
                return name +
                        "\nEventBusCostTime: " + eventBusAverageTime / sampleSize +
                        "\nBusUtilsCostTime: " + busUtilsAverageTime / sampleSize;
            }

            override fun onSuccess(result: String?) {
                onFinishCallback.onFinish()
                dismissLoading()
                titleItem?.title = result
            }

            override fun onCancel() {
                onFinishCallback.onFinish()
                dismissLoading()
            }

            override fun onFail(t: Throwable?) {
                onFinishCallback.onFinish()
                dismissLoading()
            }
        })
    }
}

interface CompareCallback {
    fun runEventBus()

    fun runBusUtils()

    fun restState()
}

interface OnFinishCallback {
    fun onFinish()
}

class BusEvent {
    @Subscribe
    fun eventBusFun(param: String) {
    }

    @BusUtils.Bus(tag = "busUtilsFun")
    fun busUtilsFun(param: String) {
    }
}
