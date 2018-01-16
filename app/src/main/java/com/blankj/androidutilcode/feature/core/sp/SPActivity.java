package com.blankj.androidutilcode.feature.core.sp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.androidutilcode.data.DataManager;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/08
 *     desc  : SP 工具类 Demo
 * </pre>
 */
public class SPActivity extends BaseBackActivity {

    private TextView tvAboutSp;

    public static void start(Context context) {
        Intent starter = new Intent(context, SPActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sp;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvAboutSp = findViewById(R.id.tv_about_sp);
        findViewById(R.id.btn_sp_put_string).setOnClickListener(this);
        findViewById(R.id.btn_sp_put_int).setOnClickListener(this);
        findViewById(R.id.btn_sp_put_long).setOnClickListener(this);
        findViewById(R.id.btn_sp_put_float).setOnClickListener(this);
        findViewById(R.id.btn_sp_put_boolean).setOnClickListener(this);
        findViewById(R.id.btn_sp_clear).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
        updateAboutSp();
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sp_put_string:
                DataManager.putString();
                break;
            case R.id.btn_sp_put_int:
                DataManager.putInt();
                break;
            case R.id.btn_sp_put_long:
                DataManager.putLong();
                break;
            case R.id.btn_sp_put_float:
                DataManager.putFloat();
                break;
            case R.id.btn_sp_put_boolean:
                DataManager.putBoolean();
                break;
            case R.id.btn_sp_clear:
                DataManager.clear();
                break;
        }
        updateAboutSp();
    }

    private void updateAboutSp() {
        tvAboutSp.setText(DataManager.sp2String());
    }
}
