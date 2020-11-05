package com.blankj.utilcode.pkg.feature.volume

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.VolumeUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/12/29
 * desc  : demo about VibrateUtils
 * ```
 */
class VolumeActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, VolumeActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_volume
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                getItemSeekBar("Voice Call", AudioManager.STREAM_VOICE_CALL),
                getItemSeekBar("System", AudioManager.STREAM_SYSTEM),
                getItemSeekBar("Music", AudioManager.STREAM_MUSIC),
                getItemSeekBar("Ring", AudioManager.STREAM_RING),
                getItemSeekBar("Alarm", AudioManager.STREAM_ALARM),
                getItemSeekBar("Notification", AudioManager.STREAM_NOTIFICATION),
                getItemSeekBar("Dtmf", AudioManager.STREAM_DTMF)
        )
    }

    private fun getItemSeekBar(title: CharSequence, streamType: Int): CommonItemSeekBar {
        return CommonItemSeekBar(title, VolumeUtils.getMaxVolume(streamType), object : CommonItemSeekBar.ProgressListener() {
            override fun getCurValue(): Int {
                return VolumeUtils.getVolume(streamType)
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                VolumeUtils.setVolume(streamType, progress,  AudioManager.FLAG_SHOW_UI)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        itemsView.updateItems(bindItems())
    }
}
