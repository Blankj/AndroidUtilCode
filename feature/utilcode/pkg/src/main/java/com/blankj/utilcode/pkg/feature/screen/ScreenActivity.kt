package com.blankj.utilcode.pkg.feature.screen

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_screen.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/29
 * desc  : demo about RomUtils
 * ```
 */
class ScreenActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        val starter = Intent(context, ScreenActivity::class.java)
                        context.startActivity(starter)
                    }

                    override fun onDenied() {
                        ToastUtils.showLong("No permission of write settings.")
                    }
                })
            } else {
                val starter = Intent(context, ScreenActivity::class.java)
                context.startActivity(starter)
            }
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_screen)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_screen
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                screenFullscreenBtn,
                screenNonFullscreenBtn,
                screenToggleFullscreenBtn,
                screenLandscapeBtn,
                screenPortraitBtn,
                screenScreenshotBtn,
                screenSetSleepDurationBtn
        )
        updateAboutScreen();
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.screenFullscreenBtn -> {
                ScreenUtils.setFullScreen(this)
                BarUtils.setStatusBarVisibility(this, false)
            }
            R.id.screenNonFullscreenBtn -> {
                ScreenUtils.setNonFullScreen(this)
                BarUtils.setStatusBarVisibility(this, true)
            }
            R.id.screenToggleFullscreenBtn -> {
                ScreenUtils.toggleFullScreen(this)
                if (ScreenUtils.isFullScreen(this)) {
                    BarUtils.setStatusBarVisibility(this, false)
                } else {
                    BarUtils.setStatusBarVisibility(this, true)
                }
            }
            R.id.screenLandscapeBtn -> ScreenUtils.setLandscape(this)
            R.id.screenPortraitBtn -> ScreenUtils.setPortrait(this)
            R.id.screenScreenshotBtn -> DialogHelper.showScreenshotDialog(ScreenUtils.screenShot(this))
            R.id.screenSetSleepDurationBtn -> ScreenUtils.setSleepDuration(100000)
        }
        Utils.runOnUiThreadDelayed(object : Runnable {
            override fun run() {
                updateAboutScreen()
            }
        }, 36)
    }

    private fun updateAboutScreen() {
        SpanUtils.with(screenAboutTv)
                .appendLine("getScreenWidth: " + ScreenUtils.getScreenWidth())
                .appendLine("getScreenHeight: " + ScreenUtils.getScreenHeight())
                .appendLine("getAppScreenWidth: " + ScreenUtils.getAppScreenWidth())
                .appendLine("getAppScreenHeight: " + ScreenUtils.getAppScreenHeight())
                .appendLine("getScreenDensity: " + ScreenUtils.getScreenDensity())
                .appendLine("getScreenDensityDpi: " + ScreenUtils.getScreenDensityDpi())
                .appendLine("isFullScreen: " + ScreenUtils.isFullScreen(this))
                .appendLine("isLandscape: " + ScreenUtils.isLandscape())
                .appendLine("isPortrait: " + ScreenUtils.isPortrait())
                .appendLine("getScreenRotation: " + ScreenUtils.getScreenRotation(this))
                .appendLine("isScreenLock: " + ScreenUtils.isScreenLock())
                .append("getSleepDuration: " + ScreenUtils.getSleepDuration())
                .create()
    }
}
