package com.blankj.common.item;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.base.rv.ItemViewHolder;
import com.blankj.common.R;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/31
 *     desc  :
 * </pre>
 */
public class CommonItemSwitch extends CommonItem {

    private CharSequence            mTitle;
    private CharSequence            mContent;
    private boolean                 mState;
    private Utils.Supplier<Boolean> mGetStateSupplier;
    private Utils.Consumer<Boolean> mSetStateConsumer;


    public CommonItemSwitch(@StringRes int title, @NonNull Utils.Supplier<Boolean> getStateSupplier, @NonNull Utils.Consumer<Boolean> setStateConsumer) {
        this(StringUtils.getString(title), getStateSupplier, setStateConsumer);
    }

    public CommonItemSwitch(@NonNull CharSequence title, @NonNull Utils.Supplier<Boolean> getStateSupplier, @NonNull Utils.Consumer<Boolean> setStateConsumer) {
        super(R.layout.common_item_title_switch);
        mTitle = title;
        mGetStateSupplier = getStateSupplier;
        mSetStateConsumer = setStateConsumer;
        mState = getStateSupplier.get();
        mContent = String.valueOf(mState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void bind(@NonNull final ItemViewHolder holder, int position) {
        super.bind(holder, position);
        ClickUtils.applyPressedBgDark(holder.itemView);
        final TextView titleTv = holder.findViewById(R.id.commonItemTitleTv);
        final TextView contentTv = holder.findViewById(R.id.commonItemContentTv);

        titleTv.setText(mTitle);
        contentTv.setText(mContent);

        final Switch switchView = holder.findViewById(R.id.commonItemSwitch);
        switchView.setChecked(mState);
        switchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.itemView.onTouchEvent(event);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetStateConsumer.accept(!mState);
                mState = mGetStateSupplier.get();
                contentTv.setText(String.valueOf(mState));
                switchView.setChecked(mState);
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
