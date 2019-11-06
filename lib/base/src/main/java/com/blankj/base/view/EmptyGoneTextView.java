package com.blankj.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/03
 *     desc  :
 * </pre>
 */
@SuppressLint("AppCompatCustomView")
public class EmptyGoneTextView extends TextView {

    public EmptyGoneTextView(Context context) {
        this(context, null);
    }

    public EmptyGoneTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setVisibility(GONE);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (StringUtils.isEmpty(text)) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }
}
