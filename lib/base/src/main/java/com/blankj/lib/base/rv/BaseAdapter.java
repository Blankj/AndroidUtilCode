package com.blankj.lib.base.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/22
 *     desc  :
 * </pre>
 */
public class BaseAdapter<Cell extends BaseCell> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<Cell>     mData;
    public Context        mContext;
    public LayoutInflater mInflater;

    public void setData(@NonNull final List<Cell> data) {
        mData = data;
    }

    public List<Cell> getData() {
        return mData;
    }

    @Override
    public final int getItemViewType(int position) {
        return mData.get(position).viewType;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = Cell.onCreateViewHolder(parent, viewType);
        return baseViewHolder;
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        mData.get(position).bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        int position = holder.getAdapterPosition();
        mData.get(position).onViewRecycled(holder, position);
    }
}
