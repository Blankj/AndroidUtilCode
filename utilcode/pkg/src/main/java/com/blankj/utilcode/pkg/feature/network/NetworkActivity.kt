package com.blankj.utilcode.pkg.feature.network

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_network.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about NetworkUtils
 * ```
 */
class NetworkActivity : BaseTitleBarActivity() {

    var cur: Int = 0
    var count: Int = 0;

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, NetworkActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var spanSb: SpannableStringBuilder

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_network)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_network
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        networkOpenWirelessSettingsBtn.setOnClickListener(this)

        if (AppUtils.isAppSystem()) {
            networkMobileDataEnabledCb.setOnCheckedChangeListener { buttonView, isChecked ->
                NetworkUtils.setMobileDataEnabled(isChecked)
                updateAboutNetwork()
            }
        } else {
            networkMobileDataEnabledCb.isEnabled = false
        }

        networkWifiEnabledCb.setOnCheckedChangeListener { buttonView, isChecked ->
            NetworkUtils.setWifiEnabled(isChecked)
            updateAboutNetwork()
        }
    }

    override fun onResume() {
        super.onResume()
        networkMobileDataEnabledCb.isChecked = NetworkUtils.getMobileDataEnabled()
        networkWifiEnabledCb.isChecked = NetworkUtils.getWifiEnabled()
        updateAboutNetwork()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.networkOpenWirelessSettingsBtn -> NetworkUtils.openWirelessSettings()
        }
        updateAboutNetwork()
    }

    private lateinit var wifiAvailableAsyncTask: Utils.Task<Boolean>
    private lateinit var availableAsyncTask: Utils.Task<Boolean>
    private lateinit var domainAddressAsyncTask: Utils.Task<String>

    private fun updateAboutNetwork() {
        spanSb = SpanUtils.with(networkAboutTv)
                .appendLine("isConnected: " + NetworkUtils.isConnected())
                .appendLine("getMobileDataEnabled: " + NetworkUtils.getMobileDataEnabled())
                .appendLine("isMobileData: " + NetworkUtils.isMobileData())
                .appendLine("is4G: " + NetworkUtils.is4G())
                .appendLine("getWifiEnabled: " + NetworkUtils.getWifiEnabled())
                .appendLine("isWifiConnected: " + NetworkUtils.isWifiConnected())
                .appendLine("getNetworkOperatorName: " + NetworkUtils.getNetworkOperatorName())
                .appendLine("getNetworkTypeName: " + NetworkUtils.getNetworkType())
                .appendLine("getIPv4Address: " + NetworkUtils.getIPAddress(true))
                .appendLine("getIPv6Address: " + NetworkUtils.getIPAddress(false))
                .appendLine("getBroadcastIpAddress: " + NetworkUtils.getBroadcastIpAddress())
                .appendLine("getIpAddressByWifi: " + NetworkUtils.getIpAddressByWifi())
                .appendLine("getGatewayByWifi: " + NetworkUtils.getGatewayByWifi())
                .appendLine("getNetMaskByWifi: " + NetworkUtils.getNetMaskByWifi())
                .append("getServerAddressByWifi: " + NetworkUtils.getServerAddressByWifi())
                .create()

        wifiAvailableAsyncTask = NetworkUtils.isWifiAvailableAsync { data ->
            spanSb = SpanUtils().appendLine(spanSb)
                    .append("isWifiAvailable: $data")
                    .create()
            networkAboutTv.text = spanSb
        }

        availableAsyncTask = NetworkUtils.isAvailableAsync { data ->
            spanSb = SpanUtils().appendLine(spanSb)
                    .append("isAvailable: $data")
                    .create()
            networkAboutTv.text = spanSb
        }

        domainAddressAsyncTask = NetworkUtils.getDomainAddressAsync("baidu.com") { data ->
            spanSb = SpanUtils().appendLine(spanSb)
                    .append("getBaiduDomainAddress: $data")
                    .create()
            networkAboutTv.text = spanSb
        }
    }

    override fun onDestroy() {
        wifiAvailableAsyncTask.cancel()
        availableAsyncTask.cancel()
        domainAddressAsyncTask.cancel()
        super.onDestroy()
    }
}
