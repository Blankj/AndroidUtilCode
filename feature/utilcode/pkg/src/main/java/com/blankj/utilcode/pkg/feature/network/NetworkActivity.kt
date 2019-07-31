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
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_network.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about NetworkUtils
 * ```
 */
class NetworkActivity : CommonTitleActivity(), NetworkUtils.OnNetworkStatusChangedListener {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, NetworkActivity::class.java)
            context.startActivity(starter)
        }
    }

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
        NetworkUtils.registerNetworkStatusChangedListener(this)
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
    }

    private lateinit var task: ThreadUtils.SimpleTask<String>

    private fun updateAboutNetwork() {

        SpanUtils.with(networkAboutTv)
                .append(getSpan())
                .appendLine("")
                .appendLine("")
                .appendLine("")
                .appendLine("")
                .appendLine("Loading...")
                .create()

        task = object : ThreadUtils.SimpleTask<String>() {

            override fun doInBackground(): String {
                val sb: StringBuilder = StringBuilder();
                sb.appendln("getIPv4Address: ${NetworkUtils.getIPAddress(true)}")
                sb.appendln("getIPv6Address: ${NetworkUtils.getIPAddress(false)}")
                sb.appendln("isWifiAvailable: ${NetworkUtils.isWifiAvailable()}")
                sb.appendln("isAvailable: ${NetworkUtils.isAvailable()}")
                sb.appendln("getBaiduDomainAddress: ${NetworkUtils.getDomainAddress("baidu.com")}")
                return sb.toString()
            }

            override fun onSuccess(result: String) {
                SpanUtils.with(networkAboutTv)
                        .append(getSpan())
                        .append(result)
                        .create()
            }
        }
        ThreadUtils.executeByCached(task)
    }

    private fun getSpan(): SpannableStringBuilder {
        return SpanUtils().appendLine("isConnected: " + NetworkUtils.isConnected())
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
                .appendLine("getServerAddressByWifi: " + NetworkUtils.getServerAddressByWifi())
                .create()
    }

    override fun onDisconnected() {
        ToastUtils.showLong("onDisconnected")
    }

    override fun onConnected(networkType: NetworkUtils.NetworkType) {


        ToastUtils.showLong("onConnected: ${networkType.name}")
    }

    override fun onDestroy() {
        task.cancel()
        NetworkUtils.unregisterNetworkStatusChangedListener(this)
        super.onDestroy()
    }
}
