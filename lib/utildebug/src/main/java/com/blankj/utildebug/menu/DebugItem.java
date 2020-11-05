package com.blankj.utildebug.menu;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.drawable.PolygonDrawable;
import com.blankj.utildebug.base.rv.BaseItem;
import com.blankj.utildebug.base.rv.ItemViewHolder;
import com.blankj.utildebug.debug.IDebug;

import java.util.List;
import java.util.Random;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/29
 *     desc  :
 * </pre>
 */
public class DebugItem extends BaseItem<DebugItem> {

    private IDebug mDebug;
    private int    mColor = getRandomColor();

    private DebugItem(IDebug debug) {
        super(R.layout.du_item_menu_item);
        mDebug = debug;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        ImageView menuItemIconIv = holder.findViewById(R.id.menuItemIconIv);
        TextView menuItemNameTv = holder.findViewById(R.id.menuItemNameTv);

        ClickUtils.applyPressedBgDark(holder.itemView);
        ClickUtils.applyPressedViewScale(holder.itemView);

        menuItemIconIv.setBackgroundDrawable(new PolygonDrawable(5, mColor));
        menuItemIconIv.setImageResource(mDebug.getIcon());
        menuItemNameTv.setText(StringUtils.getString(mDebug.getName()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDebug.onClick(v);
            }
        });
    }

    public static List<DebugItem> getDebugItems(List<IDebug> debugs) {
        return (List<DebugItem>) CollectionUtils.collect(debugs, new CollectionUtils.Transformer<IDebug, DebugItem>() {
            @Override
            public DebugItem transform(IDebug input) {
                return new DebugItem(input);
            }
        });
    }

    private static final Random RANDOM = new Random();

    private static int getRandomColor() {
        return ColorUtils.getColor(COLORS[RANDOM.nextInt(6)]);
    }

    private static final int[] COLORS = new int[]{
            R.color.bittersweet, R.color.sunflower, R.color.grass,
            R.color.blueJeans, R.color.lavander, R.color.pinkRose
    };
}
