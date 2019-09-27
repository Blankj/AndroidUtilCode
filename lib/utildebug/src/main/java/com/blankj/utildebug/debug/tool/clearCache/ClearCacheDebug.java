package com.blankj.utildebug.debug.tool.clearCache;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.debug.tool.AbsToolDebug;
import com.blankj.utildebug.menu.DebugMenu;

import java.io.File;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/04
 *     desc  :
 * </pre>
 */
public class ClearCacheDebug extends AbsToolDebug {

    private ThreadUtils.SimpleTask<Long> clearCacheTask;

    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        return R.drawable.du_ic_debug_clear_cache;
    }

    @Override
    public int getName() {
        return R.string.du_clear_cache;
    }

    @Override
    public void onClick(View view) {
        clearCache();
    }

    private void clearCache() {
        DebugMenu.getInstance().dismiss();
        if (clearCacheTask != null && !clearCacheTask.isDone()) {
            ToastUtils.showShort("Cleaning...");
            return;
        }
        clearCacheTask = createClearCacheTask();
        ThreadUtils.executeByIo(clearCacheTask);
    }

    private ThreadUtils.SimpleTask<Long> createClearCacheTask() {
        return new ThreadUtils.SimpleTask<Long>() {
            @Override
            public Long doInBackground() throws Throwable {
                try {
                    long len = 0;
                    File appDataDir = new File(PathUtils.getInternalAppDataPath());
                    if (appDataDir.exists()) {
                        String[] names = appDataDir.list();
                        for (String name : names) {
                            if (!name.equals("lib")) {
                                File file = new File(appDataDir, name);
                                len += FileUtils.getLength(file);
                                FileUtils.delete(file);
                                LogUtils.i("「" + file + "」 was deleted.");
                            }
                        }
                    }
                    String externalAppCachePath = PathUtils.getExternalAppCachePath();
                    len += FileUtils.getLength(externalAppCachePath);
                    FileUtils.delete(externalAppCachePath);
                    LogUtils.i("「" + externalAppCachePath + "」 was deleted.");
                    return len;
                } catch (Exception e) {
                    ToastUtils.showLong(e.toString());
                    return -1L;
                }
            }

            @Override
            public void onSuccess(Long result) {
                if (result != -1) {
                    ToastUtils.showLong("Clear Cache: " + ConvertUtils.byte2FitMemorySize(result));
                }
            }
        };
    }
}
