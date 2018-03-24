package com.blankj.androidutilcode.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2018/03/23
 *     desc  :
 * </pre>
 */
public abstract class BaseLazyFragment extends Fragment
        implements IBaseView {

    private long lastClick = 0;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d("setUserVisibleHint() called with: isVisibleToUser = [" + isVisibleToUser + "]");;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate() called VisibleHint: " + getUserVisibleHint());
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) onWidgetClick(view);
    }
}
