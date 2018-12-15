package com.blankj.lib.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View

/**
 * ```
 * author: blankj
 * blog  : http://blankj.com
 * time  : 2018/11/16
 * desc  : base about activity
 * ```
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {

    protected lateinit var mContentView: View
    protected lateinit var mActivity: Activity
    private var lastClick: Long = 0// the time of last click
    private val isFastClick: Boolean
        get() {
            val now = System.currentTimeMillis()
            if (now - lastClick >= 200) {
                lastClick = now
                return false
            }
            return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivity = this
        super.onCreate(savedInstanceState)
        initData(intent.extras)
        setRootLayout(bindLayout())
        initView(savedInstanceState, mContentView)
        doBusiness()
    }

    override fun setRootLayout(layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    override fun onClick(view: View) {
        if (!isFastClick) onWidgetClick(view)
    }
}