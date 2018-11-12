package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.content.FileProvider
import java.lang.reflect.InvocationTargetException
import java.util.*

object Utils {

    @SuppressLint("StaticFieldLeak")
    @JvmStatic
    private var sApplication: Application? = null

    @JvmStatic
    private val ACTIVITY_LIFECYCLE = ActivityLifecycleImpl()

    private const val PERMISSION_ACTIVITY_CLASS_NAME =
            "com.blankj.utilcode.util.PermissionUtils\$PermissionUtils.PermissionActivity"


    /**
     * Init utils.
     *
     * Init it in the class of Application.
     *
     * @param context context
     */
    @JvmStatic
    fun init(context: Context?) {
        if (context == null) {
            init(getApplicationByReflect())
            return
        }
        init(context.applicationContext as Application)
    }

    /**
     * Init utils.
     *
     * Init it in the class of Application.
     *
     * @param app application
     */
    @JvmStatic
    fun init(app: Application?) {
        if (sApplication == null) {
            sApplication = app ?: getApplicationByReflect()
            sApplication?.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE)
        }
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    @JvmStatic
    fun getApp(): Application {
        if (sApplication != null) return sApplication as Application
        val app = getApplicationByReflect()
        init(app)
        return app
    }

    private fun getApplicationByReflect(): Application {
        try {
            @SuppressLint("PrivateApi")
            val activityThread = Class.forName("android.app.ActivityThread")
            val thread = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
            return app as Application
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        throw NullPointerException("u should init first")
    }

    @JvmStatic
    fun getActivityLifecycle(): ActivityLifecycleImpl {
        return ACTIVITY_LIFECYCLE
    }

    @JvmStatic
    fun getActivityList(): LinkedList<Activity> {
        return ACTIVITY_LIFECYCLE.mActivityList
    }

    @JvmStatic
    fun getTopActivityOrApp(): Context {
        return if (isAppForeground()) {
            val topActivity = ACTIVITY_LIFECYCLE.topActivity
            topActivity ?: getApp()
        } else {
            getApp()
        }
    }

    @JvmStatic
    fun isAppForeground(): Boolean {
        val am = getApp().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val info = am.runningAppProcesses
        if (info == null || info.size == 0) return false
        for (aInfo in info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName == getApp().packageName
            }
        }
        return false
    }

    @JvmStatic
    val ADAPT_SCREEN_ARGS = AdaptScreenArgs()

    @JvmStatic
    fun restoreAdaptScreen() {
        val systemDm = Resources.getSystem().displayMetrics
        val appDm = getApp().resources.displayMetrics
        val activity = ACTIVITY_LIFECYCLE.topActivity
        if (activity != null) {
            val activityDm = activity.resources.displayMetrics
            if (ADAPT_SCREEN_ARGS.isVerticalSlide) {
                activityDm.density = activityDm.widthPixels / ADAPT_SCREEN_ARGS.sizeInPx.toFloat()
            } else {
                activityDm.density = activityDm.heightPixels / ADAPT_SCREEN_ARGS.sizeInPx.toFloat()
            }
            activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density)
            activityDm.densityDpi = (160 * activityDm.density).toInt()

            appDm.density = activityDm.density
            appDm.scaledDensity = activityDm.scaledDensity
            appDm.densityDpi = activityDm.densityDpi
        } else {
            if (ADAPT_SCREEN_ARGS.isVerticalSlide) {
                appDm.density = appDm.widthPixels / ADAPT_SCREEN_ARGS.sizeInPx.toFloat()
            } else {
                appDm.density = appDm.heightPixels / ADAPT_SCREEN_ARGS.sizeInPx.toFloat()
            }
            appDm.scaledDensity = appDm.density * (systemDm.scaledDensity / systemDm.density)
            appDm.densityDpi = (160 * appDm.density).toInt()
        }
    }

    @JvmStatic
    fun cancelAdaptScreen() {
        val systemDm = Resources.getSystem().displayMetrics
        val appDm = getApp().resources.displayMetrics
        val activity = ACTIVITY_LIFECYCLE.topActivity
        if (activity != null) {
            val activityDm = activity.resources.displayMetrics
            activityDm.density = systemDm.density
            activityDm.scaledDensity = systemDm.scaledDensity
            activityDm.densityDpi = systemDm.densityDpi
        }
        appDm.density = systemDm.density
        appDm.scaledDensity = systemDm.scaledDensity
        appDm.densityDpi = systemDm.densityDpi
    }

    @JvmStatic
    fun isAdaptScreen(): Boolean {
        val systemDm = Resources.getSystem().displayMetrics
        val appDm = getApp().resources.displayMetrics
        return systemDm.density != appDm.density
    }

    public class AdaptScreenArgs {
        var sizeInPx: Int = 0
        var isVerticalSlide: Boolean = false
    }

    class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {

        val mActivityList: LinkedList<Activity> = LinkedList()
        private val mStatusListenerMap: HashMap<Any, OnAppStatusChangedListener> = HashMap()

        private var mForegroundCount = 0
        private var mConfigCount = 0

        var topActivity: Activity?
            get() {
                if (!mActivityList.isEmpty()) {
                    val topActivity = mActivityList.last
                    if (topActivity != null) {
                        return topActivity
                    }
                }
                val topActivityByReflect = topActivityByReflect
                if (topActivityByReflect != null) {
                    topActivity = topActivityByReflect
                }
                return topActivityByReflect
            }
            private set(activity) {
                if (PERMISSION_ACTIVITY_CLASS_NAME == activity?.javaClass?.name) return;
                if (activity?.javaClass == PermissionUtils.PermissionActivity::class.java) return
                if (mActivityList.contains(activity)) {
                    if (!mActivityList.last.equals(activity)) {
                        mActivityList.remove(activity)
                        mActivityList.addLast(activity)
                    }
                } else {
                    mActivityList.addLast(activity)
                }
            }

        private val topActivityByReflect: Activity?
            get() {
                try {
                    @SuppressLint("PrivateApi")
                    val activityThreadClass = Class.forName("android.app.ActivityThread")
                    val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
                    val activitiesField = activityThreadClass.getDeclaredField("mActivityList")
                    activitiesField.isAccessible = true
                    val activities = activitiesField.get(activityThread) as Map<*, *>
                    for (activityRecord in activities.values) {
                        if (activityRecord == null) continue
                        val activityRecordClass = activityRecord.javaClass
                        val pausedField = activityRecordClass.getDeclaredField("paused")
                        pausedField.isAccessible = true
                        if (!pausedField.getBoolean(activityRecord)) {
                            val activityField = activityRecordClass.getDeclaredField("activity")
                            activityField.isAccessible = true
                            return activityField.get(activityRecord) as Activity
                        }
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                }
                return null
            }

        fun addListener(obj: Any, listener: OnAppStatusChangedListener) {
            mStatusListenerMap[obj] = listener
        }

        fun removeListener(obj: Any) {
            mStatusListenerMap.remove(obj)
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            topActivity = activity
        }

        override fun onActivityStarted(activity: Activity?) {
            topActivity = activity
            if (mForegroundCount <= 0) {
                postStatus(true)
            }
            if (mConfigCount < 0) {
                ++mConfigCount
            } else {
                ++mForegroundCount
            }
        }

        override fun onActivityResumed(activity: Activity?) {
            topActivity = activity
        }

        override fun onActivityPaused(activity: Activity?) {}

        override fun onActivityStopped(activity: Activity?) {
            if (activity == null) return
            if (activity.isChangingConfigurations) {
                --mConfigCount
            } else {
                --mForegroundCount
                if (mForegroundCount <= 0) {
                    postStatus(false)
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

        override fun onActivityDestroyed(activity: Activity?) {
            mActivityList.remove(activity)
        }

        private fun postStatus(isForeground: Boolean) {
            if (mStatusListenerMap.isEmpty()) return
            for (onAppStatusChangedListener in mStatusListenerMap.values) {
                if (isForeground) {
                    onAppStatusChangedListener.onForeground()
                } else {
                    onAppStatusChangedListener.onBackground()
                }
            }
        }
    }

    class FileProvider4UtilCode : FileProvider() {

        override fun onCreate(): Boolean {
            init(context)
            return true
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    interface OnAppStatusChangedListener {
        fun onForeground()

        fun onBackground()
    }
}