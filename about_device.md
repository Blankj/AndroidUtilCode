# 设备相关

### 判断设备是否 root
``` java
/**
 * 判断设备是否 root
 * @return the boolean
 */
public static boolean isDeviceRooted() {
     String su = "su";
     String[] locations = {"/sbin/", "/system/bin/", "/system/xbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                     "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
     for (String location: locations) {
           if (new File(location + su).exists()) {
                 return true;
           }
     }

     return false;
}
```

### 获取设备MAC地址
``` java
/**
 * 获取设备MAC地址
 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 */
public static String getMacAddress(Context context) {
    String macAddress;
    WifiManager wifi = (WifiManager) context
            .getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = wifi.getConnectionInfo();
    macAddress = info.getMacAddress();
    if (null == macAddress) {
        return "";
    }
    macAddress = macAddress.replace(":", "");
    return macAddress;
}
```

### 获取设备厂商，如Xiaomi
``` java
/**
* 获取设备厂商，如Xiaomi
*/
public static String getManufacturer() {
    String MANUFACTURER = Build.MANUFACTURER;
    return MANUFACTURER;
}
```

### 获取设备型号，如MI2SC
``` java
/**
 * 获取设备型号，如MI2SC
 */
public static String getModel() {
    String model = Build.MODEL;
    if (model != null) {
        model = model.trim().replaceAll("\\s*", "");
    } else {
        model = "";
    }
    return model;
}
```

### 获取设备SD卡是否可用
``` java
/**
 * 获取设备SD卡是否可用
 */
public static boolean isSDCardEnable() {
    return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
}
```

### 获取设备SD卡路径
```
/**
 * 获取设备SD卡路径
 * <p>一般是/storage/emulated/0/
 */
public static String getSDCardPath() {
    return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
}
```
