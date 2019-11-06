package com.blankj.utildebug.debug.tool.fileExplorer.sp;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.MapUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItem;
import com.blankj.utildebug.base.rv.ItemViewHolder;
import com.blankj.utildebug.base.view.FloatToast;
import com.blankj.utildebug.debug.tool.fileExplorer.FileExplorerFloatView;
import com.blankj.utildebug.helper.SpHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/09
 *     desc  :
 * </pre>
 */
public class SpItem extends BaseItem<SpItem> {

    private SPUtils mSPUtils;
    private String  mKey;
    private Object  mValue;
    private Class   mClass;

    private RelativeLayout contentRl;
    private TextView       titleTv;
    private TextView       contentTv;
    private ImageView      goIv;
    private Switch         aSwitch;
    private TextView       deleteTv;

    public SpItem() {
        super(R.layout.du_item_empty);
    }

    public SpItem(SPUtils spUtils, String key, Object value) {
        super(R.layout.du_item_sp);
        mSPUtils = spUtils;
        mKey = key;
        mValue = value;
        mClass = mValue.getClass();
    }

    @Override
    public void bind(@NonNull final ItemViewHolder holder, int position) {
        if (isViewType(R.layout.du_item_empty)) return;
        contentRl = holder.findViewById(R.id.itemSpContentRl);
        titleTv = holder.findViewById(R.id.itemSpTitleTv);
        contentTv = holder.findViewById(R.id.itemSpContentTv);
        goIv = holder.findViewById(R.id.itemSpGoIv);
        aSwitch = holder.findViewById(R.id.itemSpSwitch);
        deleteTv = holder.findViewById(R.id.itemSpDeleteTv);

        SpanUtils.with(titleTv)
                .append(mKey)
                .append("(" + SpHelper.getSpClassName(mClass) + ")").setForegroundColor(ColorUtils.getColor(R.color.loveGreen))
                .create();
        contentTv.setText(mValue.toString());

        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatToast.showShort("haha");
            }
        });

        if (Boolean.class.equals(mClass)) {
            holder.itemView.setOnTouchListener(null);
            aSwitch.setVisibility(View.VISIBLE);
            goIv.setVisibility(View.GONE);
            aSwitch.setChecked((Boolean) mValue);
            View.OnClickListener toggle = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean state = !(Boolean) mValue;
                    mValue = state;
                    mSPUtils.put(mKey, state);
                    aSwitch.setChecked(state);
                    contentTv.setText(mValue.toString());
                }
            };
            aSwitch.setOnClickListener(toggle);
            contentRl.setOnClickListener(toggle);
        } else if (HashSet.class.equals(mClass)) {
            holder.itemView.setOnTouchListener(null);
            aSwitch.setVisibility(View.GONE);
            goIv.setVisibility(View.GONE);
            contentRl.setOnClickListener(null);
        } else {
            ClickUtils.applyPressedBgDark(holder.itemView);
            aSwitch.setVisibility(View.GONE);
            goIv.setVisibility(View.VISIBLE);
            contentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileExplorerFloatView floatView = (FileExplorerFloatView) v.getRootView();
                    SpModifyContentView.show(floatView, mSPUtils, mKey, mValue);
                }
            });
        }
    }

    public static List<SpItem> getSpItems(SPUtils spUtils) {
        Map<String, ?> spMap = spUtils.getAll();
        if (MapUtils.isEmpty(spMap)) {
            return CollectionUtils.newArrayList(new SpItem());
        }
        List<SpItem> items = new ArrayList<>();
        for (Map.Entry<String, ?> entry : spMap.entrySet()) {
            items.add(new SpItem(spUtils, entry.getKey(), entry.getValue()));
        }
        return items;
    }

    public static List<SpItem> filterItems(List<SpItem> items, final String key) {
        return (List<SpItem>) CollectionUtils.select(items, new CollectionUtils.Predicate<SpItem>() {
            @Override
            public boolean evaluate(SpItem item) {
                return item.mKey.toLowerCase().contains(key.toLowerCase());
            }
        });
    }
}
