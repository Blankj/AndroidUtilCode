package com.blankj.utilcode.pkg.feature.sdcard

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SDCardUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : demo about SDCardUtils
 * ```
 */
class SDCardActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SDCardActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_sdcard
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("isSDCardEnableByEnvironment", SDCardUtils.isSDCardEnableByEnvironment().toString()),
                CommonItemTitle("getSDCardPathByEnvironment", SDCardUtils.getSDCardPathByEnvironment()),
                CommonItemTitle("getSDCardInfo", SDCardUtils.getSDCardInfo().toString()),
                CommonItemTitle("getMountedSDCardPath", SDCardUtils.getMountedSDCardPath().toString()),
                CommonItemTitle("getExternalTotalSize", ConvertUtils.byte2FitMemorySize(SDCardUtils.getExternalTotalSize(), 2)),
                CommonItemTitle("getExternalAvailableSize", ConvertUtils.byte2FitMemorySize(SDCardUtils.getExternalAvailableSize(), 2)),
                CommonItemTitle("getInternalTotalSize", ConvertUtils.byte2FitMemorySize(SDCardUtils.getInternalTotalSize(), 2)),
                CommonItemTitle("getInternalAvailableSize", ConvertUtils.byte2FitMemorySize(SDCardUtils.getInternalAvailableSize(), 2))
        )
    }
}
