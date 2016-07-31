为方便查找，已进行大致归类，其大纲如下所示： 
> - [1 尺寸相关](#1)
>    - [1.1 dp与px转换](#1.1)
>    - [1.2 sp与px转换](#1.2)
>    - [1.3 各种单位转换](#1.3)
>    - [1.4 在onCreate()即可获取View的宽高](#1.4)
>    - [1.5 ListView中提前测量View尺寸](#1.5)
> - [2 手机相关](#2)
>    - [2.1 判断设备是否是手机](#2.1)
>    - [2.2 获取当前设备的IMIE，需与上面的isPhone一起使用](#2.2)
>    - [2.3 获取手机状态信息](#2.3)
>    - [2.4 是否有SD卡](#2.4)
>    - [2.5 获取MAC地址](#2.5)
>    - [2.6 获取手机厂商，如Xiaomi](#2.6)
>    - [2.7 获取手机型号，如MI2SC](#2.7)
>    - [2.8 跳转至拨号界面](#2.8)
>    - [2.9 拨打电话](#2.9)
>    - [2.10 发送短信](#2.10)
>    - [2.11 获取手机联系人](#2.11)
>    - [2.12 直接打开手机联系人界面，并获取联系人号码](#2.12)
>    - [2.13 获取手机短信并保存到xml中](#2.13)
> - [3 网络相关](#3)
>    - [3.1 打开网络设置界面](#3.1)
>    - [3.2 判断是否网络连接](#3.2)
>    - [3.3 判断wifi是否连接状态](#3.1)
>    - [3.4 获取移动网络运营商名称，如中国联通、中国移动、中国电信](#3.4)
>    - [3.5 返回移动终端类型](#3.5)
>    - [3.6 判断手机连接的网络类型(2G,3G,4G)](#3.6)
>    - [3.7 判断当前手机的网络类型(WIFI还是2,3,4G)](#3.7)
> - [4 App相关](#4)
>    - [4.1 安装指定路径下的Apk](#4.1)
>    - [4.2 卸载指定包名的App](#4.2)
>    - [4.3 获取App名称](#4.3)
>    - [4.4 获取当前App版本号](#4.4)
>    - [4.5 打开指定包名的App](#4.5)
>    - [4.6 打开指定包名的App应用信息界面](#4.6)
>    - [4.7 分享Apk信息](#4.7)
>    - [4.8 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)](#4.8)
>    - [4.9 判断当前App处于前台还是后台](#4.9)
> - [5 屏幕相关](#5)
>    - [5.1 获取手机分辨率](#5.1)
>    - [5.2 获取状态栏高度](#5.2)
>    - [5.3 获取状态栏高度＋标题栏(ActionBar)高度](#5.3)
>    - [5.4 获取屏幕截图](#5.4)
>    - [5.5 设置透明状态栏，需在setContentView之前调用](#5.5)
> - [6 键盘相关](#6)
>    - [6.1 避免输入法面板遮挡](#6.1)
>    - [6.2 动态隐藏软键盘](#6.2)
>    - [6.3 点击屏幕空白区域隐藏软键盘](#6.3)
>    - [6.4 动态显示软键盘](#6.4)
>    - [6.5 切换键盘显示与否状态](#6.5)
> - [7 正则相关](#7)
>    - [7.1 正则工具类](#7.1)
> - [8 加解密相关](#8)
>    - [8.1 MD5加密](#8.1)
>    - [8.2 SHA加密](#8.2)
> - [x 未归类](#x)
>    - [x.1 获取服务是否开启](#x.1)
> - [更新Log](#log)

大部分代码已验证过可行，如有错误，请及时告之。  
分类已上传至Github，传送门→[期待你的Star和完善](https://github.com/Blankj/AndroidUtilCode)  

# <span id = "1"/>1 尺寸相关
### <span id = "1.1"/>1.1 dp与px转换
``` java
/**
* dp转px
*/
public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
}

/**
* px转dp
*/
public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
}
```
 
### <span id = "1.2"/>1.2 sp与px转换
``` java
/**
* sp转px
*/
public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
}

/**
* px转sp
*/
public static int px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
}
```

### <span id = "1.3"/>1.3 各种单位转换
``` java
// 该方法存在于TypedValue
/**
* 各种单位转换
*/
public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
    switch (unit) {
        case TypedValue.COMPLEX_UNIT_PX:
            return value;
        case TypedValue.COMPLEX_UNIT_DIP:
            return value * metrics.density;
        case TypedValue.COMPLEX_UNIT_SP:
            return value * metrics.scaledDensity;
        case TypedValue.COMPLEX_UNIT_PT:
            return value * metrics.xdpi * (1.0f / 72);
        case TypedValue.COMPLEX_UNIT_IN:
            return value * metrics.xdpi;
        case TypedValue.COMPLEX_UNIT_MM:
            return value * metrics.xdpi * (1.0f / 25.4f);
    }
    return 0;
}
```

### <span id = "1.4"/>1.4 在onCreate()即可获取View的宽高
``` java
/**
* 在onCreate()即可获取View的宽高
*/
public static int[] getViewMeasure(View view) {
    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    view.measure(widthMeasureSpec, heightMeasureSpec);
    return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
}
```

### <span id = "1.5"/>1.5 ListView中提前测量View尺寸
``` java
// 通知父布局，占用的宽，高；
/**
* ListView中提前测量View尺寸，如headerView
*/
private void measureView(View view) {
    ViewGroup.LayoutParams p = view.getLayoutParams();
    if (p == null) {
        p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
    int height;
    int tempHeight = p.height;
    if (tempHeight > 0) {
        height = MeasureSpec.makeMeasureSpec(tempHeight,
                MeasureSpec.EXACTLY);
    } else {
        height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    }
    view.measure(width, height);
}
```

********************************************************************************************
# <span id = "2"/>2 手机相关
### <span id = "2.1"/>2.1 判断设备是否是手机
``` java
/**
* 判断设备是否是手机
*/
public static boolean isPhone(Context context) {
    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephony.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
}
```

### <span id = "2.2"/>2.2 获取当前设备的IMIE，需与上面的isPhone一起使用
``` java
/**
* 获取当前设备的IMIE，需与上面的isPhone一起使用
*/
public static String getDeviceIMEI(Context context) {
    String deviceId;
    if (isPhone(context)) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephony.getDeviceId();
    } else {
        deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    return deviceId;
}
```

### <span id = "2.3"/>2.3 获取手机状态信息
``` java
// 需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
/**
* 获取手机状态信息
*
* 返回如下
* DeviceId(IMEI) = 99000311726612
* DeviceSoftwareVersion = 00
* Line1Number =
* NetworkCountryIso = cn
* NetworkOperator = 46003
* NetworkOperatorName = 中国电信
* NetworkType = 6
* honeType = 2
* SimCountryIso = cn
* SimOperator = 46003
* SimOperatorName = 中国电信
* SimSerialNumber = 89860315045710604022
* SimState = 5
* SubscriberId(IMSI) = 460030419724900
* VoiceMailNumber = *86
*/
public static String getPhoneStatus(Context context) {
    TelephonyManager tm = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    String str = "";
    str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
    str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
    str += "Line1Number = " + tm.getLine1Number() + "\n";
    str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
    str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
    str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
    str += "NetworkType = " + tm.getNetworkType() + "\n";
    str += "honeType = " + tm.getPhoneType() + "\n";
    str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
    str += "SimOperator = " + tm.getSimOperator() + "\n";
    str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
    str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
    str += "SimState = " + tm.getSimState() + "\n";
    str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
    str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
    return str;
}
```

### <span id = "2.4"/>2.4 是否有SD卡
``` java
/**
* 是否有SD卡
*/
public static boolean haveSDCard() {
    return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
}
```

### <span id = "2.5"/>2.5 获取MAC地址
``` java
// 需添加权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
/**
* 获取MAC地址
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

### <span id = "2.6"/>2.6 获取手机厂商，如Xiaomi
``` java
/**
* 获取手机厂商，如Xiaomi
*/
public static String getOsName() {
    String MANUFACTURER = Build.MANUFACTURER;
    return MANUFACTURER;
}
```

### <span id = "2.7"/>2.7 获取手机型号，如MI2SC
``` java
/**
* 获取手机型号，如MI2SC
*/
private String getModel() {
    String model = android.os.Build.MODEL;
    if (model != null) {
        model = model.trim().replaceAll("\\s*", "");
    } else {
        model = "";
    }
    return model;
}
```

### <span id = "2.8"/>2.8 跳转至拨号界面
``` java
/**
* 跳转至拨号界面
*/
public static void callDial(Context context, String phoneNumber) {
    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
}
```

### <span id = "2.9"/>2.9 拨打电话
``` java
/**
* 拨打电话
*/
public static void call(Context context, String phoneNumber) {
    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
}
```

### <span id = "2.10"/>2.10 发送短信
``` java
/**
* 发送短信
*/
public static void sendSms(Context context, String phoneNumber, String content) {
    Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
    intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
    context.startActivity(intent);
}
```

### <span id = "2.11"/>2.11 获取手机联系人
``` java
/**
* 获取手机联系人
*/
public static List<HashMap<String, String>> getAllContactInfo(Context context) {
    SystemClock.sleep(3000);
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    // 1.获取内容解析者
    ContentResolver resolver = context.getContentResolver();
    // 2.获取内容提供者的地址:com.android.contacts
    // raw_contacts表的地址 :raw_contacts
    // view_data表的地址 : data
    // 3.生成查询地址
    Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
    Uri date_uri = Uri.parse("content://com.android.contacts/data");
    // 4.查询操作,先查询raw_contacts,查询contact_id
    // projection : 查询的字段
    Cursor cursor = resolver.query(raw_uri, new String[] { "contact_id" },
            null, null, null);
    // 5.解析cursor
    while (cursor.moveToNext()) {
        // 6.获取查询的数据
        String contact_id = cursor.getString(0);
        // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
        // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
        // 判断contact_id是否为空
        if (!TextUtils.isEmpty(contact_id)) {//null   ""
            // 7.根据contact_id查询view_data表中的数据
            // selection : 查询条件
            // selectionArgs :查询条件的参数
            // sortOrder : 排序
            // 空指针: 1.null.方法 2.参数为null
            Cursor c = resolver.query(date_uri, new String[] { "data1",
                            "mimetype" }, "raw_contact_id=?",
                    new String[] { contact_id }, null);
            HashMap<String, String> map = new HashMap<String, String>();
            // 8.解析c
            while (c.moveToNext()) {
                // 9.获取数据
                String data1 = c.getString(0);
                String mimetype = c.getString(1);
                // 10.根据类型去判断获取的data1数据并保存
                if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                    // 电话
                    map.put("phone", data1);
                } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                    // 姓名
                    map.put("name", data1);
                }
            }
            // 11.添加到集合中数据
            list.add(map);
            // 12.关闭cursor
            c.close();
        }
    }
    // 12.关闭cursor
    cursor.close();
    return list;
}
```

### <span id = "2.12"/>2.12 直接打开手机联系人界面，并获取联系人号码
``` java
// 在按钮点击事件中设置Intent，
Intent intent = new Intent();
intent.setAction("android.intent.action.PICK");
intent.addCategory("android.intent.category.DEFAULT");
intent.setType("vnd.android.cursor.dir/phone_v2");
startActivityForResult(intent, 1);
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
        Uri uri = data.getData();
        String num = null;
        // 创建内容解析者
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri,
                null, null, null, null);
        while (cursor.moveToNext()) {
            num = cursor.getString(cursor.getColumnIndex("data1"));
        }
        cursor.close();
        num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
    }
}
```

### <span id = "2.13"/>2.13 获取手机短信并保存到xml中
``` java
/**
* 获取手机短信并保存到xml中
*/
public static void getAllSMS(Context context) {
    //1.获取短信
    //1.1获取内容解析者
    ContentResolver resolver = context.getContentResolver();
    //1.2获取内容提供者地址   sms,sms表的地址:null  不写
    //1.3获取查询路径
    Uri uri = Uri.parse("content://sms");
    //1.4.查询操作
    //projection : 查询的字段
    //selection : 查询的条件
    //selectionArgs : 查询条件的参数
    //sortOrder : 排序
    Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
    //设置最大进度
    int count = cursor.getCount();//获取短信的个数
    //2.备份短信
    //2.1获取xml序列器
    XmlSerializer xmlSerializer = Xml.newSerializer();
    try {
        //2.2设置xml文件保存的路径
        //os : 保存的位置
        //encoding : 编码格式
        xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
        //2.3设置头信息
        //standalone : 是否独立保存
        xmlSerializer.startDocument("utf-8", true);
        //2.4设置根标签
        xmlSerializer.startTag(null, "smss");
        //1.5.解析cursor
        while (cursor.moveToNext()) {
            SystemClock.sleep(1000);
            //2.5设置短信的标签
            xmlSerializer.startTag(null, "sms");
            //2.6设置文本内容的标签
            xmlSerializer.startTag(null, "address");
            String address = cursor.getString(0);
            //2.7设置文本内容
            xmlSerializer.text(address);
            xmlSerializer.endTag(null, "address");
            xmlSerializer.startTag(null, "date");
            String date = cursor.getString(1);
            xmlSerializer.text(date);
            xmlSerializer.endTag(null, "date");
            xmlSerializer.startTag(null, "type");
            String type = cursor.getString(2);
            xmlSerializer.text(type);
            xmlSerializer.endTag(null, "type");
            xmlSerializer.startTag(null, "body");
            String body = cursor.getString(3);
            xmlSerializer.text(body);
            xmlSerializer.endTag(null, "body");
            xmlSerializer.endTag(null, "sms");
            System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
        }
        xmlSerializer.endTag(null, "smss");
        xmlSerializer.endDocument();
        //2.8将数据刷新到文件中
        xmlSerializer.flush();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
```

********************************************************************************************
# <span id = "3"/>3 网络相关
### <span id = "3.1"/>3.1 打开网络设置界面
``` java
/**
* 打开网络设置界面
*/
public static void openSetting(Activity activity) {
    Intent intent = new Intent("/");
    ComponentName cm = new ComponentName("com.android.settings",
            "com.android.settings.WirelessSettings");
    intent.setComponent(cm);
    intent.setAction("android.intent.action.VIEW");
    activity.startActivityForResult(intent, 0);
}
```

### <span id = "3.2"/>3.2 判断是否网络连接
``` java
/**
* 判断是否网络连接
*/
public static boolean isOnline(Context context) {
    ConnectivityManager manager = (ConnectivityManager) context
            .getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    if (info != null && info.isConnected()) {
        return true;
    }
    return false;
}
```

### <span id = "3.3"/>3.3 判断wifi是否连接状态
``` java
/**
* 判断wifi是否连接状态
*/
public static boolean isWifi(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
}
```

### <span id = "3.4"/>3.4 获取移动网络运营商名称，如中国联通、中国移动、中国电信
``` java
/**
* 获取移动网络运营商名称，如中国联通、中国移动、中国电信
*/
public static String getNetworkOperatorName(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getNetworkOperatorName();
}
```

### <span id = "3.5"/>3.5 返回移动终端类型
``` java
// PHONE_TYPE_NONE :0 手机制式未知
// PHONE_TYPE_GSM :1 手机制式为GSM，移动和联通
// PHONE_TYPE_CDMA :2 手机制式为CDMA，电信
// PHONE_TYPE_SIP:3
/**
* 返回移动终端类型
*/
public static int getPhoneType(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getPhoneType();
}
```

### <span id = "3.6"/>3.6 判断手机连接的网络类型(2G,3G,4G)
``` java
// 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
public class Constants {
    /**
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;
    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;
    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;
    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;
}
/**
* 判断手机连接的网络类型(2G,3G,4G)
*/
public static int getNetWorkClass(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    switch (telephonyManager.getNetworkType()) {
        case TelephonyManager.NETWORK_TYPE_GPRS:
        case TelephonyManager.NETWORK_TYPE_EDGE:
        case TelephonyManager.NETWORK_TYPE_CDMA:
        case TelephonyManager.NETWORK_TYPE_1xRTT:
        case TelephonyManager.NETWORK_TYPE_IDEN:
            return Constants.NETWORK_CLASS_2_G;
        case TelephonyManager.NETWORK_TYPE_UMTS:
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
        case TelephonyManager.NETWORK_TYPE_HSDPA:
        case TelephonyManager.NETWORK_TYPE_HSUPA:
        case TelephonyManager.NETWORK_TYPE_HSPA:
        case TelephonyManager.NETWORK_TYPE_EVDO_B:
        case TelephonyManager.NETWORK_TYPE_EHRPD:
        case TelephonyManager.NETWORK_TYPE_HSPAP:
            return Constants.NETWORK_CLASS_3_G;
        case TelephonyManager.NETWORK_TYPE_LTE:
            return Constants.NETWORK_CLASS_4_G;
        default:
            return Constants.NETWORK_CLASS_UNKNOWN;
    }
}
```

### <span id = "3.7"/>3.7 判断当前手机的网络类型(WIFI还是2,3,4G)
``` java
/**
* 判断当前手机的网络类型(WIFI还是2,3,4G)，需要用到上面的方法
*/
public static int getNetWorkStatus(Context context) {
    int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
    ConnectivityManager connectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            netWorkType = Constants.NETWORK_WIFI;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            netWorkType = getNetWorkClass(context);
        }
    }
    return netWorkType;
}
```

********************************************************************************************
# <span id = "4"/>4 App相关
### <span id = "4.1"/>4.1 安装指定路径下的Apk
``` java
/**
* 安装指定路径下的Apk
*/
public void installApk(String filePath) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
    startActivityForResult(intent, 0);
}
```

### <span id = "4.2"/>4.2 卸载指定包名的App
``` java
/**
* 卸载指定包名的App
*/
public void uninstallApp(String packageName) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.DELETE");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setData(Uri.parse("package:" + packageName));
    startActivityForResult(intent, 0);
}
```

### <span id = "4.3"/>4.3 获取App名称
```
/**
* 获取App名称
*/
public static String getAppName(Context context) {
    try {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
        int labelRes = packageInfo.applicationInfo.labelRes;
        return context.getResources().getString(labelRes);
    } catch (NameNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}
```


### <span id = "4.4"/>4.4 获取当前App版本号
``` java
/**
* 获取当前App版本号
*/
public static String getVersonName(Context context) {
    String versionName = null;
    PackageManager pm = context.getPackageManager();
    PackageInfo info = null;
    try {
        info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
    } catch (NameNotFoundException e) {
        e.printStackTrace();
    }
    if (info != null) {
        versionName = info.versionName;
    }
    return versionName;
}
```

### <span id = "4.5"/>4.5 打开指定包名的App
```
/**
* 打开指定包名的App
*/
public void openOtherApp(String packageName){
    PackageManager manager = getPackageManager();
    Intent launchIntentForPackage = manager.getLaunchIntentForPackage(packageName);
    if (launchIntentForPackage != null) {
        startActivity(launchIntentForPackage);
    }
}
```

### <span id = "4.6"/>4.6 打开指定包名的App应用信息界面
``` java
/**
* 打开指定包名的App应用信息界面
*/
public void showAppInfo(String packageName) {
    Intent intent = new Intent();
    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
    intent.setData(Uri.parse("package:" + packageName));
    startActivity(intent);
}
```

### <span id = "4.7"/>4.7 分享Apk信息
``` java
/**
* 分享Apk信息
*/
public void shareApkInfo(String info) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.SEND");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, info);
    startActivity(intent);
}
```

### <span id = "4.8"/>4.8 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)
``` java
/**
* 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)
*/
public class AppEnging {
    public static List<AppInfo> getAppInfos(Context context) {
        List<AppInfo> list = new ArrayList<AppInfo>();
        //获取应用程序信息
        //包的管理者
        PackageManager pm = context.getPackageManager();
        //获取系统中安装到所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            //获取包名
            String packageName = packageInfo.packageName;
            //获取版本号
            String versionName = packageInfo.versionName;
            //获取application
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int uid = applicationInfo.uid;
            //获取应用程序的图标
            Drawable icon = applicationInfo.loadIcon(pm);
            //获取应用程序的名称
            String name = applicationInfo.loadLabel(pm).toString();
            //是否是用户程序
            //获取应用程序中相关信息,是否是系统程序和是否安装到SD卡
            boolean isUser;
            int flags = applicationInfo.flags;
            if ((applicationInfo.FLAG_SYSTEM & flags) == applicationInfo.FLAG_SYSTEM) {
                //系统程序
                isUser = false;
            } else {
                //用户程序
                isUser = true;
            }
            //是否安装到SD卡
            boolean isSD;
            if ((applicationInfo.FLAG_EXTERNAL_STORAGE & flags) == applicationInfo.FLAG_EXTERNAL_STORAGE) {
                //安装到了SD卡
                isSD = true;
            } else {
                //安装到手机中
                isSD = false;
            }
            //添加到bean中
            AppInfo appInfo = new AppInfo(name, icon, packageName, versionName, isSD, isUser);
            //将bean存放到list集合
            list.add(appInfo);
        }
        return list;
    }
}
 
// 封装软件信息的bean类
class AppInfo {
    //名称
    private String name;
    //图标
    private Drawable icon;
    //包名
    private String packagName;
    //版本号
    private String versionName;
    //是否安装到SD卡
    private boolean isSD;
    //是否是用户程序
    private boolean isUser;
 
    public AppInfo() {
        super();
    }
 
    public AppInfo(String name, Drawable icon, String packagName,
                   String versionName, boolean isSD, boolean isUser) {
        super();
        this.name = name;
        this.icon = icon;
        this.packagName = packagName;
        this.versionName = versionName;
        this.isSD = isSD;
        this.isUser = isUser;
    }
}
```

### <span id = "4.9"/>4.9 判断当前App处于前台还是后台
``` java
// 需添加<uses-permission android:name="android.permission.GET_TASKS"/>
// 并且必须是系统应用该方法才有效
/**
* 判断当前App处于前台还是后台
*/
public static boolean isApplicationBackground(final Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    @SuppressWarnings("deprecation")
    List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
    if (!tasks.isEmpty()) {
        ComponentName topActivity = tasks.get(0).topActivity;
        if (!topActivity.getPackageName().equals(context.getPackageName())) {
            return true;
        }
    }
    return false;
}
```

********************************************************************************************
# <span id = "5"/>5 屏幕相关
### <span id = "5.1"/>5.1 获取手机分辨率
``` java
/**
* 获取屏幕的宽度px
*/
public static int getDeviceWidth(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
    return outMetrics.widthPixels;
}

/**
* 获取屏幕的高度px
*/
public static int getDeviceHeight(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
    return outMetrics.heightPixels;
}
```

### <span id = "5.2"/>5.2 获取状态栏高度
```
/**
* 获取状态栏高度
*/
public int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
}
```

### <span id = "5.3"/>5.3 获取状态栏高度＋标题栏(ActionBar)高度
``` java
/**
* 获取状态栏高度＋标题栏(ActionBar)高度
*/
public static int getTopBarHeight(Activity activity) {
    return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
}
```

### <span id = "5.4"/>5.4 获取屏幕截图
``` java
/**
* 获取当前屏幕截图，包含状态栏
*/
public static Bitmap snapShotWithStatusBar(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bmp = view.getDrawingCache();
    int width = getScreenWidth(activity);
    int height = getScreenHeight(activity);
    Bitmap bp = null;
    bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
    view.destroyDrawingCache();
    return bp;
}

/**
* 获取当前屏幕截图，不包含状态栏
*/
public static Bitmap snapShotWithoutStatusBar(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bmp = view.getDrawingCache();
    Rect frame = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    int statusBarHeight = frame.top;
    int width = getScreenWidth(activity);
    int height = getScreenHeight(activity);
    Bitmap bp = null;
    bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
            - statusBarHeight);
    view.destroyDrawingCache();
    return bp;
}
```

### <span id = "5.5"/>5.5 设置透明状态栏，需在setContentView之前调用
``` java
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //透明状态栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //透明导航栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
}

// 需在顶部控件布局中加入以下属性让内容出现在状态栏之下
android:clipToPadding="true" 
android:fitsSystemWindows="true"
```

********************************************************************************************
# <span id = "6"/>6 键盘相关
### <span id = "6.1"/>6.1 避免输入法面板遮挡
``` java
// 在manifest.xml中activity中设置
android:windowSoftInputMode="stateVisible|adjustResize"
```

### <span id = "6.2"/>6.2 动态隐藏软键盘
``` java
/**
* 动态隐藏软键盘
*/
public static void hideSoftInput(Activity activity) {
    View view = activity.getWindow().peekDecorView();
    if (view != null) {
        InputMethodManager inputmanger = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

/**
* 动态隐藏软键盘
*/
public static void hideSoftInput(Context context, EditText edit) {
    edit.clearFocus();
    InputMethodManager inputmanger = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
}
```

### <span id = "6.3"/>6.3 点击屏幕空白区域隐藏软键盘
``` java
// 方法1：在onTouch中处理，未获焦点则隐藏
/**
* 在onTouch中处理，未获焦点则隐藏
*/
@Override
public boolean onTouchEvent(MotionEvent event) {
    if (null != this.getCurrentFocus()) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    return super.onTouchEvent(event);
}

// 方法2：根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，需重写dispatchTouchEvent
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        View v = getCurrentFocus();
        if (isShouldHideKeyboard(v, ev)) {
            hideKeyboard(v.getWindowToken());
        }
    }
    return super.dispatchTouchEvent(ev);
}

/**
* 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
*/
private boolean isShouldHideKeyboard(View v, MotionEvent event) {
    if (v != null && (v instanceof EditText)) {
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0],
                top = l[1],
                bottom = top + v.getHeight(),
                right = left + v.getWidth();
        return !(event.getX() > left && event.getX() < right
                && event.getY() > top && event.getY() < bottom);
    }
    return false;
}

/**
* 获取InputMethodManager，隐藏软键盘
*/
private void hideKeyboard(IBinder token) {
    if (token != null) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
```

### <span id = "6.4"/>6.4 动态显示软键盘
``` java
/**
* 动态显示软键盘
*/
public static void showSoftInput(Context context, EditText edit) {
    edit.setFocusable(true);
    edit.setFocusableInTouchMode(true);
    edit.requestFocus();
    InputMethodManager inputManager = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.showSoftInput(edit, 0);
}
```

### <span id = "6.5"/>6.5 切换键盘显示与否状态
``` java
/**
* 切换键盘显示与否状态
*/
public static void toggleSoftInput(Context context, EditText edit) {
    edit.setFocusable(true);
    edit.setFocusableInTouchMode(true);
    edit.requestFocus();
    InputMethodManager inputManager = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
}
```

********************************************************************************************
# <span id = "7"/>7 正则相关
### <span id = "7.1"/>7.1 正则工具类
``` java
public class RegularUtils {
    //验证手机号
    private static final String REGEX_MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    //验证座机号,正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx
    private static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    //验证邮箱
    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //验证url
    private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    //验证汉字
    private static final String REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$";
    //验证用户名,取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
    private static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    //验证IP地址
    private static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    //If u want more please visit http://toutiao.com/i6231678548520731137/
 
    /**
     * @param string 待验证文本
     * @return 是否符合手机号格式
     */
    public static boolean isMobile(String string) {
        return isMatch(REGEX_MOBILE, string);
    }
 
    /**
     * @param string 待验证文本
     * @return 是否符合座机号码格式
     */
    public static boolean isTel(String string) {
        return isMatch(REGEX_TEL, string);
    }
 
    /**
     * @param string 待验证文本
     * @return 是否符合邮箱格式
     */
    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }
 
    /**
     * @param string 待验证文本
     * @return 是否符合网址格式
     */
    public static boolean isURL(String string) {
        return isMatch(REGEX_URL, string);
    }
 
    /**
     * @param string 待验证文本
     * @return 是否符合汉字
     */
    public static boolean isChz(String string) {
        return isMatch(REGEX_CHZ, string);
    }
 
    /**
     * @param string 待验证文本
     * @return 是否符合用户名
     */
    public static boolean isUsername(String string) {
        return isMatch(REGEX_USERNAME, string);
    }
 
    /**
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean isMatch(String regex, String string) {
        return !TextUtils.isEmpty(string) && Pattern.matches(regex, string);
    }
}
```

********************************************************************************************
# <span id = "8"/>8 加解密相关
### <span id = "8.1"/>8.1 MD5加密
``` java
/**
* MD5加密
*/
public static String encryptMD5(String data) throws Exception {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    return new BigInteger(md5.digest(data.getBytes())).toString(16);
}
```

### <span id = "8.2"/>8.2 SHA加密
```
/**
* SHA加密
*/
public static String encryptSHA(String data) throws Exception {
    MessageDigest sha = MessageDigest.getInstance("SHA");
    return new BigInteger(sha.digest(data.getBytes())).toString(32);
}
```

********************************************************************************************
# <span id = "x"/>x 未归类
### <span id = "x.1"/>x.1 获取服务是否开启
```
/**
* 获取服务是否开启
*/
public static boolean isRunningService(String className, Context context) {
    //进程的管理者,活动的管理者
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    //获取正在运行的服务
    List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);//maxNum 返回正在运行的服务的上限个数,最多返回多少个服务
    //遍历集合
    for (RunningServiceInfo runningServiceInfo : runningServices) {
        //获取控件的标示
        ComponentName service = runningServiceInfo.service;
        //获取正在运行的服务的全类名
        String className2 = service.getClassName();
        //将获取到的正在运行的服务的全类名和传递过来的服务的全类名比较,一直表示服务正在运行  返回true,不一致表示服务没有运行  返回false
        if (className.equals(className2)) {
            return true;
        }
    }
    return false;
}
```

********************************************************************************************
# <span id = "log"/> 更新Log
### 2016/7/31 新增点击屏幕空白区域隐藏软键盘
### 2016/7/31 新增目录跳转功能