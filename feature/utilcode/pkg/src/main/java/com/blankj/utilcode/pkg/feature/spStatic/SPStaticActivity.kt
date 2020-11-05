package com.blankj.utilcode.pkg.feature.spStatic

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.SPStaticUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/08
 * desc  : demo about SPUtils
 * ```
 */
class SPStaticActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SPStaticActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_spStatic
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        val itemTitle = CommonItemTitle(sp2String(), true)
        return CollectionUtils.newArrayList(
                itemTitle,
                CommonItemClick(R.string.sp_put_string) {
                    SPStaticUtils.put("STRING", "string")
                    itemTitle.title = sp2String()
                },
                CommonItemClick(R.string.sp_put_int) {
                    SPStaticUtils.put("INT", 21)
                    itemTitle.title = sp2String()
                },
                CommonItemClick(R.string.sp_put_long) {
                    SPStaticUtils.put("LONG", java.lang.Long.MAX_VALUE)
                    itemTitle.title = sp2String()
                },
                CommonItemClick(R.string.sp_put_float) {
                    SPStaticUtils.put("FLOAT", Math.PI.toFloat())
                    itemTitle.title = sp2String()
                },
                CommonItemClick(R.string.sp_put_boolean) {
                    SPStaticUtils.put("BOOLEAN", true)
                    itemTitle.title = sp2String()
                },
                CommonItemClick(R.string.sp_put_string_set) {
                    SPStaticUtils.put("SET", setOf("1", "2"))
                    itemTitle.title = sp2String()
                },
                CommonItemClick(R.string.sp_clear) {
                    SPStaticUtils.clear()
                    itemTitle.title = sp2String()
                }
        )
    }

    private fun sp2String(): String {
        val sb = StringBuilder()
        val map = SPStaticUtils.getAll()
        if (map.isEmpty()) return ""
        for ((key, value) in map) {
            sb.append("\n")
                    .append(key)
                    .append(": ")
                    .append(value)

        }
        return sb.deleteCharAt(0).toString()
    }
}
