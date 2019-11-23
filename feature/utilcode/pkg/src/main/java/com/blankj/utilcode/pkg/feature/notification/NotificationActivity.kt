package com.blankj.utilcode.pkg.feature.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.NotificationUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/10/22
 * desc  : demo about NotificationUtils
 * ```
 */
class NotificationActivity : CommonActivity() {

    private var id: Int = 0
    private var cancelId: Int = 0

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, NotificationActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_notification
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("areNotificationsEnabled", NotificationUtils.areNotificationsEnabled().toString()),
                CommonItemClick(R.string.notification_notify) {
                    NotificationUtils.notify(id++) { param ->
                        intent.putExtra("id", id);
                        param.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("title")
                                .setContentText("content text: $id")
                                .setContentIntent(PendingIntent.getActivity(mActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                                .setAutoCancel(true)
                        null
                    }
                },
                CommonItemClick(R.string.notification_cancel) {
                    if (cancelId < id) {
                        NotificationUtils.cancel(cancelId++)
                    } else {
                        ToastUtils.showShort("No notification.")
                    }
                },
                CommonItemClick(R.string.notification_cancel_all) {
                    NotificationUtils.cancelAll()
                    cancelId = id;
                },
                CommonItemClick(R.string.notification_show) {
                    NotificationUtils.setNotificationBarVisibility(true)
                }
        )
    }
}
