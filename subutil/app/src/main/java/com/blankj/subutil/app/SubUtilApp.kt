package com.blankj.subutil.app

import android.content.Context

import com.blankj.lib.base.BaseApplication


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/12
 * desc  : app about utils
 * ```
 */
class SubUtilApp : BaseApplication() {

    companion object {
        var instance: SubUtilApp? = null
            private set
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}


