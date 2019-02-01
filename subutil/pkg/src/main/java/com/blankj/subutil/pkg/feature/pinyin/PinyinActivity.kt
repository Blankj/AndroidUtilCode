package com.blankj.subutil.pkg.feature.pinyin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.PinyinUtils
import kotlinx.android.synthetic.main.activity_pinyin.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about PinyinUtils
 * ```
 */
class PinyinActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PinyinActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_pinyin)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_pinyin
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        val surnames = "乐乘乜仇会便区单参句召员宓弗折曾朴查洗盖祭种秘繁缪能蕃覃解谌适都阿难黑"
        val size = surnames.length
        val sb = StringBuilder("汉字转拼音: " + PinyinUtils.ccs2Pinyin("汉字转拼音", " ")
                + "\n获取首字母: " + PinyinUtils.getPinyinFirstLetters("获取首字母", " ")
                + "\n\n测试姓氏"
                + "\n澹台: " + PinyinUtils.getSurnamePinyin("澹台")
                + "\n尉迟: " + PinyinUtils.getSurnamePinyin("尉迟")
                + "\n万俟: " + PinyinUtils.getSurnamePinyin("万俟")
                + "\n单于: " + PinyinUtils.getSurnamePinyin("单于"))
        for (i in 0 until size) {
            val surname = surnames[i].toString()
            sb.append(String.format(
                    "\n%s 正确: %-6s 错误: %-6s",
                    surname,
                    PinyinUtils.getSurnamePinyin(surname),
                    PinyinUtils.ccs2Pinyin(surname)
            ))
        }
        pinyinAboutTv.text = sb.toString()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}
}
