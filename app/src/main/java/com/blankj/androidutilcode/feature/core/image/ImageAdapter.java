package com.blankj.androidutilcode.feature.core.image;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.rv.BaseViewHolder;
import com.blankj.androidutilcode.base.rv.adapter.SingleAdapter;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/09/18
 *     desc  :
 * </pre>
 */
public class ImageAdapter extends SingleAdapter<ImageBean> {

    public ImageAdapter(List<ImageBean> list, @LayoutRes int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, ImageBean data) {
        TextView textView = holder.getView(R.id.tv_image_name);
        textView.setText(data.getName());
        ImageView image = holder.getView(R.id.iv_image);
        image.setImageBitmap(data.getImage());
    }
}
