package com.blankj.utilcode.pkg.feature.api

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.api.other.export.OtherModuleApi
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about ApiUtils
 * ```
 */
class ApiActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ApiActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_api
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.api_invoke_with_params) {
                    ApiUtils.getApi(OtherModuleApi::class.java).invokeWithParams(OtherModuleApi.ApiBean("params"))
                },
                CommonItemClick(R.string.api_invoke_with_return_value) {
                    ToastUtils.showShort(ApiUtils.getApi(OtherModuleApi::class.java).invokeWithReturnValue().name)
                }
        );
    }
}
