package com.blankj.utilcode.pkg.feature.bar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_bar_status_alpha.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusAlphaFragment : BaseLazyFragment() {

    companion object {
        fun newInstance(): BarStatusAlphaFragment {
            return BarStatusAlphaFragment()
        }
    }

    private var mAlpha: Int = 0

    private val translucentListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            barStatusAlphaFragmentAboutTv.text = mAlpha.toString()
            updateFakeStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    override fun initData(bundle: Bundle?) {
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_bar_status_alpha
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        barStatusAlphaFragmentSetTransparentBtn.setOnClickListener(this)
        barStatusAlphaFragmentChangeAlphaSb.setOnSeekBarChangeListener(translucentListener)
        barStatusAlphaFragmentAboutTv.text = mAlpha.toString()

        updateFakeStatusBar()
    }

    override fun doLazyBusiness() {
        LogUtils.d("doLazyBusiness() called")
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barStatusAlphaSetTransparentBtn -> barStatusAlphaFragmentChangeAlphaSb.progress = 0
        }
    }

    fun updateFakeStatusBar() {
        BarUtils.setStatusBarColor(barStatusAlphaFragmentFakeStatusBar, Color.argb(mAlpha, 0, 0, 0))
    }
}
