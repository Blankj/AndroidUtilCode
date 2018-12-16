package com.blankj.lib.base.rv.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.lib.base.rv.BaseViewHolder;
import com.blankj.lib.base.rv.listener.OnItemClickListener;
import com.blankj.lib.base.rv.listener.OnItemLongClickListener;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/22
 *     desc  :
 * </pre>
 */
public abstract class BaseAdapter<M> extends RecyclerView.Adapter<BaseViewHolder> {

    protected static final int VIEW_TYPE_EMPTY   = 0xfff0;
    protected static final int VIEW_TYPE_HEADER  = 0xfff1;
    protected static final int VIEW_TYPE_FOOTER  = 0xfff2;
    protected static final int VIEW_TYPE_DEFAULT = 0xfff3;

    private final SparseArray<View> mViewArray = new SparseArray<>();

    protected List<M>   mData;
    protected Context   mContext;
    protected ViewGroup mParent;

    protected LayoutInflater mInflater;

    private OnItemClickListener     mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public void setData(@NonNull final List<M> data) {
        mData = data;
    }

    @Override
    public final int getItemViewType(int position) {
        if (getDataSize() == 0 && mViewArray.get(VIEW_TYPE_EMPTY) != null) {
            return VIEW_TYPE_EMPTY;
        } else if (position == 0 && mViewArray.get(VIEW_TYPE_HEADER) != null) {
            return VIEW_TYPE_HEADER;
        } else if (position == getItemCount() - 1 && mViewArray.get(VIEW_TYPE_FOOTER) != null) {
            return VIEW_TYPE_FOOTER;
        } else {
            return getCustomViewType(position);
        }
    }

    protected int getCustomViewType(final int position) {
        return VIEW_TYPE_DEFAULT;
    }

    protected abstract int bindLayout(final int viewType);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mParent == null) {
            mParent = parent;
            mContext = parent.getContext();
            mInflater = LayoutInflater.from(mContext);
        }
        View itemView = mViewArray.get(viewType);
        if (itemView == null) {
            itemView = inflateLayout(bindLayout(viewType));
        }
        return new BaseViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_EMPTY:
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
                break;
            default:
                bindCustomViewHolder(holder, position);
                break;
        }
    }

    protected void bindCustomViewHolder(final BaseViewHolder holder, final int position) {
        final int dataPos = position - (mViewArray.get(VIEW_TYPE_HEADER) == null ? 0 : 1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, dataPos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mLongClickListener != null && mLongClickListener.onItemLongClick(v, dataPos);
            }
        });
        bind(holder, mData.get(dataPos));
    }

    protected abstract void bind(final BaseViewHolder holder, final M data);

    @Override
    public int getItemCount() {
        return getDataSize() + getExtraViewCount();
    }

    public void setEmptyView(@NonNull View emptyView) {
        setView(VIEW_TYPE_EMPTY, emptyView);
    }

    public View getEmptyView() {
        return getView(VIEW_TYPE_EMPTY);
    }

    public void removeEmptyView() {
        removeView(VIEW_TYPE_EMPTY);
    }

    public void setHeaderView(@NonNull View headerView) {
        setView(VIEW_TYPE_HEADER, headerView);
    }

    public View getHeaderView() {
        return getView(VIEW_TYPE_HEADER);
    }

    public void removeHeaderView() {
        removeView(VIEW_TYPE_HEADER);
    }

    public void setFooterView(@NonNull View footerView) {
        setView(VIEW_TYPE_FOOTER, footerView);
    }

    public View getFooterView() {
        return getView(VIEW_TYPE_FOOTER);
    }

    public void removeFooterView() {
        removeView(VIEW_TYPE_FOOTER);
    }

    private void setView(final int type, @NonNull final View view) {
        mViewArray.put(type, view);
        notifyDataSetChanged();
    }

    private View getView(final int type) {
        return mViewArray.get(type);
    }

    private void removeView(final int type) {
        if (mViewArray.get(type) != null) {
            mViewArray.delete(type);
            notifyDataSetChanged();
        }
    }

    private View inflateLayout(@LayoutRes final int layoutId) {
        return mInflater.inflate(layoutId, mParent, false);
    }

    private int getExtraViewCount() {
        int extraViewCount = 0;
        if (mViewArray.get(VIEW_TYPE_HEADER) != null) {
            extraViewCount++;
        }
        if (mViewArray.get(VIEW_TYPE_FOOTER) != null) {
            extraViewCount++;
        }
        return extraViewCount;
    }

    public void setOnItemClickListener(final OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    private int getDataSize() {
        return mData == null ? 0 : mData.size();
    }
}
