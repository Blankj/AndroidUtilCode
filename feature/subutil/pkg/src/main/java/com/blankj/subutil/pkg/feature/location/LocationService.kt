package com.blankj.subutil.pkg.feature.location

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.Looper

import com.blankj.subutil.util.LocationUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/11/21
 * desc  : demo about LocationUtils
 * ```
 */
class LocationService : Service() {

    private var isSuccess: Boolean = false
    private var lastLatitude = "loading..."
    private var lastLongitude = "loading..."
    private var latitude = "loading..."
    private var longitude = "loading..."
    private var country = "loading..."
    private var locality = "loading..."
    private var street = "loading..."

    private var mOnGetLocationListener: OnGetLocationListener? = null

    private val mOnLocationChangeListener = object : LocationUtils.OnLocationChangeListener {
        override fun getLastKnownLocation(location: Location) {
            lastLatitude = location.latitude.toString()
            lastLongitude = location.longitude.toString()
            mOnGetLocationListener?.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street)
        }

        override fun onLocationChanged(location: Location) {
            latitude = location.latitude.toString()
            longitude = location.longitude.toString()
            mOnGetLocationListener?.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street)
            country = LocationUtils.getCountryName(java.lang.Double.parseDouble(latitude), java.lang.Double.parseDouble(longitude))
            locality = LocationUtils.getLocality(java.lang.Double.parseDouble(latitude), java.lang.Double.parseDouble(longitude))
            street = LocationUtils.getStreet(java.lang.Double.parseDouble(latitude), java.lang.Double.parseDouble(longitude))
            mOnGetLocationListener?.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

    fun setOnGetLocationListener(onGetLocationListener: OnGetLocationListener) {
        mOnGetLocationListener = onGetLocationListener
    }

    override fun onCreate() {
        super.onCreate()
        Thread(Runnable {
            Looper.prepare()
            isSuccess = LocationUtils.register(0, 0, mOnLocationChangeListener)
            if (isSuccess) ToastUtils.showShort("init success")
            Looper.loop()
        }).start()
    }

    override fun onBind(intent: Intent): IBinder? {
        return LocationBinder()
    }

    inner class LocationBinder : Binder() {
        val service: LocationService
            get() = this@LocationService
    }

    override fun onDestroy() {
        LocationUtils.unregister()
        // 一定要制空，否则内存泄漏
        mOnGetLocationListener = null
        super.onDestroy()
    }

    /**
     * 获取位置监听器
     */
    interface OnGetLocationListener {
        fun getLocation(
                lastLatitude: String, lastLongitude: String,
                latitude: String, longitude: String,
                country: String, locality: String, street: String
        )
    }
}
