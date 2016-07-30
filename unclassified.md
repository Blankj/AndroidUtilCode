# 未归类
### 获取服务是否开启
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

### MD5加密
``` java
/**
* MD5加密
*/
public static String passwordMD5(String password) {
    StringBuilder sb = new StringBuilder();
    try {
        //1.获取数据摘要器
        //arg0 : 加密的方式
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //2.将一个byte数组进行加密,返回的是一个加密过的byte数组,二进制的哈希计算,md5加密的第一步
        byte[] digest = messageDigest.digest(password.getBytes());
        //3.遍历byte数组
        for (int i = 0; i < digest.length; i++) {
            //4.MD5加密
            //byte值    -128-127
            int result = digest[i] & 0xff;
            //将得到int类型转化成16进制字符串
            //String hexString = Integer.toHexString(result)+1;//不规则加密,加盐
            String hexString = Integer.toHexString(result);
            if (hexString.length() < 2) {
                //                    System.out.print("0");
                sb.append("0");
            }
            //System.out.println(hexString);
            //e10adc3949ba59abbe56e057f20f883e
            sb.append(hexString);
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        //找不到加密方式的异常
        e.printStackTrace();
    }
    return null;
}
```
