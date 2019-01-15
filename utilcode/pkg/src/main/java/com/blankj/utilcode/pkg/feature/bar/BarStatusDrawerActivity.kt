package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.blankj.lib.base.BaseDrawerActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_bar_status_drawer.*
import java.util.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusDrawerActivity : BaseDrawerActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusDrawerActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mRandom: Random = Random()
    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private val mColorListener = object : SeekBar.OnSeekBarChangeListener {
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

    internal var mAlphaCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            randomColorBtn.visibility = View.GONE
            mDrawerContainerView.setBackgroundResource(R.drawable.bar_status_alpha_bg)
        } else {
            randomColorBtn.visibility = View.VISIBLE
            mDrawerContainerView.setBackgroundColor(Color.WHITE)
        }
        updateStatusBar()
    }

    internal var mFrontCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> updateStatusBar() }

    override fun initData(bundle: Bundle?) {
        mColor = ContextCompat.getColor(Utils.getApp(), R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_drawer
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        alphaCb.setOnCheckedChangeListener(mAlphaCheckedChangeListener)
        frontCb.setOnCheckedChangeListener(mFrontCheckedChangeListener)
        randomColorBtn.setOnClickListener(this)
        findViewById<View>(R.id.setTransparentBtn).setOnClickListener(this)
        changeAlphaSb.setOnSeekBarChangeListener(mColorListener)
        statusAlphaTv.text = mAlpha.toString()

        updateStatusBar()
    }


    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.randomColorBtn -> {
                mColor = -0x1000000 or mRandom.nextInt(0xffffff)
                updateStatusBar()
            }
            R.id.setTransparentBtn -> changeAlphaSb.progress = 0
        }
    }

    private fun updateStatusBar() {
        if (alphaCb.isChecked) {
//            BarUtils.setStatusBarAlpha4Drawer(mDrawerRootLayout, fakeStatusBar, mColor, frontCb.isChecked)
        } else {
            BarUtils.setStatusBarColor4Drawer(mDrawerRootLayout, fakeStatusBar, mColor, frontCb.isChecked)
        }
        BarUtils.addMarginTopEqualStatusBarHeight(alphaCb)// 其实这个只需要调用一次即可
    }
}
