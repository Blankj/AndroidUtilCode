package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import kotlinx.android.synthetic.main.activity_bar_status_alpha.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusAlphaActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusAlphaActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mAlpha: Int = 0

    private val translucentListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            statusAlphaTv.text = mAlpha.toString()
            updateStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }

    override fun initData(bundle: Bundle?) {
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_alpha
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        findViewById<View>(R.id.setTransparentBtn).setOnClickListener(this)
        changeAlphaSb.setOnSeekBarChangeListener(translucentListener)
        statusAlphaTv.text = mAlpha.toString()

        updateStatusBar()
    }


    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.setTransparentBtn -> changeAlphaSb.progress = 0
        }
    }

    private fun updateStatusBar() {
        BarUtils.setStatusBarColor(this, Color.argb(mAlpha, 0, 0, 0))
        BarUtils.addMarginTopEqualStatusBarHeight(statusAlphaTv)// 其实这个只需要调用一次即可
    }
}
