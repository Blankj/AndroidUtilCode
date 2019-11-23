package com.blankj.utilcode.pkg.feature.bar.status

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusActivityImageView : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivityImageView::class.java)
            context.startActivity(starter)
        }
    }

    private var mAlpha: Int = 112

    override fun bindLayout(): Int {
        return R.layout.bar_status_image_view_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
        updateStatusBar()
    }

    private fun getItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemSeekBar("Status Bar Alpha", 255, mAlpha, object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        mAlpha = progress
                        updateStatusBar()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
        )
    }

    private fun updateStatusBar() {
        BarUtils.setStatusBarColor(this, Color.argb(mAlpha, 0, 0, 0), true)
    }
}
