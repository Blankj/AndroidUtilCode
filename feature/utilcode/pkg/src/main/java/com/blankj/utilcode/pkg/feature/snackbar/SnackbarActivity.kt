package com.blankj.utilcode.pkg.feature.snackbar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.annotation.StringRes
import android.text.SpannableStringBuilder
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/17
 * desc  : demo about SnackbarUtils
 * ```
 */
class SnackbarActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SnackbarActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_snackbar
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.snackbar_show_short) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_short))
                            .setMessageColor(Color.WHITE)
                            .setBgResource(R.drawable.snackbar_custom_bg)
                            .show()
                },
                CommonItemClick(R.string.snackbar_show_short_with_action) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_short))
                            .setMessageColor(Color.WHITE)
                            .setBgResource(R.drawable.snackbar_custom_bg)
                            .setAction(getString(R.string.snackbar_click), Color.YELLOW) { ToastUtils.showShort(getString(R.string.snackbar_click)) }
                            .show()
                },
                CommonItemClick(R.string.snackbar_show_long) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_long))
                            .setMessageColor(Color.WHITE)
                            .setDuration(SnackbarUtils.LENGTH_LONG)
                            .setBgResource(R.drawable.snackbar_custom_bg)
                            .show()
                },
                CommonItemClick(R.string.snackbar_show_long_with_action) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_long))
                            .setMessageColor(Color.WHITE)
                            .setBgResource(R.drawable.snackbar_custom_bg)
                            .setDuration(SnackbarUtils.LENGTH_LONG)
                            .setAction(getString(R.string.snackbar_click), Color.YELLOW) { ToastUtils.showShort(getString(R.string.snackbar_click)) }
                            .show()
                },
                CommonItemClick(R.string.snackbar_show_indefinite) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_indefinite))
                            .setMessageColor(Color.WHITE)
                            .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                            .setBgResource(R.drawable.snackbar_custom_bg)
                            .show()
                },
                CommonItemClick(R.string.snackbar_show_indefinite_with_action) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_indefinite))
                            .setMessageColor(Color.WHITE)
                            .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                            .setBgResource(R.drawable.snackbar_custom_bg)
                            .setAction(getString(R.string.snackbar_click), Color.YELLOW) { ToastUtils.showShort(getString(R.string.snackbar_click)) }
                            .show()
                },
                CommonItemClick(R.string.snackbar_add_view) {
                    val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    SnackbarUtils.with(mContentView)
                            .setBgColor(Color.TRANSPARENT)
                            .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                            .show()
                    SnackbarUtils.addView(R.layout.snackbar_custom, params)
                },
                CommonItemClick(R.string.snackbar_add_view_with_action) {
                    val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    SnackbarUtils.with(mContentView)
                            .setBgColor(Color.TRANSPARENT)
                            .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                            .show()
                    SnackbarUtils.addView(R.layout.snackbar_custom, params)
                    val snackbarView = SnackbarUtils.getView()
                    if (snackbarView != null) {
                        val tvSnackbarCustom = snackbarView.findViewById<TextView>(R.id.snackbarCustomTv)
                        tvSnackbarCustom.setText(R.string.snackbar_click_to_dismiss)
                        snackbarView.setOnClickListener { SnackbarUtils.dismiss() }
                    }
                },
                CommonItemClick(R.string.snackbar_show_success) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_success))
                            .showSuccess()
                },
                CommonItemClick(R.string.snackbar_show_warning) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_warning))
                            .showWarning()
                },
                CommonItemClick(R.string.snackbar_show_error) {
                    SnackbarUtils.with(mContentView)
                            .setMessage(getMsg(R.string.snackbar_error))
                            .showError()
                },
                CommonItemClick(R.string.snackbar_dismiss) {
                    SnackbarUtils.dismiss()
                }
        )
    }

    private fun getMsg(@StringRes resId: Int): SpannableStringBuilder {
        return SpanUtils()
                .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                .appendSpace(32)
                .append(getString(resId)).setFontSize(24, true)
                .create()
    }
}
