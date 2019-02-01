package com.blankj.utilcode.pkg.feature.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.transition.*
import android.view.View
import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.R.id.*
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_container.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class ContainerFragment : BaseLazyFragment(), FragmentUtils.OnBackClickListener {

    companion object {
        fun newInstance(): ContainerFragment {
            val args = Bundle()
            val fragment = ContainerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.fragment_container
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        FragmentUtils.setBackgroundColor(this, ColorUtils.getRandomColor(false))
        fragmentRootShowStackBtn.setOnClickListener(this)
        fragmentRootAddChildBtn.setOnClickListener(this)
        fragmentRootAddChildStackBtn.setOnClickListener(this)

        fragmentRootAddHideBtn.setOnClickListener(this)
        fragmentRootAddHideStackBtn.setOnClickListener(this)
        fragmentRootAddShowBtn.setOnClickListener(this)
        fragmentRootPopToRootBtn.setOnClickListener(this)
        fragmentRootHideShowBtn.setOnClickListener(this)
        fragmentRootReplaceBtn.setOnClickListener(this)
    }

    override fun doLazyBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.fragmentRootShowStackBtn -> DialogHelper.showFragmentDialog(
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
            R.id.fragmentRootAddChildBtn -> FragmentUtils.add(
                    fragmentManager!!,
                    ChildFragment.newInstance(),
                    id
            )
            R.id.fragmentRootAddChildStackBtn -> FragmentUtils.add(
                    fragmentManager!!,
                    ChildFragment.newInstance(),
                    id,
                    false,
                    true
            )
            R.id.fragmentRootAddHideBtn -> FragmentUtils.add(
                    fragmentManager!!,
                    ChildFragment.newInstance(),
                    id,
                    true
            )
            R.id.fragmentRootAddHideStackBtn -> FragmentUtils.add(
                    fragmentManager!!,
                    ChildFragment.newInstance(),
                    id,
                    true,
                    true
            )
            R.id.fragmentRootAddShowBtn -> FragmentUtils.add(
                    fragmentManager!!,
                    addSharedElement(ChildFragment.newInstance()),
                    id,
                    false,
                    false
            )
            R.id.fragmentRootPopToRootBtn -> FragmentUtils.popTo(
                    fragmentManager!!,
                    ChildFragment::class.java,
                    true
            )
            R.id.fragmentRootHideShowBtn -> {
                val fragment1 = FragmentUtils.findFragment(fragmentManager!!, ChildFragment::class.java)
                if (fragment1 != null) {
                    FragmentUtils.showHide(this, fragment1)
                } else {
                    ToastUtils.showLong("please add demo1 first!")
                }
            }
            R.id.fragmentRootReplaceBtn -> FragmentUtils.replace(
                    fragmentManager!!,
                    addSharedElement(ChildFragment.newInstance()),
                    id,
                    true,
                    fragmentRootSharedElementIv
            )
        }
    }

    private fun addSharedElement(fragment: Fragment): Fragment {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.sharedElementEnterTransition = DetailTransition()
            fragment.enterTransition = Fade()
            fragment.sharedElementReturnTransition = DetailTransition()
        }
        return fragment
    }

    override fun onBackClick(): Boolean {
        return false
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DetailTransition() : TransitionSet() {
    init {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(ChangeImageTransform())
    }
}
