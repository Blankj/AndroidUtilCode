package com.blankj.utildebug.debug.tool;

import com.blankj.utildebug.debug.IDebug;
import com.blankj.utildebug.debug.tool.appInfo.AppInfoDebug;
import com.blankj.utildebug.debug.tool.clearCache.ClearCacheDebug;
import com.blankj.utildebug.debug.tool.clearStorage.ClearStorageDebug;
import com.blankj.utildebug.debug.tool.deviceInfo.DeviceInfoDebug;
import com.blankj.utildebug.debug.tool.fileExplorer.FileExplorerDebug;
import com.blankj.utildebug.debug.tool.logcat.LogcatDebug;
import com.blankj.utildebug.debug.tool.restartApp.RestartAppDebug;

import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/04
 *     desc  :
 * </pre>
 */
public abstract class AbsToolDebug implements IDebug {

    @Override
    public int getCategory() {
        return TOOLS;
    }

    public static void addToolDebugs(List<IDebug> debugList) {
        debugList.add(new AppInfoDebug());
        debugList.add(new DeviceInfoDebug());
        debugList.add(new FileExplorerDebug());
        debugList.add(new LogcatDebug());
        debugList.add(new RestartAppDebug());
        debugList.add(new ClearStorageDebug());
        debugList.add(new ClearCacheDebug());
    }
}
