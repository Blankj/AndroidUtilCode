package com.blankj.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/11/16
 *     desc  :
 * </pre>
 */
public interface IBaseView {

    void initData(@Nullable Bundle bundle);

    int bindLayout();

    void setContentView();

    void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView);

    void doBusiness();

    void onDebouncingClick(@NonNull View view);
}
