package com.blankj.utilcode.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.RawRes;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.R;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author leo
 * @version 1.0
 * @className SoundUtils
 * @description TODO
 * @date 2022/11/24 15:21
 **/
public class SoundUtils {

    private int maxStreams = 5;
    private int streamType = AudioManager.STREAM_MUSIC;
    private int contentType = AudioAttributes.CONTENT_TYPE_UNKNOWN;
    private int flags;
    private boolean muted;
    private int capturePolicy = AudioAttributes.ALLOW_CAPTURE_BY_NONE;
    private int usage;

    private HashMap<String, Integer> soundIdMap;
    private HashMap<String, Integer> streamIdMap;

    private static SoundUtils soundUtils;

    private SoundPool soundPool;

    public static SoundUtils getInstance() {
        synchronized (SoundUtils.class) {
            if (soundUtils == null) {
                soundUtils = new Builder()
                        .setStreamType(AudioManager.STREAM_MUSIC)
                        .setMaxStreams(5)
                        .build();
            }
        }
        return soundUtils;
    }


    private SoundUtils() {
        soundIdMap = new HashMap<>();
        streamIdMap = new HashMap<>();
        create();
    }


    private void setMaxStreams(int maxStreams) {
        this.maxStreams = maxStreams;
    }

    private void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    private void setContentType(int contentType) {
        this.contentType = contentType;
    }

    private void setFlags(int flags) {
        this.flags = flags;
    }

    private void setHapticChannelsMuted(boolean muted) {
        this.muted = muted;
    }

    private void setAllowedCapturePolicy(int capturePolicy) {
        this.capturePolicy = capturePolicy;
    }

    private void setUsage(int usage) {
        this.usage = usage;
    }

    public static class Builder {

        private int maxStreams = 5;
        private int streamType = AudioManager.STREAM_MUSIC;
        private int contentType = AudioAttributes.CONTENT_TYPE_UNKNOWN;
        private int flags;
        private boolean muted;
        private int capturePolicy = AudioAttributes.ALLOW_CAPTURE_BY_NONE;
        private int usage;


        public Builder setMaxStreams(int maxStreams) {
            this.maxStreams = maxStreams;
            return this;
        }

        public Builder setStreamType(int streamType) {
            this.streamType = streamType;
            return this;
        }

        public Builder setContentType(int contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder setHapticChannelsMuted(boolean muted) {
            this.muted = muted;
            return this;
        }

        public Builder setAllowedCapturePolicy(int capturePolicy) {
            this.capturePolicy = capturePolicy;
            return this;
        }

        public Builder setUsage(int usage) {
            this.usage = usage;
            return this;
        }

        public SoundUtils build() {
            SoundUtils soundUtils = new SoundUtils();
            soundUtils.setFlags(flags);
            soundUtils.setContentType(contentType);
            soundUtils.setMaxStreams(maxStreams);
            soundUtils.setHapticChannelsMuted(muted);
            soundUtils.setStreamType(streamType);
            soundUtils.setAllowedCapturePolicy(capturePolicy);
            soundUtils.setUsage(usage);
            return soundUtils;
        }
    }

    private void create() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                attributes = new AudioAttributes.Builder()
                        .setLegacyStreamType(streamType)
                        .setContentType(contentType)
                        .setFlags(flags)
                        .setHapticChannelsMuted(muted)
                        .setUsage(usage)
                        .setAllowedCapturePolicy(capturePolicy)
                        .build();
            } else {
                attributes = new AudioAttributes.Builder()
                        .setLegacyStreamType(streamType)
                        .setContentType(contentType)
                        .setFlags(flags)
                        .setUsage(usage)
                        .build();
            }
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, streamType, 0);
        }
    }

    public void preload(String key, @RawRes int resId) {
        preload(key, resId, null);
    }

    public void preload(String key, @RawRes int resId, SoundPool.OnLoadCompleteListener listener) {

        if (soundIdMap.size() > maxStreams) {
            throw new IllegalArgumentException("u cannot preload raw count > maxStreams...");
        }

        int soundID = soundPool.load(Utils.getApp(), resId, 1);
        if (listener != null) {
            soundPool.setOnLoadCompleteListener(listener);
        }
        soundIdMap.put(key, soundID);
    }

    public void preload(String key, String path) {
        preload(key, path, null);
    }

    public void preload(String key, String path, SoundPool.OnLoadCompleteListener listener) {

        if (soundIdMap.size() > maxStreams) {
            throw new IllegalArgumentException("u cannot preload raw count > maxStreams...");
        }

        int soundID = soundPool.load(path, 1);
        if (listener != null) {
            soundPool.setOnLoadCompleteListener(listener);
        }
        soundIdMap.put(key, soundID);
    }

    public void preload(String key, AssetFileDescriptor afd) {
        preload(key, afd, null);
    }

    public void preload(String key, AssetFileDescriptor afd, SoundPool.OnLoadCompleteListener listener) {

        if (soundIdMap.size() > maxStreams) {
            throw new IllegalArgumentException("u cannot preload raw count > maxStreams...");
        }

        int soundID = soundPool.load(afd, 1);
        if (listener != null) {
            soundPool.setOnLoadCompleteListener(listener);
        }
        soundIdMap.put(key, soundID);
    }

    public void preload(String key, FileDescriptor fd, long offset, long length) {
        preload(key, fd, offset, length, null);
    }

    public void preload(String key, FileDescriptor fd, long offset, long length, SoundPool.OnLoadCompleteListener listener) {

        if (soundIdMap.size() > maxStreams) {
            throw new IllegalArgumentException("u cannot preload raw count > maxStreams...");
        }

        int soundID = soundPool.load(fd, offset, length, 1);
        if (listener != null) {
            soundPool.setOnLoadCompleteListener(listener);
        }
        soundIdMap.put(key, soundID);
    }


    public void unload(String key) {
        Integer soundId = soundIdMap.get(key);
        if (soundId != null) {
            soundPool.unload(soundId);
        }
        soundIdMap.remove(key);
    }


    /**
     * @param: key
     * @param: leftVolume  left volume value (range = 0.0 to 1.0)
     * @param: rightVolume right volume value (range = 0.0 to 1.0)
     * @param: priority    tream priority (0 = lowest priority)
     * @param: loop        loop mode (0 = no loop, -1 = loop forever)
     * @param: rate        playback rate (1.0 = normal playback, range 0.5 to 2.0)
     * @description: TODO
     * @return: void
     * @author: leo
     * @date: 2022/12/1 9:52
     */
    public void play(String key, float leftVolume, float rightVolume, int priority, int loop, float rate) {
        if (soundPool != null) {
            Integer resId = soundIdMap.get(key);
            if (resId != null) {
                int streamID = soundPool.play(resId, leftVolume, rightVolume, priority, loop, rate);
                streamIdMap.put(key, streamID);
            } else {
                throw new IllegalStateException("i have not find this " + key + " raw!");
            }
        }
    }

    public void play(String key, int priority, int loop, float rate) {
        play(key, 1, 1, priority, loop, rate);
    }

    public void play(String key, int loop, float rate) {
        play(key, 1, 1, 1, loop, rate);
    }

    public void play(String key) {
        play(key, 1, 1, 1, 0, 1);
    }

    public void play(String key, boolean loop) {
        play(key, loop ? -1 : 0, 1);
    }

    public void play(String key, float rate) {
        play(key, 0, rate);
    }


    public void stop(String key) {
        Integer streamID = streamIdMap.get(key);
        if (streamID != null) {
            soundPool.stop(streamID);
        }
    }

    public void resume(String key) {
        Integer streamId = streamIdMap.get(key);
        if (streamId != null) {
            soundPool.resume(streamId);
        }
    }

    public void pause(String key) {
        Integer streamId = streamIdMap.get(key);
        if (streamId != null) {
            soundPool.pause(streamId);
        }
    }

    /**
     * @param: key
     * @param: loop   loop mode (0 = no loop, -1 = loop forever)
     * @description: TODO
     * @return: void
     * @author: leo
     * @date: 2022/12/1 10:24
     */
    public void setLoop(String key, int loop) {
        if (soundPool != null) {
            soundIdMap.get(key);
            Integer streamId = streamIdMap.get(key);
            if (streamId != null) {
                soundPool.pause(streamId);
                soundPool.setLoop(streamId, loop);
                soundPool.resume(streamId);
            }
        }
    }

    public void setLoop(String key, boolean loop) {
        if (loop) {
            setLoop(key, -1);
        } else {
            setLoop(key, 0);
        }
    }

    /**
     * @param: key
     * @param: leftVolume left volume value (range = 0.0 to 1.0)
     * @param: rightVolume right volume value (range = 0.0 to 1.0)
     * @description: TODO
     * @return: void
     * @author: leo
     * @date: 2022/12/1 10:19
     */
    public void setVolume(String key, float leftVolume, float rightVolume) {
        if (soundPool != null) {
            Integer streamID = streamIdMap.get(key);
            if (streamID != null) {
                soundPool.setVolume(streamID, leftVolume, rightVolume);
            }
        }
    }

    /**
     * @param: key
     * @param: rate     rate – playback rate (1.0 = normal playback, range 0.5 to 2.0)
     * @description: TODO
     * @return: void
     * @author: leo
     * @date: 2022/12/1 10:26
     */
    public void setRate(String key, float rate) {
        if (soundPool != null) {
            Integer streamID = streamIdMap.get(key);
            if (streamID != null) {
                soundPool.setRate(streamID, rate);
            }
        }
    }


    public void release() {
        Iterator<String> iterator = soundIdMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            stop(key);
            Integer soundId = soundIdMap.get(key);
            if (soundId != null) {
                soundPool.unload(soundId);
            }
            iterator.remove();
        }
        streamIdMap.clear();
        if (soundPool != null) {
            soundPool.release();
            soundUtils = null;
        }
    }
}
