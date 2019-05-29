package com.blankj.utilcode.pkg.feature.adaptScreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.util.ArrayMap;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/05/20
 *     desc  :
 * </pre>
 */
public class AppStoreUtils {

    private static final String TAG = "AppStoreUtils";

    private static final String larkPkg = "com.ss.android.lark";

    private static ArrayMap<String, String> appStore;

    /**
     * 判断手机是否有应用商店
     */
    public static boolean hasAppStore(final Context context) {
        if (context == null) return false;
        Uri uri = Uri.parse("appStore://details?id=" + larkPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return getAvailableIntentSize(context, intent) > 0;
    }

    /**
     * 跳转到应用商店的 lark 页面
     * 默认跳转
     */
    public static void go2AppStoreAtLarkPage(final Context context) {
        if (context == null) return;
        try {
            Uri uri = Uri.parse("appStore://details?id=" + larkPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            int availableIntentSize = getAvailableIntentSize(context, intent);
            if (availableIntentSize == 0) {
                LogUtils.e(TAG, "No app store!");
            } else if (availableIntentSize == 1) {
                context.startActivity(intent);
                LogUtils.e(TAG, "Show app store");
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    int systemSize = context.getPackageManager()
                            .queryIntentActivities(intent, PackageManager.MATCH_SYSTEM_ONLY)
                            .size();// 获取系统商店个数
                    if (systemSize > 0) {
                        go2FirstAppStore(context, intent, PackageManager.MATCH_SYSTEM_ONLY);
                        return;
                    }
                }
                go2AppStoreWithPriority(context, intent);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
    }

    private static void go2AppStoreWithPriority(final Context context, final Intent intent) {
        if (appStore == null) {
            appStore = new ArrayMap<>();
            appStore.put("yingyongbao", "com.tencent.android.qqdownloader");
            appStore.put("360", "com.qihoo.appstore");
            appStore.put("wandoujia", "com.wandoujia.phoenix2");
            appStore.put("xiaomi", "com.xiaomi.shop");
            appStore.put("oppo", "com.oppo.appStore");
            appStore.put("huawei", "com.huawei.appmarket");
            appStore.put("meizu", "com.meizu.mstore");
            appStore.put("vivo", "com.bbk.appstore");
            appStore.put("sanxing", "com.sec.android.app.samsungapps");
            appStore.put("baidu", "com.baidu.appsearch");
            appStore.put("sougou", "com.sogou.appmall");
            appStore.put("yingyonghui", "com.yingyonghui.appStore");
            appStore.put("anzhi", "cn.goapk.appStore");
        }

        List<String> list = getAppStoreList();
        if (list == null || list.isEmpty()) {// 商店列表为空则默认跳转到第一个
            go2FirstAppStore(context, intent, PackageManager.MATCH_DEFAULT_ONLY);
            return;
        }
        for (String s : list) {
            String pkgName = appStore.get(s);
            if (pkgName != null) {
                intent.setPackage(pkgName);
                if (getAvailableIntentSize(context, intent) > 0) {
                    go2FirstAppStore(context, intent, PackageManager.MATCH_DEFAULT_ONLY);
                    return;
                }
            }
        }

        go2FirstAppStore(context, intent, PackageManager.MATCH_DEFAULT_ONLY);
    }

    private static List<String> getAppStoreList() {
        return null;
    }

    private static void go2FirstAppStore(Context context, Intent intent, int matchDefaultOnly) {
        String packageName = context.getPackageManager()
                .queryIntentActivities(intent, matchDefaultOnly)
                .get(0).activityInfo.packageName;
        intent.setPackage(packageName);
        context.startActivity(intent);
    }

    private static int getAvailableIntentSize(Context context, final Intent intent) {
        return context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size();
    }
}
