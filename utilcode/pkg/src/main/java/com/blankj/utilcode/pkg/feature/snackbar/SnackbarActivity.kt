package com.blankj.utilcode.pkg.feature.snackbar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.StringRes
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_snackbar.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/17
 * desc  : demo about SnackbarUtils
 * ```
 */
class SnackbarActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SnackbarActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var snackBarRootView: View

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_snackbar)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_snackbar
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        snackBarRootView = findViewById(android.R.id.content)
        snackbarShowShortBtn.setOnClickListener(this)
        snackbarShowShortWithActionBtn.setOnClickListener(this)
        snackbarShowLongBtn.setOnClickListener(this)
        snackbarShowLongWithActionBtn.setOnClickListener(this)
        snackbarShowIndefiniteBtn.setOnClickListener(this)
        snackbarShowIndefiniteWithActionBtn.setOnClickListener(this)
        snackbarAddViewBtn.setOnClickListener(this)
        snackbarAddViewWithActionBtn.setOnClickListener(this)
        snackbarShowSuccessBtn.setOnClickListener(this)
        snackbarShowWarningBtn.setOnClickListener(this)
        snackbarShowErrorBtn.setOnClickListener(this)
        snackbarDismissBtn.setOnClickListener(this)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.snackbarShowShortBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_short))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .show()
            R.id.snackbarShowShortWithActionBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_short))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW) { ToastUtils.showShort(getString(R.string.snackbar_click)) }
                    .show()
            R.id.snackbarShowLongBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_long))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .show()
            R.id.snackbarShowLongWithActionBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_long))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW) { ToastUtils.showShort(getString(R.string.snackbar_click)) }
                    .show()
            R.id.snackbarShowIndefiniteBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_indefinite))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .show()
            R.id.snackbarShowIndefiniteWithActionBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_indefinite))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .setBgResource(R.drawable.snackbar_custom_bg)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW) { ToastUtils.showShort(getString(R.string.snackbar_click)) }
                    .show()
            R.id.snackbarAddViewBtn -> {
                val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show()
                SnackbarUtils.addView(R.layout.snackbar_custom, params)

            }
            R.id.snackbarAddViewWithActionBtn -> {
                val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show()
                SnackbarUtils.addView(R.layout.snackbar_custom, params)
                val snackbarView = SnackbarUtils.getView()
                if (snackbarView != null) {
                    val tvSnackbarCustom = snackbarView.findViewById<TextView>(R.id.snackbarCustomTv)
                    tvSnackbarCustom.text = "点我可消失"
                    snackbarView.setOnClickListener { SnackbarUtils.dismiss() }
                }

            }
            R.id.snackbarShowSuccessBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_success))
                    .showSuccess()
            R.id.snackbarShowWarningBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_warning))
                    .showWarning()
            R.id.snackbarShowErrorBtn -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_error))
                    .showError()
            R.id.snackbarDismissBtn -> SnackbarUtils.dismiss()
        }
    }

    private fun getMsg(@StringRes resId: Int): SpannableStringBuilder {
        return SpanUtils()
                .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                .appendSpace(32)
                .append(getString(resId)).setFontSize(24, true)
                .create()
    }
}
