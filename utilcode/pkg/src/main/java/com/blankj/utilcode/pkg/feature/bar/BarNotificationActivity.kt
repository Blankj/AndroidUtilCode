package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import kotlinx.android.synthetic.main.activity_bar_notification.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about BarUtils
 * ```
 */
class BarNotificationActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarNotificationActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val mHandler = Handler()

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_bar)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_bar_notification
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        barNotificationShowBtn.setOnClickListener(this)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barNotificationShowBtn -> {
                BarUtils.setNotificationBarVisibility(true)
                mHandler.postDelayed({ BarUtils.setNotificationBarVisibility(false) }, 2000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
