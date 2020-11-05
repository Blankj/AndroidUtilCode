package com.blankj.utilcode.util;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/09/08
 *     desc  : utils about volume
 * </pre>
 */
public class VolumeUtils {

    /**
     * Return the volume.
     *
     * @param streamType The stream type.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @return the volume
     */
    public static int getVolume(int streamType) {
        AudioManager am = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        //noinspection ConstantConditions
        return am.getStreamVolume(streamType);
    }

    /**
     * Sets media volume.<br>
     * When setting the value of parameter 'volume' greater than the maximum value of the media volume will not either cause error or throw exception but maximize the media volume.<br>
     * Setting the value of volume lower than 0 will minimize the media volume.
     *
     * @param streamType The stream type.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @param volume     The volume.
     * @param flags      The flags.
     *                   <ul>
     *                   <li>{@link AudioManager#FLAG_SHOW_UI}</li>
     *                   <li>{@link AudioManager#FLAG_ALLOW_RINGER_MODES}</li>
     *                   <li>{@link AudioManager#FLAG_PLAY_SOUND}</li>
     *                   <li>{@link AudioManager#FLAG_REMOVE_SOUND_AND_VIBRATE}</li>
     *                   <li>{@link AudioManager#FLAG_VIBRATE}</li>
     *                   </ul>
     */
    public static void setVolume(int streamType, int volume, int flags) {
        AudioManager am = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        try {
            //noinspection ConstantConditions
            am.setStreamVolume(streamType, volume, flags);
        } catch (SecurityException ignore) {
        }
    }

    /**
     * Return the maximum volume.
     *
     * @param streamType The stream type.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @return the maximum volume
     */
    public static int getMaxVolume(int streamType) {
        AudioManager am = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        //noinspection ConstantConditions
        return am.getStreamMaxVolume(streamType);
    }

    /**
     * Return the minimum volume.
     *
     * @param streamType The stream type.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @return the minimum volume
     */
    public static int getMinVolume(int streamType) {
        AudioManager am = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //noinspection ConstantConditions
            return am.getStreamMinVolume(streamType);
        }
        return 0;
    }
}
