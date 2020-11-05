package com.blankj.utilcode.pkg.feature.resource

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ResourceUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/05/07
 * desc  : demo about ResourceUtils
 * ```
 */
class ResourceActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ResourceActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_resource
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("readAssets2String", ResourceUtils.readAssets2String("test/test.txt")),
                CommonItemTitle("readAssets2List", ResourceUtils.readAssets2List("test/test.txt").toString()),
                CommonItemTitle("readRaw2List", ResourceUtils.readRaw2List(R.raw.test).toString()),

                CommonItemClick(R.string.resource_copy_file_from_assets_2_cache) {
                    ResourceUtils.copyFileFromAssets("test", Config.CACHE_PATH + "assets/test")
                },
                CommonItemClick(R.string.resource_copy_file_from_raw_2_cache) {
                    ResourceUtils.copyFileFromRaw(R.raw.test, Config.CACHE_PATH + "raw/test.txt")
                }
        )
    }
}
