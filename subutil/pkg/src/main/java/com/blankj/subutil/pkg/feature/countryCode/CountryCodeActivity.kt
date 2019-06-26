package com.blankj.subutil.pkg.feature.countryCode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.common.CommonTitleActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.CountryUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_country_code.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about PinyinUtils
 * ```
 */
class CountryCodeActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CountryCodeActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_country_code)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_country_code
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        SpanUtils.with(countryCodeAboutTv)
                .appendLine("getCountryByLanguage: " + CountryUtils.getCountryByLanguage())
                .appendLine("getCountryBySim: " + CountryUtils.getCountryBySim())
                .appendLine("getCountryCodeByLanguage: " + CountryUtils.getCountryCodeByLanguage("Default"))
                .appendLine("getCountryCodeBySim: " + CountryUtils.getCountryCodeBySim("Default"))
                .create()
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {}
}
