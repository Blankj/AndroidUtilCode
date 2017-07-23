package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : 栏相关工具类
 * </pre>
 */
public final class BarUtils {

    ///////////////////////////////////////////////////////////////////////////
    // status bar
    ///////////////////////////////////////////////////////////////////////////

    private static final int    DEFAULT_ALPHA = 112;
    private static final String TAG_COLOR     = "TAG_COLOR";
    private static final String TAG_ALPHA     = "TAG_ALPHA";
    private static final int    TAG_OFFSET    = -123;

    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取状态栏高度(px)
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        Resources resources = Utils.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity activity
     * @param color    状态栏颜色值
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setStatusBarColor(activity, color, DEFAULT_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideAlphaView(activity);
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getStatusBarColor(color, alpha));
            fitWindowAndClipToPadding(activity);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            addColorView(activity, color, alpha);
            fitWindowAndClipToPadding(activity);
        }
    }

    /**
     * 设置状态栏透明度
     *
     * @param activity activity
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @NonNull final View... needOffsetViews) {
        setStatusBarAlpha(activity, DEFAULT_ALPHA, needOffsetViews);
    }

    /**
     * 设置状态栏透明度
     *
     * @param activity activity
     * @param alpha    状态栏透明度
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         @NonNull final View... needOffsetViews) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideColorView(activity);
        transparentStatusBar(activity);
        addAlphaView(activity, alpha);
        for (View view : needOffsetViews) {
            addOffset(activity, view);
        }
    }

    /**
     * 为fragment初始化状态栏
     *
     * @param activity activity
     */
    public static void initStatusBar4Fragment(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;

    }

    public static void addPaddingTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop() + getStatusBarHeight(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    public static void setFakeStatusBarColor(@NonNull View fakeStatusBar,
                                             @ColorInt final int color,
                                             @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha));
    }

    public static void setFakeStatusBarAlpha(@NonNull View fakeStatusBar,
                                             @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }

    /**
     * 为背景图设置状态栏透明度
     *
     * @param activity activity
     */
    public static void setStatusBarColorInFragment(@NonNull final Activity activity,
                                                   @ColorInt final int color,
                                                   @NonNull final View... needOffsetViews) {
        setStatusBarColorInFragment(activity, color, DEFAULT_ALPHA, needOffsetViews);
    }

    /**
     * 为背景图设置状态栏透明度
     *
     * @param activity activity
     * @param alpha    状态栏透明度
     */
    public static void setStatusBarColorInFragment(@NonNull final Activity activity,
                                                   @ColorInt final int color,
                                                   @IntRange(from = 0, to = 255) final int alpha,
                                                   @NonNull final View... needOffsetViews) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        hideAlphaView(activity);
        addColorView(activity, color, alpha);
        for (View view : needOffsetViews) {
            addOffset(activity, view);
        }
    }

    /**
     * 为背景图设置状态栏透明度
     *
     * @param activity activity
     */
    public static void setStatusBar4BgInFragment(@NonNull final Activity activity,
                                                 @NonNull final View... needOffsetViews) {
        setStatusBar4BgInFragment(activity, DEFAULT_ALPHA, needOffsetViews);
    }

    /**
     * 为背景图设置状态栏透明度
     *
     * @param activity activity
     * @param alpha    状态栏透明度
     */
    public static void setStatusBar4BgInFragment(@NonNull final Activity activity,
                                                 @IntRange(from = 0, to = 255) final int alpha,
                                                 @NonNull final View... needOffsetViews) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        hideColorView(activity);
        addAlphaView(activity, alpha);
        for (View view : needOffsetViews) {
            addOffset(activity, view);
        }
    }

    /**
     * 为fragment头部是ImageView设置状态栏透明度
     *
     * @param activity        activity
     * @param needOffsetViews 需要向下偏移的views
     */
    public static void setStatusBar4ImageViewInFragment(@NonNull final Activity activity,
                                                        @NonNull final View... needOffsetViews) {
        setStatusBar4ImageViewInFragment(activity, DEFAULT_ALPHA, needOffsetViews);
    }

    /**
     * 为fragment头部是ImageView设置状态栏透明度
     *
     * @param activity        activity
     * @param alpha           状态栏透明度
     * @param needOffsetViews 需要向下偏移的views
     */
    public static void setStatusBar4ImageViewInFragment(@NonNull final Activity activity,
                                                        @IntRange(from = 0, to = 255) final int alpha,
                                                        @NonNull final View... needOffsetViews) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        hideColorView(activity);
        addAlphaView(activity, alpha);
        for (View view : needOffsetViews) {
            addOffset(activity, view);
        }
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColorForSwipeBack(@NonNull final Activity activity, final int color) {
        setColorForSwipeBack(activity, color, DEFAULT_ALPHA);
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度
     */
    public static void setColorForSwipeBack(@NonNull final Activity activity,
                                            @ColorInt final int color,
                                            @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
        View rootView = contentView.getChildAt(0);
        int statusBarHeight = getStatusBarHeight();
        if (rootView != null && rootView instanceof CoordinatorLayout) {
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                coordinatorLayout.setFitsSystemWindows(false);
                contentView.setBackgroundColor(getStatusBarColor(color, alpha));
                boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
                if (isNeedRequestLayout) {
                    contentView.setPadding(contentView.getPaddingLeft(),
                            statusBarHeight,
                            contentView.getPaddingRight(),
                            contentView.getPaddingBottom());
                    coordinatorLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            coordinatorLayout.requestLayout();
                        }
                    });
                }
            } else {
                coordinatorLayout.setStatusBarBackgroundColor(getStatusBarColor(color, alpha));
            }
        } else {
            contentView.setPadding(contentView.getPaddingLeft(),
                    contentView.getPaddingTop() + statusBarHeight,
                    contentView.getPaddingRight(),
                    contentView.getPaddingBottom());
            contentView.setBackgroundColor(getStatusBarColor(color, alpha));
        }
        transparentStatusBar(activity);
    }

    /**
     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     * @param alpha    状态栏透明度
     */
    public static void setTranslucentForCoordinatorLayout(@NonNull final Activity activity,
                                                          @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        addAlphaView(activity, alpha);
    }


    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorForDrawerLayout(@NonNull final Activity activity,
                                               @NonNull final DrawerLayout drawerLayout,
                                               @ColorInt final int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_ALPHA);
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorNoTranslucentForDrawerLayout(@NonNull final Activity activity,
                                                            @NonNull final DrawerLayout drawerLayout,
                                                            @ColorInt final int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0);
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     * @param alpha        状态栏透明度
     */
    public static void setColorForDrawerLayout(@NonNull final Activity activity,
                                               @NonNull final DrawerLayout drawerLayout,
                                               final @ColorInt int color,
                                               @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            contentLayout.addView(createColorStatusBarView(activity, color, 0), 0);
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight() + contentLayout.getPaddingTop(),
                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }
        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
        addAlphaView(activity, alpha);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(@NonNull final Activity activity,
                                                     @NonNull final DrawerLayout drawerLayout) {
        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_ALPHA);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(@NonNull final Activity activity,
                                                     @NonNull final DrawerLayout drawerLayout,
                                                     @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        setTransparentForDrawerLayout(activity, drawerLayout);
        addAlphaView(activity, alpha);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTransparentForDrawerLayout(@NonNull final Activity activity,
                                                     @NonNull final DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        // 内容布局不是 LinearLayout 时,设置padding top
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(), 0, 0);
        }

        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    public static void hideFakeStatusBarView(@NonNull final Activity activity) {
        hideColorView(activity);
        hideAlphaView(activity);
    }


    private static void addColorView(final Activity activity, final int color, final int alpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        } else {
            decorView.addView(createColorStatusBarView(activity, color, alpha));
        }
    }

    private static void addAlphaView(final Activity activity, final int alpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        } else {
            decorView.addView(createAlphaStatusBarView(activity, alpha));
        }
    }

    private static void hideColorView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setVisibility(View.GONE);
        }
    }

    private static void hideAlphaView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setVisibility(View.GONE);
        }
    }

    private static int getStatusBarColor(final int color, final int alpha) {
        if (alpha == 0) return color;
        float a = 1 - alpha / 255f;
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return Color.argb(255, red, green, blue);
    }

    /**
     * 绘制一个和状态栏一样高的颜色矩形
     */
    private static View createColorStatusBarView(final Activity activity, final int color, final int alpha) {
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        statusBarView.setTag(TAG_COLOR);
        return statusBarView;
    }

    private static View createAlphaStatusBarView(final Activity activity, final int alpha) {
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setTag(TAG_ALPHA);
        return statusBarView;
    }

    private static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 使内容布局在StatusBar下，内容不可滚动到StatusBar
     */
    private static void fitWindowAndClipToPadding(final Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    private static void addOffset(Activity activity, View view) {
        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView();
        if (view != null) {
            Object haveSetOffset = view.getTag(TAG_OFFSET);
            if (haveSetOffset != null && (Boolean) haveSetOffset) return;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            view.setTag(TAG_OFFSET, true);
        }
    }

    private static void setDrawerLayoutProperty(final DrawerLayout drawerLayout,
                                                final ViewGroup drawerLayoutContentLayout) {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /*--------------------------------old--------------------------------*/

    /**
     * 设置透明状态栏（api大于19方可使用）
     * <p>可在Activity的onCreat()中调用</p>
     * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下</p>
     * <p>android:clipToPadding="true"</p>
     * <p>android:fitsSystemWindows="true"</p>
     *
     * @param activity activity
     */
    public static void setTransparentStatusBar(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 隐藏状态栏
     * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错</p>
     * <p>此方法Activity可以继承AppCompatActivity</p>
     * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
     * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
     * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
     *
     * @param activity activity
     */
    public static void hideStatusBar(@NonNull final Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 判断状态栏是否存在
     *
     * @param activity activity
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isStatusBarExists(@NonNull final Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(@NonNull final Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 显示通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context        上下文
     * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
     */
    public static void showNotificationBar(@NonNull final Context context, final boolean isSettingPanel) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
        invokePanels(context, methodName);
    }

    /**
     * 隐藏通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context 上下文
     */
    public static void hideNotificationBar(@NonNull final Context context) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        invokePanels(context, methodName);
    }

    /**
     * 反射唤醒通知栏
     *
     * @param context    上下文
     * @param methodName 方法名
     */
    private static void invokePanels(@NonNull final Context context, final String methodName) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // navigation bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取导航栏高度
     *
     * @return 导航栏高度，0代表不存在
     */
    public int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(Utils.getContext()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            Resources res = Utils.getContext().getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }
}
