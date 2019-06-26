package com.blankj.lib.base.rv;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/16
 *     desc  :
 * </pre>
 */
public abstract class BaseCell {

    private static final SparseIntArray    LAYOUT_SPARSE_ARRAY = new SparseIntArray();
    private static final SparseArray<View> VIEW_SPARSE_ARRAY   = new SparseArray<>();

    static int getLayoutByType(int type) {
        return LAYOUT_SPARSE_ARRAY.get(type, -1);
    }

    static View getViewByType(int type) {
        return VIEW_SPARSE_ARRAY.get(type);
    }

    public abstract void bind(@NonNull final BaseViewHolder holder, final int position);

    public void onViewRecycled(@NonNull final BaseViewHolder holder, final int position) {
    }

    protected int viewType;

    public BaseCell(int layoutId) {
        viewType = getClass().hashCode();
        LAYOUT_SPARSE_ARRAY.put(viewType, layoutId);
    }

    public BaseCell(View view) {
        viewType = getClass().hashCode();
        VIEW_SPARSE_ARRAY.put(viewType, view);
    }

    public static BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutByType = getLayoutByType(viewType);
        if (layoutByType != -1) {
            return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutByType, parent, false));
        }
        View viewByType = getViewByType(viewType);
        if (viewByType != null) {
            return new BaseViewHolder(viewByType);
        }
        throw new RuntimeException("onCreateViewHolder: get holder from view type failed.");
    }
}
