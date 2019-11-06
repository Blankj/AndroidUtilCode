package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import com.blankj.common.activity.CommonActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/14
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusCustomActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusCustomActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun doBusiness() {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT).setBackgroundResource(R.drawable.bar_status_custom)
    }
}
