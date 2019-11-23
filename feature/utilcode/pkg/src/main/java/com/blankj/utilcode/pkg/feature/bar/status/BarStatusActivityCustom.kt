package com.blankj.utilcode.pkg.feature.bar.status

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
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
class BarStatusActivityCustom : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivityCustom::class.java)
            context.startActivity(starter)
        }
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT).setBackgroundResource(R.drawable.bar_status_custom)
    }
}
