package com.blankj.utilcode.pkg.feature.adaptScreen

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils

class AdaptScreenActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AdaptScreenActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_adapt_screen
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.adaptScreen_adapt_width, true) {
                    AdaptWidthActivity.start(this)
                },
                CommonItemClick(R.string.adaptScreen_adapt_height, true) {
                    AdaptHeightActivity.start(this)
                },
                CommonItemClick(R.string.adaptScreen_adapt_close, true) {
                    AdaptCloseActivity.start(this)
                }
        )
    }
}
