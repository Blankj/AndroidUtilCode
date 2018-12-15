package com.blankj.utilcode.pkg.feature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.pkg.feature.activity.ActivityActivity;
import com.blankj.utilcode.pkg.feature.adaptScreen.AdaptScreenActivity;
import com.blankj.utilcode.pkg.feature.app.AppActivity;
import com.blankj.utilcode.pkg.feature.bar.BarActivity;
import com.blankj.utilcode.pkg.feature.blur.BlurActivity;
import com.blankj.utilcode.pkg.feature.clean.CleanActivity;
import com.blankj.utilcode.pkg.feature.device.DeviceActivity;
import com.blankj.utilcode.pkg.feature.fragment.FragmentActivity;
import com.blankj.utilcode.pkg.feature.image.ImageActivity;
import com.blankj.utilcode.pkg.feature.keyboard.KeyboardActivity;
import com.blankj.utilcode.pkg.feature.log.LogActivity;
import com.blankj.utilcode.pkg.feature.metaData.MetaDataActivity;
import com.blankj.utilcode.pkg.feature.network.NetworkActivity;
import com.blankj.utilcode.pkg.feature.path.PathActivity;
import com.blankj.utilcode.pkg.feature.permission.PermissionActivity;
import com.blankj.utilcode.pkg.feature.phone.PhoneActivity;
import com.blankj.utilcode.pkg.feature.process.ProcessActivity;
import com.blankj.utilcode.pkg.feature.reflect.ReflectActivity;
import com.blankj.utilcode.pkg.feature.resource.ResourceActivity;
import com.blankj.utilcode.pkg.feature.sdcard.SDCardActivity;
import com.blankj.utilcode.pkg.feature.snackbar.SnackbarActivity;
import com.blankj.utilcode.pkg.feature.sp.SPActivity;
import com.blankj.utilcode.pkg.feature.span.SpanActivity;
import com.blankj.utilcode.pkg.feature.toast.ToastActivity;
import com.blankj.utilcode.util.BusUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  :
 * </pre>
 */
public class CoreUtilActivity extends BaseBackActivity {

    @BusUtils.Subscribe(name = "CoreUtilActivity#start")
    public static void start(Context context) {
        Intent starter = new Intent(context, CoreUtilActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_util_core;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.core_util);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void coreUtilClick(View view) {

    }

    public void activityClick(View view) {
        ActivityActivity.start(this);
    }

    public void adaptScreenClick(View view) {
        AdaptScreenActivity.Companion.start(this);
    }

    public void appClick(View view) {
        AppActivity.start(this);
    }

    public void barClick(View view) {
        BarActivity.start(this);
    }

    public void blurClick(View view) {
        BlurActivity.start(this);
    }

    public void cleanClick(View view) {
        CleanActivity.start(this);
    }

    public void crashClick(View view) {
        throw new NullPointerException("crash test");
    }

    public void deviceClick(View view) {
        DeviceActivity.start(this);
    }

    public void fragmentClick(View view) {
        FragmentActivity.start(this);
    }

    public void imageClick(View view) {
        ImageActivity.start(this);
    }

    public void keyboardClick(View view) {
        KeyboardActivity.start(this);
    }

    public void logClick(View view) {
        LogActivity.start(this);
    }

    public void metaDataClick(View view) {
        MetaDataActivity.start(this);
    }

    public void networkClick(View view) {
        NetworkActivity.start(this);
    }

    public void pathClick(View view) {
        PathActivity.start(this);
    }

    public void permissionClick(View view) {
        PermissionActivity.start(this);
    }

    public void phoneClick(View view) {
        PhoneActivity.start(this);
    }

    public void processClick(View view) {
        ProcessActivity.start(this);
    }

    public void reflectClick(View view) {
        ReflectActivity.start(this);
    }

    public void resourceClick(View view) {
        ResourceActivity.start(this);
    }

    public void sdcardClick(View view) {
        SDCardActivity.start(this);
    }

    public void snackbarClick(View view) {
        SnackbarActivity.start(this);
    }

    public void spClick(View view) {
        SPActivity.start(this);
    }

    public void spannableClick(View view) {
        SpanActivity.start(this);
    }

    public void toastClick(View view) {
        ToastActivity.start(this);
    }
}
