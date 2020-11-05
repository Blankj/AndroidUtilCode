package com.blankj.utilcode.pkg.feature.fragment

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.blankj.common.fragment.CommonFragment
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class ChildFragment : CommonFragment() {

    companion object {
        fun newInstance(): ChildFragment {
            val args = Bundle()
            val fragment = ChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var fm: FragmentManager
    private val mBgColor = ColorUtils.getRandomColor(false)

    override fun bindLayout(): Int {
        return R.layout.fragment_child
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        FragmentUtils.setBackgroundColor(this, mBgColor)
        fm = fragmentManager!!
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
    }

    private fun getItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemClick(R.string.fragment_show_stack) {
                    DialogHelper.showFragmentDialog(
                            SpanUtils().appendLine("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(fm)))
                                    .appendLine("topInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(fm)))
                                    .appendLine("topShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(fm)))
                                    .appendLine("topShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(fm)))
                                    .appendLine()
                                    .appendLine("---all of fragments---")
                                    .appendLine(FragmentUtils.getAllFragments(fm).toString())
                                    .appendLine("----------------------")
                                    .appendLine()
                                    .appendLine("---stack top---")
                                    .appendLine(FragmentUtils.getAllFragmentsInStack(fm).toString())
                                    .appendLine("---stack bottom---")
                                    .create()
                    )
                },
                CommonItemClick(R.string.fragment_pop) {
                    FragmentUtils.pop(fm)
                },
                CommonItemClick(R.string.fragment_remove) {
                    FragmentUtils.remove(this)
                },
                SharedElementItem()
        ).apply {
            for (ci: CommonItem<*> in this) {
                ci.backgroundColor = mBgColor
            }
        }
    }
}