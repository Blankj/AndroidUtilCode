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
    private boolean border    = true;
    private boolean file      = false;
    private int     filter    = LogUtils.V;

    private static final int UPDATE_TAG    = 0x01;
    private static final int UPDATE_FILE   = 0x01 << 1;
    private static final int UPDATE_BORDER = 0x01 << 2;
    private static final int UPDATE_FILTER = 0x01 << 3;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            LogUtils.v("verbose");
            LogUtils.d("debug");
            LogUtils.i("info");
            LogUtils.w("warn");
            LogUtils.e("error");
            LogUtils.a("assert");
        }
    };

    private static final String longStr;

    private String json = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"json format\" , \"site\":\"http://tools.w3cschool.cn/code/json\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}";
    private String xml = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("len = 10400\ncontent = \"");
        for (int i = 0; i < 800; ++i) {
            sb.append("Hello world. ");
        }
        sb.append("\"");
        longStr = sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mBuilder = UtilsApp.lBuilder;
        findViewById(R.id.btn_toggle_tag).setOnClickListener(this);
        findViewById(R.id.btn_toggle_border).setOnClickListener(this);
        findViewById(R.id.btn_toggle_file).setOnClickListener(this);
        findViewById(R.id.btn_toggle_filter).setOnClickListener(this);
        findViewById(R.id.btn_log_no_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_with_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_in_new_thread).setOnClickListener(this);
        findViewById(R.id.btn_log_null).setOnClickListener(this);
        findViewById(R.id.btn_log_many_params).setOnClickListener(this);
        findViewById(R.id.btn_log_long).setOnClickListener(this);
        findViewById(R.id.btn_log_file).setOnClickListener(this);
        findViewById(R.id.btn_log_json).setOnClickListener(this);
        findViewById(R.id.btn_log_xml).setOnClickListener(this);
        tvAboutLog = (TextView) findViewById(R.id.tv_about_log);
        updateAbout(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggle_tag:
                updateAbout(UPDATE_TAG);
                break;
            case R.id.btn_toggle_file:
                updateAbout(UPDATE_FILE);
                break;
            case R.id.btn_toggle_border:
                updateAbout(UPDATE_BORDER);
                break;
            case R.id.btn_toggle_filter:
                updateAbout(UPDATE_FILTER);
                break;
            case R.id.btn_log_no_tag:
                LogUtils.v("verbose");
                LogUtils.d("debug");
                LogUtils.i("info");
                LogUtils.w("warn");
                LogUtils.e("error");
                LogUtils.a("assert");
                break;
            case R.id.btn_log_with_tag:
                LogUtils.v("customTag", "verbose");
                LogUtils.d("customTag", "debug");
                LogUtils.i("customTag", "info");
                LogUtils.w("customTag", "warn");
                LogUtils.e("customTag", "error");
                LogUtils.a("customTag", "assert");
                break;
            case R.id.btn_log_in_new_thread:
                Thread thread = new Thread(mRunnable);
                thread.start();
                break;
            case R.id.btn_log_null:
                LogUtils.v(null);
                LogUtils.d(null);
                LogUtils.i(null);
                LogUtils.w(null);
                LogUtils.e(null);
                LogUtils.a(null);
                break;
            case R.id.btn_log_many_params:
                LogUtils.v("customTag", "verbose0", "verbose1");
                LogUtils.d("customTag", "debug0", "debug1");
                LogUtils.i("customTag", "info0", "info1");
                LogUtils.w("customTag", "warn0", "warn1");
                LogUtils.e("customTag", "error0", "error1");
                LogUtils.a("customTag", "assert0", "assert1");
                break;
            case R.id.btn_log_long:
                LogUtils.d(longStr);
                break;
            case R.id.btn_log_file:
                LogUtils.file(longStr);
                break;
            case R.id.btn_log_json:
                LogUtils.json(json);
                break;
            case R.id.btn_log_xml:
                LogUtils.xml(xml);
                break;
        }
    }

    private void updateAbout(int args) {
        switch (args) {
            case UPDATE_TAG:
                globalTag = globalTag.equals(TAG) ? "" : TAG;
                break;
            case UPDATE_FILE:
                file = !file;
                break;
            case UPDATE_BORDER:
                border = !border;
                break;
            case UPDATE_FILTER:
                filter = filter == LogUtils.V ? LogUtils.W : LogUtils.V;
                break;
        }
        mBuilder.setGlobalTag(globalTag)
                .setLog2FileSwitch(file)
                .setBorderSwitch(border)
                .setLogFilter(filter);
        tvAboutLog.setText("tag: " + (globalTag.equals("") ? "null" : TAG)
                + "\nfile: " + String.valueOf(file)
                + "\nborder: " + String.valueOf(border)
                + "\nfilter: " + (filter == LogUtils.V ? "Verbose" : "Warn")
        );
    }
}
