package com.blankj.androidutilcode.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/24
 *     desc  : Activity基类
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener {

    /**
     * 是否全屏
     */
    private boolean isFullScreen     = false;
    /**
     * 是否沉浸状态栏
     */
    private boolean isSteepStatusBar = false;
    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    protected BaseActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mActivity = this;
            Bundle bundle = getIntent().getExtras();
            initData(bundle);
            contentView = LayoutInflater.from(this).inflate(bindLayout(), null);
            if (isFullScreen) {
                this.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
            if (isSteepStatusBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }
            setContentView(contentView);
            initView(savedInstanceState, contentView);
            doBusiness(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化数据
     *
     * @param bundle 从上个Activity传递过来的bundle
     */
    public abstract void initData(final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局Id
     */
    public abstract int bindLayout();

    /**
     * 初始化view
     */
    public abstract void initView(final Bundle savedInstanceState, final View view);

    /**
     * 业务操作
     *
     * @param context 上下文
     */
    public abstract void doBusiness(final Context context);

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    public abstract void onWidgetClick(final View view);

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
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }

    /**
     * 设置是否全屏
     *
     * @param isFullScreen 是否全屏
     */
    public void setFullScreen(final boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    /**
     * 设置是否沉浸状态栏
     *
     * @param isSteepStatusBar 是否沉浸状态栏
     */
    public void setSteepStatusBar(final boolean isSteepStatusBar) {
        this.isSteepStatusBar = isSteepStatusBar;
    }
}
