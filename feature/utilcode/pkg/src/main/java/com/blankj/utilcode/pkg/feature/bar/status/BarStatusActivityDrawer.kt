package com.blankj.utilcode.pkg.feature.bar.status

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.common.item.CommonItemSwitch
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.bar_status_drawer_activity.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusActivityDrawer : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivityDrawer::class.java)
            context.startActivity(starter)
        }
    }

    private var mColor: Int = ColorUtils.getColor(R.color.colorPrimary)
    private var mAlpha: Int = 112

    private var mAlphaStatus: Boolean = false
    private var mFrontStatus: Boolean = false

    override fun isSwipeBack(): Boolean {
        return false
    }

    override fun bindDrawer(): Boolean {
        return true
    }

    override fun bindLayout(): Int {
        return R.layout.bar_status_drawer_activity
    }

    private fun getItems(): MutableList<CommonItem<*>> {
        val randomColorItem = CommonItemClick(R.string.bar_status_random_color, ColorUtils.int2ArgbString(mColor)).setOnClickUpdateContentListener {
            mColor = ColorUtils.getRandomColor()
            updateStatusBar()
            return@setOnClickUpdateContentListener ColorUtils.int2ArgbString(mColor)
        }

        val alphaItem: CommonItem<*> = CommonItemSeekBar("Status Bar Alpha", 255, mAlpha, object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mAlpha = progress
                updateStatusBar()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return CollectionUtils.newArrayList(
                CommonItemSwitch(R.string.bar_status_title_alpha, Utils.Func1 {
                    updateStatusBar()
                    return@Func1 mAlphaStatus
                }, Utils.Func1 {
                    mAlphaStatus = it
                    if (mAlphaStatus) {
                        barStatusDrawerRootLl.setBackgroundResource(R.drawable.image_lena)
                        commonItemAdapter.replaceItem(2, alphaItem, true)
                    } else {
                        barStatusDrawerRootLl.setBackgroundColor(Color.TRANSPARENT)
                        commonItemAdapter.replaceItem(2, randomColorItem, true)
                    }
                }),
                CommonItemSwitch(R.string.bar_status_is_front, Utils.Func1 {
                    return@Func1 mFrontStatus
                }, Utils.Func1 {
                    mFrontStatus = it
                    updateStatusBar()
                }),
                randomColorItem
        )
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
    }

    private fun updateStatusBar() {
        if (mAlphaStatus) {
            BarUtils.setStatusBarColor4Drawer(drawerView.mBaseDrawerRootLayout, barStatusDrawerFakeStatusBar, Color.argb(mAlpha, 0, 0, 0), mFrontStatus)
        } else {
            BarUtils.setStatusBarColor4Drawer(drawerView.mBaseDrawerRootLayout, barStatusDrawerFakeStatusBar, mColor, mFrontStatus)
        }
    }
}
