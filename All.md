<p>为方便查找，已进行大致归类，其大纲如下所示： </p>

<blockquote><ul><li><a href="#1">1 尺寸相关</a><ul><li><a href="#1.1">1.1 dp与px转换</a></li><li><a href="#1.2">1.2 sp与px转换</a></li><li><a href="#1.3">1.3 各种单位转换</a></li><li><a href="#1.4">1.4 在onCreate()即可获取View的宽高</a></li><li><a href="#1.5">1.5 ListView中提前测量View尺寸</a></li></ul></li><li><a href="#2">2 手机相关</a><ul><li><a href="#2.1">2.1 判断设备是否是手机</a></li><li><a href="#2.2">2.2 获取当前设备的IMIE，需与上面的isPhone一起使用</a></li><li><a href="#2.3">2.3 获取手机状态信息</a></li><li><a href="#2.4">2.4 是否有SD卡</a></li><li><a href="#2.5">2.5 获取MAC地址</a></li><li><a href="#2.6">2.6 获取手机厂商，如Xiaomi</a></li><li><a href="#2.7">2.7 获取手机型号，如MI2SC</a></li><li><a href="#2.8">2.8 跳转至拨号界面</a></li><li><a href="#2.9">2.9 拨打电话</a></li><li><a href="#2.10">2.10 发送短信</a></li><li><a href="#2.11">2.11 获取手机联系人</a></li><li><a href="#2.12">2.12 直接打开手机联系人界面，并获取联系人号码</a></li><li><a href="#2.13">2.13 获取手机短信并保存到xml中</a></li></ul></li><li><a href="#3">3 网络相关</a><ul><li><a href="#3.1">3.1 打开网络设置界面</a></li><li><a href="#3.2">3.2 判断是否网络连接</a></li><li><a href="#3.1">3.3 判断wifi是否连接状态</a></li><li><a href="#3.4">3.4 获取移动网络运营商名称，如中国联通、中国移动、中国电信</a></li><li><a href="#3.5">3.5 返回移动终端类型</a></li><li><a href="#3.6">3.6 判断手机连接的网络类型(2G,3G,4G)</a></li><li><a href="#3.7">3.7 判断当前手机的网络类型(WIFI还是2,3,4G)</a></li></ul></li><li><a href="#4">4 App相关</a><ul><li><a href="#4.1">4.1 安装指定路径下的Apk</a></li><li><a href="#4.2">4.2 卸载指定包名的App</a></li><li><a href="#4.3">4.3 获取App名称</a></li><li><a href="#4.4">4.4 获取当前App版本号</a></li><li><a href="#4.5">4.5 打开指定包名的App</a></li><li><a href="#4.6">4.6 打开指定包名的App应用信息界面</a></li><li><a href="#4.7">4.7 分享Apk信息</a></li><li><a href="#4.8">4.8 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)</a></li><li><a href="#4.9">4.9 判断当前App处于前台还是后台</a></li></ul></li><li><a href="#5">5 屏幕相关</a><ul><li><a href="#5.1">5.1 获取手机分辨率</a></li><li><a href="#5.2">5.2 获取状态栏高度</a></li><li><a href="#5.3">5.3 获取状态栏高度＋标题栏(ActionBar)高度</a></li><li><a href="#5.4">5.4 获取屏幕截图</a></li><li><a href="#5.5">5.5 设置透明状态栏，需在setContentView之前调用</a></li></ul></li><li><a href="#6">6 键盘相关</a><ul><li><a href="#6.1">6.1 避免输入法面板遮挡</a></li><li><a href="#6.2">6.2 动态隐藏软键盘</a></li><li><a href="#6.3">6.3 点击屏幕空白区域隐藏软键盘</a></li><li><a href="#6.4">6.4 动态显示软键盘</a></li><li><a href="#6.5">6.5 切换键盘显示与否状态</a></li></ul></li><li><a href="#7">7 正则相关</a><ul><li><a href="#7.1">7.1 正则工具类</a></li></ul></li><li><a href="#8">8 加解密相关</a><ul><li><a href="#8.1">8.1 MD5加密</a></li><li><a href="#8.2">8.2 SHA加密</a></li></ul></li><li><a href="#x">x 未归类</a><ul><li><a href="#x.1">x.1 获取服务是否开启</a></li></ul></li><li><a href="#log">更新Log</a></li></ul></blockquote>

<p>大部分代码已验证过可行，如有错误，请及时告之。<br/>分类已上传至Github，传送门→<a href="https://github.com/Blankj/AndroidUtilCode">期待你的Star和完善</a>  </p>

<h1>&lt;span id = &quot;1&quot;/&gt;1 尺寸相关</h1>

<h3>&lt;span id = &quot;1.1&quot;/&gt;1.1 dp与px转换</h3>

<p><code></code>` java
/**
<em> dp转px
</em>/
public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
}</p>

<p>/**
<em> px转dp
</em>/
public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
}
<code></code>`</p>

<h3>&lt;span id = &quot;1.2&quot;/&gt;1.2 sp与px转换</h3>

<p><code></code>` java
/**
<em> sp转px
</em>/
public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
}</p>

<p>/**
<em> px转sp
</em>/
public static int px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
}
<code></code>`</p>

<h3>&lt;span id = &quot;1.3&quot;/&gt;1.3 各种单位转换</h3>

<p><code> java
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
</code></p>

<h3>&lt;span id = &quot;1.4&quot;/&gt;1.4 在onCreate()即可获取View的宽高</h3>

<p><code> java
/**
* 在onCreate()即可获取View的宽高
*/
public static int[] getViewMeasure(View view) {
    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    view.measure(widthMeasureSpec, heightMeasureSpec);
    return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
}
</code></p>

<h3>&lt;span id = &quot;1.5&quot;/&gt;1.5 ListView中提前测量View尺寸</h3>

<p><code> java
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
    if (tempHeight &gt; 0) {
        height = MeasureSpec.makeMeasureSpec(tempHeight,
                MeasureSpec.EXACTLY);
    } else {
        height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    }
    view.measure(width, height);
}
</code></p>

<hr/>

<h1>&lt;span id = &quot;2&quot;/&gt;2 手机相关</h1>

<h3>&lt;span id = &quot;2.1&quot;/&gt;2.1 判断设备是否是手机</h3>

<p><code> java
/**
* 判断设备是否是手机
*/
public static boolean isPhone(Context context) {
    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephony.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
}
</code></p>

<h3>&lt;span id = &quot;2.2&quot;/&gt;2.2 获取当前设备的IMIE，需与上面的isPhone一起使用</h3>

<p><code> java
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
</code></p>

<h3>&lt;span id = &quot;2.3&quot;/&gt;2.3 获取手机状态信息</h3>

<p><code> java
// 需添加权限&lt;uses-permission android:name=&quot;android.permission.READ_PHONE_STATE&quot;/&gt;
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
    String str = &quot;&quot;;
    str += &quot;DeviceId(IMEI) = &quot; + tm.getDeviceId() + &quot;\n&quot;;
    str += &quot;DeviceSoftwareVersion = &quot; + tm.getDeviceSoftwareVersion() + &quot;\n&quot;;
    str += &quot;Line1Number = &quot; + tm.getLine1Number() + &quot;\n&quot;;
    str += &quot;NetworkCountryIso = &quot; + tm.getNetworkCountryIso() + &quot;\n&quot;;
    str += &quot;NetworkOperator = &quot; + tm.getNetworkOperator() + &quot;\n&quot;;
    str += &quot;NetworkOperatorName = &quot; + tm.getNetworkOperatorName() + &quot;\n&quot;;
    str += &quot;NetworkType = &quot; + tm.getNetworkType() + &quot;\n&quot;;
    str += &quot;honeType = &quot; + tm.getPhoneType() + &quot;\n&quot;;
    str += &quot;SimCountryIso = &quot; + tm.getSimCountryIso() + &quot;\n&quot;;
    str += &quot;SimOperator = &quot; + tm.getSimOperator() + &quot;\n&quot;;
    str += &quot;SimOperatorName = &quot; + tm.getSimOperatorName() + &quot;\n&quot;;
    str += &quot;SimSerialNumber = &quot; + tm.getSimSerialNumber() + &quot;\n&quot;;
    str += &quot;SimState = &quot; + tm.getSimState() + &quot;\n&quot;;
    str += &quot;SubscriberId(IMSI) = &quot; + tm.getSubscriberId() + &quot;\n&quot;;
    str += &quot;VoiceMailNumber = &quot; + tm.getVoiceMailNumber() + &quot;\n&quot;;
    return str;
}
</code></p>

<h3>&lt;span id = &quot;2.4&quot;/&gt;2.4 是否有SD卡</h3>

<p><code> java
/**
* 是否有SD卡
*/
public static boolean haveSDCard() {
    return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
}
</code></p>

<h3>&lt;span id = &quot;2.5&quot;/&gt;2.5 获取MAC地址</h3>

<p><code> java
// 需添加权限&lt;uses-permission android:name=&quot;android.permission.ACCESS_WIFI_STATE&quot;/&gt;
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
        return &quot;&quot;;
    }
    macAddress = macAddress.replace(&quot;:&quot;, &quot;&quot;);
    return macAddress;
}
</code></p>

<h3>&lt;span id = &quot;2.6&quot;/&gt;2.6 获取手机厂商，如Xiaomi</h3>

<p><code> java
/**
* 获取手机厂商，如Xiaomi
*/
public static String getOsName() {
    String MANUFACTURER = Build.MANUFACTURER;
    return MANUFACTURER;
}
</code></p>

<h3>&lt;span id = &quot;2.7&quot;/&gt;2.7 获取手机型号，如MI2SC</h3>

<p><code> java
/**
* 获取手机型号，如MI2SC
*/
private String getModel() {
    String model = android.os.Build.MODEL;
    if (model != null) {
        model = model.trim().replaceAll(&quot;\\s*&quot;, &quot;&quot;);
    } else {
        model = &quot;&quot;;
    }
    return model;
}
</code></p>

<h3>&lt;span id = &quot;2.8&quot;/&gt;2.8 跳转至拨号界面</h3>

<p><code> java
/**
* 跳转至拨号界面
*/
public static void callDial(Context context, String phoneNumber) {
    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(&quot;tel:&quot; + phoneNumber)));
}
</code></p>

<h3>&lt;span id = &quot;2.9&quot;/&gt;2.9 拨打电话</h3>

<p><code> java
/**
* 拨打电话
*/
public static void call(Context context, String phoneNumber) {
    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(&quot;tel:&quot; + phoneNumber)));
}
</code></p>

<h3>&lt;span id = &quot;2.10&quot;/&gt;2.10 发送短信</h3>

<p><code> java
/**
* 发送短信
*/
public static void sendSms(Context context, String phoneNumber, String content) {
    Uri uri = Uri.parse(&quot;smsto:&quot; + (TextUtils.isEmpty(phoneNumber) ? &quot;&quot; : phoneNumber));
    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
    intent.putExtra(&quot;sms_body&quot;, TextUtils.isEmpty(content) ? &quot;&quot; : content);
    context.startActivity(intent);
}
</code></p>

<h3>&lt;span id = &quot;2.11&quot;/&gt;2.11 获取手机联系人</h3>

<p><code> java
/**
* 获取手机联系人
*/
public static List&lt;HashMap&lt;String, String&gt;&gt; getAllContactInfo(Context context) {
    SystemClock.sleep(3000);
    ArrayList&lt;HashMap&lt;String, String&gt;&gt; list = new ArrayList&lt;HashMap&lt;String, String&gt;&gt;();
    // 1.获取内容解析者
    ContentResolver resolver = context.getContentResolver();
    // 2.获取内容提供者的地址:com.android.contacts
    // raw_contacts表的地址 :raw_contacts
    // view_data表的地址 : data
    // 3.生成查询地址
    Uri raw_uri = Uri.parse(&quot;content://com.android.contacts/raw_contacts&quot;);
    Uri date_uri = Uri.parse(&quot;content://com.android.contacts/data&quot;);
    // 4.查询操作,先查询raw_contacts,查询contact_id
    // projection : 查询的字段
    Cursor cursor = resolver.query(raw_uri, new String[] { &quot;contact_id&quot; },
            null, null, null);
    // 5.解析cursor
    while (cursor.moveToNext()) {
        // 6.获取查询的数据
        String contact_id = cursor.getString(0);
        // cursor.getString(cursor.getColumnIndex(&quot;contact_id&quot;));//getColumnIndex
        // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
        // 判断contact_id是否为空
        if (!TextUtils.isEmpty(contact_id)) {//null   &quot;&quot;
            // 7.根据contact_id查询view_data表中的数据
            // selection : 查询条件
            // selectionArgs :查询条件的参数
            // sortOrder : 排序
            // 空指针: 1.null.方法 2.参数为null
            Cursor c = resolver.query(date_uri, new String[] { &quot;data1&quot;,
                            &quot;mimetype&quot; }, &quot;raw_contact_id=?&quot;,
                    new String[] { contact_id }, null);
            HashMap&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
            // 8.解析c
            while (c.moveToNext()) {
                // 9.获取数据
                String data1 = c.getString(0);
                String mimetype = c.getString(1);
                // 10.根据类型去判断获取的data1数据并保存
                if (mimetype.equals(&quot;vnd.android.cursor.item/phone_v2&quot;)) {
                    // 电话
                    map.put(&quot;phone&quot;, data1);
                } else if (mimetype.equals(&quot;vnd.android.cursor.item/name&quot;)) {
                    // 姓名
                    map.put(&quot;name&quot;, data1);
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
</code></p>

<h3>&lt;span id = &quot;2.12&quot;/&gt;2.12 直接打开手机联系人界面，并获取联系人号码</h3>

<p><code> java
// 在按钮点击事件中设置Intent，
Intent intent = new Intent();
intent.setAction(&quot;android.intent.action.PICK&quot;);
intent.addCategory(&quot;android.intent.category.DEFAULT&quot;);
intent.setType(&quot;vnd.android.cursor.dir/phone_v2&quot;);
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
            num = cursor.getString(cursor.getColumnIndex(&quot;data1&quot;));
        }
        cursor.close();
        num = num.replaceAll(&quot;-&quot;, &quot;&quot;);//替换的操作,555-6 -&gt; 5556
    }
}
</code></p>

<h3>&lt;span id = &quot;2.13&quot;/&gt;2.13 获取手机短信并保存到xml中</h3>

<p><code> java
/**
* 获取手机短信并保存到xml中
*/
public static void getAllSMS(Context context) {
    //1.获取短信
    //1.1获取内容解析者
    ContentResolver resolver = context.getContentResolver();
    //1.2获取内容提供者地址   sms,sms表的地址:null  不写
    //1.3获取查询路径
    Uri uri = Uri.parse(&quot;content://sms&quot;);
    //1.4.查询操作
    //projection : 查询的字段
    //selection : 查询的条件
    //selectionArgs : 查询条件的参数
    //sortOrder : 排序
    Cursor cursor = resolver.query(uri, new String[]{&quot;address&quot;, &quot;date&quot;, &quot;type&quot;, &quot;body&quot;}, null, null, null);
    //设置最大进度
    int count = cursor.getCount();//获取短信的个数
    //2.备份短信
    //2.1获取xml序列器
    XmlSerializer xmlSerializer = Xml.newSerializer();
    try {
        //2.2设置xml文件保存的路径
        //os : 保存的位置
        //encoding : 编码格式
        xmlSerializer.setOutput(new FileOutputStream(new File(&quot;/mnt/sdcard/backupsms.xml&quot;)), &quot;utf-8&quot;);
        //2.3设置头信息
        //standalone : 是否独立保存
        xmlSerializer.startDocument(&quot;utf-8&quot;, true);
        //2.4设置根标签
        xmlSerializer.startTag(null, &quot;smss&quot;);
        //1.5.解析cursor
        while (cursor.moveToNext()) {
            SystemClock.sleep(1000);
            //2.5设置短信的标签
            xmlSerializer.startTag(null, &quot;sms&quot;);
            //2.6设置文本内容的标签
            xmlSerializer.startTag(null, &quot;address&quot;);
            String address = cursor.getString(0);
            //2.7设置文本内容
            xmlSerializer.text(address);
            xmlSerializer.endTag(null, &quot;address&quot;);
            xmlSerializer.startTag(null, &quot;date&quot;);
            String date = cursor.getString(1);
            xmlSerializer.text(date);
            xmlSerializer.endTag(null, &quot;date&quot;);
            xmlSerializer.startTag(null, &quot;type&quot;);
            String type = cursor.getString(2);
            xmlSerializer.text(type);
            xmlSerializer.endTag(null, &quot;type&quot;);
            xmlSerializer.startTag(null, &quot;body&quot;);
            String body = cursor.getString(3);
            xmlSerializer.text(body);
            xmlSerializer.endTag(null, &quot;body&quot;);
            xmlSerializer.endTag(null, &quot;sms&quot;);
            System.out.println(&quot;address:&quot; + address + &quot;   date:&quot; + date + &quot;  type:&quot; + type + &quot;  body:&quot; + body);
        }
        xmlSerializer.endTag(null, &quot;smss&quot;);
        xmlSerializer.endDocument();
        //2.8将数据刷新到文件中
        xmlSerializer.flush();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
</code></p>

<hr/>

<h1>&lt;span id = &quot;3&quot;/&gt;3 网络相关</h1>

<h3>&lt;span id = &quot;3.1&quot;/&gt;3.1 打开网络设置界面</h3>

<p><code> java
/**
* 打开网络设置界面
*/
public static void openSetting(Activity activity) {
    Intent intent = new Intent(&quot;/&quot;);
    ComponentName cm = new ComponentName(&quot;com.android.settings&quot;,
            &quot;com.android.settings.WirelessSettings&quot;);
    intent.setComponent(cm);
    intent.setAction(&quot;android.intent.action.VIEW&quot;);
    activity.startActivityForResult(intent, 0);
}
</code></p>

<h3>&lt;span id = &quot;3.2&quot;/&gt;3.2 判断是否网络连接</h3>

<p><code> java
/**
* 判断是否网络连接
*/
public static boolean isOnline(Context context) {
    ConnectivityManager manager = (ConnectivityManager) context
            .getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    if (info != null &amp;&amp; info.isConnected()) {
        return true;
    }
    return false;
}
</code></p>

<h3>&lt;span id = &quot;3.3&quot;/&gt;3.3 判断wifi是否连接状态</h3>

<p><code> java
/**
* 判断wifi是否连接状态
*/
public static boolean isWifi(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm != null &amp;&amp; cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
}
</code></p>

<h3>&lt;span id = &quot;3.4&quot;/&gt;3.4 获取移动网络运营商名称，如中国联通、中国移动、中国电信</h3>

<p><code> java
/**
* 获取移动网络运营商名称，如中国联通、中国移动、中国电信
*/
public static String getNetworkOperatorName(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getNetworkOperatorName();
}
</code></p>

<h3>&lt;span id = &quot;3.5&quot;/&gt;3.5 返回移动终端类型</h3>

<p><code> java
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
</code></p>

<h3>&lt;span id = &quot;3.6&quot;/&gt;3.6 判断手机连接的网络类型(2G,3G,4G)</h3>

<p><code> java
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
     * &quot;2G&quot; networks
     */
    public static final int NETWORK_CLASS_2_G = 2;
    /**
     * &quot;3G&quot; networks
     */
    public static final int NETWORK_CLASS_3_G = 3;
    /**
     * &quot;4G&quot; networks
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
</code></p>

<h3>&lt;span id = &quot;3.7&quot;/&gt;3.7 判断当前手机的网络类型(WIFI还是2,3,4G)</h3>

<p><code> java
/**
* 判断当前手机的网络类型(WIFI还是2,3,4G)，需要用到上面的方法
*/
public static int getNetWorkStatus(Context context) {
    int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
    ConnectivityManager connectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo != null &amp;&amp; networkInfo.isConnected()) {
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            netWorkType = Constants.NETWORK_WIFI;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            netWorkType = getNetWorkClass(context);
        }
    }
    return netWorkType;
}
</code></p>

<hr/>

<h1>&lt;span id = &quot;4&quot;/&gt;4 App相关</h1>

<h3>&lt;span id = &quot;4.1&quot;/&gt;4.1 安装指定路径下的Apk</h3>

<p><code> java
/**
* 安装指定路径下的Apk
*/
public void installApk(String filePath) {
    Intent intent = new Intent();
    intent.setAction(&quot;android.intent.action.VIEW&quot;);
    intent.addCategory(&quot;android.intent.category.DEFAULT&quot;);
    intent.setDataAndType(Uri.fromFile(new File(filePath)), &quot;application/vnd.android.package-archive&quot;);
    startActivityForResult(intent, 0);
}
</code></p>

<h3>&lt;span id = &quot;4.2&quot;/&gt;4.2 卸载指定包名的App</h3>

<p><code> java
/**
* 卸载指定包名的App
*/
public void uninstallApp(String packageName) {
    Intent intent = new Intent();
    intent.setAction(&quot;android.intent.action.DELETE&quot;);
    intent.addCategory(&quot;android.intent.category.DEFAULT&quot;);
    intent.setData(Uri.parse(&quot;package:&quot; + packageName));
    startActivityForResult(intent, 0);
}
</code></p>

<h3>&lt;span id = &quot;4.3&quot;/&gt;4.3 获取App名称</h3>

<p><code>
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
</code></p>

<h3>&lt;span id = &quot;4.4&quot;/&gt;4.4 获取当前App版本号</h3>

<p><code> java
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
</code></p>

<h3>&lt;span id = &quot;4.5&quot;/&gt;4.5 打开指定包名的App</h3>

<p><code>
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
</code></p>

<h3>&lt;span id = &quot;4.6&quot;/&gt;4.6 打开指定包名的App应用信息界面</h3>

<p><code> java
/**
* 打开指定包名的App应用信息界面
*/
public void showAppInfo(String packageName) {
    Intent intent = new Intent();
    intent.setAction(&quot;android.settings.APPLICATION_DETAILS_SETTINGS&quot;);
    intent.setData(Uri.parse(&quot;package:&quot; + packageName));
    startActivity(intent);
}
</code></p>

<h3>&lt;span id = &quot;4.7&quot;/&gt;4.7 分享Apk信息</h3>

<p><code> java
/**
* 分享Apk信息
*/
public void shareApkInfo(String info) {
    Intent intent = new Intent();
    intent.setAction(&quot;android.intent.action.SEND&quot;);
    intent.addCategory(&quot;android.intent.category.DEFAULT&quot;);
    intent.setType(&quot;text/plain&quot;);
    intent.putExtra(Intent.EXTRA_TEXT, info);
    startActivity(intent);
}
</code></p>

<h3>&lt;span id = &quot;4.8&quot;/&gt;4.8 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)</h3>

<p><code></code>` java
/**
<em> 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)
</em>/
public class AppEnging {
    public static List&lt;AppInfo&gt; getAppInfos(Context context) {
        List&lt;AppInfo&gt; list = new ArrayList&lt;AppInfo&gt;();
        //获取应用程序信息
        //包的管理者
        PackageManager pm = context.getPackageManager();
        //获取系统中安装到所有软件信息
        List&lt;PackageInfo&gt; installedPackages = pm.getInstalledPackages(0);
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
            if ((applicationInfo.FLAG<em>SYSTEM &amp; flags) == applicationInfo.FLAG</em>SYSTEM) {
                //系统程序
                isUser = false;
            } else {
                //用户程序
                isUser = true;
            }
            //是否安装到SD卡
            boolean isSD;
            if ((applicationInfo.FLAG<em>EXTERNAL</em>STORAGE &amp; flags) == applicationInfo.FLAG<em>EXTERNAL</em>STORAGE) {
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
}</p>

<p>// 封装软件信息的bean类
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
    private boolean isUser;</p>

<pre><code>public AppInfo() {
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
}</code></pre>

<p>}
<code></code>`</p>

<h3>&lt;span id = &quot;4.9&quot;/&gt;4.9 判断当前App处于前台还是后台</h3>

<p><code> java
// 需添加&lt;uses-permission android:name=&quot;android.permission.GET_TASKS&quot;/&gt;
// 并且必须是系统应用该方法才有效
/**
* 判断当前App处于前台还是后台
*/
public static boolean isApplicationBackground(final Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    @SuppressWarnings(&quot;deprecation&quot;)
    List&lt;ActivityManager.RunningTaskInfo&gt; tasks = am.getRunningTasks(1);
    if (!tasks.isEmpty()) {
        ComponentName topActivity = tasks.get(0).topActivity;
        if (!topActivity.getPackageName().equals(context.getPackageName())) {
            return true;
        }
    }
    return false;
}
</code></p>

<hr/>

<h1>&lt;span id = &quot;5&quot;/&gt;5 屏幕相关</h1>

<h3>&lt;span id = &quot;5.1&quot;/&gt;5.1 获取手机分辨率</h3>

<p><code></code>` java
/**
<em> 获取屏幕的宽度px
</em>/
public static int getDeviceWidth(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
    return outMetrics.widthPixels;
}</p>

<p>/**
<em> 获取屏幕的高度px
</em>/
public static int getDeviceHeight(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
    return outMetrics.heightPixels;
}
<code></code>`</p>

<h3>&lt;span id = &quot;5.2&quot;/&gt;5.2 获取状态栏高度</h3>

<p><code>
/**
* 获取状态栏高度
*/
public int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier(&quot;status_bar_height&quot;, &quot;dimen&quot;, &quot;android&quot;);
    if (resourceId &gt; 0) {
        result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
}
</code></p>

<h3>&lt;span id = &quot;5.3&quot;/&gt;5.3 获取状态栏高度＋标题栏(ActionBar)高度</h3>

<p><code> java
/**
* 获取状态栏高度＋标题栏(ActionBar)高度
*/
public static int getTopBarHeight(Activity activity) {
    return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
}
</code></p>

<h3>&lt;span id = &quot;5.4&quot;/&gt;5.4 获取屏幕截图</h3>

<p><code></code>` java
/**
<em> 获取当前屏幕截图，包含状态栏
</em>/
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
}</p>

<p>/**
<em> 获取当前屏幕截图，不包含状态栏
</em>/
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
<code></code>`</p>

<h3>&lt;span id = &quot;5.5&quot;/&gt;5.5 设置透明状态栏，需在setContentView之前调用</h3>

<p><code></code>` java
if(Build.VERSION.SDK<em>INT &gt;= Build.VERSION</em>CODES.KITKAT) {
    //透明状态栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG<em>TRANSLUCENT</em>STATUS);
    //透明导航栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG<em>TRANSLUCENT</em>NAVIGATION);
}</p>

<p>// 需在顶部控件布局中加入以下属性让内容出现在状态栏之下
android:clipToPadding=&quot;true&quot; 
android:fitsSystemWindows=&quot;true&quot;
<code></code>`</p>

<hr/>

<h1>&lt;span id = &quot;6&quot;/&gt;6 键盘相关</h1>

<h3>&lt;span id = &quot;6.1&quot;/&gt;6.1 避免输入法面板遮挡</h3>

<p><code> java
// 在manifest.xml中activity中设置
android:windowSoftInputMode=&quot;stateVisible|adjustResize&quot;
</code></p>

<h3>&lt;span id = &quot;6.2&quot;/&gt;6.2 动态隐藏软键盘</h3>

<p><code></code>` java
/**
<em> 动态隐藏软键盘
</em>/
public static void hideSoftInput(Activity activity) {
    View view = activity.getWindow().peekDecorView();
    if (view != null) {
        InputMethodManager inputmanger = (InputMethodManager) activity
                .getSystemService(Context.INPUT<em>METHOD</em>SERVICE);
        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}</p>

<p>/**
<em> 动态隐藏软键盘
</em>/
public static void hideSoftInput(Context context, EditText edit) {
    edit.clearFocus();
    InputMethodManager inputmanger = (InputMethodManager) context
            .getSystemService(Context.INPUT<em>METHOD</em>SERVICE);
    inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
}
<code></code>`</p>

<h3>&lt;span id = &quot;6.3&quot;/&gt;6.3 点击屏幕空白区域隐藏软键盘</h3>

<p><code></code>` java
// 方法1：在onTouch中处理，未获焦点则隐藏
/**
<em> 在onTouch中处理，未获焦点则隐藏
</em>/
@Override
public boolean onTouchEvent(MotionEvent event) {
    if (null != this.getCurrentFocus()) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT<em>METHOD</em>SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    return super.onTouchEvent(event);
}</p>

<p>// 方法2：根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，需重写dispatchTouchEvent
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        View v = getCurrentFocus();
        if (isShouldHideKeyboard(v, ev)) {
            hideKeyboard(v.getWindowToken());
        }
    }
    return super.dispatchTouchEvent(ev);
}</p>

<p>/**
<em> 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
</em>/
private boolean isShouldHideKeyboard(View v, MotionEvent event) {
    if (v != null &amp;&amp; (v instanceof EditText)) {
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0],
                top = l[1],
                bottom = top + v.getHeight(),
                right = left + v.getWidth();
        return !(event.getX() &gt; left &amp;&amp; event.getX() &lt; right
                &amp;&amp; event.getY() &gt; top &amp;&amp; event.getY() &lt; bottom);
    }
    return false;
}</p>

<p>/**
<em> 获取InputMethodManager，隐藏软键盘
</em>/
private void hideKeyboard(IBinder token) {
    if (token != null) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT<em>METHOD</em>SERVICE);
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE<em>NOT</em>ALWAYS);
    }
}
<code></code>`</p>

<h3>&lt;span id = &quot;6.4&quot;/&gt;6.4 动态显示软键盘</h3>

<p><code> java
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
</code></p>

<h3>&lt;span id = &quot;6.5&quot;/&gt;6.5 切换键盘显示与否状态</h3>

<p><code> java
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
</code></p>

<hr/>

<h1>&lt;span id = &quot;7&quot;/&gt;7 正则相关</h1>

<h3>&lt;span id = &quot;7.1&quot;/&gt;7.1 正则工具类</h3>

<p><code></code>` java
public class RegularUtils {
    //验证手机号
    private static final String REGEX<em>MOBILE = &quot;^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$&quot;;
    //验证座机号,正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx
    private static final String REGEX</em>TEL = &quot;^0\d{2,3}[- ]?\d{7,8}&quot;;
    //验证邮箱
    private static final String REGEX_EMAIL = &quot;^\w+([-+.]\w+)<em>@\w+([-.]\w+)</em>\.\w+([-.]\w+)*$&quot;;
    //验证url
    private static final String REGEX<em>URL = &quot;http(s)?://([\w-]+\.)+[\w-]+(/[\w-./?%&amp;=]*)?&quot;;
    //验证汉字
    private static final String REGEX</em>CHZ = &quot;^[\u4e00-\u9fa5]+$&quot;;
    //验证用户名,取值范围为a-z,A-Z,0-9,&quot;<em>&quot;,汉字，不能以&quot;</em>&quot;结尾,用户名必须是6-20位
    private static final String REGEX<em>USERNAME = &quot;^[\w\u4e00-\u9fa5]{6,20}(?&lt;!</em>)$&quot;;
    //验证IP地址
    private static final String REGEX_IP = &quot;((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)&quot;;</p>

<pre><code>//If u want more please visit http://toutiao.com/i6231678548520731137/

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
    return !TextUtils.isEmpty(string) &amp;&amp; Pattern.matches(regex, string);
}</code></pre>

<p>}
<code></code>`</p>

<hr/>

<h1>&lt;span id = &quot;8&quot;/&gt;8 加解密相关</h1>

<h3>&lt;span id = &quot;8.1&quot;/&gt;8.1 MD5加密</h3>

<p><code> java
/**
* MD5加密
*/
public static String encryptMD5(String data) throws Exception {
    MessageDigest md5 = MessageDigest.getInstance(&quot;MD5&quot;);
    return new BigInteger(md5.digest(data.getBytes())).toString(16);
}
</code></p>

<h3>&lt;span id = &quot;8.2&quot;/&gt;8.2 SHA加密</h3>

<p><code>
/**
* SHA加密
*/
public static String encryptSHA(String data) throws Exception {
    MessageDigest sha = MessageDigest.getInstance(&quot;SHA&quot;);
    return new BigInteger(sha.digest(data.getBytes())).toString(32);
}
</code></p>

<hr/>

<h1>&lt;span id = &quot;x&quot;/&gt;x 未归类</h1>

<h3>&lt;span id = &quot;x.1&quot;/&gt;x.1 获取服务是否开启</h3>

<p><code>
/**
* 获取服务是否开启
*/
public static boolean isRunningService(String className, Context context) {
    //进程的管理者,活动的管理者
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    //获取正在运行的服务
    List&lt;RunningServiceInfo&gt; runningServices = activityManager.getRunningServices(1000);//maxNum 返回正在运行的服务的上限个数,最多返回多少个服务
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
</code></p>

<hr/>

<h1>&lt;span id = &quot;log&quot;/&gt; 更新Log</h1>

<h3>2016/7/31 新增点击屏幕空白区域隐藏软键盘</h3>

<h3>2016/7/31 新增目录跳转功能</h3>
