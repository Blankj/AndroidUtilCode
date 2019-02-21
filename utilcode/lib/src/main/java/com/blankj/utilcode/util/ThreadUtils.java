package com.blankj.utilcode.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
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

    private static final SparseArray<SparseArray<ExecutorService>> TYPE_PRIORITY_POOLS = new SparseArray<>();
    private static final Map<Task, ScheduledExecutorService>       TASK_SCHEDULED      = new HashMap<>();

    private static final byte TYPE_SINGLE = -1;
    private static final byte TYPE_CACHED = -2;
    private static final byte TYPE_IO     = -4;
    private static final byte TYPE_CPU    = -8;

    private static Executor sDeliver;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * Return whether the thread is the main thread.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Return a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue, using the provided
     * ThreadFactory to create new threads when needed.
     *
     * @param size The size of thread in the pool.
     * @return a fixed thread pool
     */
    public static ExecutorService getFixedPool(@IntRange(from = 1) final int size) {
        return getPoolByTypeAndPriority(size);
    }

    /**
     * Return a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue, using the provided
     * ThreadFactory to create new threads when needed.
     *
     * @param size     The size of thread in the pool.
     * @param priority The priority of thread in the poll.
     * @return a fixed thread pool
     */
    public static ExecutorService getFixedPool(@IntRange(from = 1) final int size,
                                               @IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(size, priority);
    }

    /**
     * Return a thread pool that uses a single worker thread operating
     * off an unbounded queue, and uses the provided ThreadFactory to
     * create a new thread when needed.
     *
     * @return a single thread pool
     */
    public static ExecutorService getSinglePool() {
        return getPoolByTypeAndPriority(TYPE_SINGLE);
    }

    /**
     * Return a thread pool that uses a single worker thread operating
     * off an unbounded queue, and uses the provided ThreadFactory to
     * create a new thread when needed.
     *
     * @param priority The priority of thread in the poll.
     * @return a single thread pool
     */
    public static ExecutorService getSinglePool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_SINGLE, priority);
    }

    /**
     * Return a thread pool that creates new threads as needed, but
     * will reuse previously constructed threads when they are
     * available.
     *
     * @return a cached thread pool
     */
    public static ExecutorService getCachedPool() {
        return getPoolByTypeAndPriority(TYPE_CACHED);
    }

    /**
     * Return a thread pool that creates new threads as needed, but
     * will reuse previously constructed threads when they are
     * available.
     *
     * @param priority The priority of thread in the poll.
     * @return a cached thread pool
     */
    public static ExecutorService getCachedPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_CACHED, priority);
    }

    /**
     * Return a thread pool that creates (2 * CPU_COUNT + 1) threads
     * operating off a queue which size is 128.
     *
     * @return a IO thread pool
     */
    public static ExecutorService getIoPool() {
        return getPoolByTypeAndPriority(TYPE_IO);
    }

    /**
     * Return a thread pool that creates (2 * CPU_COUNT + 1) threads
     * operating off a queue which size is 128.
     *
     * @param priority The priority of thread in the poll.
     * @return a IO thread pool
     */
    public static ExecutorService getIoPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_IO, priority);
    }

    /**
     * Return a thread pool that creates (CPU_COUNT + 1) threads
     * operating off a queue which size is 128 and the maximum
     * number of threads equals (2 * CPU_COUNT + 1).
     *
     * @return a cpu thread pool for
     */
    public static ExecutorService getCpuPool() {
        return getPoolByTypeAndPriority(TYPE_CPU);
    }

    /**
     * Return a thread pool that creates (CPU_COUNT + 1) threads
     * operating off a queue which size is 128 and the maximum
     * number of threads equals (2 * CPU_COUNT + 1).
     *
     * @param priority The priority of thread in the poll.
     * @return a cpu thread pool for
     */
    public static ExecutorService getCpuPool(@IntRange(from = 1, to = 10) final int priority) {
        return getPoolByTypeAndPriority(TYPE_CPU, priority);
    }

    /**
     * Executes the given task in a fixed thread pool.
     *
     * @param size The size of thread in the fixed thread pool.
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
     */
    public static <T> void executeByFixed(@IntRange(from = 1) final int size, final Task<T> task) {
        execute(getPoolByTypeAndPriority(size), task);
    }

    /**
     * Executes the given task in a fixed thread pool.
     *
     * @param size     The size of thread in the fixed thread pool.
     * @param task     The task to execute.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByFixed(@IntRange(from = 1) final int size,
                                          final Task<T> task,
                                          @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(size, priority), task);
    }

    /**
     * Executes the given task in a fixed thread pool after the given delay.
     *
     * @param size  The size of thread in the fixed thread pool.
     * @param task  The task to execute.
     * @param delay The time from now to delay execution.
     * @param unit  The time unit of the delay parameter.
     * @param <T>   The type of the task's result.
     */
    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(size), task, delay, unit);
    }

    /**
     * Executes the given task in a fixed thread pool after the given delay.
     *
     * @param size     The size of thread in the fixed thread pool.
     * @param task     The task to execute.
     * @param delay    The time from now to delay execution.
     * @param unit     The time unit of the delay parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByFixedWithDelay(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long delay,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(size, priority), task, delay, unit);
    }

    /**
     * Executes the given task in a fixed thread pool at fix rate.
     *
     * @param size   The size of thread in the fixed thread pool.
     * @param task   The task to execute.
     * @param period The period between successive executions.
     * @param unit   The time unit of the period parameter.
     * @param <T>    The type of the task's result.
     */
    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long period,
                                                   final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(size), task, 0, period, unit);
    }

    /**
     * Executes the given task in a fixed thread pool at fix rate.
     *
     * @param size     The size of thread in the fixed thread pool.
     * @param task     The task to execute.
     * @param period   The period between successive executions.
     * @param unit     The time unit of the period parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   final long period,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(size, priority), task, 0, period, unit);
    }

    /**
     * Executes the given task in a fixed thread pool at fix rate.
     *
     * @param size         The size of thread in the fixed thread pool.
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   long initialDelay,
                                                   final long period,
                                                   final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(size), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in a fixed thread pool at fix rate.
     *
     * @param size         The size of thread in the fixed thread pool.
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param priority     The priority of thread in the poll.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByFixedAtFixRate(@IntRange(from = 1) final int size,
                                                   final Task<T> task,
                                                   long initialDelay,
                                                   final long period,
                                                   final TimeUnit unit,
                                                   @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(size, priority), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in a single thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
     */
    public static <T> void executeBySingle(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_SINGLE), task);
    }

    /**
     * Executes the given task in a single thread pool.
     *
     * @param task     The task to execute.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeBySingle(final Task<T> task,
                                           @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task);
    }

    /**
     * Executes the given task in a single thread pool after the given delay.
     *
     * @param task  The task to execute.
     * @param delay The time from now to delay execution.
     * @param unit  The time unit of the delay parameter.
     * @param <T>   The type of the task's result.
     */
    public static <T> void executeBySingleWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_SINGLE), task, delay, unit);
    }

    /**
     * Executes the given task in a single thread pool after the given delay.
     *
     * @param task     The task to execute.
     * @param delay    The time from now to delay execution.
     * @param unit     The time unit of the delay parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeBySingleWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, delay, unit);
    }

    /**
     * Executes the given task in a single thread pool at fix rate.
     *
     * @param task   The task to execute.
     * @param period The period between successive executions.
     * @param unit   The time unit of the period parameter.
     * @param <T>    The type of the task's result.
     */
    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE), task, 0, period, unit);
    }

    /**
     * Executes the given task in a single thread pool at fix rate.
     *
     * @param task     The task to execute.
     * @param period   The period between successive executions.
     * @param unit     The time unit of the period parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, 0, period, unit);
    }

    /**
     * Executes the given task in a single thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_SINGLE), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in a single thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param priority     The priority of thread in the poll.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeBySingleAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_SINGLE, priority), task, initialDelay, period, unit
        );
    }

    /**
     * Executes the given task in a cached thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
     */
    public static <T> void executeByCached(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_CACHED), task);
    }

    /**
     * Executes the given task in a cached thread pool.
     *
     * @param task     The task to execute.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByCached(final Task<T> task,
                                           @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_CACHED, priority), task);
    }

    /**
     * Executes the given task in a cached thread pool after the given delay.
     *
     * @param task  The task to execute.
     * @param delay The time from now to delay execution.
     * @param unit  The time unit of the delay parameter.
     * @param <T>   The type of the task's result.
     */
    public static <T> void executeByCachedWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CACHED), task, delay, unit);
    }

    /**
     * Executes the given task in a cached thread pool after the given delay.
     *
     * @param task     The task to execute.
     * @param delay    The time from now to delay execution.
     * @param unit     The time unit of the delay parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByCachedWithDelay(final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CACHED, priority), task, delay, unit);
    }

    /**
     * Executes the given task in a cached thread pool at fix rate.
     *
     * @param task   The task to execute.
     * @param period The period between successive executions.
     * @param unit   The time unit of the period parameter.
     * @param <T>    The type of the task's result.
     */
    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED), task, 0, period, unit);
    }

    /**
     * Executes the given task in a cached thread pool at fix rate.
     *
     * @param task     The task to execute.
     * @param period   The period between successive executions.
     * @param unit     The time unit of the period parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED, priority), task, 0, period, unit);
    }

    /**
     * Executes the given task in a cached thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CACHED), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in a cached thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param priority     The priority of thread in the poll.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByCachedAtFixRate(final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit,
                                                    @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_CACHED, priority), task, initialDelay, period, unit
        );
    }

    /**
     * Executes the given task in an IO thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
     */
    public static <T> void executeByIo(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_IO), task);
    }

    /**
     * Executes the given task in an IO thread pool.
     *
     * @param task     The task to execute.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByIo(final Task<T> task,
                                       @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_IO, priority), task);
    }

    /**
     * Executes the given task in an IO thread pool after the given delay.
     *
     * @param task  The task to execute.
     * @param delay The time from now to delay execution.
     * @param unit  The time unit of the delay parameter.
     * @param <T>   The type of the task's result.
     */
    public static <T> void executeByIoWithDelay(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_IO), task, delay, unit);
    }

    /**
     * Executes the given task in an IO thread pool after the given delay.
     *
     * @param task     The task to execute.
     * @param delay    The time from now to delay execution.
     * @param unit     The time unit of the delay parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByIoWithDelay(final Task<T> task,
                                                final long delay,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_IO, priority), task, delay, unit);
    }

    /**
     * Executes the given task in an IO thread pool at fix rate.
     *
     * @param task   The task to execute.
     * @param period The period between successive executions.
     * @param unit   The time unit of the period parameter.
     * @param <T>    The type of the task's result.
     */
    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                final long period,
                                                final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_IO), task, 0, period, unit);
    }

    /**
     * Executes the given task in an IO thread pool at fix rate.
     *
     * @param task     The task to execute.
     * @param period   The period between successive executions.
     * @param unit     The time unit of the period parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                final long period,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_IO, priority), task, 0, period, unit);
    }

    /**
     * Executes the given task in an IO thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                long initialDelay,
                                                final long period,
                                                final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_IO), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in an IO thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param priority     The priority of thread in the poll.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByIoAtFixRate(final Task<T> task,
                                                long initialDelay,
                                                final long period,
                                                final TimeUnit unit,
                                                @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_IO, priority), task, initialDelay, period, unit
        );
    }

    /**
     * Executes the given task in a cpu thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
     */
    public static <T> void executeByCpu(final Task<T> task) {
        execute(getPoolByTypeAndPriority(TYPE_CPU), task);
    }

    /**
     * Executes the given task in a cpu thread pool.
     *
     * @param task     The task to execute.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByCpu(final Task<T> task,
                                        @IntRange(from = 1, to = 10) final int priority) {
        execute(getPoolByTypeAndPriority(TYPE_CPU, priority), task);
    }

    /**
     * Executes the given task in a cpu thread pool after the given delay.
     *
     * @param task  The task to execute.
     * @param delay The time from now to delay execution.
     * @param unit  The time unit of the delay parameter.
     * @param <T>   The type of the task's result.
     */
    public static <T> void executeByCpuWithDelay(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CPU), task, delay, unit);
    }

    /**
     * Executes the given task in a cpu thread pool after the given delay.
     *
     * @param task     The task to execute.
     * @param delay    The time from now to delay execution.
     * @param unit     The time unit of the delay parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByCpuWithDelay(final Task<T> task,
                                                 final long delay,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeWithDelay(getPoolByTypeAndPriority(TYPE_CPU, priority), task, delay, unit);
    }

    /**
     * Executes the given task in a cpu thread pool at fix rate.
     *
     * @param task   The task to execute.
     * @param period The period between successive executions.
     * @param unit   The time unit of the period parameter.
     * @param <T>    The type of the task's result.
     */
    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 final long period,
                                                 final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU), task, 0, period, unit);
    }

    /**
     * Executes the given task in a cpu thread pool at fix rate.
     *
     * @param task     The task to execute.
     * @param period   The period between successive executions.
     * @param unit     The time unit of the period parameter.
     * @param priority The priority of thread in the poll.
     * @param <T>      The type of the task's result.
     */
    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 final long period,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU, priority), task, 0, period, unit);
    }

    /**
     * Executes the given task in a cpu thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 long initialDelay,
                                                 final long period,
                                                 final TimeUnit unit) {
        executeAtFixedRate(getPoolByTypeAndPriority(TYPE_CPU), task, initialDelay, period, unit);
    }

    /**
     * Executes the given task in a cpu thread pool at fix rate.
     *
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param priority     The priority of thread in the poll.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByCpuAtFixRate(final Task<T> task,
                                                 long initialDelay,
                                                 final long period,
                                                 final TimeUnit unit,
                                                 @IntRange(from = 1, to = 10) final int priority) {
        executeAtFixedRate(
                getPoolByTypeAndPriority(TYPE_CPU, priority), task, initialDelay, period, unit
        );
    }

    /**
     * Executes the given task in a custom thread pool.
     *
     * @param pool The custom thread pool.
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
     */
    public static <T> void executeByCustom(final ExecutorService pool, final Task<T> task) {
        execute(pool, task);
    }

    /**
     * Executes the given task in a custom thread pool after the given delay.
     *
     * @param pool  The custom thread pool.
     * @param task  The task to execute.
     * @param delay The time from now to delay execution.
     * @param unit  The time unit of the delay parameter.
     * @param <T>   The type of the task's result.
     */
    public static <T> void executeByCustomWithDelay(final ExecutorService pool,
                                                    final Task<T> task,
                                                    final long delay,
                                                    final TimeUnit unit) {
        executeWithDelay(pool, task, delay, unit);
    }

    /**
     * Executes the given task in a custom thread pool at fix rate.
     *
     * @param pool   The custom thread pool.
     * @param task   The task to execute.
     * @param period The period between successive executions.
     * @param unit   The time unit of the period parameter.
     * @param <T>    The type of the task's result.
     */
    public static <T> void executeByCustomAtFixRate(final ExecutorService pool,
                                                    final Task<T> task,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(pool, task, 0, period, unit);
    }

    /**
     * Executes the given task in a custom thread pool at fix rate.
     *
     * @param pool         The custom thread pool.
     * @param task         The task to execute.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @param <T>          The type of the task's result.
     */
    public static <T> void executeByCustomAtFixRate(final ExecutorService pool,
                                                    final Task<T> task,
                                                    long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        executeAtFixedRate(pool, task, initialDelay, period, unit);
    }

    /**
     * Cancel the given task.
     *
     * @param task The task to cancel.
     */
    public static void cancel(final Task task) {
        task.cancel();
    }

    /**
     * Set the deliver.
     *
     * @param deliver The deliver.
     */
    public static void setDeliver(final Executor deliver) {
        sDeliver = deliver;
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
                                               final long period,
                                               final TimeUnit unit) {
        task.isSchedule = true;
        getScheduledByTask(task).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pool.execute(task);
            }
        }, initialDelay, period, unit);
    }

    private synchronized static ScheduledExecutorService getScheduledByTask(final Task task) {
        ScheduledExecutorService scheduled = TASK_SCHEDULED.get(task);
        if (scheduled == null) {
            UtilsThreadFactory factory = new UtilsThreadFactory("scheduled", Thread.MAX_PRIORITY);
            scheduled = Executors.newScheduledThreadPool(1, factory);
            TASK_SCHEDULED.put(task, scheduled);
        }
        return scheduled;
    }

    private synchronized static void removeScheduleByTask(final Task task) {
        ScheduledExecutorService scheduled = TASK_SCHEDULED.get(task);
        if (scheduled != null) {
            TASK_SCHEDULED.remove(task);
            scheduled.shutdownNow();
        }
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type) {
        return getPoolByTypeAndPriority(type, Thread.NORM_PRIORITY);
    }

    private synchronized static ExecutorService getPoolByTypeAndPriority(final int type, final int priority) {
        ExecutorService pool;
        SparseArray<ExecutorService> priorityPools = TYPE_PRIORITY_POOLS.get(type);
        if (priorityPools == null) {
            priorityPools = new SparseArray<>();
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
            case TYPE_IO:
                return new ThreadPoolExecutor(2 * CPU_COUNT + 1,
                        2 * CPU_COUNT + 1,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(128),
                        new UtilsThreadFactory("io", priority)
                );
            case TYPE_CPU:
                return new ThreadPoolExecutor(CPU_COUNT + 1,
                        2 * CPU_COUNT + 1,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(128),
                        new UtilsThreadFactory("cpu", priority)
                );
            default:
                return Executors.newFixedThreadPool(
                        type,
                        new UtilsThreadFactory("fixed(" + type + ")", priority)
                );
        }
    }

    private static Executor getDeliver() {
        if (sDeliver == null) {
            sDeliver = new Executor() {
                private final Handler mHandler = new Handler(Looper.getMainLooper());

                @Override
                public void execute(@NonNull Runnable command) {
                    mHandler.post(command);
                }
            };
        }
        return sDeliver;
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

        private static final int NEW         = 0;
        private static final int COMPLETING  = 1;
        private static final int CANCELLED   = 2;
        private static final int EXCEPTIONAL = 3;

        private volatile int     state = NEW;
        private          boolean isSchedule;

        @Nullable
        public abstract T doInBackground() throws Throwable;

        public abstract void onSuccess(@Nullable T result);

        public abstract void onCancel();

        public abstract void onFail(Throwable t);


        @Override
        public void run() {
            try {
                final T result = doInBackground();

                if (state != NEW) return;

                if (isSchedule) {
                    getDeliver().execute(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                        }
                    });
                } else {
                    state = COMPLETING;
                    getDeliver().execute(new Runnable() {
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
                getDeliver().execute(new Runnable() {
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
            getDeliver().execute(new Runnable() {
                @Override
                public void run() {
                    onCancel();
                    removeScheduleByTask(Task.this);
                }
            });
        }

        public boolean isCanceled() {
            return state == CANCELLED;
        }
    }

    private static final class UtilsThreadFactory extends AtomicLong
            implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER      = new AtomicInteger(1);
        private static final long          serialVersionUID = -9209200509960368598L;
        private final        String        namePrefix;
        private final        int           priority;

        UtilsThreadFactory(String prefix, int priority) {
            namePrefix = prefix + "-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
            this.priority = priority;
        }

        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, namePrefix + getAndIncrement()) {
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
}
