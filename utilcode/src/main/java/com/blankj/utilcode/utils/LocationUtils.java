package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/11/13
 *     desc  : 定位相关工具类
 * </pre>
 */
public class LocationUtils {

    private Context mContext;
    private OnLocationChangeListener mListener;
    private MyLocationListener myLocationListener;
    private LocationManager mLocationManager;

    /**
     * LocationUtils构造函数
     *
     * @param context 上下文
     */
    public LocationUtils(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener();
    }

    /**
     * 判断Gps是否可用
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

    /**
     * 初始化
     * <p>使用完记得调用{@link #removeAndGc()}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}</p>
     *
     * @param minTime     最小刷新时间（单位：毫秒）
     * @param minDistance 最小刷新距离（单位：米）
     * @param listener    位置刷新的回调接口
     */
    public void init(long minTime, long minDistance, OnLocationChangeListener listener) {
        if (listener == null) return;
        Location location;
        mListener = listener;
        if (!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ToastUtils.showShortToastSafe(mContext, "无法定位，请打开定位服务");
            openGpsSettings();
        }
        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // 通过网络获取定位（省电，但精确度略低）
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                listener.onLocationChanged(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationListener);
        } else if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 通过Gps获取定位（精确度高，但耗电）
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                listener.onLocationChanged(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocationListener);
        }
    }

    /**
     * 根据经纬度获取地理位置
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public Address getAddress(double latitude, double longitude) {
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        try {
            return gc.getFromLocation(latitude, longitude, 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据经纬度获取所在国家
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public String getCountryName(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? null : address.getCountryName();
    }

    /**
     * 根据经纬度获取所在地
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public String getLocality(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? null : address.getLocality();
    }

    /**
     * 根据经纬度获取所在街道
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public String getStreet(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? null : address.getAddressLine(0);
    }

    /**
     * 移除并gc
     */
    public void removeAndGc() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(myLocationListener);
            mLocationManager = null;
            myLocationListener = null;
            System.gc();
        }
    }

    private class MyLocationListener
            implements LocationListener {
        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    LogUtils.d("onStatusChanged", "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtils.d("onStatusChanged", "当前GPS状态为服务区外状态");
                    break;
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

    public interface OnLocationChangeListener {

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        void onLocationChanged(Location location);

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        void onStatusChanged(String provider, int status, Bundle extras);//位置状态发生改变
    }
}
