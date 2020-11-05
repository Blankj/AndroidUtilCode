package com.blankj.utilcode.pkg.feature.clipboard

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2020/09/11
 * desc  : demo about ClipboardUtils
 * ```
 */
class ClipboardActivity : CommonActivity() {

    private var index: Int = 0
    private var isAddListener: Boolean = false
    private var listener = {
        ToastUtils.showShort(ClipboardUtils.getText())
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ClipboardActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_clipboard
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("getText", ClipboardUtils.getText()),
                CommonItemTitle("getLabel", ClipboardUtils.getLabel()),
                CommonItemClick("copyText: value{$index}").setOnItemClickListener { _, _, _ ->
                    ClipboardUtils.copyText("value{${index++}}")
                    itemsView.updateItems(bindItems())
                },
                CommonItemClick("clear").setOnItemClickListener { _, _, _ ->
                    ClipboardUtils.clear()
                    itemsView.updateItems(bindItems())
                },
                CommonItemSwitch("clipChangeListener", { isAddListener }, {
                    isAddListener = it
                    if (isAddListener) {
                        ClipboardUtils.addChangedListener(listener)
                    } else {
                        ClipboardUtils.removeChangedListener(listener)
                    }
                })
        )
    }

    override fun onResume() {
        super.onResume()
        itemsView.updateItems(bindItems())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isAddListener) {
            ClipboardUtils.removeChangedListener(listener)
        }
    }
}
