package com.blankj.lib.base

import android.util.Log

/**
 * ```
 * author: blankj
 * blog  : http://blankj.com
 * time  : 2018/03/23
 * desc  : base about lazy fragment
 * ```
 */
abstract class BaseLazyFragment : BaseFragment() {

    companion object {
        private const val TAG = "BaseLazyFragment"
    }

    private var isDataLoaded: Boolean = false

    abstract fun doLazyBusiness()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.d(TAG, "setUserVisibleHint: $isVisibleToUser")
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isDataLoaded) {
            doLazyBusiness()
            isDataLoaded = true
        }
    }

    override fun doBusiness() {
        if (userVisibleHint && !isDataLoaded) {
            doLazyBusiness()
            isDataLoaded = true
        }
    }
}
