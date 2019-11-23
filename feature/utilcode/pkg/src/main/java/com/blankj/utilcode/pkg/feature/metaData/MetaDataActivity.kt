package com.blankj.utilcode.pkg.feature.metaData

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.MetaDataUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/05/15
 * desc  : demo about MetaDataUtils
 * ```
 */
class MetaDataActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MetaDataActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_meta_data
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("getMetaDataInApp", MetaDataUtils.getMetaDataInApp("app_meta_data")),
                CommonItemTitle("getMetaDataInActivity", MetaDataUtils.getMetaDataInActivity(this, "activity_meta_data"))
        )
    }
}
