package com.blankj.utilcode.pkg.feature.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.keyboard_activity.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : demo about KeyboardUtils
 * ```
 */
class KeyboardActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, KeyboardActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var titleItem: CommonItemTitle = CommonItemTitle("", true)

    override fun bindTitleRes(): Int {
        return R.string.demo_keyboard
    }

    override fun bindLayout(): Int {
        return R.layout.keyboard_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        KeyboardUtils.fixAndroidBug5497(this)
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
        KeyboardUtils.registerSoftInputChangedListener(this) { height ->
            titleItem.title = "isSoftInputVisible: " + KeyboardUtils.isSoftInputVisible(this@KeyboardActivity) + "\nkeyboardHeight: $height"
            if (height > 0) {
                keyboardEt.requestFocus()
            }
        }
    }

    private fun getItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                titleItem,
                CommonItemClick(R.string.keyboard_hide_soft_input) {
                    KeyboardUtils.hideSoftInput(this)
                },
                CommonItemClick(R.string.keyboard_show_soft_input) {
                    KeyboardUtils.showSoftInput(this)
                },
                CommonItemClick(R.string.keyboard_toggle_soft_input) {
                    KeyboardUtils.toggleSoftInput()
                },
                CommonItemClick(R.string.keyboard_show_dialog) {
                    keyboardEt.clearFocus()
                    DialogHelper.showKeyboardDialog()
                }
        )
    }
}
