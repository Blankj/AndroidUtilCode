package com.blankj.utildebug.base.view;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.blankj.utildebug.DebugUtils;
import com.blankj.utildebug.R;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/10
 *     desc  :
 * </pre>
 */
public abstract class BaseFloatView extends RelativeLayout
        implements Utils.OnAppStatusChangedListener {

    private boolean isCreated;

    protected WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();

    @LayoutRes
    public abstract int bindLayout();

    public abstract void initContentView();

    public BaseFloatView() {
        super(DebugUtils.getApp());
        setId(R.id.baseFloatView);
        if (bindLayout() != NO_ID) {
            inflate(getContext(), bindLayout(), this);
        }
        onCreateLayoutParams();
    }

    void createFloatView() {
        if (isCreated) return;
        isCreated = true;
        initContentView();
    }

    @CallSuper
    protected void onCreateLayoutParams() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.TRANSPARENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        try {
            int currentFlags = (Integer) mLayoutParams.getClass().getField("privateFlags").get(mLayoutParams);
            mLayoutParams.getClass().getField("privateFlags").set(mLayoutParams, currentFlags | 0x00000040);
        } catch (Exception ignore) {
        }
    }

    public void show() {
        FloatViewManager.getInstance().show(this);
    }

    public void dismiss() {
        FloatViewManager.getInstance().dismiss(this);
    }

    @Override
    public WindowManager.LayoutParams getLayoutParams() {
        return mLayoutParams;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AppUtils.registerAppStatusChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        AppUtils.unregisterAppStatusChangedListener(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onForeground(Activity activity) {
        setVisibility(VISIBLE);
    }

    @Override
    public void onBackground(Activity activity) {
        setVisibility(GONE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // When flag with FLAG_NOT_TOUCH_MODAL, should process the key event.
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity != null) {
            if (topActivity.getWindow().getDecorView().dispatchKeyEvent(event)) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
