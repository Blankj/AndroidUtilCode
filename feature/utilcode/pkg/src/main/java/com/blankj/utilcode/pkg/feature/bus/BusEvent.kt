package com.blankj.utilcode.pkg.feature.bus

import com.blankj.utilcode.util.BusUtils
import org.greenrobot.eventbus.Subscribe

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/07/14
 * desc  : demo about BusUtils
 * ```
 */
class BusEvent {
    @Subscribe
    fun eventBusFun(param: String) {
    }

    @BusUtils.Bus(tag = "busUtilsFun")
    fun busUtilsFun(param: String) {
    }
}