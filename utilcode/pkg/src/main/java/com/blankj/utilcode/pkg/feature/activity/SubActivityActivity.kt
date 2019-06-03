package com.blankj.utilcode.pkg.feature.activity

import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.view.View
import android.view.Window
import com.blankj.lib.base.BaseTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ColorUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ActivityUtils
 * ```
 */
class SubActivityActivity : BaseTitleActivity() {

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        return R.layout.activity_activity_sub
    }

    override fun bindTitle(): String {
        return getString(R.string.demo_activity)
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        contentView?.setBackgroundColor(ColorUtils.getRandomColor(false))
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAfterTransition(this)
    }
}
