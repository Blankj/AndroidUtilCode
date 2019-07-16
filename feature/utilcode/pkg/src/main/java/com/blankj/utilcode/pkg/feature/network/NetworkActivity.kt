package com.blankj.utilcode.pkg.feature.network

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_network.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about NetworkUtils
 * ```
 */
class NetworkActivity : CommonTitleActivity() {

    var cur: Int = 0
    var count: AtomicInteger = AtomicInteger();

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

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(networkOpenWirelessSettingsBtn)

        networkWifiEnabledCb.setOnCheckedChangeListener { buttonView, isChecked ->
            NetworkUtils.setWifiEnabled(isChecked)
            updateAboutNetwork()
        }
    }

    override fun onResume() {
        super.onResume()
        networkWifiEnabledCb.isChecked = NetworkUtils.getWifiEnabled()
        updateAboutNetwork()
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.networkOpenWirelessSettingsBtn -> NetworkUtils.openWirelessSettings()
        }
        updateAboutNetwork()
    }

    private lateinit var ipV4AddressAsyncTask: Utils.Task<String>
    private lateinit var ipV6AddressAsyncTask: Utils.Task<String>
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
                .appendLine("getBroadcastIpAddress: " + NetworkUtils.getBroadcastIpAddress())
                .appendLine("getIpAddressByWifi: " + NetworkUtils.getIpAddressByWifi())
                .appendLine("getGatewayByWifi: " + NetworkUtils.getGatewayByWifi())
                .appendLine("getNetMaskByWifi: " + NetworkUtils.getNetMaskByWifi())
                .append("getServerAddressByWifi: " + NetworkUtils.getServerAddressByWifi())
                .create()
        cur += 5

        ipV4AddressAsyncTask = NetworkUtils.getIPAddressAsync(true) { data ->
            val num = count.get()
            if (num >= cur - 5) {
                spanSb = SpanUtils().appendLine(spanSb)
                        .append("getIPv4Address: $data")
                        .create()
                networkAboutTv.text = spanSb
            }
            count.addAndGet(1)
        }

        ipV6AddressAsyncTask = NetworkUtils.getIPAddressAsync(false) { data ->
            val num = count.get()
            if (num >= cur - 5) {
                spanSb = SpanUtils().appendLine(spanSb)
                        .append("getIPv6Address: $data")
                        .create()
                networkAboutTv.text = spanSb
            }
            count.addAndGet(1)
        }

        wifiAvailableAsyncTask = NetworkUtils.isWifiAvailableAsync { data ->
            val num = count.get()
            if (num >= cur - 5) {
                spanSb = SpanUtils().appendLine(spanSb)
                        .append("isWifiAvailable: $data")
                        .create()
                networkAboutTv.text = spanSb
            }
            count.addAndGet(1)
        }

        availableAsyncTask = NetworkUtils.isAvailableAsync { data ->
            val num = count.get()
            if (num >= cur - 5) {
                spanSb = SpanUtils().appendLine(spanSb)
                        .append("isAvailable: $data")
                        .create()
                networkAboutTv.text = spanSb
            }
            count.addAndGet(1)
        }

        domainAddressAsyncTask = NetworkUtils.getDomainAddressAsync("baidu.com") { data ->
            val num = count.get()
            if (num >= cur - 5) {
                spanSb = SpanUtils().appendLine(spanSb)
                        .append("getBaiduDomainAddress: $data")
                        .create()
                networkAboutTv.text = spanSb
            }
            count.addAndGet(1)
        }
    }

    override fun onDestroy() {
        ipV4AddressAsyncTask.cancel()
        ipV6AddressAsyncTask.cancel()
        wifiAvailableAsyncTask.cancel()
        availableAsyncTask.cancel()
        domainAddressAsyncTask.cancel()
        super.onDestroy()
    }
}
