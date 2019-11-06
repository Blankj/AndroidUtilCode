package com.blankj.common.item;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.base.rv.ItemViewHolder;
import com.blankj.common.R;
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
public class CommonItemImage extends CommonItem {

    private CharSequence                    mTitle;
    private Utils.Func1<Unit, ImageView>    mSetImageFunc1;

    public CommonItemImage(@StringRes int title, @NonNull Utils.Func1<Unit, ImageView> setImageFunc1) {
        this(StringUtils.getString(title), setImageFunc1);
    }

    public CommonItemImage(@NonNull CharSequence title, @NonNull Utils.Func1<Unit, ImageView> setImageFunc1) {
        super(R.layout.common_item_title_image);
        mTitle = title;
        mSetImageFunc1 = setImageFunc1;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        super.bind(holder, position);
        final TextView titleTv = holder.findViewById(R.id.commonItemTitleTv);

        titleTv.setText(mTitle);
        ImageView commonItemIv = holder.findViewById(R.id.commonItemIv);
        mSetImageFunc1.call(commonItemIv);
    }
}
