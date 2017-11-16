package com.blankj.utilcode.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 软键盘监视器。用于对软键盘的开启、关闭状态进行监听的工具类
 *
 * <p>使用需求：对activity添加 <b>android:windowSoftInputMode="stateAlwaysHidden|adjustResize"</b>
 * 使Activity可以在弹出软键盘时布局被压缩。以触发软键盘通知
 *
 * <p>使用方式：
 *
 * <ol>
 *     <li>在Activity调用setContentView之后。创建Monitor实例并调用{@link #attach(Activity)}方法绑定</li>
 *     <li>在任意处调用{@link #setListener(MonitorListener)}设置状态监听，监听软键盘弹出/隐藏操作</li>
 *     <li>通过{@link #isOpen()}判断当前软键盘状态</li>
 * </ol>
 *
 * @author haoge
 */
public final class KeyboardMonitor{

    private boolean open;
    private MonitorListener listener;

    public boolean isOpen() {
        return open;
    }

    public void setListener(MonitorListener listener) {
        this.listener = listener;
    }

    public void attach(final Activity activity) {
        final int minOffset = (int) (activity.getResources().getDisplayMetrics().density * 200);
        final FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        final FrameLayout monitor = new FrameLayout(activity);
        monitor.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        monitor.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom == 0
                        || Math.abs(bottom - oldBottom) < minOffset) {
                    return;
                }

                open = bottom <= oldBottom;

                if (listener != null) {
                    listener.onStatusChange(open);
                }
            }
        });
        content.addView(monitor);
    }

    public interface MonitorListener {
        void onStatusChange(boolean open);
    }
}
