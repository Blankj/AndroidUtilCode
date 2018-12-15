package com.blankj.utilcode.pkg.feature.bar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseLazyFragment;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/07/01
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarStatusCustomFragment extends BaseLazyFragment {

    private View fakeStatusBar;


    public static BarStatusCustomFragment newInstance() {
        return new BarStatusCustomFragment();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_bar_status_custom;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        BarUtils.setStatusBarCustom(findViewById(R.id.fake_status_bar));
    }

    @Override
    public void doLazyBusiness() {
        LogUtils.d("doLazyBusiness() called");
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
