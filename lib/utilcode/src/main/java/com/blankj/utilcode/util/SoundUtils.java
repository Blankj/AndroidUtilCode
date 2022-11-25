package com.blankj.utilcode.util;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RawRes;

import java.io.IOException;

/**
 * @author leo
 * @version 1.0
 * @className SoundUtils
 * @description TODO
 * @date 2022/11/24 15:21
 **/
public class SoundUtils {


    private static MediaPlayer mediaPlayer = null;

    private SoundUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static void play(@RawRes int id) {
        play(id, 1f, 1f);
    }

    public static void play(@RawRes int id, float leftVolume, float rightVolume) {
        if (mediaPlayer == null) {
            mediaPlayer = buildMediaPlayer(id, leftVolume, rightVolume);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
                mediaPlayer.setVolume(1f, 1f);
            });
        }

    }


    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private static MediaPlayer buildMediaPlayer(@RawRes int id, float leftVolume, float rightVolume) {
        MediaPlayer player = null;
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                player = new MediaPlayer();

                AssetFileDescriptor fileDescriptor = Utils.getApp().getResources().openRawResourceFd(id);
                player.setDataSource(fileDescriptor);
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setLooping(true);
                player.setVolume(leftVolume, rightVolume);
                player.prepare();
            } else {
                player = MediaPlayer.create(Utils.getApp(), id);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return player;
    }


}
