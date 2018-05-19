package com.blankj.androidutilcode.helper;

import com.blankj.androidutilcode.Config;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/07
 *     desc  : helper about assert
 * </pre>
 */
public class AssertHelper {

    public static void releaseInstallApk(final OnReleasedListener listener) {
        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    FileIOUtils.writeFileFromIS(
                            Config.TEST_APK_PATH,
                            Utils.getApp().getAssets().open("test_install"),
                            false
                    );
                    return null;
                }

                @Override
                public void onSuccess(Void result) {
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
