package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.bar.nav.BarNavActivity
import com.blankj.utilcode.pkg.feature.bar.notification.BarNotificationActivity
import com.blankj.utilcode.pkg.feature.bar.status.*
import com.blankj.utilcode.pkg.feature.bar.status.fragment.BarStatusFragmentActivity
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_bar
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle(R.string.bar_about_status_bar, true),
                CommonItemClick(R.string.bar_status_about, true) {
                    BarStatusActivity.start(this)
                },
                CommonItemClick(R.string.bar_status_set_color, true) {
                    BarStatusActivityColor.start(this)
                },
                CommonItemClick(R.string.bar_status_set_alpha, true) {
                    BarStatusActivityAlpha.start(this)
                },
                CommonItemClick(R.string.bar_status_set_image_view, true) {
                    BarStatusActivityImageView.start(this)
                },
                CommonItemClick(R.string.bar_status_set_custom, true) {
                    BarStatusActivityCustom.start(this)
                },
                CommonItemClick(R.string.bar_status_set_fragment, true) {
                    BarStatusFragmentActivity.start(this)
                },
                CommonItemClick(R.string.bar_status_set_drawer, true) {
                    BarStatusActivityDrawer.start(this)
                },
                CommonItemTitle(R.string.bar_about_notification_bar, true),
                CommonItemClick(R.string.bar_notification_about, true) {
                    BarNotificationActivity.start(this)
                },
                CommonItemTitle(R.string.bar_about_nav_bar, true),
                CommonItemClick(R.string.bar_nav_about, true) {
                    BarNavActivity.start(this)
                }
        )
    }
}
