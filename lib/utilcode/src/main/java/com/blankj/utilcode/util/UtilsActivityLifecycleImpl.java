package com.blankj.utilcode.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
final class UtilsActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

    static final UtilsActivityLifecycleImpl INSTANCE = new UtilsActivityLifecycleImpl();

    final LinkedList<Activity> mActivityList = new LinkedList<>();

    private final List<Utils.OnAppStatusChangedListener>                 mStatusListeners               = new ArrayList<>();
    private final Map<Activity, List<Utils.OnActivityDestroyedListener>> mDestroyedListenerMap          = new HashMap<>();
    private final Map<Activity, List<Utils.ActivityLifecycleCallbacks>>  mActivityLifecycleCallbacksMap = new HashMap<>();

    private int     mForegroundCount = 0;
    private int     mConfigCount     = 0;
    private boolean mIsBackground    = false;

    void init() {
        mActivityList.clear();
        Utils.getApp().registerActivityLifecycleCallbacks(this);
    }

    Activity getTopActivity() {
        if (!mActivityList.isEmpty()) {
            for (int i = mActivityList.size() - 1; i >= 0; i--) {
                Activity activity = mActivityList.get(i);
                if (!UtilsBridge.isActivityAlive(activity)) {
                    continue;
                }
                return activity;
            }
        }
        Activity topActivityByReflect = getTopActivityByReflect();
        if (topActivityByReflect != null) {
            setTopActivity(topActivityByReflect);
        }
        return topActivityByReflect;
    }

    void addOnAppStatusChangedListener(final Utils.OnAppStatusChangedListener listener) {
        mStatusListeners.add(listener);
    }

    void removeOnAppStatusChangedListener(final Utils.OnAppStatusChangedListener listener) {
        mStatusListeners.remove(listener);
    }

    void addActivityLifecycleCallbacks(final Activity activity,
                                       final Utils.ActivityLifecycleCallbacks listener) {
        if (activity == null || listener == null) return;
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addActivityLifecycleCallbacksInner(activity, listener);
            }
        });
    }

    private void addActivityLifecycleCallbacksInner(final Activity activity,
                                                    final Utils.ActivityLifecycleCallbacks lifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> callbacks = mActivityLifecycleCallbacksMap.get(activity);
        if (callbacks == null) {
            callbacks = new ArrayList<>();
            mActivityLifecycleCallbacksMap.put(activity, callbacks);
        } else {
            if (callbacks.contains(lifecycleCallbacks)) return;
        }
        callbacks.add(lifecycleCallbacks);
    }

    void removeActivityLifecycleCallbacks(final Activity activity) {
        if (activity == null) return;
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityLifecycleCallbacksMap.remove(activity);
            }
        });
    }

    void removeActivityLifecycleCallbacks(final Activity activity,
                                          final Utils.ActivityLifecycleCallbacks callbacks) {
        if (activity == null || callbacks == null) return;
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeActivityLifecycleCallbacksInner(activity, callbacks);
            }
        });
    }

    private void removeActivityLifecycleCallbacksInner(final Activity activity,
                                                       final Utils.ActivityLifecycleCallbacks lifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> callbacks = mActivityLifecycleCallbacksMap.get(activity);
        if (callbacks != null && !callbacks.isEmpty()) {
            callbacks.remove(lifecycleCallbacks);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // lifecycle start
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        UtilsBridge.applyLanguage(activity);
        setAnimatorsEnabled();
        setTopActivity(activity);
    }

    @Override
    public void onActivityCreated(@NotNull Activity activity, Bundle savedInstanceState) {
        setTopActivity(activity);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onActivityStarted(@NotNull Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_START);
    }

    @Override
    public void onActivityResumed(@NotNull final Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(activity, true);
        }
        processHideSoftInputOnActivityDestroy(activity, false);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onActivityPaused(@NotNull Activity activity) {
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
                postStatus(activity, false);
            }
        }
        processHideSoftInputOnActivityDestroy(activity, true);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onActivitySaveInstanceState(@NotNull Activity activity, Bundle outState) {/**/}

    @Override
    public void onActivityDestroyed(@NotNull Activity activity) {
        mActivityList.remove(activity);
        consumeOnActivityDestroyedListener(activity);
        UtilsBridge.fixSoftInputLeaks(activity);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_DESTROY);
    }
    ///////////////////////////////////////////////////////////////////////////
    // lifecycle end
    ///////////////////////////////////////////////////////////////////////////

    /**
     * To solve close keyboard when activity onDestroy.
     * The preActivity set windowSoftInputMode will prevent
     * the keyboard from closing when curActivity onDestroy.
     */
    private void processHideSoftInputOnActivityDestroy(final Activity activity, boolean isSave) {
        if (isSave) {
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            final int softInputMode = attrs.softInputMode;
            activity.getWindow().getDecorView().setTag(-123, softInputMode);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } else {
            final Object tag = activity.getWindow().getDecorView().getTag(-123);
            if (!(tag instanceof Integer)) return;
            UtilsBridge.runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    Window window = activity.getWindow();
                    if (window != null) {
                        window.setSoftInputMode(((Integer) tag));
                    }
                }
            }, 100);
        }
    }

    private void postStatus(final Activity activity, final boolean isForeground) {
        if (mStatusListeners.isEmpty()) return;
        for (Utils.OnAppStatusChangedListener statusListener : mStatusListeners) {
            if (isForeground) {
                statusListener.onForeground(activity);
            } else {
                statusListener.onBackground(activity);
            }
        }
    }

    private void setTopActivity(final Activity activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getLast().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addLast(activity);
            }
        } else {
            mActivityList.addLast(activity);
        }
    }

    private void consumeOnActivityDestroyedListener(Activity activity) {
        Iterator<Map.Entry<Activity, List<Utils.OnActivityDestroyedListener>>> iterator
                = mDestroyedListenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Activity, List<Utils.OnActivityDestroyedListener>> entry = iterator.next();
            if (entry.getKey() == activity) {
                List<Utils.OnActivityDestroyedListener> value = entry.getValue();
                for (Utils.OnActivityDestroyedListener listener : value) {
                    listener.onActivityDestroyed(activity);
                }
                iterator.remove();
            }
        }
    }

    private void consumeActivityLifecycleCallbacks(Activity activity, Lifecycle.Event event) {
        Iterator<Map.Entry<Activity, List<Utils.ActivityLifecycleCallbacks>>> iterator
                = mActivityLifecycleCallbacksMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Activity, List<Utils.ActivityLifecycleCallbacks>> entry = iterator.next();
            if (entry.getKey() == activity) {
                List<Utils.ActivityLifecycleCallbacks> value = entry.getValue();
                for (Utils.ActivityLifecycleCallbacks listener : value) {
                    listener.onLifecycleChanged(activity, event);
                    if (event.equals(Lifecycle.Event.ON_CREATE)) {
                        listener.onActivityCreated(activity);
                    } else if (event.equals(Lifecycle.Event.ON_START)) {
                        listener.onActivityStarted(activity);
                    } else if (event.equals(Lifecycle.Event.ON_RESUME)) {
                        listener.onActivityResumed(activity);
                    } else if (event.equals(Lifecycle.Event.ON_PAUSE)) {
                        listener.onActivityPaused(activity);
                    } else if (event.equals(Lifecycle.Event.ON_STOP)) {
                        listener.onActivityStopped(activity);
                    } else if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                        listener.onActivityDestroyed(activity);
                        iterator.remove();
                    }
                }
            }
        }
    }

    private Activity getTopActivityByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
            mActivityListField.setAccessible(true);
            Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
            if (activities == null) return null;
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", e.getMessage());
        }
        return null;
    }

    /**
     * Set animators enabled.
     */
    private static void setAnimatorsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && ValueAnimator.areAnimatorsEnabled()) {
            return;
        }
        try {
            //noinspection JavaReflectionMemberAccess
            Field sDurationScaleField = ValueAnimator.class.getDeclaredField("sDurationScale");
            sDurationScaleField.setAccessible(true);
            //noinspection ConstantConditions
            float sDurationScale = (Float) sDurationScaleField.get(null);
            if (sDurationScale == 0f) {
                sDurationScaleField.set(null, 1f);
                Log.i("UtilsActivityLifecycle", "setAnimatorsEnabled: Animators are enabled now!");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
