package com.blankj.subutil.pkg.feature.location

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.pkg.helper.PermissionHelper
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_location.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about LocationUtils
 * ```
 */
class LocationActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            PermissionHelper.requestLocation(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    val starter = Intent(context, LocationActivity::class.java)
                    context.startActivity(starter)
                }
            }, object : PermissionHelper.OnPermissionDeniedListener {
                override fun onPermissionDenied() {
                    start(context)
                }
            })
        }
    }

    private lateinit var mLocationService: LocationService

    private var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {}

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mLocationService = (service as LocationService.LocationBinder).service
            mLocationService.setOnGetLocationListener(object : LocationService.OnGetLocationListener {
                override fun getLocation(lastLatitude: String, lastLongitude: String, latitude: String,
                                         longitude: String, country: String, locality: String, street: String) {
                    runOnUiThread {
                        SpanUtils.with(locationAboutTv)
                                .appendLine("lastLatitude: $lastLatitude")
                                .appendLine("lastLongitude: $lastLongitude")
                                .appendLine("latitude: $latitude")
                                .appendLine("longitude: $longitude")
                                .appendLine("getCountryName: $country")
                                .appendLine("getLocality: $locality")
                                .appendLine("getStreet: $street")
                                .create()
                    }
                }
            })
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_location)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_location
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        SpanUtils.with(locationAboutTv)
                .appendLine("lastLatitude: unknown")
                .appendLine("lastLongitude: unknown")
                .appendLine("latitude: unknown")
                .appendLine("longitude: unknown")
                .appendLine("getCountryName: unknown")
                .appendLine("getLocality: unknown")
                .appendLine("getStreet: unknown")
                .create()
    }

    override fun doBusiness() {
        bindService(Intent(this, LocationService::class.java), conn, Context.BIND_AUTO_CREATE)
    }

    override fun onWidgetClick(view: View) {}

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }
}
