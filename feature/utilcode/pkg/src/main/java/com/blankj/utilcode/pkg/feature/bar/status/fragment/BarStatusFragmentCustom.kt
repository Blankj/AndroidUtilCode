package com.blankj.utilcode.pkg.feature.bar.status.fragment

import android.os.Bundle
import android.view.View
import com.blankj.common.fragment.CommonFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import kotlinx.android.synthetic.main.bar_status_custom_fragment.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusFragmentCustom : CommonFragment() {

    companion object {
        fun newInstance(): BarStatusFragmentCustom {
            return BarStatusFragmentCustom()
        }
    }

    override fun isLazy(): Boolean {
        return true
    }

    override fun bindLayout(): Int {
        return R.layout.bar_status_custom_fragment
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        BarUtils.setStatusBarCustom(barStatusCustomFragmentFakeStatusBar)
    }
}
