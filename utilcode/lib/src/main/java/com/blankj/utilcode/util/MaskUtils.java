package com.blankj.utilcode.util;

import android.app.Activity;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.WeakHashMap;

/**
 * 可用于动态为Activity添加遮罩层，悬浮窗等
 */
public class MaskUtils {
    private static WeakHashMap<Window, View> viewMap = new WeakHashMap<>();

    public synchronized static void show(final Activity activity, @LayoutRes final int layoutId) {
        if (idMainThread()) {
            addView(activity, layoutId);
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addView(activity, layoutId);
                }
            });
        }
    }


    public synchronized static void show(final Activity activity, final View view) {
        if (idMainThread()) {
            addView(activity, view);
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addView(activity, view);
                }
            });
        }
    }

    public synchronized static void show(final Window window, @LayoutRes final int viewId) {
        if (idMainThread()) {
            addView(window, viewId);
        } else {
            window.getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    addView(window, viewId);
                }
            });
        }
    }

    private static void addView(Activity activity, @LayoutRes int viewIdp) {
        addView(activity.getWindow(), viewIdp);
    }

    private static void addView(Activity activity, View view) {
        addView(activity.getWindow(), view);
    }

    private static void addView(Window window, @LayoutRes int viewId) {
        View mask = window.getLayoutInflater().inflate(viewId, null);
        addView(window, mask);
    }

    private static void addView(Window window, View mask) {
        viewMap.put(window, mask);
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        decorView.addView(mask);
    }


    public synchronized static void hide(final Activity activity) {
        hide(activity.getWindow());
    }

    public synchronized static void hide(final Window window) {
        if (idMainThread()) {
            removeView(window);
        } else {
            window.getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    removeView(window);
                }
            });
        }
    }

    private static void removeView(Window window) {
        View view = viewMap.get(window);
        if (view != null) {
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            try {
                decorView.removeView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean idMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
