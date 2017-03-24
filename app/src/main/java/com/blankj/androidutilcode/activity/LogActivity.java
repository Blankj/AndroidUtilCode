package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.utilcode.util.LogUtils;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/22
 *     desc  : Log工具类Demo
 * </pre>
 */

public class LogActivity extends Activity
        implements View.OnClickListener {

    private static final String TAG = "CMJ";

    private LogUtils.Builder mBuilder;

    private TextView tvAboutLog;

    private String  globalTag = "";
    private boolean border    = false;
    private int     filter    = LogUtils.V;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mBuilder = UtilsApp.lBuilder;
        findViewById(R.id.btn_toggle_tag).setOnClickListener(this);
        findViewById(R.id.btn_toggle_border).setOnClickListener(this);
        findViewById(R.id.btn_toggle_filter).setOnClickListener(this);
        findViewById(R.id.btn_log_v).setOnClickListener(this);
        findViewById(R.id.btn_log_d).setOnClickListener(this);
        findViewById(R.id.btn_log_i).setOnClickListener(this);
        findViewById(R.id.btn_log_w).setOnClickListener(this);
        findViewById(R.id.btn_log_e).setOnClickListener(this);
        findViewById(R.id.btn_log_a).setOnClickListener(this);
        tvAboutLog = (TextView) findViewById(R.id.tv_about_log);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggle_tag:
                updateAbout();
                break;
            case R.id.btn_toggle_border:
                updateAbout();
                break;
            case R.id.btn_toggle_filter:
                updateAbout();
                break;
            case R.id.btn_log_v:
                //TODO implement
                break;
            case R.id.btn_log_d:
                //TODO implement
                break;
            case R.id.btn_log_i:
                //TODO implement
                break;
            case R.id.btn_log_w:
                //TODO implement
                break;
            case R.id.btn_log_e:
                //TODO implement
                break;
            case R.id.btn_log_a:
                //TODO implement
                break;
        }
    }

    private void updateAbout() {
        StringBuilder sb = new StringBuilder();
        globalTag = globalTag.equals(TAG) ? "" : TAG;
        mBuilder.setGlobalTag(globalTag);
        sb.append("tag: ")
                .append(globalTag.equals("") ? "null" : TAG);

        border = !border;
        mBuilder.set



        tvAboutLog.setText(sb.toString());
    }
}
