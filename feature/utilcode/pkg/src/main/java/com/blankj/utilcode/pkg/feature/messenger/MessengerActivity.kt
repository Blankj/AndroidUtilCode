package com.blankj.utilcode.pkg.feature.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.MessengerUtils
import com.blankj.utilcode.util.SnackbarUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about MessengerUtils
 * ```
 */
class MessengerActivity : CommonActivity() {

    companion object {
        const val MESSENGER_KEY = "MessengerActivity"

        fun start(context: Context) {
            val starter = Intent(context, MessengerActivity::class.java)
            context.startActivity(starter)
            MessengerUtils.register()
        }

        val BUNDLE = Bundle()

        init {
            BUNDLE.putString(MESSENGER_KEY, "MessengerActivity")
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_messenger
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.messenger_post_to_main_server) {
                    MessengerUtils.post(MESSENGER_KEY, BUNDLE)
                },
                CommonItemClick(R.string.messenger_start_remote) {
                    MessengerRemoteActivity.start(this)
                }
        )
    }

    override fun doBusiness() {
        MessengerUtils.subscribe(MESSENGER_KEY) { data ->
            SnackbarUtils.with(mContentView)
                    .setMessage(data.getString(MESSENGER_KEY) ?: "")
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MessengerUtils.unregister()
    }
}
