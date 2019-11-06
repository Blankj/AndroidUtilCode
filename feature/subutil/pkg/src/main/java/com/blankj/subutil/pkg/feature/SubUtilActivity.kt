package com.blankj.subutil.pkg.feature

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.subutil.pkg.R
import com.blankj.subutil.pkg.feature.appStore.AppStoreActivity
import com.blankj.subutil.pkg.feature.country.CountryActivity
import com.blankj.subutil.pkg.feature.dangerous.DangerousActivity
import com.blankj.subutil.pkg.feature.location.LocationActivity
import com.blankj.subutil.pkg.feature.pinyin.PinyinActivity
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
 * ```
 */
class SubUtilActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SubUtilActivity::class.java)
            context.startActivity(starter)
        }
    }


    override fun bindTitleRes(): Int {
        return R.string.sub_util
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.demo_app_store, true) {
                    AppStoreActivity.start(this)
                },
                CommonItemClick(R.string.demo_country, true) {
                    CountryActivity.start(this)
                },
                CommonItemClick(R.string.demo_dangerous, true) {
                    DangerousActivity.start(this)
                },
                CommonItemClick(R.string.demo_location, true) {
                    LocationActivity.start(this)
                },
                CommonItemClick(R.string.demo_pinyin, true) {
                    PinyinActivity.start(this)
                }
        )
    }
}
