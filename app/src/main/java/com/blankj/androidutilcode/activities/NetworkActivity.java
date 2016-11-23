package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.App;
import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.NetworkUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Network工具类Demo
 * </pre>
 */
public class NetworkActivity extends Activity
        implements View.OnClickListener {

    private TextView tvAboutNetwork;
    private Context  mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        tvAboutNetwork = (TextView) findViewById(R.id.tv_about_network);
        mContext = App.getInstance();

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
        tvAboutNetwork.setText("isConnected: " + NetworkUtils.isConnected(mContext) +
                "\nisAvailableByPing: " + NetworkUtils.isAvailableByPing(mContext) +
                "\ngetDataEnabled: " + NetworkUtils.getDataEnabled(mContext) +
                "\nis4G: " + NetworkUtils.is4G(mContext) +
                "\ngetWifiEnabled: " + NetworkUtils.getWifiEnabled(mContext) +
                "\nisWifiConnected: " + NetworkUtils.isWifiConnected(mContext) +
                "\nisWifiAvailable: " + NetworkUtils.isWifiAvailable(mContext) +
                "\ngetNetworkOperatorName: " + NetworkUtils.getNetworkOperatorName(mContext) +
                "\ngetNetworkTypeName: " + NetworkUtils.getNetworkType(mContext) +
                "\ngetIPAddress: " + NetworkUtils.getIPAddress(true) +
                "\ngetDomainAddress: " + NetworkUtils.getDomainAddress("baidu.com")
        );
    }
}
