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
        ThreadUtils.executeBySingleAtFixRate(new ThreadUtils.SimpleTask<Void>() {
            @Override
            public Void doInBackground() throws Throwable {
                System.out.println("haha");
                return null;
            }

            @Override
            public void onSuccess(Void result) {
                System.out.println("onSuccess");
            }
        }, 1, TimeUnit.SECONDS);


        final CountDownLatch countDownLatch = new CountDownLatch(1);
////        for (int i = 0; i < 100; i++) {
////            final int finalI = i;
//            ThreadUtils.Task<Void> task = new ThreadUtils.Task<Void>() {
//
//                @Override
//                public Void doInBackground() throws Throwable {
//                    for (int j = 0; j < 10000; j++) {
//                        Thread.sleep(100);
//                        System.out.println(j);
//                    }
//                    return null;
//                }
//
//                @Override
//                public void onSuccess(@Nullable Void result) {
//                    System.out.println(Thread.currentThread().getName());
//                }
//
//                @Override
//                public void onCancel() {
//                    System.out.println("onCancel");
//                }
//
//                @Override
//                public void onFail(Throwable t) {
//                    System.out.println(t + "onFail");
//                }
//            };
//            ThreadUtils.executeBySingle(task);
//
////        }
//        Thread.sleep(100);
//        task.cancel();
        Thread.sleep(1000000);
        countDownLatch.countDown();
        countDownLatch.await();


//        final Scanner scanner = new Scanner(System.in);
//
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        final Future<?> submit = ThreadUtils.getSinglePool().submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("haha0");
//                scanner.nextLine();
//                System.out.println("haha");
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println(1);
//                }
//                for (int i = 0; i < 1000000; i++) {
//                    if (Thread.currentThread().isInterrupted()) {
//                        break;
//                    }
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    System.out.println(i);
//                }
//            }
//        });
//        Thread.sleep(500);
//        scanner.close();
//        Thread.sleep(500);
//        submit.cancel(true);
//        countDownLatch.await();

//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        final Scanner scanner = new Scanner(System.in);
//        ExecutorService singlePool = ThreadUtils.getSinglePool();
//        final Thread[] thread = new Thread[1];
//        singlePool.execute(new Runnable() {
//            @Override
//            public void run() {
//                thread[0] = Thread.currentThread();
//                for (int i = 0; i < 1000; i++) {
//                    if (Thread.currentThread().isInterrupted()) {
//                        break;
//                    }
//                    System.out.println(i);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println("scanner start");
//                scanner.nextLine();
//                System.out.println("scanner end");
//
//            }
//        });
//        Thread.sleep(200);
//        thread[0].interrupt();
//        System.out.println("haha");
//        singlePool.shutdownNow();


//        countDownLatch.await();

//
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        final Scanner s = new Scanner(System.in);
//        final ThreadUtils.SimpleTask<Void> task = new ThreadUtils.SimpleTask<Void>() {
//            @Nullable
//            @Override
//            public Void doInBackground() throws Throwable {
//                for (int i = 0; i < 1000; i++) {
////                    if (isCanceled()) break;
//                    System.out.println(String.valueOf(i));
//                    boolean b = s.hasNext();
//                    System.out.println(b);
//                }
//                return null;
//            }
//
//            @Override
//            public void onSuccess(@Nullable Void result) {
//                countDownLatch.countDown();
//            }
//
//            @Override
//            public void onCancel() {
//                System.out.println("---> cancel");
//                Thread.interrupted();
//                super.onCancel();
//            }
//        };
//
//        ThreadUtils.SimpleTask<Void> task1 = new ThreadUtils.SimpleTask<Void>() {
//            @Nullable
//            @Override
//            public Void doInBackground() throws Throwable {
//                for (int i = 1000; i < 2000; i++) {
//                    System.out.println(String.valueOf(i));
//                    Thread.sleep(10);
//                }
//                return null;
//            }
//
//            @Override
//            public void onSuccess(@Nullable Void result) {
//                countDownLatch.countDown();
//            }
//
//            @Override
//            public void onCancel() {
//                System.out.println("---> cancel");
//                super.onCancel();
//            }
//        };
//
//        ThreadUtils.executeBySingle(task);
//        ThreadUtils.executeBySingle(task1);
//        ThreadUtils.cancel(task);
//        countDownLatch.await();

    }

    static class Person implements Comparable<Person> {

        String name;
        int    age;
        int    time;

        public Person(String name) {
            this.name = name;
        }

        public Person(String name, int age, int time) {
            this.name = name;
            this.age = age;
            this.time = time;
        }

        @Override
        public String toString() {
            return name + age + time;
        }

        @Override
        public int compareTo(@NonNull Person o) {
            int res = o.age - age;
            if (res != 0) {
                return res;
            }
            return time - o.time;
        }
    }
}
