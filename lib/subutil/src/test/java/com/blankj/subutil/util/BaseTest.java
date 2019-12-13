package com.blankj.subutil.util;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.concurrent.Executor;

/**
 * Copy from {@link com.blankj.utilcode.util.BaseTest}
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/08/03
 *     desc  :
 * </pre>
 */
@RunWith(BaseTest.MRobolectricTestRunner.class)
@Config(manifest = Config.NONE, shadows = {ShadowLog.class}, sdk = 28)
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

    public static class MRobolectricTestRunner extends RobolectricTestRunner {

        static {
            //Robolectric下载龟速问题，可以去阿里云下载后放到【USER_HOME\.m2\repository\org\robolectric\android-all】下
            //https://maven.aliyun.com/repository/public
            //<dependency>
            //    <groupId>org.robolectric</groupId>
            //    <artifactId>android-all</artifactId>
            //    <version>4.1.2_r1-robolectric-r1</version>
            //</dependency>

            System.setProperty("robolectric.dependency.repo.id", "customMaven");
            System.setProperty("robolectric.dependency.repo.url", "http://repo1.maven.org/maven2");
        }

        public MRobolectricTestRunner(Class<?> testClass) throws InitializationError {
            super(testClass);
        }

    }

}

