package com.blankj.base.rv;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/16
 *     desc  :
 * </pre>
 */
public abstract class BaseItem<T extends BaseItem> {

    private static final SparseIntArray    LAYOUT_SPARSE_ARRAY = new SparseIntArray();
    private static final SparseArray<View> VIEW_SPARSE_ARRAY   = new SparseArray<>();

    static ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutByType = LAYOUT_SPARSE_ARRAY.get(viewType, -1);
        if (layoutByType != -1) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutByType, parent, false));
        }
        View viewByType = VIEW_SPARSE_ARRAY.get(viewType);
        if (viewByType != null) {
            return new ItemViewHolder(viewByType);
        }
        throw new RuntimeException("onCreateViewHolder: get holder from view type failed.");
    }

    public abstract void bind(@NonNull final ItemViewHolder holder, final int position);

    public void onViewRecycled(@NonNull final ItemViewHolder holder, final int position) {/**/}

    public long getItemId() {
        return RecyclerView.NO_ID;
    }

    private int viewType;
    BaseItemAdapter<T> mAdapter;

    public BaseItem(@LayoutRes int layoutId) {
        viewType = getViewTypeByLayoutId(layoutId);
        LAYOUT_SPARSE_ARRAY.put(viewType, layoutId);
    }

    public BaseItem(@NonNull View view) {
        viewType = getViewTypeByView(view);
        VIEW_SPARSE_ARRAY.put(viewType, view);
    }

    public int getViewType() {
        return viewType;
    }

    public BaseItemAdapter<T> getAdapter() {
        return mAdapter;
    }

    public boolean isViewType(@LayoutRes int layoutId) {
        return viewType == getViewTypeByLayoutId(layoutId);
    }

    public boolean isViewType(@NonNull View view) {
        return viewType == getViewTypeByView(view);
    }

    private int getViewTypeByLayoutId(@LayoutRes int layoutId) {
        return layoutId + getClass().hashCode();
    }

    private int getViewTypeByView(@NonNull View view) {
        return view.hashCode() + getClass().hashCode();
    }
}
