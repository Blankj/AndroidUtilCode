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
        ShadowUtils.apply(shadowRectView, Config().setShadowColor(0x44000000, 0x55000000))
        ShadowUtils.apply(shadowRoundRectView, Config().setShadowColor(0x44000000, 0x55000000).setShadowRadius(
                SizeUtils.dp2px(16f).toFloat()))
        ShadowUtils.apply(shadowCircleView, Config().setCircle().setShadowColor(0x44000000, 0x55000000))

        ShadowUtils.apply(shadowRectView1, Config().setShadowColor(0x44000000, 0x55000000))
        ShadowUtils.apply(shadowRoundRectView1, Config().setShadowColor(0x44000000, 0x55000000).setShadowRadius(
                SizeUtils.dp2px(16f).toFloat()))
        ShadowUtils.apply(shadowCircleView1, Config().setCircle().setShadowColor(0x44000000, 0x55000000))
    }
}
