package com.blankj.utilcode.pkg.feature.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ThreadUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about NetworkUtils
 * </pre>
 */
public class NetworkActivity extends BaseBackActivity {

    TextView               tvAboutNetwork;
    TextView               tvAboutNetworkAsync;
    ThreadUtils.SimpleTask mSimpleTask = new ThreadUtils.SimpleTask<String>() {
        @Override
        public String doInBackground() {
            return "isAvailableByPing: " + NetworkUtils.isAvailableByPing()
                    + "\ngetBaiduDomainAddress: " + NetworkUtils.getDomainAddress("baidu.com");
        }

        @Override
        public void onSuccess(String result) {
            tvAboutNetworkAsync.setText(result);
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_network;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_network);

        tvAboutNetwork = findViewById(R.id.tv_about_network);
        tvAboutNetworkAsync = findViewById(R.id.tv_about_network_async);
        findViewById(R.id.btn_open_wireless_settings).setOnClickListener(this);
        findViewById(R.id.btn_set_wifi_enabled).setOnClickListener(this);
        updateAboutNetwork();
    }

    @Override
    public void doBusiness() {
        ThreadUtils.executeBySingle(mSimpleTask);
    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_open_wireless_settings) {
            NetworkUtils.openWirelessSettings();

        } else if (i == R.id.btn_set_data_enabled) {
            NetworkUtils.setMobileDataEnabled(!NetworkUtils.getMobileDataEnabled());

        } else if (i == R.id.btn_set_wifi_enabled) {
            NetworkUtils.setWifiEnabled(!NetworkUtils.getWifiEnabled());

        }
        updateAboutNetwork();
    }

    private void updateAboutNetwork() {
        tvAboutNetwork.setText(new SpanUtils()
                .appendLine("isConnected: " + NetworkUtils.isConnected())
                .appendLine("getMobileDataEnabled: " + NetworkUtils.getMobileDataEnabled())
                .appendLine("isMobileData: " + NetworkUtils.isMobileData())
                .appendLine("is4G: " + NetworkUtils.is4G())
                .appendLine("getWifiEnabled: " + NetworkUtils.getWifiEnabled())
                .appendLine("isWifiConnected: " + NetworkUtils.isWifiConnected())
                .appendLine("isWifiAvailable: " + NetworkUtils.isWifiAvailable())
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
        );
    }

    @Override
    protected void onDestroy() {
        mSimpleTask.cancel();
        super.onDestroy();
    }
}
