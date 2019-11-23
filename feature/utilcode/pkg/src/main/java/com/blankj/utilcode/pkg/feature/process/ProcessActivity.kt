package com.blankj.utilcode.pkg.feature.process

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ProcessUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ProcessUtils
 * ```
 */
class ProcessActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ProcessActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_process
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        val set = ProcessUtils.getAllBackgroundProcesses()
        return CollectionUtils.newArrayList(
                CommonItemTitle("getForegroundProcessName", ProcessUtils.getForegroundProcessName()!!),
                CommonItemTitle("getAllBackgroundProcesses -> ${set.size}", getSetItems(set)),
                CommonItemTitle("isMainProcess", ProcessUtils.isMainProcess().toString()),
                CommonItemTitle("getCurrentProcessName", ProcessUtils.getCurrentProcessName()),

                CommonItemClick(R.string.process_kill_all_background).setOnItemClickListener { _, item, _ ->
                    val bgSet = ProcessUtils.getAllBackgroundProcesses()
                    val killedSet = ProcessUtils.killAllBackgroundProcesses()
                    itemsView.updateItems(
                            CollectionUtils.newArrayList(
                                    CommonItemTitle("getForegroundProcessName", ProcessUtils.getForegroundProcessName()),
                                    CommonItemTitle("getAllBackgroundProcesses -> ${bgSet.size}", getSetItems(bgSet)),
                                    CommonItemTitle("killAllBackgroundProcesses -> ${killedSet.size}", getSetItems(killedSet)),
                                    CommonItemTitle("isMainProcess", ProcessUtils.isMainProcess().toString()),
                                    CommonItemTitle("getCurrentProcessName", ProcessUtils.getCurrentProcessName()),
                                    item
                            )
                    )
                }
        )
    }

    private fun getSetItems(set: Set<String>): String {
        val iterator = set.iterator()
        val sb = StringBuilder()
        while (iterator.hasNext()) {
            sb.append("\n").append(iterator.next())
        }
        return if (sb.isNotEmpty()) sb.deleteCharAt(0).toString() else ""
    }
}
