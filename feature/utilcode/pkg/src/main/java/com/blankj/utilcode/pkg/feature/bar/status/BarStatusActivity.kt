package com.blankj.utilcode.pkg.feature.bar.status

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.Utils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_bar
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("getStatusBarHeight", BarUtils.getStatusBarHeight().toString()),
                CommonItemSwitch(
                        R.string.bar_status_visibility,
                        Utils.Func1 {
                            BarUtils.isStatusBarVisible(this)
                        },
                        Utils.Func1 {
                            BarUtils.setStatusBarVisibility(this, it)
                        }
                ),
                CommonItemSwitch(
                        R.string.bar_status_light_mode,
                        Utils.Func1 {
                            BarUtils.isStatusBarLightMode(this)
                        },
                        Utils.Func1 {
                            BarUtils.setStatusBarLightMode(this, it)
                        }
                )
        )
    }

    override fun onResume() {
        super.onResume()
        itemsView.updateItems(bindItems())
    }
}
