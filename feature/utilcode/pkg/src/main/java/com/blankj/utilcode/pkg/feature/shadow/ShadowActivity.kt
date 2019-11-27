package com.blankj.utilcode.pkg.feature.shadow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ShadowUtils
import com.blankj.utilcode.util.ShadowUtils.Config
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.shadow_activity.*

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

    override fun bindLayout(): Int {
        return R.layout.shadow_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        ShadowUtils.apply(shadowRectView, Config().setShadowRadius(0.01f).setShadowColor(Color.BLUE, Color.GREEN))
        ShadowUtils.apply(shadowRoundRectView, Config().setShadowRadius(
                SizeUtils.dp2px(16f).toFloat()).setShadowColor(Color.RED, Color.BLUE))
        ShadowUtils.apply(shadowCircleView, Config().setCircle().setShadowColor(Color.GREEN, Color.BLUE))
    }
}
