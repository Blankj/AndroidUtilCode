package com.blankj.utilcode.pkg.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.activity.ActivityActivity
import com.blankj.utilcode.pkg.feature.adaptScreen.AdaptScreenActivity
import com.blankj.utilcode.pkg.feature.app.AppActivity
import com.blankj.utilcode.pkg.feature.bar.BarActivity
import com.blankj.utilcode.pkg.feature.brightness.BrightnessActivity
import com.blankj.utilcode.pkg.feature.clean.CleanActivity
import com.blankj.utilcode.pkg.feature.device.DeviceActivity
import com.blankj.utilcode.pkg.feature.flashlight.FlashlightActivity
import com.blankj.utilcode.pkg.feature.fragment.FragmentActivity
import com.blankj.utilcode.pkg.feature.image.ImageActivity
import com.blankj.utilcode.pkg.feature.keyboard.KeyboardActivity
import com.blankj.utilcode.pkg.feature.log.LogActivity
import com.blankj.utilcode.pkg.feature.metaData.MetaDataActivity
import com.blankj.utilcode.pkg.feature.network.NetworkActivity
import com.blankj.utilcode.pkg.feature.path.PathActivity
import com.blankj.utilcode.pkg.feature.permission.PermissionActivity
import com.blankj.utilcode.pkg.feature.phone.PhoneActivity
import com.blankj.utilcode.pkg.feature.process.ProcessActivity
import com.blankj.utilcode.pkg.feature.reflect.ReflectActivity
import com.blankj.utilcode.pkg.feature.resource.ResourceActivity
import com.blankj.utilcode.pkg.feature.rom.RomActivity
import com.blankj.utilcode.pkg.feature.screen.ScreenActivity
import com.blankj.utilcode.pkg.feature.sdcard.SDCardActivity
import com.blankj.utilcode.pkg.feature.snackbar.SnackbarActivity
import com.blankj.utilcode.pkg.feature.spStatic.SPStaticActivity
import com.blankj.utilcode.pkg.feature.span.SpanActivity
import com.blankj.utilcode.pkg.feature.toast.ToastActivity
import com.blankj.utilcode.pkg.feature.vibrate.VibrateActivity
import com.blankj.utilcode.util.BusUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  :
 * ```
 */
class CoreUtilActivity : BaseTitleBarActivity() {

    companion object {
        @BusUtils.Subscribe(name = "CoreUtilActivity#start")
        fun start(context: Context) {
            val starter = Intent(context, CoreUtilActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.core_util)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_util_core
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {}

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    fun activityClick(view: View) {
        ActivityActivity.start(this)
    }

    fun adaptScreenClick(view: View) {
        AdaptScreenActivity.start(this)
    }

    fun appClick(view: View) {
        AppActivity.start(this)
    }

    fun barClick(view: View) {
        BarActivity.start(this)
    }

    fun brightnessClick(view: View) {
        BrightnessActivity.start(this)
    }

    fun cleanClick(view: View) {
        CleanActivity.start(this)
    }

    fun crashClick(view: View) {
        throw NullPointerException("crash test")
    }

    fun deviceClick(view: View) {
        DeviceActivity.start(this)
    }

    fun flashlightClick(view: View) {
        FlashlightActivity.start(this)
    }

    fun fragmentClick(view: View) {
        FragmentActivity.start(this)
    }

    fun imageClick(view: View) {
        ImageActivity.start(this)
    }

    fun keyboardClick(view: View) {
        KeyboardActivity.start(this)
    }

    fun logClick(view: View) {
        LogActivity.start(this)
    }

    fun metaDataClick(view: View) {
        MetaDataActivity.start(this)
    }

    fun networkClick(view: View) {
        NetworkActivity.start(this)
    }

    fun pathClick(view: View) {
        PathActivity.start(this)
    }

    fun permissionClick(view: View) {
        PermissionActivity.start(this)
    }

    fun phoneClick(view: View) {
        PhoneActivity.start(this)
    }

    fun processClick(view: View) {
        ProcessActivity.start(this)
    }

    fun reflectClick(view: View) {
        ReflectActivity.start(this)
    }

    fun resourceClick(view: View) {
        ResourceActivity.start(this)
    }

    fun romClick(view: View) {
        RomActivity.start(this)
    }

    fun screenClick(view: View) {
        ScreenActivity.start(this)
    }

    fun sdcardClick(view: View) {
        SDCardActivity.start(this)
    }

    fun snackbarClick(view: View) {
        SnackbarActivity.start(this)
    }

    fun spStaticClick(view: View) {
        SPStaticActivity.start(this)
    }

    fun spannableClick(view: View) {
        SpanActivity.start(this)
    }

    fun toastClick(view: View) {
        ToastActivity.start(this)
    }

    fun vibrateClick(view: View) {
        VibrateActivity.start(this)
    }
}
