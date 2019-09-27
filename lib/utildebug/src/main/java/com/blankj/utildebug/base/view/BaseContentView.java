package com.blankj.utildebug.base.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.blankj.utildebug.DebugUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.listener.OnBackListener;
import com.blankj.utildebug.base.view.listener.OnRefreshListener;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/01
 *     desc  :
 * </pre>
 */
public abstract class BaseContentView<T extends BaseContentFloatView<T>> extends FrameLayout
        implements OnBackListener {

    private T       mFloatView;
    private boolean isAddStack;

    private OnRefreshListener mRefreshRunnable;
    private boolean           mRefreshEnabled;

    public BaseContentView() {
        super(DebugUtils.getApp());
        setId(R.id.baseContentView);
        inflate(getContext(), bindLayout(), this);
    }

    public void attach(T floatView, boolean isAddStack) {
        this.mFloatView = floatView;
        this.isAddStack = isAddStack;
        floatView.replace(this, isAddStack);
    }

    @LayoutRes
    public abstract int bindLayout();

    public abstract void onAttach();

    public T getFloatView() {
        return mFloatView;
    }

    public boolean isAddStack() {
        return isAddStack;
    }

    public void setOnRefreshListener(RecyclerView rv, OnRefreshListener listener) {
        mRefreshRunnable = listener;
        mFloatView.setOnRefreshListener(listener);
        attachRefresh(rv);
    }

    @Override
    public void onBack() {
    }

    OnRefreshListener getOnRefreshListener() {
        return mRefreshRunnable;
    }

    boolean isRefreshEnabled() {
        return mRefreshEnabled;
    }

    private void attachRefresh(RecyclerView rv) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mRefreshEnabled = newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(-1);
                mFloatView.setRefreshEnabled(mRefreshEnabled);
            }
        });
        mRefreshEnabled = !rv.canScrollVertically(-1);
        mFloatView.setRefreshEnabled(mRefreshEnabled);
    }
}
