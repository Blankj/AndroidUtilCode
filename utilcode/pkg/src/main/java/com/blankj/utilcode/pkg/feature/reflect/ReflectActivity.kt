package com.blankj.utilcode.pkg.feature.reflect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ReflectUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_reflect.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/29
 * desc  : demo about ReflectUtils
 * ```
 */
class ReflectActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ReflectActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_reflect)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_reflect
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        SpanUtils.with(reflectAboutTv)
                .appendLine("before reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("I1").get<Any>())
                .append("after reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("I1", 2).field("I1").get<Any>())
                .create()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun onDestroy() {
        ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("I1", 1)
        super.onDestroy()
    }
}

object TestPrivateStaticFinal {
    val I1 = 1
}
