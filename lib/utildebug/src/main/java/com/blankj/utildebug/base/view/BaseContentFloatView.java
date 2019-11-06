package com.blankj.utildebug.base.view;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.swipepanel.SwipePanel;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TouchUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.listener.OnRefreshListener;
import com.blankj.utildebug.config.DebugConfig;
import com.blankj.utildebug.helper.ShadowHelper;
import com.blankj.utildebug.helper.WindowHelper;

import java.util.Stack;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/30
 *     desc  :
 * </pre>
 */
public abstract class BaseContentFloatView<T extends BaseContentFloatView<T>> extends BaseFloatView {

    private static final int ROTATE_DELAY = 30;

    private LinearLayout       bcfRootLayout;
    private RelativeLayout     bcfTitleRl;
    private ImageView          bcfCloseIv;
    private TextView           bcfTitleTv;
    private ImageView          bcfAdjustIv;
    private SwipePanel         swipePanel;
    private BaseContentView<T> mContentView;

    private OnRefreshListener mRefreshListener;
    private Runnable          mRotateRunnable = new Runnable() {
        @Override
        public void run() {
            Drawable topDrawable = swipePanel.getTopDrawable();
            topDrawable.setLevel(topDrawable.getLevel() + 500);
            swipePanel.invalidate();
            startRotate();
        }
    };

    private static final ViewGroup.LayoutParams PARAMS = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    );

    private Stack<BaseContentView<T>> mViewStack = new Stack<>();

    @StringRes
    public abstract int bindTitle();

    @LayoutRes
    public abstract int bindContentLayout();

    @Override
    public int bindLayout() {
        return R.layout.du_base_content_float;
    }

    public BaseContentFloatView() {
        bcfRootLayout = findViewById(R.id.bcfRootLayout);
        ShadowHelper.applyFloatView(bcfRootLayout);

        initTitleBar();
        initSwipePanel();

        if (bindContentLayout() != NO_ID) {
            //noinspection unchecked
            new BaseContentView<T>() {
                @Override
                public int bindLayout() {
                    return BaseContentFloatView.this.bindContentLayout();
                }

                @Override
                public void onAttach() {
                }
            }.attach((T) this, true);
        }
    }

    public void setTitle(CharSequence title) {
        bcfTitleTv.setText(title);
    }

    void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    void setRefreshEnabled(boolean enabled) {
        swipePanel.setTopEnabled(enabled);
    }

    public void setSwipeBackEnabled(boolean enabled) {
        swipePanel.setLeftEnabled(enabled);
    }

    public void back() {
        swipePanel.close(SwipePanel.TOP, false);// 返回先关闭刷新
        swipePanel.removeAllViews();
        if (mContentView == null) {
            dismiss();
            return;
        }
        mContentView.onBack();
        if (mViewStack.isEmpty()) {
            dismiss();
            return;
        }
        if (mContentView.isAddStack()) {
            mViewStack.pop();
        }
        if (mViewStack.isEmpty()) {
            dismiss();
            return;
        }
        BaseContentView<T> peek = mViewStack.peek();
        swipePanel.addView(peek, PARAMS);
        setOnRefreshListener(peek.getOnRefreshListener());
        setRefreshEnabled(peek.isRefreshEnabled());
        mContentView = peek;
    }

    public void closeRefresh() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                swipePanel.close(SwipePanel.TOP);
            }
        }, 500);
    }

    public BaseContentView<T> getContentView() {
        return mContentView;
    }

    @Override
    protected void onDetachedFromWindow() {
        DebugConfig.saveViewY(this, mLayoutParams.y);
        DebugConfig.saveViewHeight(this, mLayoutParams.height);
        DebugConfig.saveViewAlpha(this, mLayoutParams.alpha);
        super.onDetachedFromWindow();
    }

    void replace(BaseContentView<T> view, boolean isAddStack) {
        if (view == null) return;
        if (isAddStack) {
            mViewStack.add(view);
        }
        swipePanel.removeAllViews();
        mContentView = view;
        mContentView.onAttach();
        swipePanel.addView(mContentView, PARAMS);
    }

    private void initTitleBar() {
        bcfTitleRl = findViewById(R.id.bcfTitleRl);
        bcfCloseIv = findViewById(R.id.bcfCloseIv);
        bcfTitleTv = findViewById(R.id.bcfTitleTv);
        bcfAdjustIv = findViewById(R.id.bcfAdjustIv);

        bcfTitleTv.setText(bindTitle());

        ClickUtils.applyPressedBgDark(bcfTitleRl);
        bcfTitleRl.setOnClickListener(new ClickUtils.OnMultiClickListener(2) {
            @Override
            public void onTriggerClick(View v) {
                mLayoutParams.alpha = mLayoutParams.alpha == 0.5f ? 1f : 0.5f;
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
                DebugConfig.saveViewAlpha(BaseContentFloatView.this, mLayoutParams.alpha);
            }

            @Override
            public void onBeforeTriggerClick(View v, int count) {
                if (count == 1) {

                }
            }
        });
        TouchUtils.setOnTouchListener(bcfTitleRl, new TouchUtils.OnTouchUtilsListener() {
            @Override
            public boolean onDown(View view, int x, int y, MotionEvent event) {
                return true;
            }

            @Override
            public boolean onMove(View view, int direction, int x, int y, int dx, int dy, int totalX, int totalY, MotionEvent event) {
                mLayoutParams.y = Math.min(Math.max(mLayoutParams.y + dy, 0), WindowHelper.getAppWindowHeight() - bcfRootLayout.getHeight());
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
                return true;
            }

            @Override
            public boolean onStop(View view, int direction, int x, int y, int totalX, int totalY, int vx, int vy, MotionEvent event) {
                DebugConfig.saveViewY(BaseContentFloatView.this, mLayoutParams.y);
                return true;
            }
        });

        ClickUtils.applyPressedBgDark(bcfCloseIv, 0.8f);
        bcfCloseIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ClickUtils.applyPressedBgDark(bcfAdjustIv, 0.8f);
        bcfAdjustIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatToast.showLong(FloatToast.WARNING, StringUtils.getString(R.string.du_adjust_tips));
            }
        });
        TouchUtils.setOnTouchListener(bcfAdjustIv, new TouchUtils.OnTouchUtilsListener() {

            private int minHeight;

            @Override
            public boolean onDown(View view, int x, int y, MotionEvent event) {
                int[] locations = new int[2];
                getLocationOnScreen(locations);
                mLayoutParams.height = WindowHelper.getAppWindowHeight() - locations[1];
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);

                minHeight = bcfTitleRl.getHeight() + SizeUtils.dp2px(30);
                return true;
            }

            @Override
            public boolean onMove(View view, int direction, int x, int y, int dx, final int dy, int totalX, int totalY, MotionEvent event) {
                ViewGroup.LayoutParams layoutParams = bcfRootLayout.getLayoutParams();
                layoutParams.height = Math.min(Math.max(bcfRootLayout.getHeight() + dy, minHeight), mLayoutParams.height);
                bcfRootLayout.setLayoutParams(layoutParams);
                return true;
            }

            @Override
            public boolean onStop(View view, int direction, int x, int y, int totalX, int totalY, int vx, int vy, MotionEvent event) {
                mLayoutParams.height = bcfRootLayout.getHeight();
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
                DebugConfig.saveViewHeight(BaseContentFloatView.this, mLayoutParams.height);
                return true;
            }
        });
    }

    private void initSwipePanel() {
        swipePanel = findViewById(R.id.bcfSwipePanel);
        swipePanel.setOnFullSwipeListener(new SwipePanel.OnFullSwipeListener() {
            @Override
            public void onFullSwipe(int direction) {
                if (direction == SwipePanel.LEFT) {
                    swipePanel.close(direction);
                    back();
                } else if (direction == SwipePanel.TOP) {
                    if (mRefreshListener != null) {
                        startRotate();
                        mRefreshListener.onRefresh(BaseContentFloatView.this);
                    }
                }
            }
        });

        swipePanel.setOnProgressChangedListener(new SwipePanel.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int direction, float progress, boolean isTouch) {
                if (direction == SwipePanel.TOP) {
                    Drawable topDrawable = swipePanel.getTopDrawable();
                    if (isTouch) {
                        topDrawable.setLevel((int) (progress * 20000));
                    } else {
                        if (progress < 0.5) {
                            stopRotate();
                        }
                    }
                }
            }
        });
    }

    private void startRotate() {
        swipePanel.postDelayed(mRotateRunnable, ROTATE_DELAY);
    }

    private void stopRotate() {
        swipePanel.removeCallbacks(mRotateRunnable);
    }

    @Override
    protected void onCreateLayoutParams() {
        super.onCreateLayoutParams();
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = DebugConfig.getViewHeight(BaseContentFloatView.this, WindowManager.LayoutParams.WRAP_CONTENT);
        mLayoutParams.windowAnimations = R.style.FloatAnimation;
        mLayoutParams.alpha = DebugConfig.getViewAlpha(this);
        mLayoutParams.y = DebugConfig.getViewY(this);
        post(new Runnable() {
            @Override
            public void run() {
                wrapWindow();
            }
        });
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        wrapWindow();
    }

    private void wrapWindow() {
        int[] locations = new int[2];
        getLocationOnScreen(locations);
        int floatViewHeight = DebugConfig.getViewHeight(BaseContentFloatView.this, bcfRootLayout.getHeight());
        if (locations[1] + floatViewHeight > WindowHelper.getAppWindowHeight()) {
            floatViewHeight = WindowHelper.getAppWindowHeight() - locations[1];
        }
        mLayoutParams.height = floatViewHeight;

        WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);

        ViewGroup.LayoutParams layoutParams = bcfRootLayout.getLayoutParams();
        layoutParams.height = mLayoutParams.height;
        bcfRootLayout.setLayoutParams(layoutParams);
    }
}