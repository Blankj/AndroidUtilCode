# 网络相关
### 打开网络设置界面
``` java
/**
 * 打开网络设置界面
 * <p>3.0以下打开设置界面
 */
public static void openWirelessSettings(Context context) {
    if (android.os.Build.VERSION.SDK_INT > 10) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
    } else {
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }
}
```

### 判断网络是否可用
``` java
/**
 * 获取活动网路信息
 */
private static NetworkInfo getActiveNetworkInfo(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo();
}

/**
 * 判断网络是否可用
 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */
public static boolean isAvailable(Context context) {
    NetworkInfo info = getActiveNetworkInfo(context);
    return info != null && info.isAvailable();
}
```

### 判断网络是否连接
``` java
/**
 * 判断网络是否连接
 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */
public static boolean isConnected(Context context) {
    NetworkInfo info = getActiveNetworkInfo(context);
    return info != null && info.isConnected();
}
```

### 判断网络是否是4G
``` java
/**
 * 判断网络是否是4G
 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */
public static boolean is4G(Context context) {
    NetworkInfo info = getActiveNetworkInfo(context);
    return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
}
```

### 判断wifi是否连接状态
``` java
/**
 * 判断wifi是否连接状态
 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */
public static boolean isWifiConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
}
```

### 获取移动网络运营商名称
``` java
/**
 * 获取移动网络运营商名称
 * <p>如中国联通、中国移动、中国电信
 */
public static String getNetworkOperatorName(Context context) {
    TelephonyManager tm = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    return tm != null ? tm.getNetworkOperatorName() : null;
}
```

### 获取移动终端类型
``` java
/**
 * 获取移动终端类型
 *
 * @return 手机制式
 * <ul>
 * <li>PHONE_TYPE_NONE  : 0 手机制式未知</li>
 * <li>PHONE_TYPE_GSM   : 1 手机制式为GSM，移动和联通</li>
 * <li>PHONE_TYPE_CDMA  : 2 手机制式为CDMA，电信</li>
 * <li>PHONE_TYPE_SIP   : 3</li>
 * </ul>
 */
public static int getPhoneType(Context context) {
    TelephonyManager tm = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    return tm != null ? tm.getPhoneType() : -1;
}
```

### 获取当前的网络类型(WIFI,2G,3G,4G)
``` java
/**
 * 获取当前的网络类型(WIFI,2G,3G,4G)
 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *
 * @return 网络类型
 * <ul>
 * <li>NETWORK_WIFI    = 1;</li>
 * <li>NETWORK_4G      = 4;</li>
 * <li>NETWORK_3G      = 3;</li>
 * <li>NETWORK_2G      = 2;</li>
 * <li>NETWORK_UNKNOWN = 5;</li>
 * <li>NETWORK_NO      = -1;</li>
 * </ul>
 */
public static int getNetWorkType(Context context) {
    int netType = NETWORK_NO;
    NetworkInfo info = getActiveNetworkInfo(context);
    if (info != null && info.isAvailable()) {

        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            netType = NETWORK_WIFI;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (info.getSubtype()) {

                case NETWORK_TYPE_GSM:
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    netType = NETWORK_2G;
                    break;

                case NETWORK_TYPE_TD_SCDMA:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    netType = NETWORK_3G;
                    break;

                case NETWORK_TYPE_IWLAN:
                case TelephonyManager.NETWORK_TYPE_LTE:
                    netType = NETWORK_4G;
                    break;
                default:

                    String subtypeName = info.getSubtypeName();
                    if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                            || subtypeName.equalsIgnoreCase("WCDMA")
                            || subtypeName.equalsIgnoreCase("CDMA2000")) {
                        netType = NETWORK_3G;
                    } else {
                        netType = NETWORK_UNKNOWN;
                    }
                    break;
            }
        } else {
            netType = NETWORK_UNKNOWN;
        }
    }
    return netType;
}

/**
 * 获取当前的网络类型(WIFI,2G,3G,4G)
 * 依赖上面的方法
 *
 * @param context
 * @return 网络类型名称
 * <ul>
 * <li>NETWORK_WIFI   </li>
 * <li>NETWORK_4G     </li>
 * <li>NETWORK_3G     </li>
 * <li>NETWORK_2G     </li>
 * <li>NETWORK_UNKNOWN</li>
 * <li>NETWORK_NO     </li>
 * </ul>
 */
public static String getNetWorkTypeName(Context context) {
    switch (getNetWorkType(context)) {
        case NETWORK_WIFI:
            return "NETWORK_WIFI";
        case NETWORK_4G:
            return "NETWORK_4G";
        case NETWORK_3G:
            return "NETWORK_3G";
        case NETWORK_2G:
            return "NETWORK_2G";
        case NETWORK_NO:
            return "NETWORK_NO";
        default:
            return "NETWORK_UNKNOWN";
    }
}
```
