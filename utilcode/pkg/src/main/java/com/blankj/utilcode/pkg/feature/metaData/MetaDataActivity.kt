package com.blankj.utilcode.pkg.feature.metaData

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.MetaDataUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_metadata.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/05/15
 * desc  : demo about MetaDataUtils
 * ```
 */
class MetaDataActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MetaDataActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_metadata
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_meta_data)

        SpanUtils.with(metaDataAboutTv)
                .appendLine("getMetaDataInApp: " + MetaDataUtils.getMetaDataInApp("app_meta_data"))
                .append("getMetaDataInActivity: " + MetaDataUtils.getMetaDataInActivity(this, "activity_meta_data"))
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }
}
