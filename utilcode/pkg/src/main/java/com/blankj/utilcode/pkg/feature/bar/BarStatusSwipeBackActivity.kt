package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_bar_status_swipe_back.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusSwipeBackActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusSwipeBackActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private val mColorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            barStatusSwipeBackAboutTv.text = mAlpha.toString()
            updateStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private var mCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            barStatusSwipeBackChangeAlphaSb.visibility = View.VISIBLE
            barStatusSwipeBackRandomColorBtn.visibility = View.GONE
            barStatusSwipeBackSetTransparentBtn.visibility = View.VISIBLE

            barStatusSwipeBackContainerLl.setBackgroundResource(R.drawable.bar_status_alpha_bg)
        } else {
            barStatusSwipeBackChangeAlphaSb.visibility = View.GONE
            barStatusSwipeBackRandomColorBtn.visibility = View.VISIBLE
            barStatusSwipeBackSetTransparentBtn.visibility = View.GONE

            barStatusSwipeBackContainerLl.setBackgroundColor(Color.WHITE)
        }
        updateStatusBar()
    }

    override fun isSwipeBack(): Boolean {
        return true
    }

    override fun initData(bundle: Bundle?) {
        mColor = ColorUtils.getColor(R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_swipe_back
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        Slidr.attach(this)
        barStatusSwipeBackAlphaCb.setOnCheckedChangeListener(mCheckedChangeListener)
        barStatusSwipeBackChangeAlphaSb.setOnSeekBarChangeListener(mColorListener)
        barStatusSwipeBackRandomColorBtn.setOnClickListener(this)
        barStatusSwipeBackSetTransparentBtn.setOnClickListener(this)

        barStatusSwipeBackSetTransparentBtn.visibility = View.GONE
        updateStatusBar()
    }


    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.barStatusSwipeBackRandomColorBtn -> {
                mColor = ColorUtils.getRandomColor()
                updateStatusBar()
            }
            R.id.barStatusSwipeBackSetTransparentBtn -> barStatusSwipeBackChangeAlphaSb.progress = 0
        }
    }

    private fun updateStatusBar() {
        if (barStatusSwipeBackAlphaCb.isChecked) {
            BarUtils.setStatusBarColor(this, Color.argb(mAlpha, 0, 0, 0))
            barStatusSwipeBackAboutTv.text = mAlpha.toString()
        } else {
            BarUtils.setStatusBarColor(this, mColor)
            barStatusSwipeBackAboutTv.text = ColorUtils.int2ArgbString(mColor)
        }
        BarUtils.addMarginTopEqualStatusBarHeight(barStatusSwipeBackAlphaCb)
    }
}
