# 手机相关
### 判断设备是否是手机
``` java
/**
 * 判断设备是否是手机
 */
public static boolean isPhone(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
}
```

### 获取手机的IMIE
``` java
/**
 * 获取当前设备的IMIE
 * <p>需与上面的isPhone一起使用
 * <p>需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 */
public static String getPhoneIMEI(Context context) {
    String deviceId;
    if (isPhone(context)) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
    } else {
        deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    return deviceId;
}
```

### 获取手机状态信息
``` java
/**
 * 获取手机状态信息
 * <p>需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <p>返回如下
 * <pre>
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
 * <pre/>
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

### 跳至填充好phoneNumber的拨号界面
``` java
/**
 * 跳至填充好phoneNumber的拨号界面
 */
public static void dial(Context context, String phoneNumber) {
    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
}
```

### 拨打phoneNumber
``` java
/**
 * 拨打phoneNumber
 * <p>需添加权限<uses-permission android:name="android.permission.CALL_PHONE"/>
 */
public static void call(Context context, String phoneNumber) {
    context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
}
```

### 发送短信
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

### 获取手机联系人
``` java
/**
 * 获取手机联系人
 * <p>需添加权限<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <p>需添加权限<uses-permission android:name="android.permission.READ_CONTACTS" />
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

### 打开手机联系人界面点击联系人后便获取该号码
``` java
/**
 * 打开手机联系人界面点击联系人后便获取该号码
 * <p>参照以下注释代码
 */
public static void getContantNum() {
    Log.i("tips", "U should copy the follow code.");
    /*
    Intent intent = new Intent();
    intent.setAction("android.intent.action.PICK");
    intent.setType("vnd.android.cursor.dir/phone_v2");
    startActivityForResult(intent, 0);
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
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
    */
}
```

### 获取手机短信并保存到xml中
``` java
/**
 * 获取手机短信并保存到xml中
 * <p>需添加权限<uses-permission android:name="android.permission.READ_SMS"/>
 * <p>需添加权限<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
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
