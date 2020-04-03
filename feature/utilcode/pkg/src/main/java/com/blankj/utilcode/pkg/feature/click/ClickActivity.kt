package com.blankj.utilcode.pkg.feature.click

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import android.view.View
import android.widget.TextView
import com.blankj.base.rv.ItemViewHolder
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about ClickUtils
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

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                ClickItem(R.string.click_view_scale_default, Utils.Consumer {
                    ClickUtils.applyPressedViewScale(it)
                }),
                ClickItem(R.string.click_view_scale_half, Utils.Consumer {
                    ClickUtils.applyPressedViewScale(it, -0.5f)
                }),
                ClickItem(R.string.click_view_alpha_default, Utils.Consumer {
                    ClickUtils.applyPressedViewAlpha(it)
                }),
                ClickItem(R.string.click_bg_alpha_default, Utils.Consumer {
                    ClickUtils.applyPressedBgAlpha(it, 0.6f)
                }),
                ClickItem(R.string.click_bg_dark_default, Utils.Consumer {
                    ClickUtils.applyPressedBgDark(it)
                }),
                ClickItem(R.string.click_single_debouncing, Utils.Consumer {
                    ClickUtils.applyPressedBgDark(it)
                    ClickUtils.applySingleDebouncing(it, 5000) {
                        SnackbarUtils.with(mContentView)
                                .setMessage(StringUtils.getString(R.string.click_single_tip))
                                .setBgColor(ColorUtils.getRandomColor(false))
                                .setDuration(SnackbarUtils.LENGTH_LONG)
                                .show()
                    }
                }),
                ClickItem(R.string.click_global_debouncing, Utils.Consumer {
                    ClickUtils.applyPressedBgDark(it)
                    ClickUtils.applySingleDebouncing(it, 5000) {
                        SnackbarUtils.with(mContentView)
                                .setMessage(StringUtils.getString(R.string.click_global_tip))
                                .setBgColor(ColorUtils.getRandomColor(false))
                                .setDuration(SnackbarUtils.LENGTH_LONG)
                                .show()
                    }
                }),
                ClickItem(R.string.click_multi, Utils.Consumer {
                    ClickUtils.applyPressedBgDark(it)
                    it.setOnClickListener(object : ClickUtils.OnMultiClickListener(5) {
                        override fun onTriggerClick(v: View) {
                            ToastUtils.showShort("onTriggerClick")
                        }

                        override fun onBeforeTriggerClick(v: View, count: Int) {
                            ToastUtils.showShort(count)
                        }
                    })
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        SnackbarUtils.dismiss()
    }
}

class ClickItem : CommonItem<ClickItem> {

    private val mConsumer: Utils.Consumer<View>;
    private val mTitle: String

    constructor(@StringRes title: Int, consumer: Utils.Consumer<View>) : super(R.layout.common_item_title_click) {
        mConsumer = consumer
        mTitle = StringUtils.getString(title)
    }

    override fun bind(holder: ItemViewHolder, position: Int) {
        super.bind(holder, position)
        holder.findViewById<TextView>(R.id.commonItemTitleTv).text = mTitle
        holder.itemView.setOnClickListener() {
            SnackbarUtils.with(it)
                    .setMessage(mTitle)
                    .setBgColor(ColorUtils.getRandomColor(false))
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .show()
        }
        mConsumer.accept(holder.itemView)
    }
}
