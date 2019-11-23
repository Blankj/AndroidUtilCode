package com.blankj.utilcode.pkg.feature.bar.nav

import android.content.Context
import android.content.Intent
import android.os.Build
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.Utils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about BarUtils
 * ```
 */
class BarNavActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarNavActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_bar
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>().apply {
            add(CommonItemTitle("navHeight", BarUtils.getNavBarHeight().toString()))
            add(CommonItemTitle("isSupportNavBar", BarUtils.isSupportNavBar().toString()))
            if (BarUtils.isSupportNavBar()) {
                add(CommonItemSwitch(
                        R.string.bar_nav_visibility,
                        Utils.Func1 {
                            BarUtils.isNavBarVisible(this@BarNavActivity)
                        },
                        Utils.Func1 {
                            BarUtils.setNavBarVisibility(this@BarNavActivity, it)
                        }
                ))

                add(CommonItemSwitch(
                        R.string.bar_nav_light_mode,
                        Utils.Func1 {
                            BarUtils.isNavBarLightMode(this@BarNavActivity)
                        },
                        Utils.Func1 {
                            BarUtils.setNavBarLightMode(this@BarNavActivity, it)
                        }
                ))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    add(CommonItemClick("getNavBarColor: ${ColorUtils.int2ArgbString(BarUtils.getNavBarColor(this@BarNavActivity))}").setOnItemClickListener() { _, item, _ ->
                        BarUtils.setNavBarColor(this@BarNavActivity, ColorUtils.getRandomColor())
                        itemsView.updateItems(bindItems())
                        item.title = "getNavBarColor: ${ColorUtils.int2ArgbString(BarUtils.getNavBarColor(this@BarNavActivity))}"
                    })
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        itemsView.updateItems(bindItems())
    }
}
