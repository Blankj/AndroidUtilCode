package com.blankj.common.item;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.base.rv.ItemViewHolder;
import com.blankj.common.R;
import com.blankj.utilcode.util.StringUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/31
 *     desc  :
 * </pre>
 */
public class CommonItemSeekBar extends CommonItem {

    private CharSequence     mTitle;
    private CharSequence     mContent;
    private int              mMaxProgress;
    private int              mCurProgress;
    private ProgressListener mProgressListener;

    public CommonItemSeekBar(@StringRes int title, int maxProgress, @NonNull ProgressListener listener) {
        this(StringUtils.getString(title), maxProgress, listener);
    }

    public CommonItemSeekBar(@NonNull CharSequence title, int maxProgress, @NonNull ProgressListener listener) {
        super(R.layout.common_item_title_seekbar);
        mTitle = title;
        mMaxProgress = maxProgress;
        mCurProgress = listener.getCurValue();
        mProgressListener = listener;
        mContent = String.valueOf(mCurProgress);
    }


    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        super.bind(holder, position);
        final TextView titleTv = holder.findViewById(R.id.commonItemTitleTv);
        final TextView contentTv = holder.findViewById(R.id.commonItemContentTv);

        titleTv.setText(mTitle);
        contentTv.setText(mContent);

        final SeekBar seekBar = holder.findViewById(R.id.commonItemSb);
        seekBar.setMax(mMaxProgress);
        seekBar.setProgress(mCurProgress);
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return seekBar.dispatchTouchEvent(event);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgressListener.onProgressChanged(seekBar, progress, fromUser);
                int curValue = mProgressListener.getCurValue();
                mCurProgress = curValue;
                contentTv.setText(String.valueOf(curValue));
                seekBar.setProgress(curValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mProgressListener.onStartTrackingTouch(seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mProgressListener.onStopTrackingTouch(seekBar);
            }
        });
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        update();
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public static abstract class ProgressListener implements SeekBar.OnSeekBarChangeListener {

        public abstract int getCurValue();

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
