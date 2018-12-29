package com.blankj.lib.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.AntiShakeUtils

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
        if (AntiShakeUtils.isValid(view, 200)) {
            onWidgetClick(view)
        }
    }
}