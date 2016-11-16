package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.LocationUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Location工具类测试
 * </pre>
 */
public class LocationActivity extends Activity {

    Context       mContext;
    TextView      tvAboutLocation;
    LocationUtils locationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        tvAboutLocation = (TextView) findViewById(R.id.tv_about_location);
        mContext = this;

        locationUtils = new LocationUtils(this);
        locationUtils.init(100, 0, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tvAboutLocation.setText("getCountryName:" + locationUtils.getCountryName(latitude, longitude) +
                        "\ngetLocality:" + locationUtils.getLocality(latitude, longitude) +
                        "\ngetStreet:" + locationUtils.getStreet(latitude, longitude)
                );
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });


    }
}
