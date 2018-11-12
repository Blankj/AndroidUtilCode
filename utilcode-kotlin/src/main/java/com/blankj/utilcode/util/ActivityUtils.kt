package com.blankj.utilcode.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/23
 * desc  : utils about activity
</pre> *
 */
object ActivityUtils {

        /**
         * Return whether the activity exists.
         *
         * @param pkg The name of the package.
         * @param cls The name of the class.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isActivityExists(pkg: String,
                             cls: String): Boolean {
            val intent = Intent()
            intent.setClassName(pkg, cls)
            return !(Utils.getApp().packageManager.resolveActivity(intent, 0) == null ||
                    intent.resolveActivity(Utils.getApp().packageManager) == null ||
                    Utils.getApp().packageManager.queryIntentActivities(intent, 0).size == 0)
        }

        /**
         * Start the activity.
         *
         * @param clz The activity class.
         */
        fun startActivity(clz: Class<out Activity>) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, null, context.packageName, clz.name, null)
        }

        /**
         * Start the activity.
         *
         * @param clz     The activity class.
         * @param options Additional options for how the Activity should be started.
         */
        fun startActivity(clz: Class<out Activity>,
                          options: Bundle) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, null, context.packageName, clz.name, options)
        }

        /**
         * Start the activity.
         *
         * @param clz       The activity class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(clz: Class<out Activity>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, null, context.packageName, clz.name,
                    getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param clz      The activity class.
         */
        fun startActivity(activity: Activity,
                          clz: Class<out Activity>) {
            startActivity(activity, null, activity.packageName, clz.name, null)
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param clz      The activity class.
         * @param options  Additional options for how the Activity should be started.
         */
        fun startActivity(activity: Activity,
                          clz: Class<out Activity>,
                          options: Bundle) {
            startActivity(activity, null, activity.packageName, clz.name, options)
        }

        /**
         * Start the activity.
         *
         * @param activity       The activity.
         * @param clz            The activity class.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivity(activity: Activity,
                          clz: Class<out Activity>,
                          vararg sharedElements: View) {
            startActivity(activity, null, activity.packageName, clz.name,
                    getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param activity  The activity.
         * @param clz       The activity class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(activity: Activity,
                          clz: Class<out Activity>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, null, activity.packageName, clz.name,
                    getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param extras The Bundle of extras to add to this intent.
         * @param clz    The activity class.
         */
        fun startActivity(extras: Bundle,
                          clz: Class<out Activity>) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, extras, context.packageName, clz.name, null)
        }

        /**
         * Start the activity.
         *
         * @param extras  The Bundle of extras to add to this intent.
         * @param clz     The activity class.
         * @param options Additional options for how the Activity should be started.
         */
        fun startActivity(extras: Bundle,
                          clz: Class<out Activity>,
                          options: Bundle) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, extras, context.packageName, clz.name, options)
        }

        /**
         * Start the activity.
         *
         * @param extras    The Bundle of extras to add to this intent.
         * @param clz       The activity class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(extras: Bundle,
                          clz: Class<out Activity>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, extras, context.packageName, clz.name,
                    getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param extras   The Bundle of extras to add to this intent.
         * @param activity The activity.
         * @param clz      The activity class.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<out Activity>) {
            startActivity(activity, extras, activity.packageName, clz.name, null)
        }

        /**
         * Start the activity.
         *
         * @param extras   The Bundle of extras to add to this intent.
         * @param activity The activity.
         * @param clz      The activity class.
         * @param options  Additional options for how the Activity should be started.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<out Activity>,
                          options: Bundle) {
            startActivity(activity, extras, activity.packageName, clz.name, options)
        }

        /**
         * Start the activity.
         *
         * @param extras         The Bundle of extras to add to this intent.
         * @param activity       The activity.
         * @param clz            The activity class.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<out Activity>,
                          vararg sharedElements: View) {
            startActivity(activity, extras, activity.packageName, clz.name,
                    getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param extras    The Bundle of extras to add to this intent.
         * @param activity  The activity.
         * @param clz       The activity class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<out Activity>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, extras, activity.packageName, clz.name,
                    getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param pkg The name of the package.
         * @param cls The name of the class.
         */
        fun startActivity(pkg: String,
                          cls: String) {
            startActivity(Utils.getTopActivityOrApp(), null, pkg, cls, null)
        }

        /**
         * Start the activity.
         *
         * @param pkg     The name of the package.
         * @param cls     The name of the class.
         * @param options Additional options for how the Activity should be started.
         */
        fun startActivity(pkg: String,
                          cls: String,
                          options: Bundle) {
            startActivity(Utils.getTopActivityOrApp(), null, pkg, cls, options)
        }

        /**
         * Start the activity.
         *
         * @param pkg       The name of the package.
         * @param cls       The name of the class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param pkg      The name of the package.
         * @param cls      The name of the class.
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String) {
            startActivity(activity, null, pkg, cls, null)
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param pkg      The name of the package.
         * @param cls      The name of the class.
         * @param options  Additional options for how the Activity should be started.
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String,
                          options: Bundle) {
            startActivity(activity, null, pkg, cls, options)
        }

        /**
         * Start the activity.
         *
         * @param activity       The activity.
         * @param pkg            The name of the package.
         * @param cls            The name of the class.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String,
                          vararg sharedElements: View) {
            startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param activity  The activity.
         * @param pkg       The name of the package.
         * @param cls       The name of the class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param extras The Bundle of extras to add to this intent.
         * @param pkg    The name of the package.
         * @param cls    The name of the class.
         */
        fun startActivity(extras: Bundle,
                          pkg: String,
                          cls: String) {
            startActivity(Utils.getTopActivityOrApp(), extras, pkg, cls, null)
        }

        /**
         * Start the activity.
         *
         * @param extras  The Bundle of extras to add to this intent.
         * @param pkg     The name of the package.
         * @param cls     The name of the class.
         * @param options Additional options for how the Activity should be started.
         */
        fun startActivity(extras: Bundle,
                          pkg: String,
                          cls: String,
                          options: Bundle) {
            startActivity(Utils.getTopActivityOrApp(), extras, pkg, cls, options)
        }

        /**
         * Start the activity.
         *
         * @param extras    The Bundle of extras to add to this intent.
         * @param pkg       The name of the package.
         * @param cls       The name of the class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(extras: Bundle,
                          pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = Utils.getTopActivityOrApp()
            startActivity(context, extras, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param extras   The Bundle of extras to add to this intent.
         * @param pkg      The name of the package.
         * @param cls      The name of the class.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String) {
            startActivity(activity, extras, pkg, cls, null)
        }

        /**
         * Start the activity.
         *
         * @param extras   The Bundle of extras to add to this intent.
         * @param activity The activity.
         * @param pkg      The name of the package.
         * @param cls      The name of the class.
         * @param options  Additional options for how the Activity should be started.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String,
                          options: Bundle) {
            startActivity(activity, extras, pkg, cls, options)
        }

        /**
         * Start the activity.
         *
         * @param extras         The Bundle of extras to add to this intent.
         * @param activity       The activity.
         * @param pkg            The name of the package.
         * @param cls            The name of the class.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String,
                          vararg sharedElements: View) {
            startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param extras    The Bundle of extras to add to this intent.
         * @param pkg       The name of the package.
         * @param cls       The name of the class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param intent The description of the activity to start.
         */
        fun startActivity(intent: Intent) {
            startActivity(intent, Utils.getTopActivityOrApp(), null)
        }

        /**
         * Start the activity.
         *
         * @param intent  The description of the activity to start.
         * @param options Additional options for how the Activity should be started.
         */
        fun startActivity(intent: Intent,
                          options: Bundle) {
            startActivity(intent, Utils.getTopActivityOrApp(), options)
        }

        /**
         * Start the activity.
         *
         * @param intent    The description of the activity to start.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(intent: Intent,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = Utils.getTopActivityOrApp()
            startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param intent   The description of the activity to start.
         */
        fun startActivity(activity: Activity,
                          intent: Intent) {
            startActivity(intent, activity, null)
        }

        /**
         * Start the activity.
         *
         * @param activity The activity.
         * @param intent   The description of the activity to start.
         * @param options  Additional options for how the Activity should be started.
         */
        fun startActivity(activity: Activity,
                          intent: Intent,
                          options: Bundle) {
            startActivity(intent, activity, options)
        }

        /**
         * Start the activity.
         *
         * @param activity       The activity.
         * @param intent         The description of the activity to start.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivity(activity: Activity,
                          intent: Intent,
                          vararg sharedElements: View) {
            startActivity(intent, activity, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param activity  The activity.
         * @param intent    The description of the activity to start.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivity(activity: Activity,
                          intent: Intent,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param activity    The activity.
         * @param clz         The activity class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         */
        fun startActivityForResult(activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int) {
            startActivityForResult(activity, null, activity.packageName, clz.name,
                    requestCode, null)
        }

        /**
         * Start the activity.
         *
         * @param activity    The activity.
         * @param clz         The activity class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param options     Additional options for how the Activity should be started.
         */
        fun startActivityForResult(activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int,
                                   options: Bundle) {
            startActivityForResult(activity, null, activity.packageName, clz.name,
                    requestCode, options)
        }

        /**
         * Start the activity.
         *
         * @param activity       The activity.
         * @param clz            The activity class.
         * @param requestCode    if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivityForResult(activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int,
                                   vararg sharedElements: View) {
            startActivityForResult(activity, null, activity.packageName, clz.name,
                    requestCode, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param activity    The activity.
         * @param clz         The activity class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param enterAnim   A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim    A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivityForResult(activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int,
                                   @AnimRes enterAnim: Int,
                                   @AnimRes exitAnim: Int) {
            startActivityForResult(activity, null, activity.packageName, clz.name,
                    requestCode, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity.
         *
         * @param extras      The Bundle of extras to add to this intent.
         * @param activity    The activity.
         * @param clz         The activity class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int) {
            startActivityForResult(activity, extras, activity.packageName, clz.name,
                    requestCode, null)
        }

        /**
         * Start the activity.
         *
         * @param extras      The Bundle of extras to add to this intent.
         * @param activity    The activity.
         * @param clz         The activity class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param options     Additional options for how the Activity should be started.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int,
                                   options: Bundle) {
            startActivityForResult(activity, extras, activity.packageName, clz.name,
                    requestCode, options)
        }

        /**
         * Start the activity.
         *
         * @param extras         The Bundle of extras to add to this intent.
         * @param activity       The activity.
         * @param clz            The activity class.
         * @param requestCode    if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int,
                                   vararg sharedElements: View) {
            startActivityForResult(activity, extras, activity.packageName, clz.name,
                    requestCode, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity.
         *
         * @param extras      The Bundle of extras to add to this intent.
         * @param activity    The activity.
         * @param clz         The activity class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param enterAnim   A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim    A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   clz: Class<out Activity>,
                                   requestCode: Int,
                                   @AnimRes enterAnim: Int,
                                   @AnimRes exitAnim: Int) {
            startActivityForResult(activity, extras, activity.packageName, clz.name,
                    requestCode, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity for result.
         *
         * @param activity    The activity.
         * @param extras      The Bundle of extras to add to this intent.
         * @param pkg         The name of the package.
         * @param cls         The name of the class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   pkg: String,
                                   cls: String,
                                   requestCode: Int) {
            startActivityForResult(activity, extras, pkg, cls, requestCode, null)
        }

        /**
         * Start the activity for result.
         *
         * @param extras      The Bundle of extras to add to this intent.
         * @param activity    The activity.
         * @param pkg         The name of the package.
         * @param cls         The name of the class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param options     Additional options for how the Activity should be started.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   pkg: String,
                                   cls: String,
                                   requestCode: Int,
                                   options: Bundle) {
            startActivityForResult(activity, extras, pkg, cls, requestCode, options)
        }

        /**
         * Start the activity for result.
         *
         * @param extras         The Bundle of extras to add to this intent.
         * @param activity       The activity.
         * @param pkg            The name of the package.
         * @param cls            The name of the class.
         * @param requestCode    if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   pkg: String,
                                   cls: String,
                                   requestCode: Int,
                                   vararg sharedElements: View) {
            startActivityForResult(activity, extras, pkg, cls,
                    requestCode, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity for result.
         *
         * @param extras      The Bundle of extras to add to this intent.
         * @param pkg         The name of the package.
         * @param cls         The name of the class.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param enterAnim   A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim    A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivityForResult(extras: Bundle,
                                   activity: Activity,
                                   pkg: String,
                                   cls: String,
                                   requestCode: Int,
                                   @AnimRes enterAnim: Int,
                                   @AnimRes exitAnim: Int) {
            startActivityForResult(activity, extras, pkg, cls,
                    requestCode, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start the activity for result.
         *
         * @param activity    The activity.
         * @param intent      The description of the activity to start.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         */
        fun startActivityForResult(activity: Activity,
                                   intent: Intent,
                                   requestCode: Int) {
            startActivityForResult(intent, activity, requestCode, null)
        }

        /**
         * Start the activity for result.
         *
         * @param activity    The activity.
         * @param intent      The description of the activity to start.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param options     Additional options for how the Activity should be started.
         */
        fun startActivityForResult(activity: Activity,
                                   intent: Intent,
                                   requestCode: Int,
                                   options: Bundle) {
            startActivityForResult(intent, activity, requestCode, options)
        }

        /**
         * Start the activity for result.
         *
         * @param activity       The activity.
         * @param intent         The description of the activity to start.
         * @param requestCode    if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param sharedElements The names of the shared elements to transfer to the called
         * Activity and their associated Views.
         */
        fun startActivityForResult(activity: Activity,
                                   intent: Intent,
                                   requestCode: Int,
                                   vararg sharedElements: View) {
            startActivityForResult(intent, activity,
                    requestCode, getOptionsBundle(activity, sharedElements))
        }

        /**
         * Start the activity for result.
         *
         * @param activity    The activity.
         * @param intent      The description of the activity to start.
         * @param requestCode if &gt;= 0, this code will be returned in
         * onActivityResult() when the activity exits.
         * @param enterAnim   A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim    A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivityForResult(activity: Activity,
                                   intent: Intent,
                                   requestCode: Int,
                                   @AnimRes enterAnim: Int,
                                   @AnimRes exitAnim: Int) {
            startActivityForResult(intent, activity,
                    requestCode, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start activities.
         *
         * @param intents The descriptions of the activities to start.
         */
        fun startActivities(intents: Array<Intent>) {
            startActivities(intents, Utils.getTopActivityOrApp(), null)
        }

        /**
         * Start activities.
         *
         * @param intents The descriptions of the activities to start.
         * @param options Additional options for how the Activity should be started.
         */
        fun startActivities(intents: Array<Intent>,
                            options: Bundle) {
            startActivities(intents, Utils.getTopActivityOrApp(), options)
        }

        /**
         * Start activities.
         *
         * @param intents   The descriptions of the activities to start.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivities(intents: Array<Intent>,
                            @AnimRes enterAnim: Int,
                            @AnimRes exitAnim: Int) {
            val context = Utils.getTopActivityOrApp()
            startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start activities.
         *
         * @param activity The activity.
         * @param intents  The descriptions of the activities to start.
         */
        fun startActivities(activity: Activity,
                            intents: Array<Intent>) {
            startActivities(intents, activity, null)
        }

        /**
         * Start activities.
         *
         * @param activity The activity.
         * @param intents  The descriptions of the activities to start.
         * @param options  Additional options for how the Activity should be started.
         */
        fun startActivities(activity: Activity,
                            intents: Array<Intent>,
                            options: Bundle) {
            startActivities(intents, activity, options)
        }

        /**
         * Start activities.
         *
         * @param activity  The activity.
         * @param intents   The descriptions of the activities to start.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun startActivities(activity: Activity,
                            intents: Array<Intent>,
                            @AnimRes enterAnim: Int,
                            @AnimRes exitAnim: Int) {
            startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Start home activity.
         */
        fun startHomeActivity() {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            startActivity(homeIntent)
        }

        /**
         * Return the list of activity.
         *
         * @return the list of activity
         */
        val activityList: List<Activity>
            get() = Utils.getActivityList()

        /**
         * Return the name of launcher activity.
         *
         * @return the name of launcher activity
         */
        val launcherActivity: String
            get() = getLauncherActivity(Utils.getApp().packageName)

        /**
         * Return the name of launcher activity.
         *
         * @param pkg The name of the package.
         * @return the name of launcher activity
         */
        fun getLauncherActivity(pkg: String): String {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pm = Utils.getApp().packageManager
            val info = pm.queryIntentActivities(intent, 0)
            for (aInfo in info) {
                if (aInfo.activityInfo.packageName == pkg) {
                    return aInfo.activityInfo.name
                }
            }
            return "no $pkg"
        }

        /**
         * Return the top activity in activity's stack.
         *
         * @return the top activity in activity's stack
         */
        val topActivity: Activity?
            get() = Utils.getActivityLifecycle().topActivity

        /**
         * Return whether the activity exists in activity's stack.
         *
         * @param activity The activity.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isActivityExistsInStack(activity: Activity): Boolean {
            val activities = Utils.getActivityList()
            for (aActivity in activities) {
                if (aActivity == activity) {
                    return true
                }
            }
            return false
        }

        /**
         * Return whether the activity exists in activity's stack.
         *
         * @param clz The activity class.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isActivityExistsInStack(clz: Class<out Activity>): Boolean {
            val activities = Utils.getActivityList()
            for (aActivity in activities) {
                if (aActivity.javaClass == clz) {
                    return true
                }
            }
            return false
        }

        /**
         * Finish the activity.
         *
         * @param activity   The activity.
         * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishActivity(activity: Activity, isLoadAnim: Boolean = false) {
            activity.finish()
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0)
            }
        }

        /**
         * Finish the activity.
         *
         * @param activity  The activity.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishActivity(activity: Activity,
                           @AnimRes enterAnim: Int,
                           @AnimRes exitAnim: Int) {
            activity.finish()
            activity.overridePendingTransition(enterAnim, exitAnim)
        }

        /**
         * Finish the activity.
         *
         * @param clz        The activity class.
         * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishActivity(clz: Class<out Activity>,
                           isLoadAnim: Boolean = false) {
            val activities = Utils.getActivityList()
            for (activity in activities) {
                if (activity.javaClass == clz) {
                    activity.finish()
                    if (!isLoadAnim) {
                        activity.overridePendingTransition(0, 0)
                    }
                }
            }
        }

        /**
         * Finish the activity.
         *
         * @param clz       The activity class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishActivity(clz: Class<out Activity>,
                           @AnimRes enterAnim: Int,
                           @AnimRes exitAnim: Int) {
            val activities = Utils.getActivityList()
            for (activity in activities) {
                if (activity.javaClass == clz) {
                    activity.finish()
                    activity.overridePendingTransition(enterAnim, exitAnim)
                }
            }
        }

        /**
         * Finish to the activity.
         *
         * @param activity      The activity.
         * @param isIncludeSelf True to include the activity, false otherwise.
         * @param isLoadAnim    True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishToActivity(activity: Activity,
                             isIncludeSelf: Boolean,
                             isLoadAnim: Boolean = false): Boolean {
            val activities = Utils.getActivityList()
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity == activity) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, isLoadAnim)
                    }
                    return true
                }
                finishActivity(aActivity, isLoadAnim)
            }
            return false
        }

        /**
         * Finish to the activity.
         *
         * @param activity      The activity.
         * @param isIncludeSelf True to include the activity, false otherwise.
         * @param enterAnim     A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim      A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishToActivity(activity: Activity,
                             isIncludeSelf: Boolean,
                             @AnimRes enterAnim: Int,
                             @AnimRes exitAnim: Int): Boolean {
            val activities = Utils.getActivityList()
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity == activity) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, enterAnim, exitAnim)
                    }
                    return true
                }
                finishActivity(aActivity, enterAnim, exitAnim)
            }
            return false
        }

        /**
         * Finish to the activity.
         *
         * @param clz           The activity class.
         * @param isIncludeSelf True to include the activity, false otherwise.
         * @param isLoadAnim    True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishToActivity(clz: Class<out Activity>,
                             isIncludeSelf: Boolean,
                             isLoadAnim: Boolean = false): Boolean {
            val activities = Utils.getActivityList()
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity.javaClass == clz) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, isLoadAnim)
                    }
                    return true
                }
                finishActivity(aActivity, isLoadAnim)
            }
            return false
        }

        /**
         * Finish to the activity.
         *
         * @param clz           The activity class.
         * @param isIncludeSelf True to include the activity, false otherwise.
         * @param enterAnim     A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim      A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishToActivity(clz: Class<out Activity>,
                             isIncludeSelf: Boolean,
                             @AnimRes enterAnim: Int,
                             @AnimRes exitAnim: Int): Boolean {
            val activities = Utils.getActivityList()
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity.javaClass == clz) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, enterAnim, exitAnim)
                    }
                    return true
                }
                finishActivity(aActivity, enterAnim, exitAnim)
            }
            return false
        }


        /**
         * Finish the activities whose type not equals the activity class.
         *
         * @param clz        The activity class.
         * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishOtherActivities(clz: Class<out Activity>,
                                  isLoadAnim: Boolean = false) {
            val activities = Utils.getActivityList()
            for (i in activities.indices.reversed()) {
                val activity = activities[i]
                if (activity.javaClass != clz) {
                    finishActivity(activity, isLoadAnim)
                }
            }
        }

        /**
         * Finish the activities whose type not equals the activity class.
         *
         * @param clz       The activity class.
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishOtherActivities(clz: Class<out Activity>,
                                  @AnimRes enterAnim: Int,
                                  @AnimRes exitAnim: Int) {
            val activities = Utils.getActivityList()
            for (i in activities.indices.reversed()) {
                val activity = activities[i]
                if (activity.javaClass != clz) {
                    finishActivity(activity, enterAnim, exitAnim)
                }
            }
        }

        /**
         * Finish all of activities.
         *
         * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishAllActivities(isLoadAnim: Boolean = false) {
            val activityList = Utils.getActivityList()
            for (i in activityList.indices.reversed()) {// remove from top
                val activity = activityList[i]
                // sActivityList remove the index activity at onActivityDestroyed
                activity.finish()
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0)
                }
            }
        }

        /**
         * Finish all of activities.
         *
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishAllActivities(@AnimRes enterAnim: Int,
                                @AnimRes exitAnim: Int) {
            val activityList = Utils.getActivityList()
            for (i in activityList.indices.reversed()) {// remove from top
                val activity = activityList[i]
                // sActivityList remove the index activity at onActivityDestroyed
                activity.finish()
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * Finish all of activities except the newest activity.
         *
         * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
         */
        @JvmOverloads
        fun finishAllActivitiesExceptNewest(isLoadAnim: Boolean = false) {
            val activities = Utils.getActivityList()
            for (i in activities.size - 2 downTo 0) {
                finishActivity(activities[i], isLoadAnim)
            }
        }

        /**
         * Finish all of activities except the newest activity.
         *
         * @param enterAnim A resource ID of the animation resource to use for the
         * incoming activity.
         * @param exitAnim  A resource ID of the animation resource to use for the
         * outgoing activity.
         */
        fun finishAllActivitiesExceptNewest(@AnimRes enterAnim: Int,
                                            @AnimRes exitAnim: Int) {
            val activities = Utils.getActivityList()
            for (i in activities.size - 2 downTo 0) {
                finishActivity(activities[i], enterAnim, exitAnim)
            }
        }

        /**
         * Return the icon of activity.
         *
         * @param activity The activity.
         * @return the icon of activity
         */
        fun getActivityIcon(activity: Activity): Drawable? {
            return getActivityIcon(activity.componentName)
        }

        /**
         * Return the icon of activity.
         *
         * @param clz The activity class.
         * @return the icon of activity
         */
        fun getActivityIcon(clz: Class<out Activity>): Drawable? {
            return getActivityIcon(ComponentName(Utils.getApp(), clz))
        }

        /**
         * Return the icon of activity.
         *
         * @param activityName The name of activity.
         * @return the icon of activity
         */
        fun getActivityIcon(activityName: ComponentName): Drawable? {
            val pm = Utils.getApp().packageManager
            try {
                return pm.getActivityIcon(activityName)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * Return the logo of activity.
         *
         * @param activity The activity.
         * @return the logo of activity
         */
        fun getActivityLogo(activity: Activity): Drawable? {
            return getActivityLogo(activity.componentName)
        }

        /**
         * Return the logo of activity.
         *
         * @param clz The activity class.
         * @return the logo of activity
         */
        fun getActivityLogo(clz: Class<out Activity>): Drawable? {
            return getActivityLogo(ComponentName(Utils.getApp(), clz))
        }

        /**
         * Return the logo of activity.
         *
         * @param activityName The name of activity.
         * @return the logo of activity
         */
        fun getActivityLogo(activityName: ComponentName): Drawable? {
            val pm = Utils.getApp().packageManager
            try {
                return pm.getActivityLogo(activityName)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        private fun startActivity(context: Context,
                                  extras: Bundle?,
                                  pkg: String,
                                  cls: String,
                                  options: Bundle?) {
            val intent = Intent(Intent.ACTION_VIEW)
            if (extras != null) intent.putExtras(extras)
            intent.component = ComponentName(pkg, cls)
            startActivity(intent, context, options)
        }

        private fun startActivity(intent: Intent,
                                  context: Context,
                                  options: Bundle?) {
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(intent, options)
            } else {
                context.startActivity(intent)
            }
        }

        private fun startActivityForResult(activity: Activity,
                                           extras: Bundle?,
                                           pkg: String,
                                           cls: String,
                                           requestCode: Int,
                                           options: Bundle?) {
            val intent = Intent(Intent.ACTION_VIEW)
            if (extras != null) intent.putExtras(extras)
            intent.component = ComponentName(pkg, cls)
            startActivityForResult(intent, activity, requestCode, options)
        }

        private fun startActivityForResult(intent: Intent,
                                           activity: Activity,
                                           requestCode: Int,
                                           options: Bundle?) {
            if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.startActivityForResult(intent, requestCode, options)
            } else {
                activity.startActivityForResult(intent, requestCode)
            }
        }

        private fun startActivities(intents: Array<Intent>,
                                    context: Context,
                                    options: Bundle?) {
            if (context !is Activity) {
                for (intent in intents) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
            if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivities(intents, options)
            } else {
                context.startActivities(intents)
            }
        }

        private fun getOptionsBundle(context: Context,
                                     enterAnim: Int,
                                     exitAnim: Int): Bundle? {
            return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle()
        }

        private fun getOptionsBundle(activity: Activity,
                                     sharedElements: Array<out View>?): Bundle? {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null
            if (sharedElements == null) return null
            val len = sharedElements.size
            if (len <= 0) return null
            val pairs = arrayOfNulls<Pair<View, String>>(len)
            for (i in 0 until len) {
                pairs[i] = Pair.create(sharedElements[i], sharedElements[i].transitionName)
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs).toBundle()
        }
}
/**
 * Finish the activity.
 *
 * @param activity The activity.
 */
/**
 * Finish the activity.
 *
 * @param clz The activity class.
 */
/**
 * Finish to the activity.
 *
 * @param activity      The activity.
 * @param isIncludeSelf True to include the activity, false otherwise.
 */
/**
 * Finish to the activity.
 *
 * @param clz           The activity class.
 * @param isIncludeSelf True to include the activity, false otherwise.
 */
/**
 * Finish the activities whose type not equals the activity class.
 *
 * @param clz The activity class.
 */
/**
 * Finish all of activities.
 */
/**
 * Finish all of activities except the newest activity.
 */
