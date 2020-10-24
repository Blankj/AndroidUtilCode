package com.blankj.utilcode.pkg.feature.toast

import android.support.annotation.StringRes
import android.widget.TextView
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.ViewUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/08/31
 * desc  : demo about ToastUtils
 * ```
 */
object CustomToast {

    fun showShort(text: CharSequence) {
        show(text, false)
    }

    fun showShort(@StringRes resId: Int) {
        show(StringUtils.getString(resId), false)
    }

    fun showShort(@StringRes resId: Int, vararg args: Any) {
        show(StringUtils.getString(resId, args), false)
    }

    fun showShort(format: String, vararg args: Any) {
        show(StringUtils.format(format, args), false)
    }

    fun showLong(text: CharSequence) {
        show(text, true)
    }

    fun showLong(@StringRes resId: Int) {
        show(StringUtils.getString(resId), true)
    }

    fun showLong(@StringRes resId: Int, vararg args: Any) {
        show(StringUtils.getString(resId, args), true)
    }

    fun showLong(format: String, vararg args: Any) {
        show(StringUtils.format(format, args), true)
    }

    private fun show(text: CharSequence, isLong: Boolean) {
        val textView = ViewUtils.layoutId2View(R.layout.toast_custom) as TextView
        textView.text = text
        ToastUtils.make().setDurationIsLong(isLong).show(textView)
    }

    fun cancel() {
        ToastUtils.cancel()
    }
}
