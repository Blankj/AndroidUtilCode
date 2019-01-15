package com.blankj.utilcode.pkg.feature.bar

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import com.blankj.lib.base.BaseLazyFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.fragment_bar_status_color.*
import java.util.*

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

    private var mRandom: Random = Random()
    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private val colorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            statusAlphaTv.text = mAlpha.toString()
            updateFakeStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }

    override fun initData(bundle: Bundle?) {
        mColor = ContextCompat.getColor(Utils.getApp(), R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_bar_status_color
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        randomColorBtn.setOnClickListener(this)
        setTransparentBtn.setOnClickListener(this)
        changeAlphaSb.setOnSeekBarChangeListener(colorListener)
        statusAlphaTv.text = mAlpha.toString()

        updateFakeStatusBar()
    }


    override fun doLazyBusiness() {
        LogUtils.d("doLazyBusiness() called")
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.randomColorBtn -> {
                mColor = -0x1000000 or mRandom.nextInt(0xffffff)
                updateFakeStatusBar()
            }
            R.id.setTransparentBtn -> changeAlphaSb.progress = 0
        }
    }

    fun updateFakeStatusBar() {
        BarUtils.setStatusBarColor(fakeStatusBar, mColor)
    }
}
