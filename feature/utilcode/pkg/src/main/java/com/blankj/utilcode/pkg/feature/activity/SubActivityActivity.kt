package com.blankj.utilcode.pkg.feature.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ColorUtils
import kotlinx.android.synthetic.main.activity_sub_activity.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ActivityUtils
 * ```
 */
class SubActivityActivity : CommonActivity() {

    override fun bindLayout(): Int {
        return R.layout.activity_sub_activity
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        contentView?.setBackgroundColor(ColorUtils.getRandomColor(false))
        activityViewSharedElement.setOnClickListener {
            val result = Intent()
            result.putExtra("data", "data")
            this@SubActivityActivity.setResult(Activity.RESULT_OK, result)
            this@SubActivityActivity.finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAfterTransition(this)
    }
}
