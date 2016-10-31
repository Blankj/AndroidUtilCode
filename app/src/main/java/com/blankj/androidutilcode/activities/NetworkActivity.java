package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.NetworkUtils;

public class NetworkActivity extends Activity
        implements View.OnClickListener {

    TextView tvAboutNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        tvAboutNetwork = (TextView) findViewById(R.id.tv_about_network);

        findViewById(R.id.btn_open_wireless_settings).setOnClickListener(this);
        findViewById(R.id.btn_set_wifi_enabled).setOnClickListener(this);

        setAboutNetwork();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_wireless_settings:
                NetworkUtils.openWirelessSettings(this);
                break;
            case R.id.btn_set_data_enabled:
                NetworkUtils.setDataEnabled(this, !NetworkUtils.getDataEnabled(this));
                break;
            case R.id.btn_set_wifi_enabled:
                NetworkUtils.setWifiEnabled(this, !NetworkUtils.getWifiEnabled(this));
                break;
        }
        setAboutNetwork();
    }

    private void setAboutNetwork() {
        tvAboutNetwork.setText("isConnected: " + NetworkUtils.isConnected(this) +
                "\nisAvailableByPing: " + NetworkUtils.isAvailableByPing(this) +
                "\ngetDataEnabled: " + NetworkUtils.getDataEnabled(this) +
                "\nis4G: " + NetworkUtils.is4G(this) +
                "\ngetWifiEnabled: " + NetworkUtils.getWifiEnabled(this) +
                "\nisWifiConnected: " + NetworkUtils.isWifiConnected(this) +
                "\nisWifiAvailable: " + NetworkUtils.isWifiAvailable(this) +
                "\ngetNetworkOperatorName: " + NetworkUtils.getNetworkOperatorName(this) +
                "\ngetNetworkTypeName: " + NetworkUtils.getNetworkTypeName(this) +
                "\ngetIPAddress: " + NetworkUtils.getIPAddress(true) +
                "\ngetDomainAddress: " + NetworkUtils.getDomainAddress("baidu.com")
        );
    }
}
