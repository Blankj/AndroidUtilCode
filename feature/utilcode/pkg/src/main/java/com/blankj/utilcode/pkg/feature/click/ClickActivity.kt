package com.blankj.utilcode.pkg.feature.click

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.blankj.common.activity.CommonActivity
import com.blankj.common.activity.CommonActivityTitleView
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_click.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about CleanUtils
 * ```
 */
class ClickActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ClickActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_click
    }

    override fun bindLayout(): Int {
        return R.layout.activity_click
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                clickViewScaleDefaultBtn,
                clickViewScaleCustomBtn,
                clickViewAlphaDefaultBtn,
                clickBgAlphaDefaultBtn,
                clickBgDarkDefaultBtn
        )

        ClickUtils.applyPressedViewScale(clickViewScaleDefaultBtn)
        ClickUtils.applyPressedViewScale(clickViewScaleCustomBtn, -0.5f)
        ClickUtils.applyPressedViewAlpha(clickViewAlphaDefaultBtn)
        ClickUtils.applyPressedBgAlpha(clickBgAlphaDefaultBtn)
        ClickUtils.applyPressedBgDark(clickBgDarkDefaultBtn)

        ClickUtils.applySingleDebouncing(clickSingleDebouncingBtn, 5000) {
            SnackbarUtils.with(mContentView)
                    .setMessage(StringUtils.getString(R.string.click_single_tip))
                    .setBgColor(ColorUtils.getRandomColor(false))
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .show()
        }
        ClickUtils.applyGlobalDebouncing(clickGlobalDebouncingBtn, 5000) {
            SnackbarUtils.with(mContentView)
                    .setMessage(StringUtils.getString(R.string.click_global_tip))
                    .setBgColor(ColorUtils.getRandomColor(false))
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .show()
        }
        clickMultiBtn.setOnClickListener(object : ClickUtils.OnMultiClickListener(5) {
            override fun onTriggerClick(v: View) {
                ToastUtils.showShort("onTriggerClick")
            }

            override fun onBeforeTriggerClick(v: View, count: Int) {
                ToastUtils.showShort(count)
            }
        })
    }

    override fun onDebouncingClick(view: View) {
        SnackbarUtils.with(mContentView)
                .setMessage((view as Button).text)
                .setBgColor(ColorUtils.getRandomColor(false))
                .setDuration(SnackbarUtils.LENGTH_LONG)
                .show()
    }
}
