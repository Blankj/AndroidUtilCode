package com.blankj.utildebug.base.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.swipepanel.SwipePanel;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.drawable.ShadowUtils;
import com.blankj.utildebug.base.view.listener.OnRefreshListener;
import com.blankj.utildebug.config.DebugConfig;
import com.blankj.utildebug.helper.TouchHelper;
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

    private RelativeLayout     bcfTitleRl;
    private ImageView          bcfCloseIv;
    private TextView           bcfTitleTv;
    private ImageView          bcfAdjustIv;
    private SwipePanel         swipePanel;
    private BaseContentView<T> mContentView;

    private int               mTitleBarHeight;
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
        initTitleBar();
        initSwipePanel();
        View bcfRootLl = findViewById(R.id.bcfRootLl);
        ShadowUtils.apply(bcfRootLl, new ShadowUtils.Builder().setShadowRadius(SizeUtils.dp2px(8)));

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

        if (mLayoutParams.height == WindowManager.LayoutParams.WRAP_CONTENT) {
            post(new Runnable() {
                @Override
                public void run() {
                    int contentHeight = 0;
                    if (mContentView != null) {
                        contentHeight = mContentView.getHeight();
                    }
                    mLayoutParams.height = contentHeight + bcfTitleRl.getHeight() + SizeUtils.dp2px(8 + 8 + 4);
                    WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
                }
            });
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
        DebugConfig.saveFloatViewY(this, mLayoutParams.y);
        DebugConfig.saveFloatViewHeight(this, mLayoutParams.height);
        DebugConfig.saveFloatViewAlpha(this, mLayoutParams.alpha);
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

        bcfCloseIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ClickUtils.applyPressedBgDark(bcfTitleRl);
        ClickUtils.applyPressedBgDark(bcfCloseIv, 0.8f);
        ClickUtils.applyPressedBgDark(bcfAdjustIv, 0.8f);

        bcfTitleTv.setText(bindTitle());

        bcfTitleRl.setOnClickListener(new ClickUtils.OnMultiClickListener(2) {
            @Override
            public void onTriggerClick(View v) {
                mLayoutParams.alpha = mLayoutParams.alpha == 0.5f ? 1f : 0.5f;
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
            }

            @Override
            public void onBeforeTriggerClick(View v, int count) {
                if (count == 1) {

                }
            }
        });
        TouchHelper.applyDrag(bcfTitleRl, new TouchHelper.OnDragListener() {
            @Override
            public void onDown(View v, int x, int y, MotionEvent event) {
            }

            @Override
            public void onMove(View view, int x, int y, int dx, int dy, MotionEvent event) {
//                bcfTitleTv.setText("(" + mLayoutParams.x + ", " + mLayoutParams.y + ")" + mLayoutParams.height);
                mLayoutParams.y = Math.min(Math.max(mLayoutParams.y - dy, 0), WindowHelper.getAppWindowHeight() - getWindowHeight());
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
            }

            @Override
            public void onStop(View view, int x, int y, MotionEvent event) {
            }
        });

        bcfAdjustIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatToast.showLong(FloatToast.WARNING, StringUtils.getString(R.string.du_adjust_tips));
            }
        });
        TouchHelper.applyDrag(bcfAdjustIv, new TouchHelper.OnDragListener() {

            @Override
            public void onDown(View v, int x, int y, MotionEvent event) {
                if (mTitleBarHeight == 0) {
                    mTitleBarHeight = findViewById(R.id.bcfTitleRl).getHeight();
                }
            }

            @Override
            public void onMove(View view, int x, int y, int dx, int dy, MotionEvent event) {
//                bcfTitleTv.setText("(" + mLayoutParams.x + ", " + mLayoutParams.y + ")" + mLayoutParams.height);
                mLayoutParams.height = Math.min(Math.max(getWindowHeight() - dy, mTitleBarHeight + SizeUtils.dp2px(30)), WindowHelper.getAppWindowHeight() - mLayoutParams.y);
                WindowHelper.updateViewLayout(BaseContentFloatView.this, mLayoutParams);
            }

            @Override
            public void onStop(View view, int x, int y, MotionEvent event) {
            }
        });
    }

    private void initSwipePanel() {
        swipePanel = findViewById(R.id.baseFloatSwipePanel);
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
        mLayoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.windowAnimations = R.style.FloatAnimation;
        mLayoutParams.height = DebugConfig.getFloatViewHeight(this);
        mLayoutParams.y = DebugConfig.getFloatViewY(this);
        mLayoutParams.alpha = DebugConfig.getFloatViewAlpha(this);
    }

    private int getWindowHeight() {
        if (mLayoutParams.height == WindowManager.LayoutParams.WRAP_CONTENT) {
            return BaseContentFloatView.this.getHeight();
        }
        return mLayoutParams.height;
    }
}