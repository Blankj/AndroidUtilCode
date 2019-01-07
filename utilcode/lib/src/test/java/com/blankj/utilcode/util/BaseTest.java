package com.blankj.utilcode.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowLooper;

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
        ReflectUtils.reflect("com.blankj.utilcode.util.ThreadUtils$Deliver")
                .field("MAIN_HANDLER", null);
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        final Scanner scanner = new Scanner(System.in);
//        ExecutorService singlePool = ThreadUtils.getSinglePool();
//        singlePool.execute(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                    if (Thread.currentThread().isInterrupted()) {
//                        break;
//                    }
//                    System.out.println(i);
//                    try {
//                        Thread.sleep(10);
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
//        singlePool.shutdownNow();
//
//
//        countDownLatch.await();


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
}
