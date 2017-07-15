package com.blankj.androidutilcode.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseFragment;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/07/01
 *     desc  :
 * </pre>
 */
public class StatusBarImageViewFragment extends BaseFragment {
    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_status_bar_image_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness(Context context) {
        BarUtils.setStatusBar4ImageViewInFragment(mActivity, null);
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
