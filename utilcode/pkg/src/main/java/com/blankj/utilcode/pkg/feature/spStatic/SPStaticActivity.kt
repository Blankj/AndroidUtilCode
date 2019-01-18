package com.blankj.utilcode.pkg.feature.spStatic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.SPStaticUtils
import kotlinx.android.synthetic.main.activity_sp.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/08
 * desc  : demo about SPUtils
 * ```
 */
class SPStaticActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_sp
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        spPutStringBtn.setOnClickListener(this)
        spPutIntBtn.setOnClickListener(this)
        spPutLongBtn.setOnClickListener(this)
        spPutFloatBtn.setOnClickListener(this)
        spPutBooleanBtn.setOnClickListener(this)
        spClearBtn.setOnClickListener(this)
    }

    override fun doBusiness() {
        updateAboutSp()
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.spPutStringBtn -> SPStaticUtils.put("STRING", "string")
            R.id.spPutIntBtn -> SPStaticUtils.put("INT", 21)
            R.id.spPutLongBtn -> SPStaticUtils.put("LONG", java.lang.Long.MAX_VALUE)
            R.id.spPutFloatBtn -> SPStaticUtils.put("FLOAT", Math.PI.toFloat())
            R.id.spPutBooleanBtn -> SPStaticUtils.put("BOOLEAN", true)
            R.id.spClearBtn -> SPStaticUtils.clear()
        }
        updateAboutSp()
    }

    private fun updateAboutSp() {
        aboutSpTv!!.text = sp2String()
    }

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
}
