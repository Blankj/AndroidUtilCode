package com.blankj.utilcode.pkg.feature.reflect

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ReflectUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/29
 * desc  : demo about ReflectUtils
 * ```
 */
class ReflectActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ReflectActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_reflect
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("source value", TestPrivateStaticFinal.STR),
                CommonItemTitle("reflect get", ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("STR").get<String>()),
                CommonItemTitle("after reflect get", ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("STR", "reflect success").field("STR").get<String>()),
                CommonItemTitle("source value", TestPrivateStaticFinal.STR)
        )
    }
}