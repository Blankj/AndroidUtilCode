package com.blankj.utilcode.pkg.feature.shadow

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.utilcode.pkg.R

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/10/22
 * desc  : demo about ShadowUtils
 * ```
 */
class ShadowActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ShadowActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_shadow
    }
}
