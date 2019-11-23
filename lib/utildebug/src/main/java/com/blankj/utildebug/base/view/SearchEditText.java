package com.blankj.utildebug.base.view;

import android.content.Context;
import android.util.AttributeSet;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/06
 *     desc  :
 * </pre>
 */
public class SearchEditText extends FloatEditText {

    private static final long LIMIT = 200;

    private OnTextChangedListener mListener;
    private String                mStartSearchText = "";// 记录开始输入前的文本内容
    private Runnable              mAction          = new Runnable() {
        @Override
        public void run() {
            if (mListener != null) {
                // 判断最终和开始前是否一致
                if (!StringUtils.equals(mStartSearchText, getText().toString())) {
                    mStartSearchText = getText().toString();// 更新 mStartSearchText
                    mListener.onTextChanged(mStartSearchText);
                }
            }
        }
    };

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 在 LIMIT 时间内连续输入不触发文本变化
     */
    public void setOnTextChangedListener(OnTextChangedListener listener) {
        mListener = listener;
    }

    public void reset() {
        mStartSearchText = "";
        setText("");
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    protected void onTextChanged(final CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        // 移除上一次的回调
        removeCallbacks(mAction);
        postDelayed(mAction, LIMIT);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(mAction);
        KeyboardUtils.hideSoftInput(this);
        super.onDetachedFromWindow();
    }

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }
}
