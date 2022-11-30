package com.blankj.utilcode.util;

import android.media.AudioManager;

import com.blankj.utilcode.R;

import org.junit.Test;

/**
 * @author leo
 * @version 1.0
 * @className SoundUtilsTest
 * @description TODO
 * @date 2022/11/30 11:15
 **/
public class SoundUtilsTest extends BaseTest {

    @Test
    public void play() {
        //前奏
        SoundUtils soundUtils = new SoundUtils.Builder()
                .setMaxStreams(8)
                .setStreamType(AudioManager.STREAM_RING)
                .build();



//        //1.用法1
//        SoundUtils.getInstance().preload("ring", R.raw.ring);
//        SoundUtils.getInstance().play("ring");
//
//        //2.用法2
//        soundUtils.preload("ring",R.raw.ring);
//        soundUtils.preload("ringback",R.raw.ring);
//
//        soundUtils.play("ring");
//
//
//        SoundUtils.getInstance().destroy();
//        soundUtils.destroy();
//
//
//        SoundUtils.play();

    }
}
