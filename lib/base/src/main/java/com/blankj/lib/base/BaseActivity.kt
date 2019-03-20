package com.blankj.lib.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.AntiShakeUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.r0adkll.slidr.Slidr

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

    abstract fun isSwipeBack(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivity = this
        super.onCreate(savedInstanceState)
        initData(intent.extras)
        setRootLayout(bindLayout())
        initView(savedInstanceState, mContentView)
        doBusiness()

        if (isSwipeBack()) {
            Slidr.attach(this)
        }
        AppUtils.registerAppStatusChangedListener(this, object : Utils.OnAppStatusChangedListener {
            override fun onForeground() {
                ToastUtils.showShort("foreground")
            }

            override fun onBackground() {
                ToastUtils.showShort("background")
            }
        })
    }

    override fun setRootLayout(layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    override fun onClick(view: View) {
        if (AntiShakeUtils.isValid(view)) {
            onWidgetClick(view)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppUtils.unregisterAppStatusChangedListener(this)
    }
}