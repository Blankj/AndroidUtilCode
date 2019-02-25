package com.blankj.utilcode.pkg.feature.screen

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_screen.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/29
 * desc  : demo about RomUtils
 * ```
 */
class ScreenActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.requestWriteSettings(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        val starter = Intent(context, ScreenActivity::class.java)
                        context.startActivity(starter)
                    }

                    override fun onDenied() {
                        start(context)
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

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        screenFullscreenBtn.setOnClickListener(this);
        screenNonFullscreenBtn.setOnClickListener(this);
        screenToggleFullscreenBtn.setOnClickListener(this);
        screenLandscapeBtn.setOnClickListener(this);
        screenPortraitBtn.setOnClickListener(this);
        screenScreenshotBtn.setOnClickListener(this);
        screenSetSleepDurationBtn.setOnClickListener(this);
        updateAboutScreen();
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.screenFullscreenBtn -> ScreenUtils.setFullScreen(this)
            R.id.screenNonFullscreenBtn -> ScreenUtils.setNonFullScreen(this)
            R.id.screenToggleFullscreenBtn -> ScreenUtils.toggleFullScreen(this)
            R.id.screenLandscapeBtn -> ScreenUtils.setLandscape(this)
            R.id.screenPortraitBtn -> ScreenUtils.setPortrait(this)
            R.id.screenScreenshotBtn -> screenScreenshotIv.setImageBitmap(ScreenUtils.screenShot(this))
            R.id.screenSetSleepDurationBtn -> ScreenUtils.setSleepDuration(100000)
        }
        updateAboutScreen()
    }

    private fun updateAboutScreen() {
        SpanUtils.with(screenAboutTv)
                .appendLine("getScreenWidth: " + ScreenUtils.getScreenWidth())
                .appendLine("getScreenHeight: " + ScreenUtils.getScreenHeight())
                .appendLine("isLandscape: " + ScreenUtils.isLandscape())
                .appendLine("isPortrait: " + ScreenUtils.isPortrait())
                .appendLine("getScreenRotation: " + ScreenUtils.getScreenRotation(this))
                .appendLine("isScreenLock: " + ScreenUtils.isScreenLock())
                .appendLine("getSleepDuration: " + ScreenUtils.getSleepDuration())
                .append("isTablet: " + ScreenUtils.isTablet())
                .create()
    }
}
