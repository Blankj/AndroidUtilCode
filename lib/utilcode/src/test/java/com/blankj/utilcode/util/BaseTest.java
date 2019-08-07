package com.blankj.utilcode.util;


import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.concurrent.Executor;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/08/03
 *     desc  :
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, shadows = {ShadowLog.class})
public class BaseTest {

    @BusUtils.Bus(tag = "base")
    public void noParamFun(int i) {
        System.out.println("base" + i);
    }

    public BaseTest() {
        ShadowLog.stream = System.out;
        ThreadUtils.setDeliver(new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                command.run();
            }
        });
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {

    }
}