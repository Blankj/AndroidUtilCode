package com.blankj.androidutilcode.activities;

import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        TextView tvAboutLocation = (TextView) findViewById(R.id.tv_about_location);

        LocationUtils.getInstance(this).init(new);

        tvAboutLocation.setText("");
    }
}
