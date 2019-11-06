package com.blankj.utilcode.pkg.feature.screen

import android.content.Context
import android.content.Intent
import android.os.Build
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/01/29
 * desc  : demo about RomUtils
 * ```
 */
class ScreenActivity : CommonActivity() {

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

    override fun bindTitleRes(): Int {
        return R.string.demo_screen
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("getScreenWidth", ScreenUtils.getScreenWidth().toString()),
                CommonItemTitle("getScreenHeight", ScreenUtils.getScreenHeight().toString()),
                CommonItemTitle("getAppScreenWidth", ScreenUtils.getAppScreenWidth().toString()),
                CommonItemTitle("getAppScreenHeight", ScreenUtils.getAppScreenHeight().toString()),
                CommonItemTitle("getScreenDensity", ScreenUtils.getScreenDensity().toString()),
                CommonItemTitle("getScreenDensityDpi", ScreenUtils.getScreenDensityDpi().toString()),
                CommonItemTitle("getScreenRotation", ScreenUtils.getScreenRotation(this).toString()),
                CommonItemTitle("isScreenLock", ScreenUtils.isScreenLock().toString()),
                CommonItemTitle("getSleepDuration", ScreenUtils.getSleepDuration().toString()),

                CommonItemSwitch(
                        "isFullScreen",
                        Utils.Func1 {
                            ScreenUtils.isFullScreen(this)
                        },
                        Utils.Func1 {
                            if (it) {
                                ScreenUtils.setFullScreen(this)
                                BarUtils.setStatusBarVisibility(this, false)
                            } else {
                                ScreenUtils.setNonFullScreen(this)
                                BarUtils.setStatusBarVisibility(this, true)
                            }
                        }
                ),
                CommonItemSwitch(
                        "isLandscape",
                        Utils.Func1 {
                            ScreenUtils.isLandscape()
                        },
                        Utils.Func1 {
                            if (it) {
                                ScreenUtils.setLandscape(this)
                            } else {
                                ScreenUtils.setPortrait(this)
                            }
                        }
                ),
                CommonItemClick(R.string.screen_screenshot) {
                    DialogHelper.showScreenshotDialog(ScreenUtils.screenShot(this))
                }
        )
    }
}
