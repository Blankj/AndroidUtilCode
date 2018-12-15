package com.blankj.utilcode.pkg.feature.process;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.SpanUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about ProcessUtils
 * </pre>
 */
public class ProcessActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ProcessActivity.class);
        context.startActivity(starter);
    }

    private TextView tvAboutProcess;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_process;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_process);

        findViewById(R.id.btn_kill_all_background_processes).setOnClickListener(this);
        tvAboutProcess = findViewById(R.id.tv_about_process);
        Set<String> set = ProcessUtils.getAllBackgroundProcesses();
        tvAboutProcess.setText(new SpanUtils()
                .appendLine("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName())
                .appendLine("getAllBackgroundProcesses: " + getSetItems(set))
                .appendLine("size: " + set.size())
                .appendLine("isMainProcess: " + ProcessUtils.isMainProcess())
                .append("getCurrentProcessName: " + ProcessUtils.getCurrentProcessName())
                .create()
        );
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_kill_all_background_processes) {
            Set<String> set = ProcessUtils.getAllBackgroundProcesses();
            Set<String> set1 = ProcessUtils.killAllBackgroundProcesses();
            tvAboutProcess.setText(new SpanUtils()
                    .appendLine("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName())
                    .appendLine("getAllBackgroundProcesses: " + getSetItems(set))
                    .appendLine("size: " + set.size())
                    .appendLine("killAllBackgroundProcesses: " + getSetItems(set1))
                    .appendLine("size: " + set1.size())
                    .appendLine("isMainProcess: " + ProcessUtils.isMainProcess())
                    .append("getCurrentProcessName: " + ProcessUtils.getCurrentProcessName())
                    .create()
            );

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
