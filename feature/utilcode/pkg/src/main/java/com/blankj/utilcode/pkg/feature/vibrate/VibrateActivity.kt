package com.blankj.utilcode.pkg.feature.vibrate

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.VibrateUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/12/29
 * desc  : demo about VibrateUtils
 * ```
 */
class VibrateActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, VibrateActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_vibrate
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.vibrate_1000ms) { VibrateUtils.vibrate(1000) },
                CommonItemClick(R.string.vibrate_custom) {
                    VibrateUtils.vibrate(longArrayOf(0, 1000, 1000, 2000, 2000, 1000), 1)
                },
                CommonItemClick(R.string.vibrate_cancel) { VibrateUtils.cancel() }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        VibrateUtils.cancel()
    }
}
