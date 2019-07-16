package com.blankj.subutil.pkg.feature.country

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.CountryUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_country.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about PinyinUtils
 * ```
 */
class CountryActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CountryActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_country)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_country
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        SpanUtils.with(countryAboutTv)
                .appendLine("getCountryCodeBySim: " + CountryUtils.getCountryCodeBySim("Default"))
                .appendLine("getCountryCodeByLanguage: " + CountryUtils.getCountryCodeByLanguage("Default"))
                .appendLine("getCountryBySim: " + CountryUtils.getCountryBySim())
                .appendLine("getCountryByLanguage: " + CountryUtils.getCountryByLanguage())
                .create()
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {}
}
