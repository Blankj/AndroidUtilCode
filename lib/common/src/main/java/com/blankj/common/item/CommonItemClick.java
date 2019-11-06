package com.blankj.common.item;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.blankj.base.rv.ItemViewHolder;
import com.blankj.common.R;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;

import kotlin.Unit;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/31
 *     desc  :
 * </pre>
 */
public class CommonItemClick extends CommonItem<CommonItemClick> {

    private CharSequence mTitle;
    private CharSequence mContent;
    private boolean      mIsJump;

    public CommonItemClick(@StringRes int title) {
        this(StringUtils.getString(title), "", false, null);
    }

    public CommonItemClick(@NonNull CharSequence title) {
        this(title, "", false, null);
    }

    public CommonItemClick(@StringRes int title, boolean isJump) {
        this(StringUtils.getString(title), "", isJump);
    }

    public CommonItemClick(@NonNull CharSequence title, boolean isJump) {
        this(title, "", isJump, null);
    }

    public CommonItemClick(@StringRes int title, CharSequence content) {
        this(StringUtils.getString(title), content, false, null);
    }

    public CommonItemClick(@NonNull CharSequence title, CharSequence content) {
        this(title, content, false, null);
    }

    public CommonItemClick(@StringRes int title, CharSequence content, boolean isJump) {
        this(StringUtils.getString(title), content, isJump);
    }

    public CommonItemClick(@NonNull CharSequence title, CharSequence content, boolean isJump) {
        this(title, content, isJump, null);
    }

    public CommonItemClick(@StringRes int title, final Runnable simpleClickListener) {
        this(StringUtils.getString(title), "", false, simpleClickListener);
    }

    public CommonItemClick(@NonNull CharSequence title, final Runnable simpleClickListener) {
        this(title, "", false, simpleClickListener);
    }

    public CommonItemClick(@StringRes int title, boolean isJump, final Runnable simpleClickListener) {
        this(StringUtils.getString(title), "", isJump, simpleClickListener);
    }

    public CommonItemClick(@NonNull CharSequence title, boolean isJump, final Runnable simpleClickListener) {
        this(title, "", isJump, simpleClickListener);
    }

    public CommonItemClick(@StringRes int title, CharSequence content, final Runnable simpleClickListener) {
        this(StringUtils.getString(title), content, false, simpleClickListener);
    }

    public CommonItemClick(@NonNull CharSequence title, CharSequence content, final Runnable simpleClickListener) {
        this(title, content, false, simpleClickListener);
    }

    public CommonItemClick(@StringRes int title, CharSequence content, boolean isJump, final Runnable simpleClickListener) {
        this(StringUtils.getString(title), content, isJump, simpleClickListener);
    }

    public CommonItemClick(@NonNull CharSequence title, CharSequence content, boolean isJump, final Runnable simpleClickListener) {
        super(R.layout.common_item_title_click);
        mTitle = title;
        mContent = content;
        mIsJump = isJump;
        if (simpleClickListener == null) return;
        setOnItemClickListener(new OnItemClickListener<CommonItemClick>() {
            @Override
            public void onItemClick(ItemViewHolder holder, CommonItemClick item, int position) {
                if (simpleClickListener != null) {
                    simpleClickListener.run();
                }
            }
        });
    }

    public CommonItemClick setOnClickUpdateContentListener(@NonNull final Utils.Func1<CharSequence, Unit> func1) {
        setOnItemClickListener(new OnItemClickListener<CommonItemClick>() {
            @Override
            public void onItemClick(ItemViewHolder holder, CommonItemClick item, int position) {
                item.mContent = func1.call(null);
                update();
            }
        });
        return this;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        super.bind(holder, position);
        final TextView titleTv = holder.findViewById(R.id.commonItemTitleTv);
        final TextView contentTv = holder.findViewById(R.id.commonItemContentTv);

        titleTv.setText(mTitle);
        contentTv.setText(mContent);

        ClickUtils.applyPressedBgDark(holder.itemView);
        holder.findViewById(R.id.commonItemGoIv).setVisibility(mIsJump ? View.VISIBLE : View.GONE);
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        update();
    }

    public CharSequence getTitle() {
        return mTitle;
    }
}
