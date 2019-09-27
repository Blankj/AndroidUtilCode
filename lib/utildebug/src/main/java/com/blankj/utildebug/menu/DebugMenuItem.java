package com.blankj.utildebug.menu;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.blankj.utilcode.util.ShadowUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItem;
import com.blankj.utildebug.base.rv.BaseItemAdapter;
import com.blankj.utildebug.base.rv.ItemViewHolder;
import com.blankj.utildebug.debug.IDebug;
import com.blankj.utildebug.helper.ShadowHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/29
 *     desc  :
 * </pre>
 */
public class DebugMenuItem extends BaseItem<DebugMenuItem> {

    private String       mTitle;
    private List<IDebug> mDebugs;

    private DebugMenuItem(String title, List<IDebug> debugs) {
        super(R.layout.du_item_menu);
        mTitle = title;
        mDebugs = debugs;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        TextView menuTitle = holder.findViewById(R.id.menuCategory);
        RecyclerView menuRv = holder.findViewById(R.id.menuRv);

        ShadowHelper.applyMenu(holder.itemView);

        menuTitle.setText(mTitle);

        BaseItemAdapter<DebugItem> adapter = new BaseItemAdapter<>();
        adapter.setItems(DebugItem.getDebugItems(mDebugs));
        menuRv.setAdapter(adapter);
        menuRv.setLayoutManager(new GridLayoutManager(menuRv.getContext(), 4));
    }

    public static List<DebugMenuItem> getDebugMenuItems(List<IDebug> debugs) {
        Map<Integer, List<IDebug>> debugMap = new LinkedHashMap<>();
        for (IDebug debug : debugs) {
            List<IDebug> debugList = debugMap.get(debug.getCategory());
            if (debugList == null) {
                debugList = new ArrayList<>();
                debugMap.put(debug.getCategory(), debugList);
            }
            debugList.add(debug);
        }
        List<DebugMenuItem> itemList = new ArrayList<>();
        for (Map.Entry<Integer, List<IDebug>> entry : debugMap.entrySet()) {
            itemList.add(new DebugMenuItem(getCategoryString(entry.getKey()), entry.getValue()));
        }
        return itemList;
    }

    private static String getCategoryString(int category) {
        switch (category) {
            case IDebug.TOOLS:
                return StringUtils.getString(R.string.du_tools);
            case IDebug.PERFORMANCE:
                return StringUtils.getString(R.string.du_performance);
            case IDebug.UI:
                return StringUtils.getString(R.string.du_ui);
            case IDebug.BIZ:
                return StringUtils.getString(R.string.du_biz);
            default:
                return StringUtils.getString(R.string.du_uncategorized);
        }
    }
}
