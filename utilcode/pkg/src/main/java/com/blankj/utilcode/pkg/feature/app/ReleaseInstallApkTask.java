package com.blankj.utilcode.pkg.feature.app;

import com.blankj.utilcode.pkg.Config;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ThreadUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/05/23
 *     desc  :
 * </pre>
 */
public class ReleaseInstallApkTask extends ThreadUtils.SimpleTask<Void> {

    private OnReleasedListener mListener;

    public ReleaseInstallApkTask(final OnReleasedListener listener) {
        mListener = listener;
    }

    @Override
    public Void doInBackground() {
        ResourceUtils.copyFileFromAssets("test_install", Config.TEST_APK_PATH);
        return null;
    }

    @Override
    public void onSuccess(Void result) {
        if (mListener != null) {
            mListener.onReleased();
        }
    }

    public void execute() {
        ThreadUtils.executeByIo(this);
    }
}
