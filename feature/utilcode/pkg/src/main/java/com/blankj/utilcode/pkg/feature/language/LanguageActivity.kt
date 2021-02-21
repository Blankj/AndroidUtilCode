package com.blankj.utilcode.pkg.feature.language

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.blankj.utilcode.util.StringUtils
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/12/29
 * desc  : demo about VibrateUtils
 * ```
 */
class LanguageActivity : CommonActivity() {

    companion object {

        const val SP_KEY_IS_RELAUNCH_APP = "SP_KEY_IS_RELAUNCH_APP"

        fun start(context: Context) {
            val starter = Intent(context, LanguageActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_language
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("isAppliedLanguage", LanguageUtils.isAppliedLanguage().toString()),
                CommonItemTitle("isAppliedLanguage(SIMPLIFIED_CHINESE)", LanguageUtils.isAppliedLanguage(Locale.SIMPLIFIED_CHINESE).toString()),
                CommonItemTitle("getAppliedLanguage", (LanguageUtils.getAppliedLanguage() ?: "null").toString()),
                CommonItemTitle("getActivityContextLanguage", LanguageUtils.getContextLanguage(this).toString()),
                CommonItemTitle("getAppContextLanguage", LanguageUtils.getAppContextLanguage().toString()),
                CommonItemTitle("getSystemLanguage", LanguageUtils.getSystemLanguage().toString()),
                CommonItemSwitch(
                        StringUtils.getString(R.string.language_relaunch_app),
                        { isRelaunchApp() },
                        { SPStaticUtils.put(SP_KEY_IS_RELAUNCH_APP, it) }
                ),
                CommonItemClick(R.string.language_apply_simple_chinese) {
                    LanguageUtils.applyLanguage(Locale.SIMPLIFIED_CHINESE, isRelaunchApp())
                },
                CommonItemClick(R.string.language_apply_american) {
                    LanguageUtils.applyLanguage(Locale.US, isRelaunchApp())
                },
                CommonItemClick(R.string.language_apply_english) {
                    LanguageUtils.applyLanguage(Locale.ENGLISH, isRelaunchApp())
                },
                CommonItemClick(R.string.language_apply_arabic) {
                    LanguageUtils.applyLanguage(Locale("ar"), isRelaunchApp())
                },
                CommonItemClick(R.string.language_apply_system) {
                    LanguageUtils.applySystemLanguage(isRelaunchApp())
                }
        )
    }

    private fun isRelaunchApp() = SPStaticUtils.getBoolean(SP_KEY_IS_RELAUNCH_APP)
}
