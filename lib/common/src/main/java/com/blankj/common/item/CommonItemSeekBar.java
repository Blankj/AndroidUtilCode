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

    private CharSequence                    mTitle;
    private CharSequence                    mContent;
    private int                             mMaxProgress;
    private int                             mCurProgress;
    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    public CommonItemSeekBar(@StringRes int title, int maxProgress, int curProgress, @NonNull SeekBar.OnSeekBarChangeListener itemClickListener) {
        this(StringUtils.getString(title), maxProgress, curProgress, itemClickListener);
    }

    public CommonItemSeekBar(@NonNull CharSequence title, int maxProgress, int curProgress, @NonNull SeekBar.OnSeekBarChangeListener itemClickListener) {
        super(R.layout.common_item_title_seekbar);
        mTitle = title;
        mMaxProgress = maxProgress;
        mCurProgress = curProgress;
        mOnSeekBarChangeListener = itemClickListener;
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
                mOnSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                mCurProgress = progress;
                contentTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
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
}
