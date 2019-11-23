package com.blankj.utilcode.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : utils about initialization
 * </pre>
 */
public final class Utils {

    private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();
    private static final ExecutorService       UTIL_POOL          = ThreadUtils.getCachedPool();
    private static final Handler               UTIL_HANDLER       = new Handler(Looper.getMainLooper());

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;


    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(final Context context) {
        if (context == null) {
            init(getApplicationByReflect());
            return;
        }
        init((Application) context.getApplicationContext());
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            UTIL_POOL.execute(new Runnable() {
                @Override
                public void run() {
                    AdaptScreenUtils.preLoad();
                }
            });
        } else {
            if (app != null && app.getClass() != sApplication.getClass()) {
                sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
                ACTIVITY_LIFECYCLE.mActivityList.clear();
                sApplication = app;
                sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            }
        }
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }

    static ActivityLifecycleImpl getActivityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.mActivityList;
    }

    static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
            return topActivity == null ? Utils.getApp() : topActivity;
        } else {
            return Utils.getApp();
        }
    }

    static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (aInfo.processName.equals(Utils.getApp().getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    static <T> Task<T> doAsync(final Task<T> task) {
        UTIL_POOL.execute(task);
        return task;
    }

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            Utils.UTIL_HANDLER.post(runnable);
        }
    }

    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        Utils.UTIL_HANDLER.postDelayed(runnable, delayMillis);
    }

    static String getCurrentProcessName() {
        String name = getCurrentProcessNameByFile();
        if (!TextUtils.isEmpty(name)) return name;
        name = getCurrentProcessNameByAms();
        if (!TextUtils.isEmpty(name)) return name;
        name = getCurrentProcessNameByReflect();
        return name;
    }

    static void fixSoftInputLeaks(final Window window) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == window.getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) {/**/}
        }
    }

    static SPUtils getSpUtils4Utils() {
        return SPUtils.getInstance("Utils");
    }

    ///////////////////////////////////////////////////////////////////////////
    // private method
    ///////////////////////////////////////////////////////////////////////////

    private static String getCurrentProcessNameByFile() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getCurrentProcessNameByAms() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return "";
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return "";
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.pid == pid) {
                if (aInfo.processName != null) {
                    return aInfo.processName;
                }
            }
        }
        return "";
    }

    private static String getCurrentProcessNameByReflect() {
        String processName = "";
        try {
            Application app = Utils.getApp();
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);

            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);

            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName");
            processName = (String) getProcessName.invoke(activityThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processName;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
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
            float sDurationScale = (Float) sDurationScaleField.get(null);
            if (sDurationScale == 0f) {
                sDurationScaleField.set(null, 1f);
                Log.i("Utils", "setAnimatorsEnabled: Animators are enabled now!");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // TransActivity
    ///////////////////////////////////////////////////////////////////////////

    public static final class TransActivity extends FragmentActivity {

        private static final Map<TransActivity, TransActivityDelegate> CALLBACK_MAP = new HashMap<>();
        private static       TransActivityDelegate                     sDelegate;

        public static void start(final Func1<Void, Intent> consumer,
                                 final TransActivityDelegate delegate) {
            if (delegate == null) return;
            Intent starter = new Intent(Utils.getApp(), TransActivity.class);
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (consumer != null) {
                consumer.call(starter);
            }
            Utils.getApp().startActivity(starter);
            sDelegate = delegate;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            overridePendingTransition(0, 0);
            if (sDelegate == null) {
                super.onCreate(savedInstanceState);
                finish();
                return;
            }
            CALLBACK_MAP.put(this, sDelegate);
            sDelegate.onCreateBefore(this, savedInstanceState);
            super.onCreate(savedInstanceState);
            sDelegate.onCreated(this, savedInstanceState);
            sDelegate = null;
        }

        @Override
        protected void onStart() {
            super.onStart();
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onStarted(this);
        }

        @Override
        protected void onResume() {
            super.onResume();
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onResumed(this);
        }

        @Override
        protected void onPause() {
            overridePendingTransition(0, 0);
            super.onPause();
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onPaused(this);
        }

        @Override
        protected void onStop() {
            super.onStop();
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onStopped(this);
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onSaveInstanceState(this, outState);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onDestroy(this);
            CALLBACK_MAP.remove(this);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return;
            callback.onActivityResult(this, requestCode, resultCode, data);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            TransActivityDelegate callback = CALLBACK_MAP.get(this);
            if (callback == null) return super.dispatchTouchEvent(ev);
            if (callback.dispatchTouchEvent(this, ev)) {
                return true;
            }
            return super.dispatchTouchEvent(ev);
        }

        public abstract static class TransActivityDelegate {
            public void onCreateBefore(Activity activity, @Nullable Bundle savedInstanceState) {/**/}

            public void onCreated(Activity activity, @Nullable Bundle savedInstanceState) {/**/}

            public void onStarted(Activity activity) {/**/}

            public void onDestroy(Activity activity) {/**/}

            public void onResumed(Activity activity) {/**/}

            public void onPaused(Activity activity) {/**/}

            public void onStopped(Activity activity) {/**/}

            public void onSaveInstanceState(Activity activity, Bundle outState) {/**/}

            public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {/**/}

            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {/**/}

            public boolean dispatchTouchEvent(Activity activity, MotionEvent ev) {
                return false;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // lifecycle
    ///////////////////////////////////////////////////////////////////////////

    static class ActivityLifecycleImpl implements ActivityLifecycleCallbacks {

        final LinkedList<Activity>                             mActivityList         = new LinkedList<>();
        final List<OnAppStatusChangedListener>                 mStatusListeners      = new ArrayList<>();
        final Map<Activity, List<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();

        private int     mForegroundCount = 0;
        private int     mConfigCount     = 0;
        private boolean mIsBackground    = false;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LanguageUtils.applyLanguage(activity);
            setAnimatorsEnabled();
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (!mIsBackground) {
                setTopActivity(activity);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }
        }

        @Override
        public void onActivityResumed(final Activity activity) {
            setTopActivity(activity);
            if (mIsBackground) {
                mIsBackground = false;
                postStatus(activity, true);
            }
            processHideSoftInputOnActivityDestroy(activity, false);
        }

        @Override
        public void onActivityPaused(Activity activity) {

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
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityList.remove(activity);
            consumeOnActivityDestroyedListener(activity);
            fixSoftInputLeaks(activity.getWindow());
        }

        Activity getTopActivity() {
            if (!mActivityList.isEmpty()) {
                for (int i = mActivityList.size() - 1; i >= 0; i--) {
                    Activity activity = mActivityList.get(i);
                    if (activity == null
                            || activity.isFinishing()
                            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
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

        void addOnAppStatusChangedListener(final OnAppStatusChangedListener listener) {
            mStatusListeners.add(listener);
        }

        void removeOnAppStatusChangedListener(final OnAppStatusChangedListener listener) {
            mStatusListeners.remove(listener);
        }

        void removeOnActivityDestroyedListener(final Activity activity) {
            if (activity == null) return;
            mDestroyedListenerMap.remove(activity);
        }

        void addOnActivityDestroyedListener(final Activity activity,
                                            final OnActivityDestroyedListener listener) {
            if (activity == null || listener == null) return;
            List<OnActivityDestroyedListener> listeners = mDestroyedListenerMap.get(activity);
            if (listeners == null) {
                listeners = new CopyOnWriteArrayList<>();
                mDestroyedListenerMap.put(activity, listeners);
            } else {
                if (listeners.contains(listener)) return;
            }
            listeners.add(listener);
        }

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
                Utils.runOnUiThreadDelayed(new Runnable() {
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
            for (OnAppStatusChangedListener statusListener : mStatusListeners) {
                if (isForeground) {
                    statusListener.onForeground(activity);
                } else {
                    statusListener.onBackground(activity);
                }
            }
        }

        private void setTopActivity(final Activity activity) {
//            if (TransActivity.class == activity.getClass()) return;
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
            Iterator<Map.Entry<Activity, List<OnActivityDestroyedListener>>> iterator
                    = mDestroyedListenerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Activity, List<OnActivityDestroyedListener>> entry = iterator.next();
                if (entry.getKey() == activity) {
                    List<OnActivityDestroyedListener> value = entry.getValue();
                    for (OnActivityDestroyedListener listener : value) {
                        listener.onActivityDestroyed(activity);
                    }
                    iterator.remove();
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
                Log.e("Utils", e.getMessage());
            }
            return null;
        }
    }

    public static final class FileProvider4UtilCode extends FileProvider {

        @Override
        public boolean onCreate() {
            Utils.init(getContext());
            try {
                Class.forName("com.blankj.utildebug.DebugUtils");
            } catch (ClassNotFoundException ignore) {
            }
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public abstract static class Task<Result> implements Runnable {

        private static final int NEW         = 0;
        private static final int COMPLETING  = 1;
        private static final int CANCELLED   = 2;
        private static final int EXCEPTIONAL = 3;

        private volatile int state = NEW;

        abstract Result doInBackground();

        private Callback<Result> mCallback;

        public Task(final Callback<Result> callback) {
            mCallback = callback;
        }

        @Override
        public void run() {
            try {
                final Result t = doInBackground();

                if (state != NEW) return;
                state = COMPLETING;
                UTIL_HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onCall(t);
                    }
                });
            } catch (Throwable th) {
                if (state != NEW) return;
                state = EXCEPTIONAL;
            }
        }

        public void cancel() {
            state = CANCELLED;
        }

        public boolean isDone() {
            return state != NEW;
        }

        public boolean isCanceled() {
            return state == CANCELLED;
        }
    }

    public interface Callback<T> {
        void onCall(T data);
    }

    public interface OnAppStatusChangedListener {
        void onForeground(Activity activity);

        void onBackground(Activity activity);
    }

    public interface OnActivityDestroyedListener {
        void onActivityDestroyed(Activity activity);
    }

    public interface Func1<Ret, Par> {
        Ret call(Par param);
    }
}
