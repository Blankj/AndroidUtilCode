package com.blankj.utilcode.pkg.feature.api

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.api.other.export.OtherModuleApi
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_api.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about ApiUtils
 * ```
 */
class ApiActivity : CommonTitleActivity() {

    private val api = ApiUtils.getApi(OtherModuleApi::class.java)

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ApiActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_api)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_api
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                apiInvokeWithParams,
                apiInvokeWithReturnValue
        )
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.apiInvokeWithParams -> {
                api.invokeWithParams(OtherModuleApi.ApiBean("params"))
            }
            R.id.apiInvokeWithReturnValue -> {
                ToastUtils.showShort(api.invokeWithReturnValue().name)
            }
        }
    }
}
