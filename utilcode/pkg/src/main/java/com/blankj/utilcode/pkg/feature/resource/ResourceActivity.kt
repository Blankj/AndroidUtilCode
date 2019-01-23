package com.blankj.utilcode.pkg.feature.resource

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_resource.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/05/07
 * desc  :
 * ```
 */
class ResourceActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ResourceActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_resource
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        resourceTestAssetsBtn.setOnClickListener(this)
        resourceTestRawBtn.setOnClickListener(this)

        SpanUtils.with(resourceAboutTv)
                .appendLine("readAssets2String: " + ResourceUtils.readAssets2String("test/test.txt"))
                .appendLine("readAssets2List: " + ResourceUtils.readAssets2List("test/test.txt").toString())
                .append("readRaw2List: " + ResourceUtils.readRaw2List(R.raw.test).toString())
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.resourceTestAssetsBtn -> ResourceUtils.copyFileFromAssets("test", Config.CACHE_PATH + "/assets/test")
            R.id.resourceTestRawBtn -> ResourceUtils.copyFileFromRaw(R.raw.test, Config.CACHE_PATH + "/raw/test.txt")
        }
    }
}
