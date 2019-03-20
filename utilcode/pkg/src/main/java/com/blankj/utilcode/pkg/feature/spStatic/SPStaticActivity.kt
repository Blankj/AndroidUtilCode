package com.blankj.utilcode.pkg.feature.spStatic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.SPStaticUtils
import kotlinx.android.synthetic.main.activity_spstatic.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/08
 * desc  : demo about SPUtils
 * ```
 */
class SPStaticActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SPStaticActivity::class.java)
            context.startActivity(starter)
        }

        fun sp2String(): String {
            val sb = StringBuilder()
            val map = SPStaticUtils.getAll()
            for ((key, value) in map) {
                sb.append(key)
                        .append(": ")
                        .append(value)
                        .append("\n")
            }
            return sb.toString()
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_spStatic)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_spstatic
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        spStaticPutStringBtn.setOnClickListener(this)
        spStaticPutIntBtn.setOnClickListener(this)
        spStaticPutLongBtn.setOnClickListener(this)
        spPutFloatBtn.setOnClickListener(this)
        spStaticPutBooleanBtn.setOnClickListener(this)
        spStaticClearBtn.setOnClickListener(this)
    }

    override fun doBusiness() {
        updateAboutSp()
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.spStaticPutStringBtn -> SPStaticUtils.put("STRING", "string")
            R.id.spStaticPutIntBtn -> SPStaticUtils.put("INT", 21)
            R.id.spStaticPutLongBtn -> SPStaticUtils.put("LONG", java.lang.Long.MAX_VALUE)
            R.id.spPutFloatBtn -> SPStaticUtils.put("FLOAT", Math.PI.toFloat())
            R.id.spStaticPutBooleanBtn -> SPStaticUtils.put("BOOLEAN", true)
            R.id.spStaticClearBtn -> SPStaticUtils.clear()
        }
        updateAboutSp()
    }

    private fun updateAboutSp() {
        spStaticAboutTv.text = sp2String()
    }
}
