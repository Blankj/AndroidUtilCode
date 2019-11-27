package com.blankj.utilcode.pkg.feature

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.activity.ActivityActivity
import com.blankj.utilcode.pkg.feature.adaptScreen.AdaptScreenActivity
import com.blankj.utilcode.pkg.feature.api.ApiActivity
import com.blankj.utilcode.pkg.feature.app.AppActivity
import com.blankj.utilcode.pkg.feature.bar.BarActivity
import com.blankj.utilcode.pkg.feature.brightness.BrightnessActivity
import com.blankj.utilcode.pkg.feature.bus.BusActivity
import com.blankj.utilcode.pkg.feature.clean.CleanActivity
import com.blankj.utilcode.pkg.feature.click.ClickActivity
import com.blankj.utilcode.pkg.feature.device.DeviceActivity
import com.blankj.utilcode.pkg.feature.flashlight.FlashlightActivity
import com.blankj.utilcode.pkg.feature.fragment.FragmentActivity
import com.blankj.utilcode.pkg.feature.image.ImageActivity
import com.blankj.utilcode.pkg.feature.keyboard.KeyboardActivity
import com.blankj.utilcode.pkg.feature.language.LanguageActivity
import com.blankj.utilcode.pkg.feature.log.LogActivity
import com.blankj.utilcode.pkg.feature.messenger.MessengerActivity
import com.blankj.utilcode.pkg.feature.metaData.MetaDataActivity
import com.blankj.utilcode.pkg.feature.mvp.MvpActivity
import com.blankj.utilcode.pkg.feature.network.NetworkActivity
import com.blankj.utilcode.pkg.feature.notification.NotificationActivity
import com.blankj.utilcode.pkg.feature.path.PathActivity
import com.blankj.utilcode.pkg.feature.permission.PermissionActivity
import com.blankj.utilcode.pkg.feature.phone.PhoneActivity
import com.blankj.utilcode.pkg.feature.process.ProcessActivity
import com.blankj.utilcode.pkg.feature.reflect.ReflectActivity
import com.blankj.utilcode.pkg.feature.resource.ResourceActivity
import com.blankj.utilcode.pkg.feature.rom.RomActivity
import com.blankj.utilcode.pkg.feature.screen.ScreenActivity
import com.blankj.utilcode.pkg.feature.sdcard.SDCardActivity
import com.blankj.utilcode.pkg.feature.shadow.ShadowActivity
import com.blankj.utilcode.pkg.feature.snackbar.SnackbarActivity
import com.blankj.utilcode.pkg.feature.spStatic.SPStaticActivity
import com.blankj.utilcode.pkg.feature.span.SpanActivity
import com.blankj.utilcode.pkg.feature.toast.ToastActivity
import com.blankj.utilcode.pkg.feature.vibrate.VibrateActivity
import com.blankj.utilcode.util.CollectionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  :
 * ```
 */
class CoreUtilActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CoreUtilActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.core_util
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick(R.string.demo_activity, true) {
                    ActivityActivity.start(this)
                },
                CommonItemClick(R.string.demo_adapt_screen, true) {
                    AdaptScreenActivity.start(this)
                },
                CommonItemClick(R.string.demo_api, true) {
                    ApiActivity.start(this)
                },
                CommonItemClick(R.string.demo_app, true) {
                    AppActivity.start(this)
                },
                CommonItemClick(R.string.demo_bar, true) {
                    BarActivity.start(this)
                },
                CommonItemClick(R.string.demo_brightness, true) {
                    BrightnessActivity.start(this)
                },
                CommonItemClick(R.string.demo_bus, true) {
                    BusActivity.start(this)
                },
                CommonItemClick(R.string.demo_clean, true) {
                    CleanActivity.start(this)
                },
                CommonItemClick(R.string.demo_click, true) {
                    ClickActivity.start(this)
                },
                CommonItemClick(R.string.demo_crash) {
                    throw NullPointerException("crash test")
                },
                CommonItemClick(R.string.demo_device, true) {
                    DeviceActivity.start(this)
                },
                CommonItemClick(R.string.demo_flashlight, true) {
                    FlashlightActivity.start(this)
                },
                CommonItemClick(R.string.demo_fragment, true) {
                    FragmentActivity.start(this)
                },
                CommonItemClick(R.string.demo_image, true) {
                    ImageActivity.start(this)
                },
                CommonItemClick(R.string.demo_keyboard, true) {
                    KeyboardActivity.start(this)
                },
                CommonItemClick(R.string.demo_language, true) {
                    LanguageActivity.start(this)
                },
                CommonItemClick(R.string.demo_log, true) {
                    LogActivity.start(this)
                },
                CommonItemClick(R.string.demo_messenger, true) {
                    MessengerActivity.start(this)
                },
                CommonItemClick(R.string.demo_meta_data, true) {
                    MetaDataActivity.start(this)
                },
                CommonItemClick(R.string.demo_mvp, true) {
                    MvpActivity.start(this)
                },
                CommonItemClick(R.string.demo_network, true) {
                    NetworkActivity.start(this)
                },
                CommonItemClick(R.string.demo_notification, true) {
                    NotificationActivity.start(this)
                },
                CommonItemClick(R.string.demo_path, true) {
                    PathActivity.start(this)
                },
                CommonItemClick(R.string.demo_permission, true) {
                    PermissionActivity.start(this)
                },
                CommonItemClick(R.string.demo_phone, true) {
                    PhoneActivity.start(this)
                },
                CommonItemClick(R.string.demo_process, true) {
                    ProcessActivity.start(this)
                },
                CommonItemClick(R.string.demo_reflect, true) {
                    ReflectActivity.start(this)
                },
                CommonItemClick(R.string.demo_resource, true) {
                    ResourceActivity.start(this)
                },
                CommonItemClick(R.string.demo_rom, true) {
                    RomActivity.start(this)
                },
                CommonItemClick(R.string.demo_screen, true) {
                    ScreenActivity.start(this)
                },
                CommonItemClick(R.string.demo_sdcard, true) {
                    SDCardActivity.start(this)
                },
                CommonItemClick(R.string.demo_shadow, true) {
                    ShadowActivity.start(this)
                },
                CommonItemClick(R.string.demo_snackbar, true) {
                    SnackbarActivity.start(this)
                },
                CommonItemClick(R.string.demo_spStatic, true) {
                    SPStaticActivity.start(this)
                },
                CommonItemClick(R.string.demo_span, true) {
                    SpanActivity.start(this)
                },
                CommonItemClick(R.string.demo_toast, true) {
                    ToastActivity.start(this)
                },
                CommonItemClick(R.string.demo_vibrate, true) {
                    VibrateActivity.start(this)
                }
        )
    }
}
