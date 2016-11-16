package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;

import java.util.HashMap;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/11/13
 *     desc  : 定位相关工具类
 * </pre>
 */
public class LocationUtils {

    private volatile static LocationUtils uniqueInstance;

    private OnLocationChangeListener mListener;

    private MyLocationListener myLocationListener;

    private LocationManager mLocationManager;

    private Context mContext;

    private LocationUtils(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener();
    }

    /**
     * 获取单例
     *
     * @param context 上下文
     * @return LocationUtils对象
     */
    public static LocationUtils getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LocationUtils(context);
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * 判断Gps是否打开
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isGpsEnabled() {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 打开Gps设置界面
     */
    public void openGpsSettings() {
        mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

//    http://blog.csdn.net/xiong_it/article/details/46968477

    /**
     * 初始化位置信息
     *
     * @param minTime     最小刷新时间（单位：毫秒）
     * @param minDistance 最小刷新距离（单位：米）
     * @param listener    位置刷新的回调接口
     */
    public void init(long minTime, long minDistance, OnLocationChangeListener listener) {
        if (listener == null) return;
        Location location;
        mListener = listener;
        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // 通过网络获取定位（省电，但精确度略低）
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                listener.updateLastLocation(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationListener);
        } else if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 通过Gps获取定位（精确度高，但耗电）
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                listener.updateLastLocation(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocationListener);
        } else {
            openGpsSettings();
        }
    }

    private class MyLocationListener
            implements LocationListener {

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.updateLocation(location);
            }
        }

        // Provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.updateStatus(provider, status, extras);
            }
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    LogUtils.d("onStatusChanged", "当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtils.d("onStatusChanged", "当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogUtils.d("onStatusChanged", "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * Provider被enable时触发此函数，比如GPS被打开
         */
        @Override
        public void onProviderEnabled(String provider) {
        }

        /**
         * Provider被disable时触发此函数，比如GPS被关闭
         */
        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    // 移除
    public void removeLocationUpdatesListener() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(myLocationListener);
            mLocationManager = null;
        }
    }

    public interface OnLocationChangeListener {

        void updateLocation(Location location);//位置信息发生改变

        void updateStatus(String provider, int status, Bundle extras);//位置状态发生改变

        void updateLastLocation(Location location);
    }
}
