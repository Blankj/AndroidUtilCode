package com.blankj.utilcode.pkg.feature.reflect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.LogUtils
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
class ReflectActivity : CommonTitleActivity() {

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

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        LogUtils.e(TestPrivateStaticFinal.STR)
        SpanUtils.with(reflectAboutTv)
                .appendLine("before reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("STR").get<Any>())
                .append("after reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("STR", "reflect success").field("STR").get<Any>())
                .create()
        LogUtils.e(TestPrivateStaticFinal.STR)
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {}

    override fun onDestroy() {
//        ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("STR", "str")
        super.onDestroy()
    }
}
