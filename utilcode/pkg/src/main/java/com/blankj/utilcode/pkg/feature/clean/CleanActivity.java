package com.blankj.utilcode.pkg.feature.clean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.SnackbarUtils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : demo about CleanUtils
 * </pre>
 */
public class CleanActivity extends BaseBackActivity {

    private View   snackBarRootView;
    private String internalCachePath;
    private String internalFilesPath;
    private String internalDbs;
    private String internalSp;
    private String externalCache;

    public static void start(Context context) {
        Intent starter = new Intent(context, CleanActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_clean;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_clean);

        snackBarRootView = findViewById(android.R.id.content);
        findViewById(R.id.btn_clean_internal_cache).setOnClickListener(this);
        findViewById(R.id.btn_clean_internal_files).setOnClickListener(this);
        findViewById(R.id.btn_clean_internal_databases).setOnClickListener(this);
        findViewById(R.id.btn_clean_internal_sp).setOnClickListener(this);
        findViewById(R.id.btn_clean_external_cache).setOnClickListener(this);

        internalCachePath = getCacheDir().getPath();
        internalFilesPath = getFilesDir().getPath();
        internalDbs = getFilesDir().getParent() + File.separator + "databases";
        internalSp = getFilesDir().getParent() + File.separator + "shared_prefs";

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            externalCache = getExternalCacheDir().getAbsolutePath();
        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_clean_internal_cache) {
            showSnackbar(CleanUtils.cleanInternalCache(), internalCachePath);

        } else if (i == R.id.btn_clean_internal_files) {
            showSnackbar(CleanUtils.cleanInternalFiles(), internalFilesPath);

        } else if (i == R.id.btn_clean_internal_databases) {
            showSnackbar(CleanUtils.cleanInternalDbs(), internalDbs);

        } else if (i == R.id.btn_clean_internal_sp) {
            showSnackbar(CleanUtils.cleanInternalSp(), internalSp);

        } else if (i == R.id.btn_clean_external_cache) {
            showSnackbar(CleanUtils.cleanExternalCache(), externalCache);

        }
    }

    private void showSnackbar(final boolean isSuccess, final String path) {
        if (isSuccess) {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage("clean \"" + path + "\" dir success")
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .showSuccess();
        } else {
            SnackbarUtils.with(snackBarRootView)
                    .setMessage("clean \"" + path + "\" dir failed")
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .showError();
        }
    }
}
