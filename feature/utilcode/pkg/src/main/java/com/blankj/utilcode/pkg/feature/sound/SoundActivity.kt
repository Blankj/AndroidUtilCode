package com.blankj.utilcode.pkg.feature.sound

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.SoundUtils
import com.blankj.utilcode.util.VibrateUtils
import com.blankj.utilcode.util.VolumeUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/12/29
 * desc  : demo about VibrateUtils
 * ```
 */
class SoundActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SoundActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_sound
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
            startRingItemCLick(
                "Start Ring 1, V=0.3, R=0.5, L=false",
                R.raw.ring,
                0.3f,
                0.5f,
                false
            ),
            startRingItemCLick(
                "Start Ring 1, V=1, R=1, L=true",
                R.raw.ring,
                1f,
                1f,
                true
            ),
            startRingItemCLick(
                "Start Ring 1, V=0.3, R=2, L=false",
                R.raw.ring,
                0.6f,
                2f,
                false
            ),
            stopRingItemCLick("Stop Ring 1", R.raw.ring),
            startRingItemCLick(
                "Start Ring 2, V=0.3, R=0.5, L=false",
                R.raw.ring2,
                0.3f,
                0.5f,
                false
            ),
            startRingItemCLick(
                "Start Ring 2, V=1, R=1, L=false",
                R.raw.ring2,
                1f,
                1f,
                false
            ),
            startRingItemCLick(
                "Start Ring 2, V=0.3, R=2, L=true",
                R.raw.ring2,
                0.6f,
                2f,
                true
            ),
            stopRingItemCLick("Stop Ring 2", R.raw.ring2)
        )
    }

    private fun startRingItemCLick(
        title: CharSequence,
        resId: Int,
        volume: Float,
        rate: Float,
        loop: Boolean
    ): CommonItemClick {
        return CommonItemClick(title) {
            SoundUtils.getInstance().stop(resId)
            SoundUtils.getInstance().play(resId)
            SoundUtils.getInstance().setVolume(resId, volume, volume)
            SoundUtils.getInstance().setRate(resId, rate)
            SoundUtils.getInstance().setLoop(resId, loop)
        }
    }

    private fun stopRingItemCLick(title: CharSequence, resId: Int): CommonItemClick {
        return CommonItemClick(title) {
            SoundUtils.getInstance().stop(resId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundUtils.getInstance().release()
    }
}
