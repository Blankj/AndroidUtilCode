package com.blankj.utilcode.pkg.feature.language

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.CoreUtilActivity
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.ToastUtils
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
                CommonItemClick(R.string.language_app_context) {
                    ToastUtils.showLong(R.string.language)
                },
                CommonItemClick(R.string.language_activity_context) {
                    ToastUtils.showLong(getString(R.string.language))
                },
                CommonItemClick(R.string.language_apply_simple_chinese) {
                    LanguageUtils.applyLanguage(Locale.SIMPLIFIED_CHINESE, CoreUtilActivity::class.java)
                },
                CommonItemClick(R.string.language_apply_american) {
                    LanguageUtils.applyLanguage(Locale.US, "")
                },
                CommonItemClick(R.string.language_apply_system) {
                    LanguageUtils.applySystemLanguage("")
                }
        )
    }
}
