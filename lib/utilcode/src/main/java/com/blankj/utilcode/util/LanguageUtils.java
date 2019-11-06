package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/20
 *     desc  : utils about language
 * </pre>
 */
public class LanguageUtils {

    private static final String KEY_LOCALE          = "KEY_LOCALE";
    private static final String VALUE_FOLLOW_SYSTEM = "VALUE_FOLLOW_SYSTEM";

    private LanguageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Apply the system language in the {@link Application#onCreate()}.
     */
    public static void applySystemLanguageInAppOnCreate() {
        if (isAppliedSystemLanguage()) return;
        applyLanguage(Resources.getSystem().getConfiguration().locale, "", true, false);
    }

    /**
     * Apply the language in the {@link Application#onCreate()}.
     *
     * @param locale The language of locale.
     */
    public static void applyLanguageInAppOnCreate(@NonNull final Locale locale) {
        if (isAppliedLanguage()) return;
        applyLanguage(locale, "", false, false);
    }

    /**
     * Apply the system language.
     *
     * @param activityClz The class of activity will be started after apply system language.
     */
    public static void applySystemLanguage(final Class<? extends Activity> activityClz) {
        applyLanguage(Resources.getSystem().getConfiguration().locale, activityClz, true, true);
    }

    /**
     * Apply the system language.
     *
     * @param activityClassName The full class name of activity will be started after apply system language.
     */
    public static void applySystemLanguage(final String activityClassName) {
        applyLanguage(Resources.getSystem().getConfiguration().locale, activityClassName, true, true);
    }

    /**
     * Apply the language.
     *
     * @param locale      The language of locale.
     * @param activityClz The class of activity will be started after apply system language.
     *                    It will start the launcher activity if the class is null.
     */
    public static void applyLanguage(@NonNull final Locale locale,
                                     final Class<? extends Activity> activityClz) {
        applyLanguage(locale, activityClz, false, true);
    }

    /**
     * Apply the language.
     *
     * @param locale            The language of locale.
     * @param activityClassName The class of activity will be started after apply system language.
     *                          It will start the launcher activity if the class name is null.
     */
    public static void applyLanguage(@NonNull final Locale locale,
                                     final String activityClassName) {
        applyLanguage(locale, activityClassName, false, true);
    }

    private static void applyLanguage(@NonNull final Locale locale,
                                      final Class<? extends Activity> activityClz,
                                      final boolean isFollowSystem,
                                      final boolean isNeedStartActivity) {
        if (activityClz == null) {
            applyLanguage(locale, "", isFollowSystem, isNeedStartActivity);
            return;
        }
        applyLanguage(locale, activityClz.getName(), isFollowSystem, isNeedStartActivity);
    }

    private static void applyLanguage(@NonNull final Locale locale,
                                      final String activityClassName,
                                      final boolean isFollowSystem,
                                      final boolean isNeedStartActivity) {
        if (isFollowSystem) {
            Utils.getSpUtils4Utils().put(KEY_LOCALE, VALUE_FOLLOW_SYSTEM);
        } else {
            String localLanguage = locale.getLanguage();
            String localCountry = locale.getCountry();
            Utils.getSpUtils4Utils().put(KEY_LOCALE, localLanguage + "$" + localCountry);
        }

        updateLanguage(Utils.getApp(), locale);

        if (isNeedStartActivity) {
            Intent intent = new Intent();
            String realActivityClassName = TextUtils.isEmpty(activityClassName) ? getLauncherActivity() : activityClassName;
            intent.setComponent(new ComponentName(Utils.getApp(), realActivityClassName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Utils.getApp().startActivity(intent);
        }
    }

    /**
     * Return whether applied the system language by {@link LanguageUtils}.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppliedSystemLanguage() {
        return VALUE_FOLLOW_SYSTEM.equals(Utils.getSpUtils4Utils().getString(KEY_LOCALE));
    }

    /**
     * Return whether applied the language by {@link LanguageUtils}.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppliedLanguage() {
        return !TextUtils.isEmpty(Utils.getSpUtils4Utils().getString(KEY_LOCALE));
    }

    /**
     * Return the locale.
     *
     * @return the locale
     */
    public static Locale getCurrentLocale() {
        return Utils.getApp().getResources().getConfiguration().locale;
    }

    static void applyLanguage(@NonNull final Activity activity) {
        final String spLocale = Utils.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(spLocale)) {
            return;
        }

        if (VALUE_FOLLOW_SYSTEM.equals(spLocale)) {
            Locale sysLocale = Resources.getSystem().getConfiguration().locale;
            updateLanguage(Utils.getApp(), sysLocale);
            updateLanguage(activity, sysLocale);
            return;
        }

        String[] language_country = spLocale.split("\\$");
        if (language_country.length != 2) {
            Log.e("LanguageUtils", "The string of " + spLocale + " is not in the correct format.");
            return;
        }

        Locale settingLocale = new Locale(language_country[0], language_country[1]);
        updateLanguage(Utils.getApp(), settingLocale);
        updateLanguage(activity, settingLocale);
    }

    private static void updateLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale contextLocale = config.locale;
        if (equals(contextLocale.getLanguage(), locale.getLanguage())
                && equals(contextLocale.getCountry(), locale.getCountry())) {
            return;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);
    }

    private static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    private static String getLauncherActivity() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(Utils.getApp().getPackageName());
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        ResolveInfo next = info.iterator().next();
        if (next != null) {
            return next.activityInfo.name;
        }
        return "no launcher activity";
    }
}
