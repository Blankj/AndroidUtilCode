package com.blankj.utilcode.pkg.feature.bar.status.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.common.fragment.CommonFragment
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import kotlinx.android.synthetic.main.bar_status_image_view_fragment.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusFragmentImageView : CommonFragment() {

    companion object {
        fun newInstance(): BarStatusFragmentImageView {
            return BarStatusFragmentImageView()
        }
    }

    private var mAlpha: Int = 112

    override fun isLazy(): Boolean {
        return true
    }

    override fun bindLayout(): Int {
        return R.layout.bar_status_image_view_fragment
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
        updateFakeStatusBar()
    }

    private fun getItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemSeekBar("Status Bar Alpha", 255, object : CommonItemSeekBar.ProgressListener() {
                    override fun getCurValue(): Int {
                        return mAlpha
                    }

                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        mAlpha = progress
                        updateFakeStatusBar()
                    }
                })
        )
    }

    fun updateFakeStatusBar() {
        BarUtils.setStatusBarColor(barStatusImageViewFragmentFakeStatusBar, Color.argb(mAlpha, 0, 0, 0))
    }
}
