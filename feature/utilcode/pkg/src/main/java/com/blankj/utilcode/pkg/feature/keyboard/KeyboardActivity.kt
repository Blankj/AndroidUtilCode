package com.blankj.utilcode.pkg.feature.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_keyboard.*

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

    override fun bindTitleRes(): Int {
        return R.string.demo_keyboard
    }

    override fun bindLayout(): Int {
        return R.layout.activity_keyboard
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        KeyboardUtils.fixAndroidBug5497(this)
        applyDebouncingClickListener(
                keyboardHideSoftInputBtn,
                keyboardShowSoftInputBtn,
                keyboardToggleSoftInputBtn,
                keyboardShowDialogBtn
        )

        KeyboardUtils.registerSoftInputChangedListener(this) { height ->
            SpanUtils.with(keyboardAboutTv)
                    .appendLine("isSoftInputVisible: " + KeyboardUtils.isSoftInputVisible(this@KeyboardActivity))
                    .append("height: $height")
                    .create()
            if (height > 0) {
                keyboardEt.requestFocus()
            }
        }
    }

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.keyboardHideSoftInputBtn -> {
                KeyboardUtils.hideSoftInput(this)
            }
            R.id.keyboardShowSoftInputBtn -> {
                KeyboardUtils.showSoftInput(this)
            }
            R.id.keyboardToggleSoftInputBtn -> {
                KeyboardUtils.toggleSoftInput()
            }
            R.id.keyboardShowDialogBtn -> {
                keyboardEt.clearFocus()
                DialogHelper.showKeyboardDialog()
            }
        }
    }
}
