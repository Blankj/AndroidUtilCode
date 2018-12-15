package com.blankj.lib.base

import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.MenuItem
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.Utils
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_back.*

/**
 * ```
 * author: blankj
 * blog  : http://blankj.com
 * time  : 2018/11/16
 * desc  : base about back activity
 * ```
 */
abstract class BaseBackActivity : BaseActivity() {

    override fun setRootLayout(layoutId: Int) {
        super.setRootLayout(R.layout.activity_back)
        Slidr.attach(this)
        if (layoutId > 0) {
            LayoutInflater.from(this).inflate(layoutId, drawerContainerView)
        }
        setSupportActionBar(baseBackToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        BarUtils.setStatusBarColor(this, ContextCompat.getColor(Utils.getApp(), R.color.colorPrimary), 0)
        BarUtils.addMarginTopEqualStatusBarHeight(backRootLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setTitle(@StringRes resId: Int) {
        supportActionBar?.title = getString(resId)
    }
}