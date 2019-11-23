package com.blankj.utilcode.pkg.feature.bar.notification

import android.content.Context
import android.content.Intent
import android.os.Handler
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about BarUtils
 * ```
 */
class BarNotificationActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarNotificationActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val mHandler = Handler()

    override fun bindTitleRes(): Int {
        return R.string.demo_bar
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.bar_notification_show) {
                    BarUtils.setNotificationBarVisibility(true)
                    mHandler.postDelayed({ BarUtils.setNotificationBarVisibility(false) }, 2000)
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
