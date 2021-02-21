package com.blankj.utilcode.pkg.feature.toast

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ColorUtils
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
                    Thread(Runnable { ToastUtils.showShort(R.string.toast_short) }).start()
                },
                CommonItemClick(R.string.toast_show_long) {
                    Thread(Runnable { ToastUtils.showLong(R.string.toast_long) }).start()
                },
                CommonItemClick(R.string.toast_show_null) {
                    ToastUtils.showLong(null)
                },
                CommonItemClick(R.string.toast_show_empty) {
                    ToastUtils.showLong("")
                },
                CommonItemClick(R.string.toast_show_span) {
                    ToastUtils.showLong(
                            SpanUtils()
                                    .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                                    .appendSpace(32)
                                    .append(getString(R.string.toast_span)).setFontSize(24, true)
                                    .create()
                    )
                },
                CommonItemClick(R.string.toast_show_long_string) {
                    ToastUtils.showLong(R.string.toast_long_string)
                },
                CommonItemClick(R.string.toast_show_green_font) {
                    ToastUtils.make().setTextColor(Color.GREEN).setDurationIsLong(true).show(R.string.toast_green_font)
                },
                CommonItemClick(R.string.toast_show_bg_color) {
                    ToastUtils.make().setBgColor(ColorUtils.getColor(R.color.colorAccent)).show(R.string.toast_bg_color)
                },
                CommonItemClick(R.string.toast_show_bg_resource) {
                    ToastUtils.make().setBgResource(R.drawable.toast_round_rect).show(R.string.toast_custom_bg)
                },
                CommonItemClick(R.string.toast_show_left_icon) {
                    ToastUtils.make().setLeftIcon(R.mipmap.ic_launcher).show(R.string.toast_show_left_icon)
                },
                CommonItemClick(R.string.toast_show_dark_mode) {
                    ToastUtils.make().setTopIcon(R.mipmap.ic_launcher).setMode(ToastUtils.MODE.DARK).show(R.string.toast_show_dark_mode)
                },
                CommonItemClick(R.string.toast_show_middle) {
                    ToastUtils.make().setGravity(Gravity.CENTER, 0, 0).show(R.string.toast_middle)
                },
                CommonItemClick(R.string.toast_show_top) {
                    ToastUtils.make().setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0).show(R.string.toast_top)
                },
                CommonItemClick(R.string.toast_show_custom_view) {
                    Thread(Runnable { CustomToast.showLong(R.string.toast_custom_view) }).start()
                },
                CommonItemClick(R.string.toast_cancel) {
                    ToastUtils.cancel()
                },
                CommonItemClick(R.string.toast_show_toast_dialog) {
                    DialogHelper.showToastDialog()
                },
                CommonItemClick(R.string.toast_show_toast_when_start_activity) {
                    ToastUtils.showLong(R.string.toast_show_toast_when_start_activity)
                    start(this)
                }
        )
    }
}
