package com.blankj.utilcode.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import static android.Manifest.permission.VIBRATE;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : utils about vibrate
 * </pre>
 */
public final class VibrateUtils {

    private static Vibrator vibrator;

    private VibrateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param milliseconds The number of milliseconds to vibrate.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(milliseconds);
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param milliseconds The number of milliseconds to vibrate.
     * @param attributes   {@link AudioAttributes} corresponding to the vibration. For example,
     *                     specify {@link AudioAttributes#USAGE_ALARM} for alarm vibrations or
     *                     {@link AudioAttributes#USAGE_NOTIFICATION_RINGTONE} for
     *                     vibrations associated with incoming calls.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long milliseconds, AudioAttributes attributes) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(milliseconds, attributes);
    }

    /**
     * VibrateCompat - Can vibrate in background
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param milliseconds he number of milliseconds to vibrate.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrateCompat(final long milliseconds) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vibrate(milliseconds, getAudioAttributes());
        } else {
            vibrate(milliseconds);
        }
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param pattern An array of longs of times for which to turn the vibrator on or off.
     * @param repeat  The index into pattern at which to repeat, or -1 if you don't want to repeat.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param pattern    An array of longs of times for which to turn the vibrator on or off.
     * @param repeat     The index into pattern at which to repeat, or -1 if you don't want to repeat.
     * @param attributes {@link AudioAttributes} corresponding to the vibration. For example,
     *                   specify {@link AudioAttributes#USAGE_ALARM} for alarm vibrations or
     *                   {@link AudioAttributes#USAGE_NOTIFICATION_RINGTONE} for
     *                   vibrations associated with incoming calls.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat, AudioAttributes attributes) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(pattern, repeat, attributes);
    }

    /**
     * VibrateCompat - Can vibrate in background
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param pattern An array of longs of times for which to turn the vibrator on or off.
     * @param repeat  The index into pattern at which to repeat, or -1 if you don't want to repeat.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrateCompat(final long[] pattern, final int repeat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vibrate(pattern, repeat, getAudioAttributes());
        } else {
            vibrate(pattern, repeat);
        }
    }

    /**
     * Cancel vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     */
    @RequiresPermission(VIBRATE)
    public static void cancel() {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.cancel();
    }

    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) Utils.getApp().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static AudioAttributes getAudioAttributes() {
        return new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
    }
}
