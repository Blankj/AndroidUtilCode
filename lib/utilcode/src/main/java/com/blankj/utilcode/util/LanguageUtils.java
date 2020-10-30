package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

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
     */
    public static void applySystemLanguage() {
        applySystemLanguage(false);
    }

    /**
     * Apply the system language.
     *
     * @param isRelaunchApp True to relaunch app, false to recreate all activities.
     */
    public static void applySystemLanguage(final boolean isRelaunchApp) {
        applyLanguageReal(null, isRelaunchApp);
    }

    /**
     * Apply the language.
     *
     * @param locale The language of locale.
     */
    public static void applyLanguage(@NonNull final Locale locale) {
        applyLanguage(locale, false);
    }

    /**
     * Apply the language.
     *
     * @param locale        The language of locale.
     * @param isRelaunchApp True to relaunch app, false to recreate all activities.
     */
    public static void applyLanguage(@NonNull final Locale locale,
                                     final boolean isRelaunchApp) {
        applyLanguageReal(locale, isRelaunchApp);
    }

    private static void applyLanguageReal(final Locale locale,
                                          final boolean isRelaunchApp) {
        if (locale == null) {
            UtilsBridge.getSpUtils4Utils().put(KEY_LOCALE, VALUE_FOLLOW_SYSTEM, true);
        } else {
            UtilsBridge.getSpUtils4Utils().put(KEY_LOCALE, locale2String(locale), true);
        }

        Locale destLocal = locale == null ? getLocal(Resources.getSystem().getConfiguration()) : locale;
        updateAppContextLanguage(destLocal, new Utils.Consumer<Boolean>() {
            @Override
            public void accept(Boolean success) {
                if (success) {
                    restart(isRelaunchApp);
                } else {
                    // use relaunch app
                    UtilsBridge.relaunchApp();
                }
            }
        });
    }

    private static void restart(final boolean isRelaunchApp) {
        if (isRelaunchApp) {
            UtilsBridge.relaunchApp();
        } else {
            for (Activity activity : UtilsBridge.getActivityList()) {
                activity.recreate();
            }
        }
    }

    /**
     * Return whether applied the language by {@link LanguageUtils}.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppliedLanguage() {
        return getAppliedLanguage() != null;
    }

    /**
     * Return whether applied the language by {@link LanguageUtils}.
     *
     * @param locale The locale.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppliedLanguage(@NonNull Locale locale) {
        Locale appliedLocale = getAppliedLanguage();
        if (appliedLocale == null) {
            return false;
        }
        return isSameLocale(locale, appliedLocale);
    }

    /**
     * Return the applied locale.
     *
     * @return the applied locale
     */
    public static Locale getAppliedLanguage() {
        final String spLocaleStr = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(spLocaleStr) || VALUE_FOLLOW_SYSTEM.equals(spLocaleStr)) {
            return null;
        }
        return string2Locale(spLocaleStr);
    }

    /**
     * Return the locale of context.
     *
     * @return the locale of context
     */
    public static Locale getContextLanguage(Context context) {
        return getLocal(context.getResources().getConfiguration());
    }

    /**
     * Return the locale of applicationContext.
     *
     * @return the locale of applicationContext
     */
    public static Locale getAppContextLanguage() {
        return getContextLanguage(Utils.getApp());
    }

    /**
     * Return the locale of system
     *
     * @return the locale of system
     */
    public static Locale getSystemLanguage() {
        return getLocal(Resources.getSystem().getConfiguration());
    }

    /**
     * Update the locale of applicationContext.
     *
     * @param destLocale The dest locale.
     * @param consumer   The consumer.
     */
    public static void updateAppContextLanguage(@NonNull Locale destLocale, @Nullable Utils.Consumer<Boolean> consumer) {
        pollCheckAppContextLocal(destLocale, 0, consumer);
    }

    static void pollCheckAppContextLocal(final Locale destLocale, final int index, final Utils.Consumer<Boolean> consumer) {
        Resources appResources = Utils.getApp().getResources();
        Configuration appConfig = appResources.getConfiguration();
        Locale appLocal = getLocal(appConfig);

        setLocal(appConfig, destLocale);

        Utils.getApp().getResources().updateConfiguration(appConfig, appResources.getDisplayMetrics());

        if (consumer == null) return;

        if (isSameLocale(appLocal, destLocale)) {
            consumer.accept(true);
        } else {
            if (index < 20) {
                UtilsBridge.runOnUiThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pollCheckAppContextLocal(destLocale, index + 1, consumer);
                    }
                }, 16);
                return;
            }
            Log.e("LanguageUtils", "appLocal didn't update.");
            consumer.accept(false);
        }
    }

    /**
     * If applyLanguage not work, try to call it in {@link Activity#attachBaseContext(Context)}.
     *
     * @param context The baseContext.
     * @return the context with language
     */
    public static Context attachBaseContext(Context context) {
        String spLocaleStr = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(spLocaleStr) || VALUE_FOLLOW_SYSTEM.equals(spLocaleStr)) {
            return context;
        }

        Locale settingsLocale = string2Locale(spLocaleStr);
        if (settingsLocale == null) return context;

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();

        setLocal(config, settingsLocale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.createConfigurationContext(config);
        } else {
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            return context;
        }
    }

    static void applyLanguage(final Activity activity) {
        String spLocale = UtilsBridge.getSpUtils4Utils().getString(KEY_LOCALE);
        if (TextUtils.isEmpty(spLocale)) {
            return;
        }

        Locale destLocal;
        if (VALUE_FOLLOW_SYSTEM.equals(spLocale)) {
            destLocal = getLocal(Resources.getSystem().getConfiguration());
        } else {
            destLocal = string2Locale(spLocale);
        }

        if (destLocal == null) return;

        updateConfiguration(activity, destLocal);
        updateConfiguration(Utils.getApp(), destLocal);
    }

    private static void updateConfiguration(Context context, Locale destLocal) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        setLocal(config, destLocal);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private static String locale2String(Locale locale) {
        String localLanguage = locale.getLanguage(); // this may be empty
        String localCountry = locale.getCountry(); // this may be empty
        return localLanguage + "$" + localCountry;
    }

    private static Locale string2Locale(String str) {
        Locale locale = string2LocaleReal(str);
        if (locale == null) {
            Log.e("LanguageUtils", "The string of " + str + " is not in the correct format.");
            UtilsBridge.getSpUtils4Utils().remove(KEY_LOCALE);
        }
        return locale;
    }

    private static Locale string2LocaleReal(String str) {
        if (!isRightFormatLocalStr(str)) {
            return null;
        }

        try {
            int splitIndex = str.indexOf("$");
            return new Locale(str.substring(0, splitIndex), str.substring(splitIndex + 1));
        } catch (Exception ignore) {
            return null;
        }
    }

    private static boolean isRightFormatLocalStr(String localStr) {
        char[] chars = localStr.toCharArray();
        int count = 0;
        for (char c : chars) {
            if (c == '$') {
                if (count >= 1) {
                    return false;
                }
                ++count;
            }
        }
        return count == 1;
    }

    private static boolean isSameLocale(Locale l0, Locale l1) {
        return UtilsBridge.equals(l1.getLanguage(), l0.getLanguage())
                && UtilsBridge.equals(l1.getCountry(), l0.getCountry());
    }

    private static Locale getLocal(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return configuration.getLocales().get(0);
        } else {
            return configuration.locale;
        }
    }

    private static void setLocal(Configuration configuration, Locale locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
    }
}
