package com.blankj.utilcode.pkg.feature.fragment

import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_root.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class RootFragment : BaseLazyFragment(), FragmentUtils.OnBackClickListener {

    companion object {
        fun newInstance(): RootFragment {
            val args = Bundle()
            val fragment = RootFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.fragment_root
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        BarUtils.setStatusBarColor(rootFragmentFakeStatusBar, ColorUtils.getColor(R.color.colorPrimary))
        FragmentUtils.add(
                childFragmentManager,
                ContainerFragment.newInstance(),
                R.id.rootFragmentContainer
        )
    }

    override fun doLazyBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun onBackClick(): Boolean {
        if (FragmentUtils.dispatchBackPress(childFragmentManager)) return true
        return if (childFragmentManager.backStackEntryCount == 0) {
            false
        } else {
            childFragmentManager.popBackStack()
            true
        }
    }
}
