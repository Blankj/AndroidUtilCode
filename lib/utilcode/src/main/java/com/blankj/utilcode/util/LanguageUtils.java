package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;
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
     * Apply the system language.
     * It will not restart Activity. u can put it in ur {@link Activity#onCreate(Bundle)}.
     */
    public static void applySystemLanguage() {
        if (isAppliedSystemLanguage()) return;
        applyLanguage(Resources.getSystem().getConfiguration().locale, "", true, false);
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
     * It will not restart Activity. u can put it in ur {@link Activity#onCreate(Bundle)}.
     *
     * @param locale The language of locale.
     */
    public static void applyLanguage(@NonNull final Locale locale) {
        if (isAppliedLanguage(locale)) return;
        applyLanguage(locale, "", false, false);
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
            UtilsBridge.getSpUtils4Utils().put(KEY_LOCALE, VALUE_FOLLOW_SYSTEM);
        } else {
            UtilsBridge.getSpUtils4Utils().put(KEY_LOCALE, locale2String(locale));
        }

        updateLanguage(Utils.getApp(), locale);

        if (isNeedStartActivity) {
            Intent intent = new Intent();
            String realActivityClassName = TextUtils.isEmpty(activityClassName) ? UtilsBridge.getLauncherActivity() : activityClassName;
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
        return VALUE_FOLLOW_SYSTEM.equals(UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE));
    }

    /**
     * Return whether applied the language by {@link LanguageUtils}.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppliedLanguage() {
        return !TextUtils.isEmpty(UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE));
    }

    /**
     * Return whether applied the language by {@link LanguageUtils}.
     *
     * @param locale The locale.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppliedLanguage(Locale locale) {
        final String spLocale = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(spLocale)) {
            return false;
        }
        if (VALUE_FOLLOW_SYSTEM.equals(spLocale)) {
            return false;
        }
        Locale settingLocale = string2Locale(spLocale);
        if (settingLocale == null) return false;
        return isSameLocale(settingLocale, locale);
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
        String spLocale = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(spLocale)) {
            return;
        }

        if (VALUE_FOLLOW_SYSTEM.equals(spLocale)) {
            Locale sysLocale = Resources.getSystem().getConfiguration().locale;
            updateLanguage(Utils.getApp(), sysLocale);
            updateLanguage(activity, sysLocale);
            return;
        }

        Locale settingLocale = string2Locale(spLocale);
        if (settingLocale == null) return;
        updateLanguage(Utils.getApp(), settingLocale);
        updateLanguage(activity, settingLocale);
    }

    private static String locale2String(Locale locale) {
        String localLanguage = locale.getLanguage();
        String localCountry = locale.getCountry();
        return localLanguage + "$" + localCountry;
    }

    private static Locale string2Locale(String str) {
        String[] language_country = str.split("\\$");
        if (language_country.length != 2) {
            Log.e("LanguageUtils", "The string of " + str + " is not in the correct format.");
            return null;
        }
        return new Locale(language_country[0], language_country[1]);
    }


    private static void updateLanguage(final Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale contextLocale = config.locale;
        if (isSameLocale(contextLocale, locale)) {
            return;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            if (context instanceof Application) {
                Context newContext = context.createConfigurationContext(config);
                try {
                    //noinspection JavaReflectionMemberAccess
                    Field mBaseField = ContextWrapper.class.getDeclaredField("mBase");
                    mBaseField.setAccessible(true);
                    mBaseField.set(context, newContext);
                } catch (Exception ignored) {/**/}
            }
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);
    }

    private static boolean isSameLocale(Locale l0, Locale l1) {
        return UtilsBridge.equals(l1.getLanguage(), l0.getLanguage())
                && UtilsBridge.equals(l1.getCountry(), l0.getCountry());
    }
}
