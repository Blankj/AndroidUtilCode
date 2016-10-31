package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.ProcessUtils;
import com.blankj.utilcode.utils.ToastUtils;

public class ProcessActivity extends Activity
        implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        findViewById(R.id.btn_kill_all_background_processes).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_kill_all_background_processes:
                ToastUtils.showShortToast(this, ProcessUtils.killAllBackgroundProcesses(this));
                break;
        }
    }
}
