package com.blankj.utilcode.pkg.feature.sp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.pkg.data.DataManager;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/08
 *     desc  : demo about SPUtils
 * </pre>
 */
public class SPActivity extends BaseBackActivity {

    private TextView tvAboutSp;

    public static void start(Context context) {
        Intent starter = new Intent(context, SPActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sp;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
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
        int i = view.getId();
        if (i == R.id.btn_sp_put_string) {
            DataManager.putString();

        } else if (i == R.id.btn_sp_put_int) {
            DataManager.putInt();

        } else if (i == R.id.btn_sp_put_long) {
            DataManager.putLong();

        } else if (i == R.id.btn_sp_put_float) {
            DataManager.putFloat();

        } else if (i == R.id.btn_sp_put_boolean) {
            DataManager.putBoolean();

        } else if (i == R.id.btn_sp_clear) {
            DataManager.clear();

        }
        updateAboutSp();
    }

    private void updateAboutSp() {
        tvAboutSp.setText(DataManager.sp2String());
    }
}
