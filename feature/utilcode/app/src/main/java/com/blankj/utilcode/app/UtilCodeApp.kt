package com.blankj.utilcode.app

import com.blankj.common.CommonApplication
import com.blankj.utilcode.util.Utils


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/12
 * desc  : app about utils
 * ```
 */
class UtilCodeApp : CommonApplication() {

    companion object {
        lateinit var instance: UtilCodeApp
            private set
    }

    override fun onCreate() {
        Utils.init(this)
        super.onCreate()
        instance = this
//        BusUtils.register("com.blankj.androidutilcode")
    }
}


