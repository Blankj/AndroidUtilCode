package com.blankj.utilcode.util;


import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

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
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            ThreadUtils.executeByCpu(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    System.out.println("" + Thread.currentThread() + finalI);
                    Thread.sleep(100);
                    return null;
                }

                @Override
                public void onSuccess(Void result) {

                }
            });
        }

        latch.await(1, TimeUnit.SECONDS);
    }
}
