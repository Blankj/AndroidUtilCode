package com.blankj.utilcode.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

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
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {

        final Scanner scanner = new Scanner(System.in);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Future<?> submit = ThreadUtils.getSinglePool().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("haha0");
                scanner.nextLine();
                System.out.println("haha");
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(1);
                }
                for (int i = 0; i < 1000000; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(i);
                }
            }
        });
        Thread.sleep(500);
        scanner.close();
        Thread.sleep(500);
        submit.cancel(true);
        countDownLatch.await();

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

    static class Person {

        String name;
        int    gender;
        String address;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof Person)) return false;
            Person p = (Person) obj;
            return equals(name, p.name) && p.gender == gender && equals(address, p.address);
        }

        private static boolean equals(final Object o1, final Object o2) {
            return o1 == o2 || (o1 != null && o1.equals(o2));
        }
    }
}
