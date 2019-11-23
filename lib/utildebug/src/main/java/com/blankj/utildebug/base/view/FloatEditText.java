package com.blankj.utildebug.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.helper.WindowHelper;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/10
 *     desc  :
 * </pre>
 */
@SuppressLint("AppCompatCustomView")
public class FloatEditText extends EditText {

    public FloatEditText(Context context) {
        super(context);
        init();
    }

    public FloatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.du_sel_et_bg);
        post(new Runnable() {
            @Override
            public void run() {
                View rootView = getRootView();
                if (rootView instanceof BaseContentFloatView) {
                    bindFloatView((BaseContentFloatView) rootView);
                }
            }
        });
    }

    public void bindFloatView(final BaseContentFloatView floatView) {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                WindowManager.LayoutParams params = floatView.getLayoutParams();
                if ((params.flags & WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE) == WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE) {
                    params.flags = params.flags & ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                    WindowHelper.updateViewLayout(floatView, params);
                    KeyboardUtils.showSoftInput(v);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        KeyboardUtils.hideSoftInput(this);
        super.onDetachedFromWindow();
    }
}
