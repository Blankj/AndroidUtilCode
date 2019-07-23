package com.blankj.utilcode.pkg.feature.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/12/29
 * desc  : demo about VibrateUtils
 * ```
 */
class LanguageActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, LanguageActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_language)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_language
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                languageApp,
                languageActivity,
                applySimpleChineseLanguage,
                applyAmericanLanguage,
                applySystemLanguage
        )
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.languageApp -> ToastUtils.showLong(R.string.language)
            R.id.languageActivity -> ToastUtils.showLong(getString(R.string.language))
            R.id.applySimpleChineseLanguage -> LanguageUtils.applyLanguage(Locale.SIMPLIFIED_CHINESE, "com.blankj.main.pkg.MainActivity")
            R.id.applyAmericanLanguage -> LanguageUtils.applyLanguage(Locale.US, "")
            R.id.applySystemLanguage -> LanguageUtils.applySystemLanguage("")
        }
    }
}
