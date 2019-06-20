package com.blankj.utilcode.pkg.feature.process

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_process.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ProcessUtils
 * ```
 */
class ProcessActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ProcessActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_process)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_process
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(processKillAllBackgroundProcesses)
        val set = ProcessUtils.getAllBackgroundProcesses()
        SpanUtils.with(processAboutTv)
                .appendLine("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName()!!)
                .appendLine("getAllBackgroundProcesses: " + getSetItems(set))
                .appendLine("size: " + set.size)
                .appendLine("isMainProcess: " + ProcessUtils.isMainProcess())
                .append("getCurrentProcessName: " + ProcessUtils.getCurrentProcessName())
                .create()
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.processKillAllBackgroundProcesses -> {
                val bgSet = ProcessUtils.getAllBackgroundProcesses()
                val killedSet = ProcessUtils.killAllBackgroundProcesses()
                SpanUtils.with(processAboutTv)
                        .appendLine("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName())
                        .appendLine("getAllBackgroundProcesses: " + getSetItems(bgSet))
                        .appendLine("size: " + bgSet.size)
                        .appendLine("killAllBackgroundProcesses: " + getSetItems(killedSet))
                        .appendLine("size: " + killedSet.size)
                        .appendLine("isMainProcess: " + ProcessUtils.isMainProcess())
                        .append("getCurrentProcessName: " + ProcessUtils.getCurrentProcessName())
                        .create()
            }
        }
    }

    private fun getSetItems(set: Set<String>): String {
        val iterator = set.iterator()
        val sb = StringBuilder()
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n")
        }
        return sb.toString()
    }
}
