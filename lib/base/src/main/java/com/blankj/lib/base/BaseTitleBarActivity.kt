package com.blankj.lib.base

import android.view.LayoutInflater
import android.view.MenuItem
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import kotlinx.android.synthetic.main.activity_back.*

/**
 * ```
 * author: blankj
 * blog  : http://blankj.com
 * time  : 2018/11/16
 * desc  : base about title bar activity
 * ```
 */
abstract class BaseTitleBarActivity : BaseActivity() {

    abstract fun bindTitle(): CharSequence

    override fun setRootLayout(layoutId: Int) {
        super.setRootLayout(R.layout.activity_back)
        if (layoutId > 0) {
            LayoutInflater.from(this).inflate(layoutId, baseBackContainerView)
        }
        setSupportActionBar(baseBackToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.colorPrimary))
        BarUtils.addMarginTopEqualStatusBarHeight(baseBackRootLayout)

        supportActionBar?.title = bindTitle()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item)
    }

    override fun isSwipeBack(): Boolean {
        return true
    }
}