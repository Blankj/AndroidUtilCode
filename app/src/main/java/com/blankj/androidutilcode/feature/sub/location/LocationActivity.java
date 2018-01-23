package com.blankj.androidutilcode.feature.sub.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Location 工具类 Demo
 * </pre>
 */
public class LocationActivity extends BaseBackActivity {

    TextView        tvAboutLocation;
    LocationService mLocationService;

    public static void start(Context context) {
        Intent starter = new Intent(context, LocationActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_location;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_location));

        tvAboutLocation = findViewById(R.id.tv_about_location);
        tvAboutLocation.setText(new SpanUtils()
                .appendLine("lastLatitude: unknown")
                .appendLine("lastLongitude: unknown")
                .appendLine("latitude: unknown")
                .appendLine("longitude: unknown")
                .appendLine("getCountryName: unknown")
                .appendLine("getLocality: unknown")
                .appendLine("getStreet: unknown")
                .create()
        );
    }

    @Override
    public void doBusiness() {
        bindService(new Intent(this, LocationService.class), conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocationService = ((LocationService.LocationBinder) service).getService();
            mLocationService.setOnGetLocationListener(new LocationService.OnGetLocationListener() {
                @Override
                public void getLocation(final String lastLatitude, final String lastLongitude, final String latitude, final String longitude, final String country, final String locality, final String street) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvAboutLocation.setText(new SpanUtils()
                                    .appendLine("lastLatitude: " + lastLatitude)
                                    .appendLine("lastLongitude: " + lastLongitude)
                                    .appendLine("latitude: " + latitude)
                                    .appendLine("longitude: " + longitude)
                                    .appendLine("getCountryName: " + country )
                                    .appendLine("getLocality: " + locality)
                                    .appendLine( "getStreet: " + street)
                                    .create()
                            );
                        }
                    });
                }
            });
        }
    };
}
