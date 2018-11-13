package com.blankj.utilcode.util

import android.content.Context
import android.content.res.Resources


class AdaptScreenUtils private constructor() {

    companion object {
        /**
         * Adapt for the horizontal screen, and call it in [android.app.Activity.getResources].
         */
        @JvmStatic
        fun adaptWidth(resources: Resources, designWidth: Int, isOpen: Boolean = true): Resources {
            val dm = resources.displayMetrics
            dm.xdpi = if (isOpen) (dm.widthPixels * 72f) / designWidth else dm.density * 72f
            return resources
        }

        /**
         * Adapt for the vertical screen, and call it in [android.app.Activity.getResources].
         */
        @JvmStatic
        fun adaptHeight(resources: Resources, designHeight: Int, isOpen: Boolean = true): Resources {
            val dm = resources.displayMetrics
            dm.xdpi = if (isOpen) (dm.heightPixels * 72f) / designHeight else dm.density * 72f
            return resources
        }

        /**
         * Value of pt to value of px.
         */
        @JvmStatic
        fun pt2Px(context: Context, value: Float): Float {
            val metrics = context.resources.displayMetrics
            return value * metrics.xdpi * (1.0f / 72);
        }

        /**
         * Value of px to value of pt.
         */
        @JvmStatic
        fun px2Pt(context: Context, value: Float): Float {
            val metrics = context.resources.displayMetrics
            return value * 72 / metrics.xdpi;
        }
    }
}