package com.blankj.utilcode.pkg.feature.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.transition.ChangeBounds
import android.support.transition.ChangeImageTransform
import android.support.transition.ChangeTransform
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.transition.Fade
import android.view.View
import com.blankj.lib.base.BaseFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_root.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class RootFragment : BaseFragment(), FragmentUtils.OnBackClickListener {

    companion object {
        fun newInstance(): RootFragment {
            val args = Bundle()
            val fragment = RootFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.fragment_root
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        val random = Random()
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
        fragmentRootShowAboutBtn.setOnClickListener(this)
        fragmentRootAddBtn.setOnClickListener(this)
        fragmentRootAddHideBtn.setOnClickListener(this)
        fragmentRootAddHideStackBtn.setOnClickListener(this)
        fragmentRootAddBtn.setOnClickListener(this)
        fragmentRootAddShowBtn.setOnClickListener(this)
        fragmentRootAddChildBtn.setOnClickListener(this)
        fragmentRootPopToRootBtn.setOnClickListener(this)
        fragmentRootHideShowBtn.setOnClickListener(this)
        fragmentRootReplaceBtn.setOnClickListener(this)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.fragmentRootShowAboutBtn -> DialogHelper.showFragmentDialog(
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
            R.id.fragmentRootAddBtn -> FragmentUtils.add(fragmentManager!!,
                    ChildFragment.newInstance(),
                    R.id.child_fragment_container,
                    false,
                    true)
            R.id.fragmentRootAddHideBtn -> FragmentUtils.add(fragmentManager!!,
                    ChildFragment.newInstance(),
                    R.id.fragmentContainer,
                    true)
            R.id.fragmentRootAddHideStackBtn -> FragmentUtils.add(fragmentManager!!,
                    ChildFragment.newInstance(),
                    R.id.fragmentContainer,
                    true,
                    true)
            R.id.fragmentRootAddShowBtn -> FragmentUtils.add(fragmentManager!!,
                    addSharedElement(ChildFragment.newInstance()),
                    R.id.fragmentContainer,
                    false,
                    false)
            R.id.fragmentRootAddChildBtn -> FragmentUtils.add(childFragmentManager,
                    ChildFragment.newInstance(),
                    R.id.child_fragment_container,
                    false,
                    true)
            R.id.fragmentRootPopToRootBtn -> FragmentUtils.popTo(fragmentManager!!,
                    ChildFragment::class.java,
                    true)
            R.id.fragmentRootHideShowBtn -> {
                val fragment1 = FragmentUtils.findFragment(fragmentManager!!, ChildFragment::class.java)
                if (fragment1 != null) {
                    FragmentUtils.showHide(this, fragment1)
                } else {
                    ToastUtils.showLong("please add demo1 first!")
                }
            }
            R.id.fragmentRootReplaceBtn -> FragmentUtils.replace(this, addSharedElement(ChildFragment.newInstance()), false, fragmentRootSharedElementIv)
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
        FragmentUtils.popTo(fragmentManager!!, ChildFragment::class.java, true)
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
