package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : Clean工具类Demo
 * </pre>
 */
public class CleanActivity extends BaseBackActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_clean;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getSupportActionBar().setTitle(getString(R.string.demo_clean));

        Button btnCleanInternalCache = (Button) findViewById(R.id.btn_clean_internal_cache);
        Button btnCleanInternalFiles = (Button) findViewById(R.id.btn_clean_internal_files);
        Button btnCleanInternalDbs = (Button) findViewById(R.id.btn_clean_internal_databases);
        Button btnCleanInternalSP = (Button) findViewById(R.id.btn_clean_internal_sp);
        Button btnCleanExternalCache = (Button) findViewById(R.id.btn_clean_external_cache);
        btnCleanInternalCache.setOnClickListener(this);
        btnCleanInternalFiles.setOnClickListener(this);
        btnCleanInternalDbs.setOnClickListener(this);
        btnCleanInternalSP.setOnClickListener(this);
        btnCleanExternalCache.setOnClickListener(this);

        btnCleanInternalCache.setText(getCacheDir().getPath());
        btnCleanInternalFiles.setText(getFilesDir().getPath());
        btnCleanInternalDbs.setText(getFilesDir().getParent() + File.separator + "databases");
        btnCleanInternalSP.setText(getFilesDir().getParent() + File.separator + "shared_prefs");

        if (getExternalCacheDir() != null) {
            btnCleanExternalCache.setText(getExternalCacheDir().getAbsolutePath());
        }
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clean_internal_cache:
                ToastUtils.showShort("cleanInternalCache" + CleanUtils.cleanInternalCache());
                break;
            case R.id.btn_clean_internal_files:
                ToastUtils.showShort("cleanInternalFiles" + CleanUtils.cleanInternalFiles());
                break;
            case R.id.btn_clean_internal_databases:
                ToastUtils.showShort("cleanInternalDbs" + CleanUtils.cleanInternalDbs());
                break;
            case R.id.btn_clean_internal_sp:
                ToastUtils.showShort("cleanInternalSP" + CleanUtils.cleanInternalSP());
                break;
            case R.id.btn_clean_external_cache:
                ToastUtils.showShort("cleanExternalCache" + CleanUtils.cleanExternalCache());
                break;
        }
    }
}
