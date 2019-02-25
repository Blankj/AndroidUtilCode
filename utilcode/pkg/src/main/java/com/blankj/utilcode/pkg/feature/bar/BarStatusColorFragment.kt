package com.blankj.utilcode.pkg.feature.bar

import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_bar_status_color.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusColorFragment : BaseLazyFragment() {

    companion object {
        fun newInstance(): BarStatusColorFragment {
            return BarStatusColorFragment()
        }
    }

    private var mColor: Int = 0

    override fun initData(bundle: Bundle?) {
        mColor = ColorUtils.getColor(R.color.colorPrimary)
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_bar_status_color
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        barStatusColorFragmentRandomColorBtn.setOnClickListener(this)
        updateFakeStatusBar()
    }

    override fun doLazyBusiness() {
        LogUtils.d("doLazyBusiness() called")
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barStatusColorFragmentRandomColorBtn -> {
                mColor = ColorUtils.getRandomColor()
                updateFakeStatusBar()
            }
        }
    }

    private fun updateFakeStatusBar() {
        BarUtils.setStatusBarColor(barStatusColorFragmentFakeStatusBar, mColor)
        barStatusColorFragmentAboutColorTv.text = String.format(ColorUtils.int2ArgbString(mColor))
    }
}
