package com.blankj.utilcode.pkg.feature.toast

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.Gravity
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about ToastUtils
 * ```
 */
class ToastActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ToastActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_toast
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.toast_show_short) {
                    resetToast()
                    Thread(Runnable { ToastUtils.showShort(R.string.toast_short) }).start()
                },
                CommonItemClick(R.string.toast_show_long) {
                    resetToast()
                    Thread(Runnable { ToastUtils.showLong(R.string.toast_long) }).start()
                },
                CommonItemClick(R.string.toast_show_green_font) {
                    resetToast()
                    ToastUtils.setMsgColor(Color.GREEN)
                    ToastUtils.showLong(R.string.toast_green_font)
                },
                CommonItemClick(R.string.toast_show_bg_color) {
                    resetToast()
                    ToastUtils.setBgColor(ContextCompat.getColor(this, R.color.colorAccent))
                    ToastUtils.showLong(R.string.toast_bg_color)
                },
                CommonItemClick(R.string.toast_show_bg_resource) {
                    resetToast()
                    ToastUtils.setBgResource(R.drawable.toast_round_rect)
                    ToastUtils.showLong(R.string.toast_custom_bg)
                },
                CommonItemClick(R.string.toast_show_span) {
                    resetToast()
                    ToastUtils.showLong(
                            SpanUtils()
                                    .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                                    .appendSpace(32)
                                    .append(getString(R.string.toast_span)).setFontSize(24, true)
                                    .create()
                    )
                },
                CommonItemClick(R.string.toast_show_custom_view) {
                    resetToast()
                    Thread(Runnable { CustomToast.showLong(R.string.toast_custom_view) }).start()
                },
                CommonItemClick(R.string.toast_show_middle) {
                    resetToast()
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0)
                    ToastUtils.showLong(R.string.toast_middle)
                },
                CommonItemClick(R.string.toast_cancel) {
                    ToastUtils.cancel()
                },
                CommonItemClick(R.string.toast_show_toast_dialog) {
                    resetToast()
                    DialogHelper.showToastDialog()
                }
        )
    }

    override fun onDestroy() {
        resetToast()
        super.onDestroy()
    }

    private fun resetToast() {
        ToastUtils.setMsgColor(-0x1000001)
        ToastUtils.setBgColor(-0x1000001)
        ToastUtils.setBgResource(-1)
        ToastUtils.setGravity(-1, -1, -1)
    }
}
