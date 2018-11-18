package com.blankj.utilcode.util;

import android.support.annotation.Nullable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

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
@Config(manifest = Config.NONE)
public class BaseTest {

    public BaseTest() {
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() {
        ThreadUtils.SimpleTask<String> task = new ThreadUtils.SimpleTask<String>() {
            @Nullable
            @Override
            public String doInBackground() throws Throwable {
                boolean fl = true;
                while (fl) {
                    System.out.println("haha");
                }
                return null;
            }

            @Override
            public void onSuccess(@Nullable String result) {

            }
        };
        ThreadUtils.executeByCpuWithDelay(task, 20, TimeUnit.SECONDS);
//        task.cancel();
//        ThreadUtils.cancel(task);
    }
}
