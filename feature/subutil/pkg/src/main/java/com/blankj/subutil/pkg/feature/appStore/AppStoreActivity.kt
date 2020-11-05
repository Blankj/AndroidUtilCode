package com.blankj.subutil.pkg.feature.appStore

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.AppStoreUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about AppStore
 * ```
 */
class AppStoreActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AppStoreActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_app_store
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.app_store_system, true) {
                    AppStoreUtils.getAppStoreIntent("com.tencent.mm").apply {
                        ActivityUtils.startActivity(this)
                    }
                }
        )
    }
}
