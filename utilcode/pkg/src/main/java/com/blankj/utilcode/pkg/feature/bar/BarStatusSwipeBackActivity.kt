package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.Utils
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_bar_status_swipe_back.*
import java.util.*

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

    private var mRandom: Random = Random()
    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private val mColorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            statusAboutTv.text = mAlpha.toString()
            updateStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }

    private var mCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            changeAlphaSb.visibility = View.VISIBLE
            randomColorBtn.visibility = View.GONE
            setTransparentBtn.visibility = View.VISIBLE

            containerLl.setBackgroundResource(R.drawable.bar_status_alpha_bg)
        } else {
            changeAlphaSb.visibility = View.GONE
            randomColorBtn.visibility = View.VISIBLE
            setTransparentBtn.visibility = View.GONE

            containerLl.setBackgroundColor(Color.WHITE)
        }
        updateStatusBar()
    }

    override fun initData(bundle: Bundle?) {
        mColor = ContextCompat.getColor(Utils.getApp(), R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_swipe_back
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        Slidr.attach(this)
        alphaCb.setOnCheckedChangeListener(mCheckedChangeListener)
        changeAlphaSb.setOnSeekBarChangeListener(mColorListener)
        randomColorBtn.setOnClickListener(this)
        setTransparentBtn.setOnClickListener(this)

        setTransparentBtn.visibility = View.GONE
        updateStatusBar()
    }


    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.randomColorBtn -> {
                mColor = ColorUtils.getRandomColor()
                updateStatusBar()
            }
            R.id.setTransparentBtn -> changeAlphaSb.progress = 0
        }
    }

    private fun updateStatusBar() {
        if (alphaCb.isChecked) {
            BarUtils.setStatusBarColor(this, Color.argb(mAlpha, 0, 0, 0))
            statusAboutTv.text = mAlpha.toString()
        } else {
            BarUtils.setStatusBarColor(this, mColor)
            statusAboutTv.text = ColorUtils.int2ArgbString(mColor)
        }
        BarUtils.addMarginTopEqualStatusBarHeight(alphaCb)
    }
}
