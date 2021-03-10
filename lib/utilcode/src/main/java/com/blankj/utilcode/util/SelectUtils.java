package com.blankj.utilcode.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ----------------
 * |BatchEdit     |
 * |ItemA    ItemB|
 * ----------------
 * <p>
 * --------------------
 * |○ SelectAll       |
 * |○ ItemA    ● ItemB|
 * --------------------
 * <p>
 * --------------------
 * |● SelectAll       |
 * |● ItemA    ● ItemB|
 * --------------------
 */
public class SelectUtils<T extends SelectUtils.CanBeSelectedData> {

    public interface CanBeSelectedData {
        void setSelected(boolean selected);

        boolean isSelected();
    }

    public interface OnSelectChangedListener {
        void onSelectChanged(boolean allSelected);
    }

    private int mSelectedCount = 0;
    private List<T> mDataList;
    private OnSelectChangedListener mListener;

    public SelectUtils(@NonNull List<T> dataList) {
        this.mDataList = dataList;
    }

    public void setNewList(@NonNull List<T> dataList) {
        this.mSelectedCount = 0;
        this.mDataList = dataList;
    }

    public void setOnSelectChangeListener(OnSelectChangedListener listener) {
        if (null != listener) {
            this.mListener = listener;
        }
    }

    public int getSelectedCount() {
        return mSelectedCount;
    }

    public List<T> getSelectedList() {
        List<T> selectedList = new ArrayList<>();
        for (T t : mDataList) {
            if (t.isSelected()) {
                selectedList.add(t);
            }
        }
        return selectedList;
    }

    public void select(@NonNull T t) {
        mSelectedCount++;
        t.setSelected(true);
        notifySelectChanged();
    }

    public void unSelect(@NonNull T t) {
        mSelectedCount--;
        t.setSelected(false);
        notifySelectChanged();
    }

    public void selectAll() {
        for (T t : mDataList) {
            t.setSelected(true);
        }
        mSelectedCount = mDataList.size();
        notifySelectChanged();
    }

    public void unSelectAll() {
        unSelectAll(true);
    }

    public void reset() {
        unSelectAll(false);
    }

    public boolean allSelected() {
        return (mSelectedCount > 0 && mSelectedCount == mDataList.size());
    }

    private void unSelectAll(boolean notify) {
        mSelectedCount = 0;
        for (T t : mDataList) {
            t.setSelected(false);
        }
        if (notify) {
            notifySelectChanged();
        }
    }

    private void notifySelectChanged() {
        if (null != mListener) {
            mListener.onSelectChanged(allSelected());
        }
    }

}
