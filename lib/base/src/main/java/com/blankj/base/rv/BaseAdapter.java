package com.blankj.base.rv;

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

    public BaseCell       mEmptyCell;
    public List<BaseCell> mHeaders;
    public List<Cell>     mData;
    public List<BaseCell> mFooters;
    public Context        mContext;
    public LayoutInflater mInflater;

    @Override
    public final int getItemViewType(int position) {
        int headerSize = getHeaderSize();
        if (headerSize > position) {
            return mHeaders.get(position).viewType;
        }
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
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        int position = holder.getAdapterPosition();
        mData.get(position).onViewRecycled(holder, position);
    }

    @Override
    public int getItemCount() {
        return getHeaderSize() + getDataSize() + getFooterSize();
    }

    public void setEmptyCell(BaseCell emptyCell) {
        mEmptyCell = emptyCell;
    }

    private int getHeaderSize() {
        if (mHeaders == null) return 0;
        return mHeaders.size();
    }

    private int getDataSize() {
        if (mData == null) return 0;
        return mData.size();
    }

    private int getFooterSize() {
        if (mFooters == null) return 0;
        return mFooters.size();
    }

    public void setData(@NonNull final List<Cell> data) {
        mData = data;
    }

    public List<Cell> getData() {
        return mData;
    }


}
