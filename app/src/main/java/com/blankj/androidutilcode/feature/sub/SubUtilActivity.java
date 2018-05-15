package com.blankj.androidutilcode.feature.sub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.androidutilcode.feature.sub.brightness.BrightnessActivity;
import com.blankj.androidutilcode.feature.sub.flashlight.FlashlightActivity;
import com.blankj.androidutilcode.feature.sub.location.LocationActivity;
import com.blankj.androidutilcode.feature.sub.meta_data.MetaDataActivity;
import com.blankj.androidutilcode.feature.sub.pinyin.PinyinActivity;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : MainActivity
 * </pre>
 */
public class SubUtilActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SubUtilActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_util_sub;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        getToolBar().setTitle(getString(R.string.sub_util));
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void brightnessClick(View view) {
        BrightnessActivity.start(this);
    }

    public void flashlightClick(View view) {
        FlashlightActivity.start(this);
    }

    public void locationClick(View view) {
        LocationActivity.start(this);
    }

    public void metaDataClick(View view) {
        MetaDataActivity.start(this);
    }

    public void pinyinClick(View view) {
        PinyinActivity.start(this);
    }
}
