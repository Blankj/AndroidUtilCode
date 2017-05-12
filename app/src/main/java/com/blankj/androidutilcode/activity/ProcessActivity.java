package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.ProcessUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Process工具类Demo
 * </pre>
 */
public class ProcessActivity extends BaseActivity {

    private TextView tvAboutProcess;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_process;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        findViewById(R.id.btn_kill_all_background_processes).setOnClickListener(this);
        tvAboutProcess = (TextView) findViewById(R.id.tv_about_process);
        Set<String> set = ProcessUtils.getAllBackgroundProcesses();
        tvAboutProcess.setText("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName()
                + "\n\ngetAllBackgroundProcesses: " + getSetItems(set)
                + "\nsize: " + set.size());
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_kill_all_background_processes:
                Set<String> set = ProcessUtils.getAllBackgroundProcesses();
                Set<String> set1 = ProcessUtils.killAllBackgroundProcesses();
                tvAboutProcess.setText("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName()
                        + "\n\ngetAllBackgroundProcesses: " + getSetItems(set)
                        + "\nsize: " + set.size()
                        + "\n\nkillAllBackgroundProcesses: " + getSetItems(set1)
                        + "\nsize: " + set1.size());
                break;
        }
    }

    private String getSetItems(Set<String> set) {
        Iterator<String> iterator = set.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n");
        }
        return sb.toString();
    }
}
