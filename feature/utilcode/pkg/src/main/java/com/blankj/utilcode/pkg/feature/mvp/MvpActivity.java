package com.blankj.utilcode.pkg.feature.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.common.activity.CommonActivity;
import com.blankj.utilcode.pkg.R;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/09
 *     desc  :
 * </pre>
 */
public class MvpActivity extends CommonActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MvpActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int bindTitleRes() {
        return R.string.demo_mvp;
    }

    @Override
    public int bindLayout() {
        return R.layout.mvp_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        super.initView(savedInstanceState, contentView);
        new MvpView(this).addPresenter(new MvpPresenter());
    }
}
