package com.blankj.utilcode.pkg.feature.toast

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_toast.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about ToastUtils
 * ```
 */
class ToastActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ToastActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_toast)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_toast
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        toastShowShortBtn.setOnClickListener(this)
        toastShowLongBtn.setOnClickListener(this)
        toastShowGreenFontBtn.setOnClickListener(this)
        toastShowBgColorBtn.setOnClickListener(this)
        toastShowBgResourceBtn.setOnClickListener(this)
        toastShowSpanBtn.setOnClickListener(this)
        toastShowCustomViewBtn.setOnClickListener(this)
        toastShowMiddleBtn.setOnClickListener(this)
        toastCancelBtn.setOnClickListener(this)
        toastShowToastDialogBtn.setOnClickListener(this)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        resetToast()
        when (view.id) {
            R.id.toastShowShortBtn -> Thread(Runnable { ToastUtils.showShort(R.string.toast_short) }).start()
            R.id.toastShowLongBtn -> Thread(Runnable { ToastUtils.showLong(R.string.toast_long) }).start()
            R.id.toastShowGreenFontBtn -> {
                ToastUtils.setMsgColor(Color.GREEN)
                ToastUtils.showLong(R.string.toast_green_font)
            }
            R.id.toastShowBgColorBtn -> {
                ToastUtils.setBgColor(ContextCompat.getColor(this, R.color.colorAccent))
                ToastUtils.showLong(R.string.toast_bg_color)
            }
            R.id.toastShowBgResourceBtn -> {
                ToastUtils.setBgResource(R.drawable.toast_shape_round_rect)
                ToastUtils.showLong(R.string.toast_custom_bg)
            }
            R.id.toastShowSpanBtn -> ToastUtils.showLong(
                    SpanUtils()
                            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                            .appendSpace(32)
                            .append(getString(R.string.toast_span)).setFontSize(24, true)
                            .create()
            )
            R.id.toastShowCustomViewBtn -> Thread(Runnable { CustomToast.showLong(R.string.toast_custom_view) }).start()
            R.id.toastShowMiddleBtn -> {
                ToastUtils.setGravity(Gravity.CENTER, 0, 0)
                ToastUtils.showLong(R.string.toast_middle)

            }
            R.id.toastCancelBtn -> ToastUtils.cancel()
            R.id.toastShowToastDialogBtn -> DialogHelper.showToastDialog()
        }
    }

    override fun onDestroy() {
        resetToast()
        super.onDestroy()
    }

    private fun resetToast() {
        ToastUtils.setMsgColor(-0x1000001)
        ToastUtils.setBgColor(-0x1000001)
        ToastUtils.setBgResource(-1)
        ToastUtils.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, resources.getDimensionPixelSize(R.dimen.offset_64))
    }
}
