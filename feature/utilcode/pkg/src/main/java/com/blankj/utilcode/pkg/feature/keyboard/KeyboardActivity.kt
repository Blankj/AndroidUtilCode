package com.blankj.utilcode.pkg.feature.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.blankj.common.CommonTitleActivity
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
class KeyboardActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, KeyboardActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_keyboard)
    }

    override fun initData(bundle: Bundle?) {}

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

    override fun doBusiness() {}

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

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            val v = currentFocus
//            if (isShouldHideKeyboard(v, ev)) {
//                KeyboardUtils.hideSoftInput(this);
//            }
//        }
//        return super.dispatchTouchEvent(ev)
//    }
//
//    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
//    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
//        if (v != null && v is EditText) {
//            val l = intArrayOf(0, 0)
//            v.getLocationInWindow(l)
//            val left = l[0]
//            val top = l[1]
//            val bottom = top + v.height
//            val right = left + v.width
//            return !(event.x > left && event.x < right
//                    && event.y > top && event.y < bottom)
//        }
//        return false
//    }
}
