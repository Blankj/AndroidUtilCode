package com.blankj.lib.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ThreadUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/14
 *     desc  : base about task activity
 * </pre>
 */
public abstract class BaseTaskActivity<T> extends BaseTitleActivity {

    private ProgressBar loadingView;

    public abstract T doInBackground();

    public abstract void runOnUiThread(T data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingVisibility(true);
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<T>() {
            @Override
            public T doInBackground() throws Throwable {
                return BaseTaskActivity.this.doInBackground();
            }

            @Override
            public void onSuccess(T result) {
                runOnUiThread(result);
                setLoadingVisibility(false);
            }
        });
    }

    void setLoadingVisibility(boolean isVisible) {
        if (loadingView == null) {
            loadingView = new ProgressBar(this, null, android.R.attr.progressBarStyle);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            baseTitleContentView.addView(loadingView, params);
        }
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
