package com.blankj.common.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.base.BaseFragment;
import com.blankj.base.rv.BaseItemAdapter;
import com.blankj.base.rv.RecycleViewDivider;
import com.blankj.common.R;
import com.blankj.common.activity.CommonActivityItemsView;
import com.blankj.common.item.CommonItem;

import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/03
 *     desc  :
 * </pre>
 */
public class CommonFragment extends BaseFragment {

    private CommonActivityItemsView mItemsView;

    ///////////////////////////////////////////////////////////////////////////
    // items view
    ///////////////////////////////////////////////////////////////////////////
    public CommonActivityItemsView bindItemsView() {
        return null;
    }

    public List<CommonItem> bindItems() {
        return null;
    }

    @CallSuper
    @Override
    public void initData(@Nullable Bundle bundle) {
        mItemsView = bindItemsView();
        if (mItemsView == null) {
            List<CommonItem> items = bindItems();
            if (items != null) {
                mItemsView = new CommonActivityItemsView(mActivity, items);
            }
        }
    }

    @Override
    public int bindLayout() {
        return View.NO_ID;
    }

    @Override
    public void setContentView() {
        if (mItemsView != null) {
            mContentView = mInflater.inflate(mItemsView.bindLayout(), null);
        } else {
            super.setContentView();
        }
    }

    @CallSuper
    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        if (mItemsView != null) {
            mItemsView.initView();
        }
    }

    @Override
    public void doBusiness() {
        log("doBusiness");
    }

    @Override
    public void onDebouncingClick(@NonNull View view) {
    }


    public CommonActivityItemsView getItemsView() {
        return mItemsView;
    }

    private BaseItemAdapter<CommonItem> mCommonItemAdapter;

    public void setCommonItems(RecyclerView rv, List<CommonItem> items) {
        mCommonItemAdapter = new BaseItemAdapter<>();
        mCommonItemAdapter.setItems(items);
        rv.setAdapter(mCommonItemAdapter);
        rv.setLayoutManager(new LinearLayoutManager(mActivity));
        rv.addItemDecoration(new RecycleViewDivider(mActivity, RecycleViewDivider.VERTICAL, R.drawable.common_item_divider));
    }

    public void updateCommonItems(List<CommonItem> data) {
        mCommonItemAdapter.setItems(data);
        mCommonItemAdapter.notifyDataSetChanged();
    }

    public void updateCommonItem(int position) {
        mCommonItemAdapter.notifyItemChanged(position);
    }

    public BaseItemAdapter<CommonItem> getCommonItemAdapter() {
        return mCommonItemAdapter;
    }
}
