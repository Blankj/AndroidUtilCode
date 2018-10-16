package com.blankj.utilcode;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/10/08
 *     desc  :
 * </pre>
 */
public class BusTest {

    @BusUtils.Subscribe(name = "lib")
    public static String libBus(String name) {
        LogUtils.e(name);
        return "libBus";
    }
}
