//package com.blankj.utilcode.util;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Build;
//import android.support.annotation.LayoutRes;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//
//import java.util.HashMap;
//import java.util.TreeSet;
//
///**
// * <pre>
// *     author: blankj
// *     blog  : http://blankj.com
// *     time  : 2019/08/26
// *     desc  : utils about dialog
// * </pre>
// */
//public class DialogUtils {
//
//    private DialogUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }
//
//    public static void show(final Dialog dialog) {
//        if (dialog == null) return;
//        Utils.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Activity activity = getActivityByContext(dialog.getContext());
//                if (!isActivityAlive(activity)) return;
//                dialog.show();
//            }
//        });
//    }
//
//    public static void dismiss(final Dialog dialog) {
//        if (dialog == null) return;
//        Utils.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//            }
//        });
//    }
//
//    public static void show(final Utils.TransActivityDelegate delegate) {
//        Utils.TransActivity.start(null, delegate);
//    }
//
//    public static Dialog create(Activity activity, @LayoutRes int layoutId) {
//        Dialog dialog = new Dialog(activity);
//        View dialogContent = LayoutInflater.from(activity).inflate(layoutId, null);
//
//        dialog.setContentView(dialogContent);
//        Window window = dialog.getWindow();
//        if (window != null) {
//            window.setBackgroundDrawable(new ColorDrawable(0));
//        }
//
//        return dialog;
//    }
//
//    private static boolean isActivityAlive(final Activity activity) {
//        return activity != null && !activity.isFinishing()
//                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
//    }
//
//    private static Activity getActivityByContext(Context context) {
//        if (context instanceof Activity) return (Activity) context;
//        while (context instanceof ContextWrapper) {
//            if (context instanceof Activity) {
//                return (Activity) context;
//            }
//            context = ((ContextWrapper) context).getBaseContext();
//        }
//        return null;
//    }
//
//    public static final class UtilsDialog extends Dialog {
//
//        private int mPriority = 5;
//
//        public UtilsDialog(@NonNull Context context) {
//            this(context, 0);
//        }
//
//        public UtilsDialog(@NonNull Context context, int themeResId) {
//            super(context, themeResId);
//        }
//
//        @Override
//        public void show() {
//            Utils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Activity activity = getActivityByContext(getContext());
//                    if (!isActivityAlive(activity)) {
//                        Log.w("DialogUtils", "Activity is not alive.");
//                        return;
//                    }
//                    UtilsDialog.super.show();
//                }
//            });
//        }
//
//        @Override
//        public void dismiss() {
//            Utils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    UtilsDialog.super.dismiss();
//                }
//            });
//        }
//
//        public void show(int priority) {
//            mPriority = priority;
//            show();
//        }
//    }
//}
