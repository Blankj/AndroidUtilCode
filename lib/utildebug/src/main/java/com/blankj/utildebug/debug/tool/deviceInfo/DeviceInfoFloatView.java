package com.blankj.utildebug.debug.tool.deviceInfo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItemAdapter;
import com.blankj.utildebug.base.rv.RecycleViewDivider;
import com.blankj.utildebug.base.view.BaseContentFloatView;
import com.blankj.utildebug.base.view.listener.OnRefreshListener;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/02
 *     desc  :
 * </pre>
 */
public class DeviceInfoFloatView extends BaseContentFloatView<DeviceInfoFloatView> {

    private RecyclerView deviceInfoRv;

    @Override
    public int bindTitle() {
        return R.string.du_device_info;
    }

    @Override
    public int bindContentLayout() {
        return R.layout.du_debug_device_info;
    }

    @Override
    public void initContentView() {
        deviceInfoRv = findViewById(R.id.deviceInfoRv);
        final BaseItemAdapter<DeviceInfoItem> adapter = new BaseItemAdapter<>();
        adapter.setItems(DeviceInfoItem.getAppInfoItems());
        deviceInfoRv.setAdapter(adapter);
        deviceInfoRv.setLayoutManager(new LinearLayoutManager(getContext()));
        deviceInfoRv.addItemDecoration(new RecycleViewDivider(getContext(), RecycleViewDivider.VERTICAL, R.drawable.du_shape_divider));

        getContentView().setOnRefreshListener(deviceInfoRv, new OnRefreshListener() {
            @Override
            public void onRefresh(final BaseContentFloatView floatView) {
                adapter.setItems(DeviceInfoItem.getAppInfoItems());
                adapter.notifyDataSetChanged();
                floatView.closeRefresh();
            }
        });
    }
}
