package com.blankj.androidutilcode.feature.core.log;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.LogUtils;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/22
 *     desc  : Log 工具类 Demo
 * </pre>
 */
public class LogActivity extends BaseBackActivity {

    private static final String TAG = "CMJ";

    private TextView tvAboutLog;

    private LogUtils.Config mConfig = LogUtils.getConfig();

    private String  dir           = "";
    private String  globalTag     = "";
    private boolean log           = true;
    private boolean console       = true;
    private boolean head          = true;
    private boolean file          = false;
    private boolean border        = true;
    private int     consoleFilter = LogUtils.V;
    private int     fileFilter    = LogUtils.V;

    private static final int UPDATE_LOG            = 0x01;
    private static final int UPDATE_CONSOLE        = 0x01 << 1;
    private static final int UPDATE_TAG            = 0x01 << 2;
    private static final int UPDATE_HEAD           = 0x01 << 3;
    private static final int UPDATE_FILE           = 0x01 << 4;
    private static final int UPDATE_DIR            = 0x01 << 5;
    private static final int UPDATE_BORDER         = 0x01 << 6;
    private static final int UPDATE_CONSOLE_FILTER = 0x01 << 7;
    private static final int UPDATE_FILE_FILTER    = 0x01 << 8;

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

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("len = 10400\ncontent = \"");
        for (int i = 0; i < 800; ++i) {
            sb.append("Hello world. ");
        }
        sb.append("\"");
        longStr = sb.toString();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LogActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_log;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_log));

        tvAboutLog = findViewById(R.id.tv_about_log);
        findViewById(R.id.btn_toggle_log).setOnClickListener(this);
        findViewById(R.id.btn_toggle_console).setOnClickListener(this);
        findViewById(R.id.btn_toggle_tag).setOnClickListener(this);
        findViewById(R.id.btn_toggle_head).setOnClickListener(this);
        findViewById(R.id.btn_toggle_border).setOnClickListener(this);
        findViewById(R.id.btn_toggle_file).setOnClickListener(this);
        findViewById(R.id.btn_toggle_dir).setOnClickListener(this);
        findViewById(R.id.btn_toggle_conole_filter).setOnClickListener(this);
        findViewById(R.id.btn_toggle_file_filter).setOnClickListener(this);
        findViewById(R.id.btn_log_no_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_with_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_in_new_thread).setOnClickListener(this);
        findViewById(R.id.btn_log_null).setOnClickListener(this);
        findViewById(R.id.btn_log_many_params).setOnClickListener(this);
        findViewById(R.id.btn_log_long).setOnClickListener(this);
        findViewById(R.id.btn_log_file).setOnClickListener(this);
        findViewById(R.id.btn_log_json).setOnClickListener(this);
        findViewById(R.id.btn_log_xml).setOnClickListener(this);
        updateConfig(0);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggle_log:
                updateConfig(UPDATE_LOG);
                break;
            case R.id.btn_toggle_console:
                updateConfig(UPDATE_CONSOLE);
                break;
            case R.id.btn_toggle_tag:
                updateConfig(UPDATE_TAG);
                break;
            case R.id.btn_toggle_head:
                updateConfig(UPDATE_HEAD);
                break;
            case R.id.btn_toggle_file:
                updateConfig(UPDATE_FILE);
                break;
            case R.id.btn_toggle_dir:
                updateConfig(UPDATE_DIR);
                break;
            case R.id.btn_toggle_border:
                updateConfig(UPDATE_BORDER);
                break;
            case R.id.btn_toggle_conole_filter:
                updateConfig(UPDATE_CONSOLE_FILTER);
                break;
            case R.id.btn_toggle_file_filter:
                updateConfig(UPDATE_FILE_FILTER);
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
                LogUtils.vTag("customTag", "verbose");
                LogUtils.dTag("customTag", "debug");
                LogUtils.iTag("customTag", "info");
                LogUtils.wTag("customTag", "warn");
                LogUtils.eTag("customTag", "error");
                LogUtils.aTag("customTag", "assert");
                break;
            case R.id.btn_log_in_new_thread:
                Thread thread = new Thread(mRunnable);
                thread.start();
                break;
            case R.id.btn_log_null:
                LogUtils.v((Object) null);
                LogUtils.d((Object) null);
                LogUtils.i((Object) null);
                LogUtils.w((Object) null);
                LogUtils.e((Object) null);
                LogUtils.a((Object) null);
                break;
            case R.id.btn_log_many_params:
                LogUtils.v("verbose0", "verbose1");
                LogUtils.vTag("customTag", "verbose0", "verbose1");
                LogUtils.d("debug0", "debug1");
                LogUtils.dTag("customTag", "debug0", "debug1");
                LogUtils.i("info0", "info1");
                LogUtils.iTag("customTag", "info0", "info1");
                LogUtils.w("warn0", "warn1");
                LogUtils.wTag("customTag", "warn0", "warn1");
                LogUtils.e("error0", "error1");
                LogUtils.eTag("customTag", "error0", "error1");
                LogUtils.a("assert0", "assert1");
                LogUtils.aTag("customTag", "assert0", "assert1");
                break;
            case R.id.btn_log_long:
                LogUtils.d(longStr);
                break;
            case R.id.btn_log_file:
                for (int i = 0; i < 100; i++) {
                    LogUtils.file("test0 log to file");
                    LogUtils.file(LogUtils.I, "test0 log to file");
                }
                break;
            case R.id.btn_log_json:
                String json = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"json format\" , \"site\":\"http://tools.w3cschool.cn/code/json\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}";
                LogUtils.json(json);
                LogUtils.json(LogUtils.I, json);
                break;
            case R.id.btn_log_xml:
                String xml = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>";
                LogUtils.xml(xml);
                LogUtils.xml(LogUtils.I, xml);
                break;
        }
    }

    private void updateConfig(int args) {
        switch (args) {
            case UPDATE_LOG:
                log = !log;
                break;
            case UPDATE_CONSOLE:
                console = !console;
                break;
            case UPDATE_TAG:
                globalTag = globalTag.equals(TAG) ? "" : TAG;
                break;
            case UPDATE_HEAD:
                head = !head;
                break;
            case UPDATE_FILE:
                file = !file;
                break;
            case UPDATE_DIR:
                if (getDir().contains("test")) {
                    dir = null;
                } else {
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        dir = Environment.getExternalStorageDirectory().getPath() + System.getProperty("file.separator") + "test";
                    }
                }
                break;
            case UPDATE_BORDER:
                border = !border;
                break;
            case UPDATE_CONSOLE_FILTER:
                consoleFilter = consoleFilter == LogUtils.V ? LogUtils.W : LogUtils.V;
                break;
            case UPDATE_FILE_FILTER:
                fileFilter = fileFilter == LogUtils.V ? LogUtils.I : LogUtils.V;
                break;
        }
        mConfig.setLogSwitch(log)
                .setConsoleSwitch(console)
                .setGlobalTag(globalTag)
                .setLogHeadSwitch(head)
                .setLog2FileSwitch(file)
                .setDir(dir)
                .setBorderSwitch(border)
                .setConsoleFilter(consoleFilter)
                .setFileFilter(fileFilter);
        tvAboutLog.setText(mConfig.toString());
    }

    private String getDir() {
        return mConfig.toString().split(System.getProperty("line.separator"))[5].substring(5);
    }

    @Override
    protected void onDestroy() {
        UtilsApp.getInstance().initLog();
        super.onDestroy();
    }
}
