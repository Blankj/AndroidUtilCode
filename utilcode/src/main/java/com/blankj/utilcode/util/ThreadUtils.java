package com.blankj.utilcode.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/05/08
 *     desc  : utils about thread
 * </pre>
 */
public final class ThreadUtils {

    private static final Map<Integer, Map<Integer, ExecutorService>> TYPE_PRIORITY_POOLS
            = new ConcurrentHashMap<>();
    private static final Map<Task, ScheduledExecutorService>         TASK_SCHEDULED
            = new ConcurrentHashMap<>();

    private static final byte TYPE_SINGLE = -1;
    private static final byte TYPE_CACHED = -2;
    private static final byte TYPE_CPU    = -4;

    private static final int CPU_COUNT             = Runtime.getRuntime().availableProcessors();
    private static final int CPU_CORE_POOL_SIZE    = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int CPU_MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * Return whether the thread is the main thread.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Return
     * @param size
     * @return
     */
    public static ExecutorService getFixedPool(@IntRange(from = 1) final int size) {
        return getPoolByTypeAndPriority(size);
    }

    public static ExecutorService getFixedPool(@IntRange(from = 1) final int size,
                                               @IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(size, priority);
    }

    public static ExecutorService getSinglePool() {
        return getPoolByTypeAndPriority(TYPE_SINGLE);
    }

    public static ExecutorService getSinglePool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_SINGLE, priority);
    }

    public static ExecutorService getIoPool() {
        return getPoolByTypeAndPriority(TYPE_CACHED);
    }

    public static ExecutorService getIoPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_CACHED, priority);
    }

    public static ExecutorService getCpuPool() {
        return getPoolByTypeAndPriority(TYPE_CPU);
    }

    public static ExecutorService getCpuPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_CPU, priority);
    }

    public static <T> void executeByFixed(@IntRange(from = 1) final int size, final Task<T> task) {
        execute(getPoolByTypeAndPriority(size), task);
    }

    public static <T> void executeByFixed(@IntRange(from = 1) final int size,
                                          final Task<T> task,
                                          @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(size, priority), task);
    }

    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(size), task, delay, unit);
    }

    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(size, priority), task, delay, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(size), task, 0, delay, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(size, priority), task, 0, delay, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   long initialDelay,
                                                   final long delay,
                                                   final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(size), task, initialDelay, delay, unit);
    }

    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   long initialDelay,
                                                   final long delay,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(size, priority), task, initialDelay, delay, unit);
    }

    public static <T> void executeBySingle(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_SINGLE), task);
    }

    public static <T> void executeBySingle(final Task<T> task,
                                           @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task);
    }

    public static <T> void executeBySingleWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_SINGLE), task, delay, unit);
    }

    public static <T> void executeBySingleWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, delay, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE), task, 0, delay, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, 0, delay, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE), task, initialDelay, delay, unit);
    }

    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, initialDelay, delay, unit
        );
    }

    public static <T> void executeByIo(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_CACHED), task);
    }

    public static <T> void executeByIo(final Task<T> task,
                                       @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_CACHED, priority), task);
    }

    public static <T> void executeByIoWithDelay(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CACHED), task, delay, unit);
    }

    public static <T> void executeByIoWithDelay(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CACHED, priority), task, delay, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED), task, 0, delay, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED, priority), task, 0, delay, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                long initialDelay,
                                                final long delay,
                                                final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED), task, initialDelay, delay, unit);
    }

    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                long initialDelay,
                                                final long delay,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_CACHED, priority), task, initialDelay, delay, unit
        );
    }

    public static <T> void executeByCpu(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_CPU), task);
    }

    public static <T> void executeByCpu(final Task<T> task,
                                        @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_CPU, priority), task);
    }

    public static <T> void executeByCpuWithDelay(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CPU), task, delay, unit);
    }

    public static <T> void executeByCpuWithDelay(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CPU, priority), task, delay, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU), task, 0, delay, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU, priority), task, 0, delay, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 long initialDelay,
                                                 final long delay,
                                                 final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU), task, initialDelay, delay, unit);
    }

    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 long initialDelay,
                                                 final long delay,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_CPU, priority), task, initialDelay, delay, unit
        );
    }

    public static <T> void executeByCustom(final ExecutorService pool, final Task<T> task) {
        execute(pool, task);
    }

    public static <T> void executeByCustomWithDelay(final ExecutorService pool,
                                                    final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(pool, task, delay, unit);
    }

    public static <T> void executeByCustomAtFixRate(final ExecutorService pool,
                                                    final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeAtFixedRate(pool, task, 0, delay, unit);
    }

    public static <T> void executeByCustomAtFixRate(final ExecutorService pool,
                                                    final Task<T> task,
                                                    long initialDelay,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeAtFixedRate(pool, task, initialDelay, delay, unit);
    }

    public static void cancel(final Task task) {
        task.cancel();
    }

    private static <T> void execute(final ExecutorService pool, final Task<T> task) {
        executeWithDelay(pool, task, 0, TimeUnit.MILLISECONDS);
    }

    private static <T> void executeWithDelay(final ExecutorService pool,
                                             final Task<T> task,
                                             final long delay,
                                             final TimeUnit unit) {
        if (delay <= 0) {
            getScheduledByTask(task).execute(new Runnable() {
                @Override
                public void run() {
                    pool.execute(task);
                }
            });
        } else {
            getScheduledByTask(task).schedule(new Runnable() {
                @Override
                public void run() {
                    pool.execute(task);
                }
            }, delay, unit);
        }
    }

    private static <T> void executeAtFixedRate(final ExecutorService pool,
                                               final Task<T> task,
                                               long initialDelay,
                                               final long delay,
                                               final TimeUnit unit) {
        task.isSchedule = true;
        getScheduledByTask(task).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pool.execute(task);
            }
        }, initialDelay, delay, unit);
    }

    private static ScheduledExecutorService getScheduledByTask(final Task task) {
        ScheduledExecutorService scheduled = TASK_SCHEDULED.get(task);
        if (scheduled == null) {
            UtilsThreadFactory factory = new UtilsThreadFactory("scheduled", Thread.MAX_PRIORITY);
            scheduled = Executors.newScheduledThreadPool(1, factory);
            TASK_SCHEDULED.put(task, scheduled);
        }
        return scheduled;
    }

    private static void removeScheduleByTask(final Task task) {
        ScheduledExecutorService scheduled = TASK_SCHEDULED.get(task);
        if (scheduled != null) {
            TASK_SCHEDULED.remove(task);
            shutdownAndAwaitTermination(scheduled);
        }
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type) {
        return getPoolByTypeAndPriority(type, Thread.NORM_PRIORITY);
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type, final int priority) {
        ExecutorService pool;
        Map<Integer, ExecutorService> priorityPools = TYPE_PRIORITY_POOLS.get(type);
        if (priorityPools == null) {
            priorityPools = new ConcurrentHashMap<>();
            pool = createPoolByTypeAndPriority(type, priority);
            priorityPools.put(priority, pool);
            TYPE_PRIORITY_POOLS.put(type, priorityPools);
        } else {
            pool = priorityPools.get(priority);
            if (pool == null) {
                pool = createPoolByTypeAndPriority(type, priority);
                priorityPools.put(priority, pool);
            }
        }
        return pool;
    }

    private static ExecutorService createPoolByTypeAndPriority(final int type, final int priority) {
        switch (type) {
            case TYPE_SINGLE:
                return Executors.newSingleThreadExecutor(
                        new UtilsThreadFactory("single", priority)
                );
            case TYPE_CACHED:
                return Executors.newCachedThreadPool(
                        new UtilsThreadFactory("cached", priority)
                );
            case TYPE_CPU:
                return new ThreadPoolExecutor(CPU_CORE_POOL_SIZE,
                        CPU_MAXIMUM_POOL_SIZE,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        new UtilsThreadFactory("cpu", priority)
                );
            default:
                return Executors.newFixedThreadPool(
                        type,
                        new UtilsThreadFactory("fixed(" + type + ")", priority)
                );
        }
    }

    private static void shutdownAndAwaitTermination(final ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public abstract static class SimpleTask<T> extends Task<T> {

        @Override
        public void onCancel() {
            Log.e("ThreadUtils", "onCancel: " + Thread.currentThread());
        }

        @Override
        public void onFail(Throwable t) {
            Log.e("ThreadUtils", "onFail: ", t);
        }

    }

    public abstract static class Task<T> implements Runnable {

        private boolean isSchedule;

        private volatile int state;
        private static final int NEW         = 0;
        private static final int COMPLETING  = 1;
        private static final int CANCELLED   = 2;
        private static final int EXCEPTIONAL = 3;

        public Task() {
            state = NEW;
        }

        public abstract T doInBackground() throws Throwable;

        public abstract void onSuccess(T result);

        public abstract void onCancel();

        public abstract void onFail(Throwable t);


        @Override
        public void run() {
            try {
                final T result = doInBackground();
                if (state != NEW) return;

                if (isSchedule) {
                    Deliver.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                        }
                    });
                } else {
                    state = COMPLETING;
                    Deliver.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                            removeScheduleByTask(Task.this);
                        }
                    });
                }
            } catch (final Throwable throwable) {
                if (state != NEW) return;

                state = EXCEPTIONAL;
                Deliver.post(new Runnable() {
                    @Override
                    public void run() {
                        onFail(throwable);
                        removeScheduleByTask(Task.this);
                    }
                });
            }
        }

        public void cancel() {
            if (state != NEW) return;

            state = CANCELLED;
            Deliver.post(new Runnable() {
                @Override
                public void run() {
                    onCancel();
                    removeScheduleByTask(Task.this);
                }
            });
        }
    }

    private static final class UtilsThreadFactory extends AtomicLong
            implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final String      namePrefix;
        private final int         priority;

        UtilsThreadFactory(String prefix, int priority) {
            SecurityManager s = System.getSecurityManager();
            group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = prefix + "-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
            this.priority = priority;
        }

        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(group, r, namePrefix + getAndIncrement(), 0) {
                @Override
                public void run() {
                    try {
                        super.run();
                    } catch (Throwable t) {
                        Log.e("ThreadUtils", "Request threw uncaught throwable", t);
                    }
                }
            };
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            t.setPriority(priority);
            return t;
        }
    }

    private static class Deliver {

        private static final Handler MAIN_HANDLER;

        static {
            Looper looper;
            try {
                looper = Looper.getMainLooper();
            } catch (Exception e) {
                looper = null;
            }
            if (looper != null) {
                MAIN_HANDLER = new Handler(looper);
            } else {
                MAIN_HANDLER = null;
            }
        }

        private static void post(final Runnable runnable) {
            if (MAIN_HANDLER != null) {
                MAIN_HANDLER.post(runnable);
            } else {
                runnable.run();
            }
        }
    }
}
