package com.blankj.utilcode.pkg.feature.fragment

import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.fragment_child.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class ChildFragment : BaseLazyFragment() {

    companion object {
        fun newInstance(): ChildFragment {
            val args = Bundle()
            val fragment = ChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.fragment_child
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        FragmentUtils.setBackgroundColor(this, ColorUtils.getRandomColor(false))
        fragmentChildShowStackBtn.setOnClickListener(this)
        fragmentChildPopBtn.setOnClickListener(this)
        fragmentChildRemoveBtn.setOnClickListener(this)
    }

    override fun doLazyBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.fragmentChildShowStackBtn -> DialogHelper.showFragmentDialog(
                    SpanUtils().appendLine("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(fragmentManager!!)))
                            .appendLine("topInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(fragmentManager!!)))
                            .appendLine("topShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(fragmentManager!!)))
                            .appendLine("topShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(fragmentManager!!)))
                            .appendLine()
                            .appendLine("---all of fragments---")
                            .appendLine(FragmentUtils.getAllFragments(fragmentManager!!).toString())
                            .appendLine("----------------------")
                            .appendLine()
                            .appendLine("---stack top---")
                            .appendLine(FragmentUtils.getAllFragmentsInStack(fragmentManager!!).toString())
                            .appendLine("---stack bottom---")
                            .create()
            )
            R.id.fragmentChildPopBtn -> FragmentUtils.pop(fragmentManager!!);
            R.id.fragmentChildRemoveBtn -> FragmentUtils.remove(this);
        }
    }
}
