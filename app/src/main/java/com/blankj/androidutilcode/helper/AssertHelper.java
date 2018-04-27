package com.blankj.androidutilcode.helper;

import com.blankj.androidutilcode.Config;
import com.blankj.subutil.util.ThreadPoolUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

import java.io.IOException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/07
 *     desc  :
 * </pre>
 */
public class AssertHelper {

    public static void releaseInstallApk(final OnReleasedListener listener) {
        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
            ThreadPoolUtils poolUtils = new ThreadPoolUtils(ThreadPoolUtils.SingleThread, 1);
            poolUtils.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        FileIOUtils.writeFileFromIS(
                                Config.TEST_APK_PATH,
                                Utils.getApp().getAssets().open("test_install"),
                                false
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (listener != null) {
                        listener.onReleased();
                    }
                }
            });
        } else {
            if (listener != null) {
                listener.onReleased();
            }
            LogUtils.d("test apk existed.");
        }
    }

    public interface OnReleasedListener {
        void onReleased();
    }
}
