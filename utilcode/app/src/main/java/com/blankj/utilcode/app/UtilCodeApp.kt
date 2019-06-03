package com.blankj.utilcode.app

import com.blankj.lib.base.BaseApplication
import com.blankj.utilcode.util.Utils


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/12
 * desc  : app about utils
 * ```
 */
class UtilCodeApp : BaseApplication() {

    companion object {
        lateinit var instance: UtilCodeApp
            private set
    }

    override fun onCreate() {
        Utils.init(this)
        super.onCreate()
        instance = this
//        BusUtils.registerClient("com.blankj.androidutilcode")
    }
}


