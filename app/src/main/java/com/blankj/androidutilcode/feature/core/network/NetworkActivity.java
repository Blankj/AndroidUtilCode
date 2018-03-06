package com.blankj.androidutilcode.feature.core.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.subutil.util.ThreadPoolUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Network 工具类 Demo
 * </pre>
 */
public class NetworkActivity extends BaseBackActivity {

    TextView tvAboutNetwork;
    TextView tvAboutNetworkAsync;
    ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils(ThreadPoolUtils.SingleThread, 1);

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String text = tvAboutNetworkAsync.getText().toString();
            if (text.length() != 0) {
                text += '\n';
            }
            if (msg.what == 1) {
                tvAboutNetworkAsync.setText(new SpanUtils()
                        .append(text + "isAvailableByPing: " + msg.obj)
                        .create()
                );
            } else {
                tvAboutNetworkAsync.setText(new SpanUtils()
                        .append(text + "getDomainAddress: " + msg.obj)
                        .create()
                );
            }
            return true;
        }
    });

    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_network;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_network));

        tvAboutNetwork = findViewById(R.id.tv_about_network);
        tvAboutNetworkAsync = findViewById(R.id.tv_about_network_async);
        findViewById(R.id.btn_open_wireless_settings).setOnClickListener(this);
        findViewById(R.id.btn_set_wifi_enabled).setOnClickListener(this);
        updateAboutNetwork();
    }

    @Override
    public void doBusiness() {
        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = NetworkUtils.isAvailableByPing();
                mHandler.sendMessage(msg);
            }
        });

        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 2;
                msg.obj = NetworkUtils.getDomainAddress("baidu.com");
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_wireless_settings:
                NetworkUtils.openWirelessSettings();
                break;
            case R.id.btn_set_data_enabled:
                NetworkUtils.setMobileDataEnabled(!NetworkUtils.getMobileDataEnabled());
                break;
            case R.id.btn_set_wifi_enabled:
                NetworkUtils.setWifiEnabled(!NetworkUtils.getWifiEnabled());
                break;
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
                .append("getIPAddress: " + NetworkUtils.getIPAddress(true))
                .create()
        );
    }

    @Override
    protected void onDestroy() {
        threadPoolUtils.shutDownNow();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
