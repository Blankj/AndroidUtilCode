package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.SDCardUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : SDCard工具类Demo
 * </pre>
 */
public class SDCardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard);

        TextView tvAboutSdcard = (TextView) findViewById(R.id.tv_about_sdcard);
        tvAboutSdcard.setText("isSDCardEnable: " + SDCardUtils.isSDCardEnable()
               + "\ngetDataPath: " + SDCardUtils.getDataPath()
               + "\ngetSDCardPath: " + SDCardUtils.getSDCardPath()
               + "\ngetFreeSpace: " + SDCardUtils.getFreeSpace()
               + "\ngetSDCardInfo: " + SDCardUtils.getSDCardInfo()
        );
    }
}
