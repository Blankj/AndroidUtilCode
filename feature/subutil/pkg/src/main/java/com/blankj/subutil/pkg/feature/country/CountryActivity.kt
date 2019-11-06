package com.blankj.subutil.pkg.feature.country

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.CountryUtils
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about Country
 * ```
 */
class CountryActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CountryActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_country
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("getCountryCodeBySim", CountryUtils.getCountryCodeBySim("Default")),
                CommonItemTitle("getCountryCodeByLanguage", CountryUtils.getCountryCodeByLanguage("Default")),
                CommonItemTitle("getCountryBySim", CountryUtils.getCountryBySim()),
                CommonItemTitle("getCountryByLanguage", CountryUtils.getCountryByLanguage())
        )
    }
}
