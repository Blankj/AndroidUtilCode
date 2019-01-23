package com.blankj.utilcode.pkg.feature.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseBackActivity
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
class KeyboardActivity : BaseBackActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, KeyboardActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData(bundle: Bundle?) {

    }

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

    override fun doBusiness() {

    }

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

    //    @Override
    //    public boolean dispatchTouchEvent(MotionEvent ev) {
    //        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
    //            View v = getCurrentFocus();
    //            if (isShouldHideKeyboard(v, ev)) {
    //                InputMethodManager imm =
    //                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    //                if (imm == null) return super.dispatchTouchEvent(ev);
    //                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    //            }
    //        }
    //        return super.dispatchTouchEvent(ev);
    //    }
    //
    //    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    //    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
    //        if (v != null && (v instanceof EditText)) {
    //            int[] l = {0, 0};
    //            v.getLocationInWindow(l);
    //            int left = l[0],
    //                    top = l[1],
    //                    bottom = top + v.getHeight(),
    //                    right = left + v.getWidth();
    //            return !(event.getX() > left && event.getX() < right
    //                    && event.getY() > top && event.getY() < bottom);
    //        }
    //        return false;
    //    }

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }
}
