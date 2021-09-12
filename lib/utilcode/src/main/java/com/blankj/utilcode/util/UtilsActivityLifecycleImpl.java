package com.blankj.utilcode.util;

import android.animation.ValueAnimator;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private final LinkedList<Activity> mActivityList = new LinkedList<>();

    private final List<Utils.OnAppStatusChangedListener>                mStatusListeners               = new CopyOnWriteArrayList<>();
    private final Map<Activity, List<Utils.ActivityLifecycleCallbacks>> mActivityLifecycleCallbacksMap = new ConcurrentHashMap<>();

    private static final Activity STUB = new Activity();

    private int     mForegroundCount = 0;
    private int     mConfigCount     = 0;
    private boolean mIsBackground    = false;

    void init(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    void unInit(Application app) {
        mActivityList.clear();
        app.unregisterActivityLifecycleCallbacks(this);
    }

    Activity getTopActivity() {
        List<Activity> activityList = getActivityList();
        for (Activity activity : activityList) {
            if (!UtilsBridge.isActivityAlive(activity)) {
                continue;
            }
            return activity;
        }
        return null;
    }

    List<Activity> getActivityList() {
        if (!mActivityList.isEmpty()) {
            return new LinkedList<>(mActivityList);
        }
        List<Activity> reflectActivities = getActivitiesByReflect();
        mActivityList.addAll(reflectActivities);
        return new LinkedList<>(mActivityList);
    }

    void addOnAppStatusChangedListener(final Utils.OnAppStatusChangedListener listener) {
        mStatusListeners.add(listener);
    }

    void removeOnAppStatusChangedListener(final Utils.OnAppStatusChangedListener listener) {
        mStatusListeners.remove(listener);
    }

    void addActivityLifecycleCallbacks(final Utils.ActivityLifecycleCallbacks listener) {
        addActivityLifecycleCallbacks(STUB, listener);
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

    boolean isAppForeground() {
        return !mIsBackground;
    }

    private void addActivityLifecycleCallbacksInner(final Activity activity,
                                                    final Utils.ActivityLifecycleCallbacks callbacks) {
        List<Utils.ActivityLifecycleCallbacks> callbacksList = mActivityLifecycleCallbacksMap.get(activity);
        if (callbacksList == null) {
            callbacksList = new CopyOnWriteArrayList<>();
            mActivityLifecycleCallbacksMap.put(activity, callbacksList);
        } else {
            if (callbacksList.contains(callbacks)) return;
        }
        callbacksList.add(callbacks);
    }

    void removeActivityLifecycleCallbacks(final Utils.ActivityLifecycleCallbacks callbacks) {
        removeActivityLifecycleCallbacks(STUB, callbacks);
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
                                                       final Utils.ActivityLifecycleCallbacks callbacks) {
        List<Utils.ActivityLifecycleCallbacks> callbacksList = mActivityLifecycleCallbacksMap.get(activity);
        if (callbacksList != null && !callbacksList.isEmpty()) {
            callbacksList.remove(callbacks);
        }
    }

    private void consumeActivityLifecycleCallbacks(Activity activity, Lifecycle.Event event) {
        consumeLifecycle(activity, event, mActivityLifecycleCallbacksMap.get(activity));
        consumeLifecycle(activity, event, mActivityLifecycleCallbacksMap.get(STUB));
    }

    private void consumeLifecycle(Activity activity, Lifecycle.Event event, List<Utils.ActivityLifecycleCallbacks> listeners) {
        if (listeners == null) return;
        for (Utils.ActivityLifecycleCallbacks listener : listeners) {
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
            }
        }
        if (event.equals(Lifecycle.Event.ON_DESTROY)) {
            mActivityLifecycleCallbacksMap.remove(activity);
        }
    }

    Application getApplicationByReflect() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object thread = getActivityThread();
            Object app = activityThreadClass.getMethod("getApplication").invoke(thread);
            if (app == null) {
                return null;
            }
            return (Application) app;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // lifecycle start
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {/**/}

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        if (mActivityList.size() == 0) {
            postStatus(activity, true);
        }
        LanguageUtils.applyLanguage(activity);
        setAnimatorsEnabled();
        setTopActivity(activity);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {/**/}

    @Override
    public void onActivityPreStarted(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
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
    public void onActivityPostStarted(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPreResumed(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityResumed(@NonNull final Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(activity, true);
        }
        processHideSoftInputOnActivityDestroy(activity, false);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onActivityPostResumed(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPrePaused(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onActivityPostPaused(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPreStopped(@NonNull Activity activity) {/**/}

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
    public void onActivityPostStopped(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityPreSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivityPostSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivityPreDestroyed(@NonNull Activity activity) {/**/}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityList.remove(activity);
        UtilsBridge.fixSoftInputLeaks(activity);
        consumeActivityLifecycleCallbacks(activity, Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public void onActivityPostDestroyed(@NonNull Activity activity) {/**/}
    ///////////////////////////////////////////////////////////////////////////
    // lifecycle end
    ///////////////////////////////////////////////////////////////////////////

    /**
     * To solve close keyboard when activity onDestroy.
     * The preActivity set windowSoftInputMode will prevent
     * the keyboard from closing when curActivity onDestroy.
     */
    private void processHideSoftInputOnActivityDestroy(final Activity activity, boolean isSave) {
        try {
            if (isSave) {
                Window window = activity.getWindow();
                final WindowManager.LayoutParams attrs = window.getAttributes();
                final int softInputMode = attrs.softInputMode;
                window.getDecorView().setTag(-123, softInputMode);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            } else {
                final Object tag = activity.getWindow().getDecorView().getTag(-123);
                if (!(tag instanceof Integer)) return;
                UtilsBridge.runOnUiThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Window window = activity.getWindow();
                            if (window != null) {
                                window.setSoftInputMode(((Integer) tag));
                            }
                        } catch (Exception ignore) {
                        }
                    }
                }, 100);
            }
        } catch (Exception ignore) {
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
            if (!mActivityList.getFirst().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addFirst(activity);
            }
        } else {
            mActivityList.addFirst(activity);
        }
    }

    /**
     * @return the activities which topActivity is first position
     */
    private List<Activity> getActivitiesByReflect() {
        LinkedList<Activity> list = new LinkedList<>();
        Activity topActivity = null;
        try {
            Object activityThread = getActivityThread();
            Field mActivitiesField = activityThread.getClass().getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(activityThread);
            if (!(mActivities instanceof Map)) {
                return list;
            }
            Map<Object, Object> binder_activityClientRecord_map = (Map<Object, Object>) mActivities;
            for (Object activityRecord : binder_activityClientRecord_map.values()) {
                Class activityClientRecordClass = activityRecord.getClass();
                Field activityField = activityClientRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                if (topActivity == null) {
                    Field pausedField = activityClientRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        topActivity = activity;
                    } else {
                        list.add(activity);
                    }
                } else {
                    list.add(activity);
                }
            }
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e.getMessage());
        }
        if (topActivity != null) {
            list.addFirst(topActivity);
        }
        return list;
    }

    private Object getActivityThread() {
        Object activityThread = getActivityThreadInActivityThreadStaticField();
        if (activityThread != null) return activityThread;
        return getActivityThreadInActivityThreadStaticMethod();
    }

    private Object getActivityThreadInActivityThreadStaticField() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            return sCurrentActivityThreadField.get(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e.getMessage());
            return null;
        }
    }

    private Object getActivityThreadInActivityThreadStaticMethod() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            return activityThreadClass.getMethod("currentActivityThread").invoke(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.getMessage());
            return null;
        }
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
