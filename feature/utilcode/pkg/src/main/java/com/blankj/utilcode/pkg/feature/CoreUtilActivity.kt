package com.blankj.utilcode.pkg.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
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
import kotlinx.android.synthetic.main.activity_util_core.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  :
 * ```
 */
class CoreUtilActivity : CommonTitleActivity() {

    companion object {
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

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                coreUtilActivityBtn,
                coreUtilAdaptScreenBtn,
                coreUtilApiBtn,
                coreUtilAppBtn,
                coreUtilBarBtn,
                coreUtilBrightnessBtn,
                coreUtilBusBtn,
                coreUtilCleanBtn,
                coreUtilClickBtn,
                coreUtilCrashBtn,
                coreUtilDeviceBtn,
                coreUtilFlashlightBtn,
                coreUtilFragmentBtn,
                coreUtilImageBtn,
                coreUtilKeyboardBtn,
                coreUtilLanguageBtn,
                coreUtilLogBtn,
                coreUtilMessengerBtn,
                coreUtilMetaDataBtn,
                coreUtilNetworkBtn,
                coreUtilPathBtn,
                coreUtilPermissionBtn,
                coreUtilPhoneBtn,
                coreUtilProcessBtn,
                coreUtilReflectBtn,
                coreUtilResourceBtn,
                coreUtilRomBtn,
                coreUtilScreenBtn,
                coreUtilSdcardBtn,
                coreUtilSnackbarBtn,
                coreUtilSpStaticBtn,
                coreUtilSpanBtn,
                coreUtilToastBtn,
                coreUtilVibrateBtn
        )
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.coreUtilActivityBtn -> ActivityActivity.start(this)
            R.id.coreUtilAdaptScreenBtn -> AdaptScreenActivity.start(this)
            R.id.coreUtilApiBtn -> ApiActivity.start(this)
            R.id.coreUtilAppBtn -> AppActivity.start(this)
            R.id.coreUtilBarBtn -> BarActivity.start(this)
            R.id.coreUtilBrightnessBtn -> BrightnessActivity.start(this)
            R.id.coreUtilBusBtn -> BusActivity.start(this)
            R.id.coreUtilCleanBtn -> CleanActivity.start(this)
            R.id.coreUtilClickBtn -> ClickActivity.start(this)
            R.id.coreUtilCrashBtn -> throw NullPointerException("crash test")
            R.id.coreUtilDeviceBtn -> DeviceActivity.start(this)
            R.id.coreUtilFlashlightBtn -> FlashlightActivity.start(this)
            R.id.coreUtilFragmentBtn -> FragmentActivity.start(this)
            R.id.coreUtilImageBtn -> ImageActivity.start(this)
            R.id.coreUtilKeyboardBtn -> KeyboardActivity.start(this)
            R.id.coreUtilLanguageBtn -> LanguageActivity.start(this)
            R.id.coreUtilLogBtn -> LogActivity.start(this)
            R.id.coreUtilMessengerBtn -> MessengerActivity.start(this)
            R.id.coreUtilMetaDataBtn -> MetaDataActivity.start(this)
            R.id.coreUtilNetworkBtn -> NetworkActivity.start(this)
            R.id.coreUtilNetworkBtn -> NetworkActivity.start(this)
            R.id.coreUtilPathBtn -> PathActivity.start(this)
            R.id.coreUtilPermissionBtn -> PermissionActivity.start(this)
            R.id.coreUtilPhoneBtn -> PhoneActivity.start(this)
            R.id.coreUtilProcessBtn -> ProcessActivity.start(this)
            R.id.coreUtilReflectBtn -> ReflectActivity.start(this)
            R.id.coreUtilResourceBtn -> ResourceActivity.start(this)
            R.id.coreUtilRomBtn -> RomActivity.start(this)
            R.id.coreUtilScreenBtn -> ScreenActivity.start(this)
            R.id.coreUtilSdcardBtn -> SDCardActivity.start(this)
            R.id.coreUtilSnackbarBtn -> SnackbarActivity.start(this)
            R.id.coreUtilSpStaticBtn -> SPStaticActivity.start(this)
            R.id.coreUtilSpanBtn -> SpanActivity.start(this)
            R.id.coreUtilToastBtn -> ToastActivity.start(this)
            R.id.coreUtilVibrateBtn -> VibrateActivity.start(this)
        }
    }
}
