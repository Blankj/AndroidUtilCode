package com.blankj.utilcode.pkg.feature.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
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
class KeyboardActivity : BaseTitleBarActivity() {

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

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        KeyboardUtils.fixAndroidBug5497(this)
        keyboardHideSoftInputBtn.setOnClickListener(this)
        keyboardShowSoftInputBtn.setOnClickListener(this)
        keyboardToggleSoftInputBtn.setOnClickListener(this)
        keyboardInFragmentBtn.setOnClickListener(this)

        KeyboardUtils.registerSoftInputChangedListener(this) { height ->
            SpanUtils.with(keyboardAboutTv)
                    .appendLine("isSoftInputVisible: " + KeyboardUtils.isSoftInputVisible(this@KeyboardActivity))
                    .append("height: $height")
                    .create()
        }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.keyboardHideSoftInputBtn -> KeyboardUtils.hideSoftInput(this)
            R.id.keyboardShowSoftInputBtn -> KeyboardUtils.showSoftInput(inputEt)
            R.id.keyboardToggleSoftInputBtn -> KeyboardUtils.toggleSoftInput()
            R.id.keyboardInFragmentBtn -> {
                DialogHelper.showKeyboardDialog()
                KeyboardUtils.showSoftInput(this)
            }
        }
    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            val v = currentFocus
//            if (isShouldHideKeyboard(v, ev)) {
//                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                        ?: return super.dispatchTouchEvent(ev)
//                imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
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

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }
}
