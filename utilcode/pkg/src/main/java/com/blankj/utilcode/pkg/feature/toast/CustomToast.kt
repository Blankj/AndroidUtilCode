package com.blankj.utilcode.pkg.feature.toast

import android.os.Handler
import android.os.Looper
import android.support.annotation.StringRes
import android.widget.TextView
import android.widget.Toast

import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/08/31
 * desc  : demo about ToastUtils
 * ```
 */
object CustomToast {

    private val HANDLER = Handler(Looper.getMainLooper())

    fun showShort(text: CharSequence) {
        showReal(text, Toast.LENGTH_SHORT)
    }

    fun showShort(@StringRes resId: Int) {
        show(resId, Toast.LENGTH_SHORT)
    }

    fun showShort(@StringRes resId: Int, vararg args: Any) {
        show(resId, Toast.LENGTH_SHORT, *args)
    }

    fun showShort(format: String, vararg args: Any) {
        show(format, Toast.LENGTH_SHORT, *args)
    }

    fun showLong(text: CharSequence) {
        showReal(text, Toast.LENGTH_LONG)
    }

    fun showLong(@StringRes resId: Int) {
        show(resId, Toast.LENGTH_LONG)
    }

    fun showLong(@StringRes resId: Int, vararg args: Any) {
        show(resId, Toast.LENGTH_LONG, *args)
    }

    fun showLong(format: String, vararg args: Any) {
        show(format, Toast.LENGTH_LONG, *args)
    }

    private fun show(@StringRes resId: Int, duration: Int) {
        show(Utils.getApp().resources.getString(resId), duration)
    }

    private fun show(@StringRes resId: Int, duration: Int, vararg args: Any) {
        show(String.format(Utils.getApp().resources.getString(resId), *args), duration)
    }

    private fun show(format: String, duration: Int, vararg args: Any) {
        showReal(String.format(format, *args), duration)
    }

    private fun showReal(text: CharSequence, duration: Int) {
        HANDLER.post {
            val toastView: TextView
            if (duration == Toast.LENGTH_SHORT) {
                toastView = ToastUtils.showCustomShort(R.layout.toast_custom) as TextView
            } else {
                toastView = ToastUtils.showCustomLong(R.layout.toast_custom) as TextView
            }
            toastView.text = text
        }
    }

    fun cancel() {
        ToastUtils.cancel()
    }
}
