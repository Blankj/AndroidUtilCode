package com.blankj.utilcode.pkg.feature.bar.status.fragment

import android.os.Bundle
import android.view.View
import com.blankj.common.fragment.CommonFragment
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ColorUtils
import kotlinx.android.synthetic.main.bar_status_color_fragment.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusFragmentColor : CommonFragment() {

    companion object {
        fun newInstance(): BarStatusFragmentColor {
            return BarStatusFragmentColor()
        }
    }

    private var mColor: Int = ColorUtils.getColor(R.color.colorPrimary)

    override fun isLazy(): Boolean {
        return true
    }

    override fun bindLayout(): Int {
        return R.layout.bar_status_color_fragment
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
        updateFakeStatusBar()
    }

    private fun getItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemClick(R.string.bar_status_random_color, ColorUtils.int2ArgbString(mColor)).setOnClickUpdateContentListener {
                    mColor = ColorUtils.getRandomColor()
                    updateFakeStatusBar()
                    return@setOnClickUpdateContentListener ColorUtils.int2ArgbString(mColor)
                }
        )
    }

    private fun updateFakeStatusBar() {
        BarUtils.setStatusBarColor(barStatusColorFragmentFakeStatusBar, mColor)
    }
}
