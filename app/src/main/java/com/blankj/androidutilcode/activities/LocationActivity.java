package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.blankj.androidutilcode.App;
import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.HandlerUtils;
import com.blankj.utilcode.utils.LocationUtils;
import com.blankj.utilcode.utils.LogUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Location工具类测试
 * </pre>
 */
public class LocationActivity extends Activity {

    private TextView                   tvAboutLocation;
    private LocationUtils              locationUtils;
    private double                     latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvAboutLocation = (TextView) findViewById(R.id.tv_about_location);

        locationUtils = new LocationUtils(App.getInstance());
        locationUtils.init(1000, 0, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                tvAboutLocation.setText("latitude: " + latitude +
                        "\nlongitude: " + longitude +
                        "\ngetCountryName: " + locationUtils.getCountryName(latitude, longitude) +
                        "\ngetLocality: " + locationUtils.getLocality(latitude, longitude) +
                        "\ngetStreet: " + locationUtils.getStreet(latitude, longitude)
                );
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        locationUtils.removeAndGc();
        super.onDestroy();
    }
}
