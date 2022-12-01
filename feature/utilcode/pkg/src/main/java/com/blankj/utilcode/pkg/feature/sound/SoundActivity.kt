package com.blankj.utilcode.pkg.feature.sound

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSeekBar
import com.blankj.common.item.CommonItemSwitch
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*

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

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        SoundUtils.getInstance().preload("ring", R.raw.ring)
        SoundUtils.getInstance().preload("ring2", R.raw.ring2)

    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
            startRingItemCLick(
                "Start Ring 1",
                "ring"
            ),
            volumeItemSeekBar("Adjust Ring 1 Volume", "ring"),
            rateItemSeekBar("Adjust Ring 1 Rate", "ring"),
            getLoopSwitch("Set Ring 1 Loop", "ring"),
            pauseRingItemCLick("Pause Ring 1", "ring"),
            resumeRingItemCLick("Resume Ring 1", "ring"),
            stopRingItemCLick("Stop Ring 1", "ring"),
            startRingItemCLick(
                "Start Ring 2",
                "ring2"
            ),
            volumeItemSeekBar("Adjust Ring 2 Volume", "ring2"),
            rateItemSeekBar("Adjust Ring 2 Rate", "ring2"),
            getLoopSwitch("Set Ring 2 Loop", "ring2"),
            pauseRingItemCLick("Pause Ring 2", "ring2"),
            resumeRingItemCLick("Resume Ring 2", "ring2"),
            stopRingItemCLick("Stop Ring 2", "ring2")
        )
    }

    private fun startRingItemCLick(
        title: CharSequence,
        resKey: String
    ): CommonItemClick {
        return CommonItemClick(title) {
            SoundUtils.getInstance().stop(resKey)
            SoundUtils.getInstance().play(resKey)
        }
    }

    private fun volumeItemSeekBar(title: CharSequence, resKey: String): CommonItemSeekBar {
        var curPos = 100
        return CommonItemSeekBar(
            title,
            100,
            object : CommonItemSeekBar.ProgressListener() {
                override fun getCurValue(): Int {
                    return curPos
                }

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    curPos = progress
                    SoundUtils.getInstance()
                        .setVolume(resKey, progress / 100f, progress / 100f)
                }
            })
    }

    private fun rateItemSeekBar(title: CharSequence, resKey: String): CommonItemSeekBar {
        var curPos = 100

        val itemSeekBar = CommonItemSeekBar(
            title,
            50,
            200,
            object : CommonItemSeekBar.ProgressListener() {
                override fun getCurValue(): Int {
                    return curPos
                }

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    curPos = progress
                    SoundUtils.getInstance()
                        .setRate(resKey, progress / 100f)
                }
            })
        itemSeekBar
        return itemSeekBar
    }


    private fun getLoopSwitch(title: CharSequence, resKey: String): CommonItemSwitch {
        var isChecked = false
        return CommonItemSwitch(
            title,
            {
                isChecked
            },
            {
                isChecked = it
                SoundUtils.getInstance().setLoop(resKey, isChecked)
            }
        )
    }


    private fun pauseRingItemCLick(title: CharSequence, resKey: String): CommonItemClick {
        return CommonItemClick(title) {
            SoundUtils.getInstance().pause(resKey)
        }
    }

    private fun resumeRingItemCLick(title: CharSequence, resKey: String): CommonItemClick {
        return CommonItemClick(title) {
            SoundUtils.getInstance().resume(resKey)
        }
    }

    private fun stopRingItemCLick(title: CharSequence, resKey: String): CommonItemClick {
        return CommonItemClick(title) {
            SoundUtils.getInstance().stop(resKey)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundUtils.getInstance().release()
    }
}
