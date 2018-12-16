package com.blankj.utilcode.pkg.feature.blur;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2018/12/13
 *     desc  :
 * </pre>
 */
public class InputReceiver extends BroadcastReceiver {

    public static void register(Context context) {
        InputReceiver innerReceiver = new InputReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        context.registerReceiver(innerReceiver, intentFilter);
    }

    final String SYSTEM_DIALOG_REASON_KEY = "reason";

    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    BlurActivity.leave(true);
                    TransparentActivity.start();
                    LogUtils.e();
                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    BlurActivity.leave(true);
                    TransparentActivity.start();
                    LogUtils.e();
                }
            }
        }
    }
}
