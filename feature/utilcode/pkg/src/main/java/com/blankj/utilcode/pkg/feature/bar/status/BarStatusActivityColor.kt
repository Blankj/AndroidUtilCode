package com.blankj.utilcode.pkg.feature.bar.status

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ColorUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusActivityColor : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivityColor::class.java)
            context.startActivity(starter)
        }
    }

    private var mColor: Int = ColorUtils.getColor(R.color.colorPrimary)

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemClick(R.string.bar_status_random_color, ColorUtils.int2ArgbString(mColor)).setOnClickUpdateContentListener {
                    mColor = ColorUtils.getRandomColor()
                    updateStatusBar()
                    return@setOnClickUpdateContentListener ColorUtils.int2ArgbString(mColor)
                }
        )
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        updateStatusBar()
    }

    private fun updateStatusBar() {
        BarUtils.setStatusBarColor(this, mColor)
        BarUtils.addMarginTopEqualStatusBarHeight(findViewById(R.id.commonItemRv))// 其实这个只需要调用一次即可
    }
}
