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
