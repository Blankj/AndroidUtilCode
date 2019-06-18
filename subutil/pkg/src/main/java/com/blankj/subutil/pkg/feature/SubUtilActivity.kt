package com.blankj.subutil.pkg.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.common.CommonTitleActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.pkg.feature.appStore.AppStoreActivity
import com.blankj.subutil.pkg.feature.countryCode.CountryCodeActivity
import com.blankj.subutil.pkg.feature.location.LocationActivity
import com.blankj.subutil.pkg.feature.pinyin.PinyinActivity
import com.blankj.subutil.util.AppStoreUtils
import com.blankj.utilcode.util.BusUtils
import kotlinx.android.synthetic.main.activity_util_sub.*
import kotlinx.android.synthetic.main.activity_util_sub.view.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
 * ```
 */
class SubUtilActivity : CommonTitleActivity() {

    companion object {
        @BusUtils.Subscribe(name = "SubUtilActivity#start")
        fun start(context: Context) {
            val starter = Intent(context, SubUtilActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.sub_util)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_util_sub
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                subUtilAppStoreBtn,
                subUtilCountryCodeBtn,
                subUtilLocationBtn,
                subUtilPinyinBtn
        )
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when(view.id) {
            R.id.subUtilAppStoreBtn -> AppStoreActivity.start(this)
            R.id.subUtilCountryCodeBtn -> CountryCodeActivity.start(this)
            R.id.subUtilLocationBtn -> LocationActivity.start(this)
            R.id.subUtilPinyinBtn -> PinyinActivity.start(this)
        }
    }
}
