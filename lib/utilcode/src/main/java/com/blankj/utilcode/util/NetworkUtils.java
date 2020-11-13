package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.content.Context.WIFI_SERVICE;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about network
 * </pre>
 */
public final class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public enum NetworkType {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    /**
     * Open the settings of wireless.
     */
    public static void openWirelessSettings() {
        Utils.getApp().startActivity(
                new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

    /**
     * Return whether network is connected.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: connected<br>{@code false}: disconnected
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * Return whether network is available.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param consumer The consumer.
     * @return the task
     */
    @RequiresPermission(INTERNET)
    public static Utils.Task<Boolean> isAvailableAsync(@NonNull final Utils.Consumer<Boolean> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<Boolean>(consumer) {
            @RequiresPermission(INTERNET)
            @Override
            public Boolean doInBackground() {
                return isAvailable();
            }
        });
    }

    /**
     * Return whether network is available.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(INTERNET)
    public static boolean isAvailable() {
        return isAvailableByDns() || isAvailableByPing(null);
    }

    /**
     * Return whether network is available using ping.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     * <p>The default ping ip: 223.5.5.5</p>
     *
     * @param consumer The consumer.
     */
    @RequiresPermission(INTERNET)
    public static void isAvailableByPingAsync(final Utils.Consumer<Boolean> consumer) {
        isAvailableByPingAsync("", consumer);
    }

    /**
     * Return whether network is available using ping.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param ip       The ip address.
     * @param consumer The consumer.
     * @return the task
     */
    @RequiresPermission(INTERNET)
    public static Utils.Task<Boolean> isAvailableByPingAsync(final String ip,
                                                             @NonNull final Utils.Consumer<Boolean> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<Boolean>(consumer) {
            @RequiresPermission(INTERNET)
            @Override
            public Boolean doInBackground() {
                return isAvailableByPing(ip);
            }
        });
    }

    /**
     * Return whether network is available using ping.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     * <p>The default ping ip: 223.5.5.5</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(INTERNET)
    public static boolean isAvailableByPing() {
        return isAvailableByPing("");
    }

    /**
     * Return whether network is available using ping.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param ip The ip address.
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(INTERNET)
    public static boolean isAvailableByPing(final String ip) {
        final String realIp = TextUtils.isEmpty(ip) ? "223.5.5.5" : ip;
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 1 %s", realIp), false);
        return result.result == 0;
    }

    /**
     * Return whether network is available using domain.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param consumer The consumer.
     */
    @RequiresPermission(INTERNET)
    public static void isAvailableByDnsAsync(final Utils.Consumer<Boolean> consumer) {
        isAvailableByDnsAsync("", consumer);
    }

    /**
     * Return whether network is available using domain.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain   The name of domain.
     * @param consumer The consumer.
     * @return the task
     */
    @RequiresPermission(INTERNET)
    public static Utils.Task isAvailableByDnsAsync(final String domain,
                                                   @NonNull final Utils.Consumer<Boolean> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<Boolean>(consumer) {
            @RequiresPermission(INTERNET)
            @Override
            public Boolean doInBackground() {
                return isAvailableByDns(domain);
            }
        });
    }

    /**
     * Return whether network is available using domain.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(INTERNET)
    public static boolean isAvailableByDns() {
        return isAvailableByDns("");
    }

    /**
     * Return whether network is available using domain.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain The name of domain.
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(INTERNET)
    public static boolean isAvailableByDns(final String domain) {
        final String realDomain = TextUtils.isEmpty(domain) ? "www.baidu.com" : domain;
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(realDomain);
            return inetAddress != null;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether mobile data is enabled.
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    public static boolean getMobileDataEnabled() {
        try {
            TelephonyManager tm =
                    (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) return false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm.isDataEnabled();
            }
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod =
                    tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return whether using mobile data.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isMobileData() {
        NetworkInfo info = getActiveNetworkInfo();
        return null != info
                && info.isAvailable()
                && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * Return whether using 4G.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean is4G() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null
                && info.isAvailable()
                && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * Return whether using 4G.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean is5G() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null
                && info.isAvailable()
                && info.getSubtype() == TelephonyManager.NETWORK_TYPE_NR;
    }

    /**
     * Return whether wifi is enabled.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static boolean getWifiEnabled() {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) Utils.getApp().getSystemService(WIFI_SERVICE);
        if (manager == null) return false;
        return manager.isWifiEnabled();
    }

    /**
     * Enable or disable wifi.
     * <p>Must hold {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />}</p>
     *
     * @param enabled True to enabled, false otherwise.
     */
    @RequiresPermission(CHANGE_WIFI_STATE)
    public static void setWifiEnabled(final boolean enabled) {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) Utils.getApp().getSystemService(WIFI_SERVICE);
        if (manager == null) return;
        if (enabled == manager.isWifiEnabled()) return;
        manager.setWifiEnabled(enabled);
    }

    /**
     * Return whether wifi is connected.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: connected<br>{@code false}: disconnected
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Return whether wifi is available.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />},
     * {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @return {@code true}: available<br>{@code false}: unavailable
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET})
    public static boolean isWifiAvailable() {
        return getWifiEnabled() && isAvailable();
    }

    /**
     * Return whether wifi is available.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />},
     * {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param consumer The consumer.
     * @return the task
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET})
    public static Utils.Task<Boolean> isWifiAvailableAsync(@NonNull final Utils.Consumer<Boolean> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<Boolean>(consumer) {
            @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET})
            @Override
            public Boolean doInBackground() {
                return isWifiAvailable();
            }
        });
    }

    /**
     * Return the name of network operate.
     *
     * @return the name of network operate
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return "";
        return tm.getNetworkOperatorName();
    }

    /**
     * Return type of network.
     * <p>Must hold {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return type of network
     * <ul>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_ETHERNET} </li>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_WIFI    } </li>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_4G      } </li>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_3G      } </li>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_2G      } </li>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_UNKNOWN } </li>
     * <li>{@link NetworkUtils.NetworkType#NETWORK_NO      } </li>
     * </ul>
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static NetworkType getNetworkType() {
        if (isEthernet()) {
            return NetworkType.NETWORK_ETHERNET;
        }
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetworkType.NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkType.NETWORK_2G;

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkType.NETWORK_3G;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkType.NETWORK_4G;

                    case TelephonyManager.NETWORK_TYPE_NR:
                        return NetworkType.NETWORK_5G;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetworkType.NETWORK_3G;
                        } else {
                            return NetworkType.NETWORK_UNKNOWN;
                        }
                }
            } else {
                return NetworkType.NETWORK_UNKNOWN;
            }
        }
        return NetworkType.NETWORK_NO;
    }

    /**
     * Return whether using ethernet.
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isEthernet() {
        final ConnectivityManager cm =
                (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        final NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null) return false;
        NetworkInfo.State state = info.getState();
        if (null == state) return false;
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm =
                (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return null;
        return cm.getActiveNetworkInfo();
    }

    /**
     * Return the ip address.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param useIPv4  True to use ipv4, false otherwise.
     * @param consumer The consumer.
     * @return the task
     */
    public static Utils.Task<String> getIPAddressAsync(final boolean useIPv4,
                                                       @NonNull final Utils.Consumer<String> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<String>(consumer) {
            @RequiresPermission(INTERNET)
            @Override
            public String doInBackground() {
                return getIPAddress(useIPv4);
            }
        });
    }

    /**
     * Return the ip address.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param useIPv4 True to use ipv4, false otherwise.
     * @return the ip address
     */
    @RequiresPermission(INTERNET)
    public static String getIPAddress(final boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp() || ni.isLoopback()) continue;
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement());
                }
            }
            for (InetAddress add : adds) {
                if (!add.isLoopbackAddress()) {
                    String hostAddress = add.getHostAddress();
                    boolean isIPv4 = hostAddress.indexOf(':') < 0;
                    if (useIPv4) {
                        if (isIPv4) return hostAddress;
                    } else {
                        if (!isIPv4) {
                            int index = hostAddress.indexOf('%');
                            return index < 0
                                    ? hostAddress.toUpperCase()
                                    : hostAddress.substring(0, index).toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Return the ip address of broadcast.
     *
     * @return the ip address of broadcast
     */
    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp() || ni.isLoopback()) continue;
                List<InterfaceAddress> ias = ni.getInterfaceAddresses();
                for (int i = 0, size = ias.size(); i < size; i++) {
                    InterfaceAddress ia = ias.get(i);
                    InetAddress broadcast = ia.getBroadcast();
                    if (broadcast != null) {
                        return broadcast.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Return the domain address.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain   The name of domain.
     * @param consumer The consumer.
     * @return the task
     */
    @RequiresPermission(INTERNET)
    public static Utils.Task<String> getDomainAddressAsync(final String domain,
                                                           @NonNull final Utils.Consumer<String> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<String>(consumer) {
            @RequiresPermission(INTERNET)
            @Override
            public String doInBackground() {
                return getDomainAddress(domain);
            }
        });
    }

    /**
     * Return the domain address.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain The name of domain.
     * @return the domain address
     */
    @RequiresPermission(INTERNET)
    public static String getDomainAddress(final String domain) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(domain);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the ip address by wifi.
     *
     * @return the ip address by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getIpAddressByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) Utils.getApp().getSystemService(Context.WIFI_SERVICE);
        if (wm == null) return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().ipAddress);
    }

    /**
     * Return the gate way by wifi.
     *
     * @return the gate way by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getGatewayByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) Utils.getApp().getSystemService(Context.WIFI_SERVICE);
        if (wm == null) return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().gateway);
    }

    /**
     * Return the net mask by wifi.
     *
     * @return the net mask by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getNetMaskByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) Utils.getApp().getSystemService(Context.WIFI_SERVICE);
        if (wm == null) return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().netmask);
    }

    /**
     * Return the server address by wifi.
     *
     * @return the server address by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getServerAddressByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) Utils.getApp().getSystemService(Context.WIFI_SERVICE);
        if (wm == null) return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress);
    }

    /**
     * Return the ssid.
     *
     * @return the ssid.
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getSSID() {
        WifiManager wm = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm == null) return "";
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null) return "";
        String ssid = wi.getSSID();
        if (TextUtils.isEmpty(ssid)) {
            return "";
        }
        if (ssid.length() > 2 && ssid.charAt(0) == '"' && ssid.charAt(ssid.length() - 1) == '"') {
            return ssid.substring(1, ssid.length() - 1);
        }
        return ssid;
    }

    /**
     * Register the status of network changed listener.
     *
     * @param listener The status of network changed listener
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static void registerNetworkStatusChangedListener(final OnNetworkStatusChangedListener listener) {
        NetworkChangedReceiver.getInstance().registerListener(listener);
    }

    /**
     * Return whether the status of network changed listener has been registered.
     *
     * @param listener The listener
     * @return true to registered, false otherwise.
     */
    public static boolean isRegisteredNetworkStatusChangedListener(final OnNetworkStatusChangedListener listener) {
        return NetworkChangedReceiver.getInstance().isRegistered(listener);
    }

    /**
     * Unregister the status of network changed listener.
     *
     * @param listener The status of network changed listener.
     */
    public static void unregisterNetworkStatusChangedListener(final OnNetworkStatusChangedListener listener) {
        NetworkChangedReceiver.getInstance().unregisterListener(listener);
    }

    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, ACCESS_COARSE_LOCATION})
    public static WifiScanResults getWifiScanResult() {
        WifiScanResults result = new WifiScanResults();
        if (!getWifiEnabled()) return result;
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) Utils.getApp().getSystemService(WIFI_SERVICE);
        //noinspection ConstantConditions
        List<ScanResult> results = wm.getScanResults();
        if (results != null) {
            result.setAllResults(results);
        }
        return result;
    }

    private static final long                                 SCAN_PERIOD_MILLIS    = 3000;
    private static final Set<Utils.Consumer<WifiScanResults>> SCAN_RESULT_CONSUMERS = new CopyOnWriteArraySet<>();
    private static       Timer                                sScanWifiTimer;
    private static       WifiScanResults                      sPreWifiScanResults;

    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, CHANGE_WIFI_STATE, ACCESS_COARSE_LOCATION})
    public static void addOnWifiChangedConsumer(final Utils.Consumer<WifiScanResults> consumer) {
        if (consumer == null) return;
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (SCAN_RESULT_CONSUMERS.isEmpty()) {
                    SCAN_RESULT_CONSUMERS.add(consumer);
                    startScanWifi();
                    return;
                }
                consumer.accept(sPreWifiScanResults);
                SCAN_RESULT_CONSUMERS.add(consumer);
            }
        });
    }

    private static void startScanWifi() {
        sPreWifiScanResults = new WifiScanResults();
        sScanWifiTimer = new Timer();
        sScanWifiTimer.schedule(new TimerTask() {
            @RequiresPermission(allOf = {ACCESS_WIFI_STATE, CHANGE_WIFI_STATE, ACCESS_COARSE_LOCATION})
            @Override
            public void run() {
                startScanWifiIfEnabled();
                WifiScanResults scanResults = getWifiScanResult();
                if (isSameScanResults(sPreWifiScanResults.allResults, scanResults.allResults)) {
                    return;
                }
                sPreWifiScanResults = scanResults;
                UtilsBridge.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Utils.Consumer<WifiScanResults> consumer : SCAN_RESULT_CONSUMERS) {
                            consumer.accept(sPreWifiScanResults);
                        }
                    }
                });
            }
        }, 0, SCAN_PERIOD_MILLIS);
    }

    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, CHANGE_WIFI_STATE})
    private static void startScanWifiIfEnabled() {
        if (!getWifiEnabled()) return;
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) Utils.getApp().getSystemService(WIFI_SERVICE);
        //noinspection ConstantConditions
        wm.startScan();
    }

    public static void removeOnWifiChangedConsumer(final Utils.Consumer<WifiScanResults> consumer) {
        if (consumer == null) return;
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SCAN_RESULT_CONSUMERS.remove(consumer);
                if (SCAN_RESULT_CONSUMERS.isEmpty()) {
                    stopScanWifi();
                }
            }
        });
    }

    private static void stopScanWifi() {
        if (sScanWifiTimer != null) {
            sScanWifiTimer.cancel();
            sScanWifiTimer = null;
        }
    }

    private static boolean isSameScanResults(List<ScanResult> l1, List<ScanResult> l2) {
        if (l1 == null && l2 == null) {
            return true;
        }
        if (l1 == null || l2 == null) {
            return false;
        }
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            ScanResult r1 = l1.get(i);
            ScanResult r2 = l2.get(i);
            if (!isSameScanResultContent(r1, r2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameScanResultContent(ScanResult r1, ScanResult r2) {
        return r1 != null && r2 != null && UtilsBridge.equals(r1.BSSID, r2.BSSID)
                && UtilsBridge.equals(r1.SSID, r2.SSID)
                && UtilsBridge.equals(r1.capabilities, r2.capabilities)
                && r1.level == r2.level;
    }

    public static final class NetworkChangedReceiver extends BroadcastReceiver {

        private static NetworkChangedReceiver getInstance() {
            return LazyHolder.INSTANCE;
        }

        private NetworkType                         mType;
        private Set<OnNetworkStatusChangedListener> mListeners = new HashSet<>();

        @RequiresPermission(ACCESS_NETWORK_STATE)
        void registerListener(final OnNetworkStatusChangedListener listener) {
            if (listener == null) return;
            UtilsBridge.runOnUiThread(new Runnable() {
                @Override
                @RequiresPermission(ACCESS_NETWORK_STATE)
                public void run() {
                    int preSize = mListeners.size();
                    mListeners.add(listener);
                    if (preSize == 0 && mListeners.size() == 1) {
                        mType = getNetworkType();
                        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                        Utils.getApp().registerReceiver(NetworkChangedReceiver.getInstance(), intentFilter);
                    }
                }
            });
        }

        boolean isRegistered(final OnNetworkStatusChangedListener listener) {
            if (listener == null) return false;
            return mListeners.contains(listener);
        }

        void unregisterListener(final OnNetworkStatusChangedListener listener) {
            if (listener == null) return;
            UtilsBridge.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int preSize = mListeners.size();
                    mListeners.remove(listener);
                    if (preSize == 1 && mListeners.size() == 0) {
                        Utils.getApp().unregisterReceiver(NetworkChangedReceiver.getInstance());
                    }
                }
            });
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                // debouncing
                UtilsBridge.runOnUiThreadDelayed(new Runnable() {
                    @Override
                    @RequiresPermission(ACCESS_NETWORK_STATE)
                    public void run() {
                        NetworkType networkType = NetworkUtils.getNetworkType();
                        if (mType == networkType) return;
                        mType = networkType;
                        if (networkType == NetworkType.NETWORK_NO) {
                            for (OnNetworkStatusChangedListener listener : mListeners) {
                                listener.onDisconnected();
                            }
                        } else {
                            for (OnNetworkStatusChangedListener listener : mListeners) {
                                listener.onConnected(networkType);
                            }
                        }
                    }
                }, 1000);
            }
        }

        private static class LazyHolder {
            private static final NetworkChangedReceiver INSTANCE = new NetworkChangedReceiver();
        }
    }

//    /**
//     * Register the status of network changed listener.
//     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @RequiresPermission(ACCESS_NETWORK_STATE)
//    public static void registerNetworkStatusChangedListener() {
//        ConnectivityManager cm = (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm == null) return;
//        NetworkCallbackImpl networkCallback = NetworkCallbackImpl.LazyHolder.INSTANCE;
//        NetworkRequest.Builder builder = new NetworkRequest.Builder();
//        NetworkRequest request = builder.build();
//        cm.registerNetworkCallback(new NetworkRequest.Builder().build(), networkCallback);
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public static final class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
//
//        @Override
//        public void onAvailable(@NonNull Network network) {
//            super.onAvailable(network);
//            LogUtils.d(TAG, "onAvailable: " + network);
//        }
//
//        @Override
//        public void onLosing(@NonNull Network network, int maxMsToLive) {
//            super.onLosing(network, maxMsToLive);
//            LogUtils.d(TAG, "onLosing: " + network);
//        }
//
//        @Override
//        public void onLost(@NonNull Network network) {
//            super.onLost(network);
//            LogUtils.e(TAG, "onLost: " + network);
//        }
//
//        @Override
//        public void onUnavailable() {
//            super.onUnavailable();
//            LogUtils.e(TAG, "onUnavailable");
//        }
//
//        @Override
//        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities cap) {
//            super.onCapabilitiesChanged(network, cap);
//            if (cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
//                if (cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                    LogUtils.d(TAG, "onCapabilitiesChanged: 网络类型为wifi");
//                } else if (cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                    LogUtils.d(TAG, "onCapabilitiesChanged: 蜂窝网络");
//                } else {
//                    LogUtils.d(TAG, "onCapabilitiesChanged: 其他网络");
//                }
//                LogUtils.d(TAG, "onCapabilitiesChanged: " + network + ", " + cap);
//            }
//        }
//
//        @Override
//        public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties lp) {
//            super.onLinkPropertiesChanged(network, lp);
//            LogUtils.d(TAG, "onLinkPropertiesChanged: " + network + ", " + lp);
//        }
//
//        @Override
//        public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
//            super.onBlockedStatusChanged(network, blocked);
//            LogUtils.d(TAG, "onBlockedStatusChanged: " + network + ", " + blocked);
//        }
//
//        private static class LazyHolder {
//            private static final NetworkCallbackImpl INSTANCE = new NetworkCallbackImpl();
//        }
//    }

    public interface OnNetworkStatusChangedListener {
        void onDisconnected();

        void onConnected(NetworkType networkType);
    }

    public static final class WifiScanResults {

        private List<ScanResult> allResults    = new ArrayList<>();
        private List<ScanResult> filterResults = new ArrayList<>();

        public WifiScanResults() {
        }

        public List<ScanResult> getAllResults() {
            return allResults;
        }

        public List<ScanResult> getFilterResults() {
            return filterResults;
        }

        public void setAllResults(List<ScanResult> allResults) {
            this.allResults = allResults;
            filterResults = filterScanResult(allResults);
        }

        private static List<ScanResult> filterScanResult(final List<ScanResult> results) {
            if (results == null || results.isEmpty()) {
                return new ArrayList<>();
            }
            LinkedHashMap<String, ScanResult> map = new LinkedHashMap<>(results.size());
            for (ScanResult result : results) {
                if (TextUtils.isEmpty(result.SSID)) {
                    continue;
                }
                ScanResult resultInMap = map.get(result.SSID);
                if (resultInMap != null && resultInMap.level >= result.level) {
                    continue;
                }
                map.put(result.SSID, result);
            }
            return new ArrayList<>(map.values());
        }

    }
}
