package com.blankj.common.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.base.BaseActivity;
import com.blankj.base.rv.BaseItemAdapter;
import com.blankj.base.rv.RecycleViewDivider;
import com.blankj.common.R;
import com.blankj.common.dialog.CommonLoadingDialog;
import com.blankj.common.item.CommonItem;
import com.blankj.swipepanel.SwipePanel;
import com.blankj.utilcode.util.SizeUtils;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/05
 *     desc  :
 * </pre>
 */
public abstract class CommonActivity extends BaseActivity {

    private CommonActivityItemsView  mItemsView;
    private CommonActivityTitleView  mTitleView;
    private CommonActivityDrawerView mDrawerView;

    private CommonLoadingDialog mLoadingDialog;

    public View commonContentView;

    ///////////////////////////////////////////////////////////////////////////
    // title view
    ///////////////////////////////////////////////////////////////////////////
    public boolean isSwipeBack() {
        return true;
    }

    @StringRes
    public int bindTitleRes() {
        return View.NO_ID;
    }

    public CharSequence bindTitle() {
        return "";
    }

    public boolean isSupportScroll() {
        return true;
    }

    public CommonActivityTitleView bindTitleView() {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // items view
    ///////////////////////////////////////////////////////////////////////////
    public CommonActivityItemsView bindItemsView() {
        return null;
    }

    public List<CommonItem> bindItems() {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // drawer view
    ///////////////////////////////////////////////////////////////////////////
    public CommonActivityDrawerView bindDrawerView() {
        return null;
    }

    public boolean bindDrawer() {
        return false;
    }

    @CallSuper
    @Override
    public void initData(@Nullable Bundle bundle) {
        mTitleView = bindTitleView();
        if (mTitleView == null) {
            int titleRes = bindTitleRes();
            if (titleRes != View.NO_ID) {
                mTitleView = new CommonActivityTitleView(this, titleRes, isSupportScroll());
            } else {
                CharSequence title = bindTitle();
                if (!TextUtils.isEmpty(title)) {
                    mTitleView = new CommonActivityTitleView(this, title, isSupportScroll());
                }
            }
        }

        mItemsView = bindItemsView();
        if (mItemsView == null) {
            List<CommonItem> items = bindItems();
            if (items != null) {
                mItemsView = new CommonActivityItemsView(this, items);
            }
        }

        mDrawerView = bindDrawerView();
        if (mDrawerView == null) {
            boolean bindDrawer = bindDrawer();
            if (bindDrawer) {
                mDrawerView = new CommonActivityDrawerView(this);
            }
        }

        if (mTitleView != null && mItemsView != null) {
            mTitleView.setIsSupportScroll(false);
        }

        findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.lightGrayDark));
        initSwipeBack();
    }

    @Override
    public int bindLayout() {
        return View.NO_ID;
    }

    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(int layoutId) {
        if (mTitleView != null) {
            super.setRootLayout(mTitleView.bindLayout());
            commonContentView = mTitleView.getContentView();
        } else if (mDrawerView != null) {
            super.setRootLayout(mDrawerView.bindLayout());
            commonContentView = mDrawerView.getContentView();
        } else {
            if (mItemsView != null) {
                super.setRootLayout(mItemsView.bindLayout());
                mItemsView.initView();
            } else {
                super.setRootLayout(layoutId);
            }
            commonContentView = mContentView;
            return;
        }

        if (mItemsView != null) {
            LayoutInflater.from(this).inflate(mItemsView.bindLayout(), (ViewGroup) commonContentView);
            mItemsView.initView();
        } else {
            if (layoutId > 0) {
                LayoutInflater.from(this).inflate(layoutId, (ViewGroup) commonContentView);
            }
        }
    }

    private void initSwipeBack() {
        if (isSwipeBack()) {
            final SwipePanel swipeLayout = new SwipePanel(this);
            swipeLayout.setLeftDrawable(R.drawable.common_back);
            swipeLayout.setLeftEdgeSize(SizeUtils.dp2px(16));
            swipeLayout.setLeftSwipeColor(getResources().getColor(R.color.colorPrimary));
            swipeLayout.wrapView(findViewById(android.R.id.content));
            swipeLayout.setOnFullSwipeListener(new SwipePanel.OnFullSwipeListener() {
                @Override
                public void onFullSwipe(int direction) {
                    swipeLayout.close(direction);
                    finish();
                }
            });
        }
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onDebouncingClick(@NonNull View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mTitleView != null) {
            return mTitleView.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoading() {
        showLoading(null);
    }

    public void showLoading(DialogInterface.OnCancelListener listener) {
        if (mLoadingDialog != null) {
            dismissLoading();
        }
        mLoadingDialog = new CommonLoadingDialog(this, listener);
        mLoadingDialog.show();
    }

    public void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public CommonActivityItemsView getItemsView() {
        return mItemsView;
    }

    public CommonActivityTitleView getTitleView() {
        return mTitleView;
    }

    public CommonActivityDrawerView getDrawerView() {
        return mDrawerView;
    }

    private BaseItemAdapter<CommonItem> mCommonItemAdapter;

    public void setCommonItems(RecyclerView rv, List<CommonItem> items) {
        mCommonItemAdapter = new BaseItemAdapter<>();
        mCommonItemAdapter.setItems(items);
        rv.setAdapter(mCommonItemAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, RecycleViewDivider.VERTICAL, R.drawable.common_item_divider));
    }

    public void updateCommonItems(List<CommonItem> data) {
        mCommonItemAdapter.setItems(data);
        mCommonItemAdapter.notifyDataSetChanged();
    }

    public void updateCommonItem(int position) {
        mCommonItemAdapter.notifyItemChanged(position);
    }
}