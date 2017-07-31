package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
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
     * 为view增加MarginTop为状态栏高度
     *
     * @param view view
     */
    public static void addPaddingTopEqualStatusBarHeight(@NonNull View view) {
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) return;
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop() + getStatusBarHeight(),
                view.getPaddingRight(),
                view.getPaddingBottom());
        view.setTag(TAG_OFFSET, true);
    }

    /**
     * 为view增加MarginTop为状态栏高度
     *
     * @param view view
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin + getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(TAG_OFFSET, true);
    }

    /**
     * 为view减少MarginTop为状态栏高度
     *
     * @param view view
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin - getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(TAG_OFFSET, false);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity activity
     * @param color    状态栏颜色值
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, false);
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
        setStatusBarColor(activity, color, alpha, false);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
     * @param isDecor  {@code true}: 设置在DecorView中<br>
     *                 {@code false}: 设置在ContentView中
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideAlphaView(activity);
        transparentStatusBar(activity);
        addStatusBarColor(activity, color, alpha, isDecor);
//        addMarginTopEqualStatusBarHeight(activity);
    }

    /**
     * 设置状态栏透明度
     *
     * @param activity activity
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity) {
        setStatusBarAlpha(activity, DEFAULT_ALPHA, false);
    }

    /**
     * 设置状态栏透明度
     *
     * @param activity activity
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarAlpha(activity, alpha, false);
    }

    /**
     * 设置状态栏透明度
     *
     * @param activity activity
     * @param alpha    状态栏透明度
     * @param isDecor  {@code true}: 设置在DecorView中<br>
     *                 {@code false}: 设置在ContentView中
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideColorView(activity);
        transparentStatusBar(activity);
        addStatusBarAlpha(activity, alpha, isDecor);
    }

    /**
     * 设置伪造状态栏颜色
     *
     * @param fakeStatusBar 伪造状态栏
     * @param color         状态栏颜色值
     */
    public static void setFakeStatusBarColor(@NonNull final View fakeStatusBar, @ColorInt final int color) {
        setFakeStatusBarColor(fakeStatusBar, color, DEFAULT_ALPHA);
    }

    /**
     * 设置伪造状态栏颜色
     *
     * @param fakeStatusBar 伪造状态栏
     * @param color         状态栏颜色值
     * @param alpha         状态栏透明度，此透明度并非颜色中的透明度
     */
    public static void setFakeStatusBarColor(@NonNull final View fakeStatusBar,
                                             @ColorInt final int color,
                                             @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha));
    }

    /**
     * 设置伪造状态栏透明度
     *
     * @param fakeStatusBar 伪造状态栏
     */
    public static void setFakeStatusBarAlpha(@NonNull final View fakeStatusBar) {
        setFakeStatusBarAlpha(fakeStatusBar, DEFAULT_ALPHA);
    }

    /**
     * 设置伪造状态栏透明度
     *
     * @param fakeStatusBar 伪造状态栏
     * @param alpha         状态栏透明度
     */
    public static void setFakeStatusBarAlpha(@NonNull final View fakeStatusBar,
                                             @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }

    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                @IntRange(from = 0, to = 255) final int alpha,
                                                final boolean isShowShaderInDrawer) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        setFakeStatusBarColor(fakeStatusBar, color, alpha);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isShowShaderInDrawer) addStatusBarAlpha(activity, alpha, false);
    }

    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                final boolean isShowShaderInDrawer) {
        setStatusBarAlpha4Drawer(activity, drawer, fakeStatusBar, DEFAULT_ALPHA, isShowShaderInDrawer);
    }

    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @IntRange(from = 0, to = 255) final int alpha,
                                                final boolean isShowShaderInDrawer) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        setFakeStatusBarAlpha(fakeStatusBar, alpha);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isShowShaderInDrawer) addStatusBarAlpha(activity, alpha, false);
    }


    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    public static void hideStatusBarView(@NonNull final Activity activity) {
        hideColorView(activity);
        hideAlphaView(activity);
    }

    private static void addStatusBarColor(final Activity activity, final int color, final int alpha, boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        addStatusBarColor(parent, color, alpha);
    }

    private static void addStatusBarColor(final ViewGroup parent, final int color, final int alpha) {
        View fakeStatusBarView = parent.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        } else {
            parent.addView(createColorStatusBarView(parent.getContext(), color, alpha));
        }
    }

    private static void addStatusBarAlpha(final Activity activity, final int alpha, boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        addStatusBarAlpha(parent, alpha);
    }

    private static void addStatusBarAlpha(final ViewGroup parent, final int alpha) {
        View fakeStatusBarView = parent.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        } else {
            parent.addView(createAlphaStatusBarView(parent.getContext(), alpha));
        }
    }

    private static void hideColorView(final Activity activity) {
//        subtractMarginTopEqualStatusBarHeight(activity);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static void hideAlphaView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
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
    private static View createColorStatusBarView(final Context context, final int color, final int alpha) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        statusBarView.setTag(TAG_COLOR);
        return statusBarView;
    }

    /**
     * 绘制一个和状态栏一样高的黑色透明度矩形
     */
    private static View createAlphaStatusBarView(final Context context, final int alpha) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setTag(TAG_ALPHA);
        return statusBarView;
    }

    public static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private static void addMarginTopEqualStatusBarHeight(final Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                addMarginTopEqualStatusBarHeight(childView);
            }
        }
    }

    private static void subtractMarginTopEqualStatusBarHeight(final Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                subtractMarginTopEqualStatusBarHeight(childView);
            }
        }
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
                                               @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        transparentStatusBar(activity);
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        addStatusBarColor((ViewGroup) drawerLayout.getChildAt(0), color, statusBarAlpha);
//        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//        if (fakeStatusBarView != null) {
//            if (fakeStatusBarView.getVisibility() == View.GONE) {
//                fakeStatusBarView.setVisibility(View.VISIBLE);
//            }
//            fakeStatusBarView.setBackgroundColor(color);
//        } else {
//            contentLayout.addView(createStatusBarView(activity, color), 0);
//        }
        // 内容布局不是 LinearLayout 时,设置padding top
//        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//            contentLayout.getChildAt(1)
//                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight() + contentLayout.getPaddingTop(),
//                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
//        }
//        // 设置属性
        drawerLayout.setFitsSystemWindows(false);

//        setDrawerLayoutProperty(drawerLayout, contentLayout);
//        addTranslucentView(activity, statusBarAlpha);
        addStatusBarAlpha(activity, statusBarAlpha, false);
    }

    /**
     * 生成一个和状态栏大小相同的彩色矩形条
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color) {
        return createStatusBarView(activity, color, 0);
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }


    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.getVisibility() == View.GONE) {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
        }
    }

    @IdRes
    private static int FAKE_TRANSLUCENT_VIEW_ID = 110;
    @IdRes
    private static int FAKE_STATUS_BAR_VIEW_ID  = 111;

    private static View createTranslucentStatusBarView(Activity activity, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarView;
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
