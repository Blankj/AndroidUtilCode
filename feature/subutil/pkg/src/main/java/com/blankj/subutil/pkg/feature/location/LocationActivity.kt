package com.blankj.subutil.pkg.feature.location

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.subutil.pkg.R
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.PermissionUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about LocationUtils
 * ```
 */
class LocationActivity : CommonActivity() {

    private var lastLatitude: String = "unknown"
    private var lastLongitude: String = "unknown"
    private var latitude: String = "unknown"
    private var longitude: String = "unknown"
    private var country: String = "unknown"
    private var locality: String = "unknown"
    private var street: String = "unknown"

    companion object {
        fun start(context: Context) {
            PermissionHelper.request(context, object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val starter = Intent(context, LocationActivity::class.java)
                    context.startActivity(starter)
                }

                override fun onDenied() {
                }
            }, PermissionConstants.LOCATION)
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
                    this@LocationActivity.apply {
                        this.lastLatitude = lastLatitude
                        this.lastLongitude = lastLongitude
                        this.latitude = latitude
                        this.longitude = longitude
                        this.country = country
                        this.locality = locality
                        this.street = street
                    }
                    runOnUiThread {
                        itemsView.updateItems(bindItems())
                    }
                }
            })
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_location
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("lastLatitude", lastLatitude),
                CommonItemTitle("lastLongitude", lastLongitude),
                CommonItemTitle("latitude", latitude),
                CommonItemTitle("longitude", longitude),
                CommonItemTitle("getCountryName", country),
                CommonItemTitle("getLocality", locality),
                CommonItemTitle("getStreet", street)
        )
    }

    override fun doBusiness() {
        bindService(Intent(this, LocationService::class.java), conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }
}
