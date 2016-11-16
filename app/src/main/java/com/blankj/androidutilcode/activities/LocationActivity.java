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

    Context  mContext;
    TextView tvAboutLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        tvAboutLocation = (TextView) findViewById(R.id.tv_about_location);
        mContext = this;

        LocationUtils.getInstance(mContext).init(1000, 0, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void onLocationChanged(Location location) {
                //得到纬度
                double latitude = location.getLatitude();
                //得到经度
                double longitude = location.getLongitude();
                tvAboutLocation.setText("getCountryName:" + LocationUtils.getInstance(mContext).getCountryName(latitude, longitude) +
                        "\ngetLocality:" + LocationUtils.getInstance(mContext).getLocality(latitude, longitude) +
                        "\ngetStreet:" + LocationUtils.getInstance(mContext).getStreet(latitude, longitude)
                );
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });


    }
}
