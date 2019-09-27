package com.blankj.utildebug.debug.tool.appInfo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.BaseContentFloatView;
import com.blankj.utildebug.base.rv.BaseItemAdapter;
import com.blankj.utildebug.base.rv.RecycleViewDivider;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/02
 *     desc  :
 * </pre>
 */
public class AppInfoFloatView extends BaseContentFloatView<AppInfoFloatView> {

    private RecyclerView appInfoRv;

    @Override
    public int bindTitle() {
        return R.string.du_app_info;
    }

    @Override
    public int bindContentLayout() {
        return R.layout.du_debug_app_info;
    }

    @Override
    public void initContentView() {
        appInfoRv = findViewById(R.id.appInfoRv);
        BaseItemAdapter<AppInfoItem> adapter = new BaseItemAdapter<>();
        adapter.setItems(AppInfoItem.getAppInfoItems());
        appInfoRv.setAdapter(adapter);
        appInfoRv.setLayoutManager(new LinearLayoutManager(getContext()));
        appInfoRv.addItemDecoration(new RecycleViewDivider(getContext(), RecycleViewDivider.VERTICAL, R.drawable.du_shape_divider));
    }
}
