package com.blankj.subutil.pkg.feature.pinyin

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.PinyinUtils
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about PinyinUtils
 * ```
 */
class PinyinActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PinyinActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_pinyin
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        val surnames = "乐乘乜仇会便区单参句召员宓弗折曾朴查洗盖祭种秘繁缪能蕃覃解谌适都阿难黑"
        val size = surnames.length
        val sb = StringBuilder("澹台: " + PinyinUtils.getSurnamePinyin("澹台")
                + "\n尉迟: " + PinyinUtils.getSurnamePinyin("尉迟")
                + "\n万俟: " + PinyinUtils.getSurnamePinyin("万俟")
                + "\n单于: " + PinyinUtils.getSurnamePinyin("单于"))
        for (i in 0 until size) {
            val surname = surnames[i].toString()
            sb.append(String.format(
                    "\n%s 正确: %-8s 错误: %-8s",
                    surname,
                    PinyinUtils.getSurnamePinyin(surname),
                    PinyinUtils.ccs2Pinyin(surname)
            ))
        }
        return CollectionUtils.newArrayList(
                CommonItemTitle("汉字转拼音", PinyinUtils.ccs2Pinyin("汉字转拼音", " ")),
                CommonItemTitle("获取首字母", PinyinUtils.getPinyinFirstLetters("获取首字母", " ")),
                CommonItemTitle("测试姓氏", sb.toString())

        )
    }
}