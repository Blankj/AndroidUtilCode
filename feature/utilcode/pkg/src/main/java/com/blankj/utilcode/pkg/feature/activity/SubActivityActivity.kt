package com.blankj.utilcode.pkg.feature.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.Window
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.util.ColorUtils
import kotlinx.android.synthetic.main.activity_activity_sub.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ActivityUtils
 * ```
 */
class SubActivityActivity : CommonTitleActivity() {

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        return com.blankj.utilcode.pkg.R.layout.activity_activity_sub
    }

    override fun bindTitle(): String {
        return getString(com.blankj.utilcode.pkg.R.string.demo_activity)
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        contentView?.setBackgroundColor(ColorUtils.getRandomColor(false))
        activityViewSharedElement.setOnClickListener(View.OnClickListener {
            val result = Intent()
            result.putExtra("data", "data")
            this@SubActivityActivity.setResult(Activity.RESULT_OK, result)
            this@SubActivityActivity.finish()
        })
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {}

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAfterTransition(this)
    }
}
