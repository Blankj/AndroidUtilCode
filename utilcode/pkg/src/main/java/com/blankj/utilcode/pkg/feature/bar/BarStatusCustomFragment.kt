package com.blankj.utilcode.pkg.feature.bar

import android.os.Bundle
import android.view.View

import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_bar_status_custom.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusCustomFragment : BaseLazyFragment() {

    companion object {
        fun newInstance(): BarStatusCustomFragment {
            return BarStatusCustomFragment()
        }
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.fragment_bar_status_custom
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        BarUtils.setStatusBarCustom(barStatusCustomFragmentFakeStatusBar)
    }

    override fun doLazyBusiness() {
        LogUtils.d("doLazyBusiness() called")
    }

    override fun onWidgetClick(view: View) {}
}
