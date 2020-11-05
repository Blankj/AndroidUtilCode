package com.blankj.utilcode.pkg.feature.clean

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityItemsView
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CleanUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.SnackbarUtils
import java.io.File

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about CleanUtils
 * ```
 */
class CleanActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CleanActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_clean
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>().apply {
            add(CommonItemClick(R.string.clean_internal_cache) {
                showSnackbar(CleanUtils.cleanInternalCache(), cacheDir.path)
            })
            add(CommonItemClick(R.string.clean_internal_files) {
                showSnackbar(CleanUtils.cleanInternalFiles(), filesDir.path)
            })
            add(CommonItemClick(R.string.clean_internal_databases) {
                showSnackbar(CleanUtils.cleanInternalDbs(), filesDir.parent + File.separator + "databases")
            })
            add(CommonItemClick(R.string.clean_internal_sp) {
                showSnackbar(CleanUtils.cleanInternalSp(), filesDir.parent + File.separator + "shared_prefs")
            })
            if (SDCardUtils.isSDCardEnableByEnvironment()) {
                add(CommonItemClick(R.string.clean_external_cache) {
                    showSnackbar(CleanUtils.cleanExternalCache(), externalCacheDir?.absolutePath)
                })
            }
        }
    }

    private fun showSnackbar(isSuccess: Boolean, path: String?) {
        SnackbarUtils.with(mContentView)
                .setDuration(SnackbarUtils.LENGTH_LONG)
                .apply {
                    if (isSuccess) {
                        setMessage("clean \"$path\" dir successful.")
                        showSuccess()
                    } else {
                        setMessage("clean \"$path\" dir failed.")
                        showError()
                    }
                }
    }
}
