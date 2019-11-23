package com.blankj.utilcode.pkg.feature.network

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about NetworkUtils
 * ```
 */
class NetworkActivity : CommonActivity(), NetworkUtils.OnNetworkStatusChangedListener {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, NetworkActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var itemsTask: ThreadUtils.SimpleTask<List<CommonItem<*>>>

    override fun bindTitleRes(): Int {
        return R.string.demo_network
    }

    private fun getItemsTask(): ThreadUtils.SimpleTask<List<CommonItem<*>>> {
        itemsTask = object : ThreadUtils.SimpleTask<List<CommonItem<*>>>() {
            override fun doInBackground(): List<CommonItem<*>> {
                return bindItems()
            }

            override fun onSuccess(result: List<CommonItem<*>>) {
                dismissLoading()
                itemsView.updateItems(result)
            }
        }
        return itemsTask
    }

    override fun bindItems(): List<CommonItem<*>> {
        if (ThreadUtils.isMainThread()) return arrayListOf()
        return CollectionUtils.newArrayList(
                CommonItemTitle("isConnected", NetworkUtils.isConnected().toString()),
                CommonItemTitle("getMobileDataEnabled", NetworkUtils.getMobileDataEnabled().toString()),
                CommonItemTitle("isMobileData", NetworkUtils.isMobileData().toString()),
                CommonItemTitle("is4G", NetworkUtils.is4G().toString()),
                CommonItemTitle("isWifiConnected", NetworkUtils.isWifiConnected().toString()),
                CommonItemTitle("getNetworkOperatorName", NetworkUtils.getNetworkOperatorName()),
                CommonItemTitle("getNetworkTypeName", NetworkUtils.getNetworkType().toString()),
                CommonItemTitle("getBroadcastIpAddress", NetworkUtils.getBroadcastIpAddress()),
                CommonItemTitle("getIpAddressByWifi", NetworkUtils.getIpAddressByWifi()),
                CommonItemTitle("getGatewayByWifi", NetworkUtils.getGatewayByWifi()),
                CommonItemTitle("getNetMaskByWifi", NetworkUtils.getNetMaskByWifi()),
                CommonItemTitle("getServerAddressByWifi", NetworkUtils.getServerAddressByWifi()),

                CommonItemTitle("getIPv4Address", NetworkUtils.getIPAddress(true)),
                CommonItemTitle("getIPv6Address", NetworkUtils.getIPAddress(false)),
                CommonItemTitle("isWifiAvailable", NetworkUtils.isWifiAvailable().toString()),
                CommonItemTitle("isAvailable", NetworkUtils.isAvailable().toString()),
                CommonItemTitle("getBaiduDomainAddress", NetworkUtils.getDomainAddress("baidu.com")),

                CommonItemSwitch(
                        R.string.network_wifi_enabled,
                        Utils.Func1 {
                            NetworkUtils.getWifiEnabled()
                        },
                        Utils.Func1 {
                            NetworkUtils.setWifiEnabled(it)
                            ThreadUtils.executeByIo(getItemsTask())
                        }
                ),
                CommonItemClick(R.string.network_open_wireless_settings) {
                    NetworkUtils.openWirelessSettings()
                }
        )
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        NetworkUtils.registerNetworkStatusChangedListener(this)
        updateItems()
    }

    override fun onDisconnected() {
        ToastUtils.showLong("onDisconnected")
        updateItems()
    }

    override fun onConnected(networkType: NetworkUtils.NetworkType) {
        ToastUtils.showLong("onConnected: ${networkType.name}")
        updateItems()
    }

    private fun updateItems() {
        showLoading()
        ThreadUtils.executeByIo(getItemsTask())
    }

    override fun onDestroy() {
        super.onDestroy()
        ThreadUtils.cancel(itemsTask)
        NetworkUtils.unregisterNetworkStatusChangedListener(this)
    }
}
