package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @package com.ihealthcare.lib_common.utils
 * @date 2018/11/27
 * @describe Activity自定义栈管理类
 */

public class AppManagerUtils {
    private static final String TAG = "AppManager";
    private Stack<WeakReference<Activity>> activityStack;
    private static AppManagerUtils instance = new AppManagerUtils();

    private AppManagerUtils() {
    }

    public static AppManagerUtils getInstance() {
        return instance;
    }

    public synchronized void addActivity(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(weakReference);
    }

    public synchronized void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        WeakReference<Activity> temp = null;
        for (WeakReference<Activity> weakReference : activityStack) {
            Activity cacheActivity = weakReference.get();
            if (cacheActivity == activity) {
                temp = weakReference;
                break;
            }
        }

        if (temp != null) {
            activityStack.remove(temp);
        }
    }

    /**
     * 将指定Activity上面的Activity全部finish
     *
     * @param clazz
     */
    public synchronized void clearAllTopActivity(Class clazz) {
        if (activityStack == null) {
            return;
        }
        int index = -1;
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i).get();
            if (activity != null && activity.getClass().equals(clazz)) {
                index = i;
                break;
            }
        }
        if (index < 0) return;
        int size = activityStack.size();
        List<WeakReference<Activity>> temp = new ArrayList<>(size);
        for (int i = index + 1; i < size; i++) {
            WeakReference<Activity> weakReference = activityStack.get(i);
            Activity activity = weakReference.get();
            if (activity != null) {
                activity.finish();
            }
            temp.add(weakReference);
        }
        activityStack.removeAll(temp);
    }

    public Activity currentActivity() {
        return activityStack.lastElement().get();
    }

    public void finishActivity() {
        WeakReference<Activity> weakReference = activityStack.lastElement();
        Activity activity = activityStack.lastElement().get();
        if (activity != null) {
            activity.finish();
            activityStack.remove(weakReference);
        }
    }


    public void finishActivity(Class<?> cls) {
        for (WeakReference<Activity> weakReference : activityStack) {
            Activity activity = weakReference.get();
            if (activity != null && Activity.class.equals(cls)) {
                activity.finish();
                activityStack.remove(weakReference);
            }
        }
    }

    public synchronized void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (i < activityStack.size() && null != activityStack.get(i)) {
                Activity activity = activityStack.get(i).get();
                if (activity != null) {
                    activity.finish();
                }
            }
        }
        activityStack.clear();
    }

    public void exitApp(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
