package com.blankj.utilcode.util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/14
 *     desc  :
 * </pre>
 */
public class BusUtilsVsEventBusTest extends BaseTest {

    @Subscribe
    public void eventBusFun(String param) {
    }

    @BusUtils.Bus(tag = "busUtilsFun")
    public void busUtilsFun(String param) {
    }

    @Before
    public void setUp() throws Exception {
        // 这一步是在 AOP 的时候注入的，这里通过反射来注入 busUtilsFun 事件，效果是一样的
        ReflectUtils getInstance = ReflectUtils.reflect(BusUtils.class).method("getInstance");
        getInstance.method("registerBus", "busUtilsFun", BusUtilsVsEventBusTest.class.getName(), "busUtilsFun", String.class.getName(), "param", false, "POSTING");
    }

    /**
     * 注册 10000 个订阅者，共执行 10 次取平均值
     */
    @Test
    public void compareRegister10000Times() {
        final List<BusUtilsVsEventBusTest> eventBusTests = new ArrayList<>();
        final List<BusUtilsVsEventBusTest> busUtilsTests = new ArrayList<>();

        compareWithEventBus("Register 10000 times.", 10, 10000, new CompareCallback() {
            @Override
            public void runEventBus() {
                BusUtilsVsEventBusTest test = new BusUtilsVsEventBusTest();
                EventBus.getDefault().register(test);
                eventBusTests.add(test);
            }

            @Override
            public void runBusUtils() {
                BusUtilsVsEventBusTest test = new BusUtilsVsEventBusTest();
                BusUtils.register(test);
                busUtilsTests.add(test);
            }

            @Override
            public void restState() {
                for (BusUtilsVsEventBusTest test : eventBusTests) {
                    EventBus.getDefault().unregister(test);
                }
                eventBusTests.clear();

                for (BusUtilsVsEventBusTest test : busUtilsTests) {
                    BusUtils.unregister(test);
                }
                busUtilsTests.clear();
            }
        });
    }

    /**
     * 向 1 个订阅者发送 * 1000000 次，共执行 10 次取平均值
     */
    @Test
    public void comparePostTo1Subscriber1000000Times() {
        comparePostTemplate("Post to 1 subscriber 1000000 times.", 1, 1000000);
    }

    /**
     * 向 100 个订阅者发送 * 100000 次，共执行 10 次取平均值
     */
    @Test
    public void comparePostTo100Subscribers100000Times() {
        comparePostTemplate("Post to 100 subscribers 100000 times.", 100, 100000);
    }

    private void comparePostTemplate(String name, int subscribeNum, int postTimes) {
        final List<BusUtilsVsEventBusTest> tests = new ArrayList<>();
        for (int i = 0; i < subscribeNum; i++) {
            BusUtilsVsEventBusTest test = new BusUtilsVsEventBusTest();
            EventBus.getDefault().register(test);
            BusUtils.register(test);
            tests.add(test);
        }

        compareWithEventBus(name, 10, postTimes, new CompareCallback() {
            @Override
            public void runEventBus() {
                EventBus.getDefault().post("EventBus");
            }

            @Override
            public void runBusUtils() {
                BusUtils.post("busUtilsFun", "BusUtils");
            }

            @Override
            public void restState() {

            }
        });
        for (BusUtilsVsEventBusTest test : tests) {
            EventBus.getDefault().unregister(test);
            BusUtils.unregister(test);
        }
    }

    /**
     * 注销 10000 个订阅者，共执行 10 次取平均值
     */
    @Test
    public void compareUnregister10000Times() {
        final List<BusUtilsVsEventBusTest> tests = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            BusUtilsVsEventBusTest test = new BusUtilsVsEventBusTest();
            EventBus.getDefault().register(test);
            BusUtils.register(test);
            tests.add(test);
        }

        compareWithEventBus("Unregister 10000 times.", 10, 1, new CompareCallback() {
            @Override
            public void runEventBus() {
                for (BusUtilsVsEventBusTest test : tests) {
                    EventBus.getDefault().unregister(test);
                }
            }

            @Override
            public void runBusUtils() {
                for (BusUtilsVsEventBusTest test : tests) {
                    BusUtils.unregister(test);
                }
            }

            @Override
            public void restState() {
                for (BusUtilsVsEventBusTest test : tests) {
                    EventBus.getDefault().register(test);
                    BusUtils.register(test);
                }
            }
        });

        for (BusUtilsVsEventBusTest test : tests) {
            EventBus.getDefault().unregister(test);
            BusUtils.unregister(test);
        }
    }

    /**
     * @param name       传入的测试函数名
     * @param sampleSize 样本的数量
     * @param times      每次执行的次数
     * @param callback   比较的回调函数
     */
    private void compareWithEventBus(String name, int sampleSize, int times, CompareCallback callback) {
        long[][] dur = new long[2][sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            long cur = System.currentTimeMillis();
            for (int j = 0; j < times; j++) {
                callback.runEventBus();
            }
            dur[0][i] = System.currentTimeMillis() - cur;

            cur = System.currentTimeMillis();
            for (int j = 0; j < times; j++) {
                callback.runBusUtils();
            }
            dur[1][i] = System.currentTimeMillis() - cur;
            callback.restState();
        }
        long eventBusAverageTime = 0;
        long busUtilsAverageTime = 0;
        for (int i = 0; i < sampleSize; i++) {
            eventBusAverageTime += dur[0][i];
            busUtilsAverageTime += dur[1][i];
        }
        System.out.println(
                name +
                "\nEventBusCostTime: " + eventBusAverageTime / sampleSize +
                "\nBusUtilsCostTime: " + busUtilsAverageTime / sampleSize
        );
    }

    public interface CompareCallback {
        void runEventBus();

        void runBusUtils();

        void restState();
    }
}
