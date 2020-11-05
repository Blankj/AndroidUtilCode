package com.blankj.utilcode.pkg.feature.rom

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.RomUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/29
 * desc  : demo about RomUtils
 * ```
 */
class RomActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, RomActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_rom
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        val romInfo = RomUtils.getRomInfo()
        return CollectionUtils.newArrayList(
                CommonItemTitle("Rom Name", romInfo.name),
                CommonItemTitle("Rom Version", romInfo.version)
        )
    }
}
