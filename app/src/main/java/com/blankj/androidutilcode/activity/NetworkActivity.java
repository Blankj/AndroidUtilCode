package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.NetworkUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Network工具类Demo
 * </pre>
 */
public class NetworkActivity extends BaseActivity {

    private TextView tvAboutNetwork;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_network;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvAboutNetwork = (TextView) findViewById(R.id.tv_about_network);
        findViewById(R.id.btn_open_wireless_settings).setOnClickListener(this);
        findViewById(R.id.btn_set_wifi_enabled).setOnClickListener(this);
        setAboutNetwork();
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_wireless_settings:
                NetworkUtils.openWirelessSettings();
                break;
            case R.id.btn_set_data_enabled:
                NetworkUtils.setDataEnabled(!NetworkUtils.getDataEnabled());
                break;
            case R.id.btn_set_wifi_enabled:
                NetworkUtils.setWifiEnabled(!NetworkUtils.getWifiEnabled());
                break;
        }
        setAboutNetwork();
    }

    private void setAboutNetwork() {
        tvAboutNetwork.setText("isConnected: " + NetworkUtils.isConnected()
                + "\nisAvailableByPing: " + NetworkUtils.isAvailableByPing()
                + "\ngetDataEnabled: " + NetworkUtils.getDataEnabled()
                + "\nis4G: " + NetworkUtils.is4G()
                + "\ngetWifiEnabled: " + NetworkUtils.getWifiEnabled()
                + "\nisWifiConnected: " + NetworkUtils.isWifiConnected()
                + "\nisWifiAvailable: " + NetworkUtils.isWifiAvailable()
                + "\nisAvailableByPing: " + NetworkUtils.isAvailableByPing()
                + "\ngetNetworkOperatorName: " + NetworkUtils.getNetworkOperatorName()
                + "\ngetNetworkTypeName: " + NetworkUtils.getNetworkType()
                + "\ngetIPAddress: " + NetworkUtils.getIPAddress(true)
                + "\ngetDomainAddress: " + NetworkUtils.getDomainAddress("baidu.com")
        );
    }
}
