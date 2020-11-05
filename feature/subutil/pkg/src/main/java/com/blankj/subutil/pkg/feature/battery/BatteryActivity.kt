package com.blankj.subutil.pkg.feature.battery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.BatteryUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 20/03/31
 * desc  : demo about Battery
 * ```
 */
class BatteryActivity : CommonActivity(), BatteryUtils.OnBatteryStatusChangedListener {

    private val titleItem: CommonItemTitle = CommonItemTitle("", true);

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BatteryActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_battery
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(titleItem)
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        BatteryUtils.registerBatteryStatusChangedListener(this)
    }

    override fun onBatteryStatusChanged(status: BatteryUtils.Status) {
        titleItem.title = status.toString()
        ToastUtils.showShort(status.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        BatteryUtils.unregisterBatteryStatusChangedListener(this)
    }
}
