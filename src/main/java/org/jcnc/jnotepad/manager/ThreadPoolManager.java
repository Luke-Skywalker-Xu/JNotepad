package org.jcnc.jnotepad.manager;

import org.jcnc.jnotepad.tool.LogUtil;
import org.slf4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池管理类
 *
 * <p>该类用于管理应用程序中的线程池，提供了异步操作的执行环境。</p>
 *
 * @author gewuyou
 */
public class ThreadPoolManager {
    private static final Logger logger = LogUtil.getLogger(ThreadPoolManager.class);
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 2;
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = 4;
    /**
     * 空闲线程回收时间间隔
     */
    private static final Long KEEP_ALIVE_TIME = 3L;
    /**
     * 空闲线程回收时间间隔单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    /**
     * 提交任务的队列<br>
     * 注：<br>
     * ArrayBlockingQueue：有界带缓冲阻塞队列<br>
     * SynchronousQueue：无缓冲阻塞队列<br>
     * LinkedBlockingQueue：无界带缓冲阻塞队列
     */
    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingQueue<>(4);
    /**
     * 当前运行线程数
     */
    private static final AtomicInteger THREAD_COUNT = new AtomicInteger(1);
    /**
     * 线程工厂
     */
    private static final ThreadFactory THREAD_FACTORY = r -> {
        Thread thread = new Thread(r);
        thread.setName("JNotepad-ThreadPool-Thread-" + THREAD_COUNT.getAndIncrement());
        thread.setDaemon(true);
        // 设置异常处理器
        thread.setUncaughtExceptionHandler((t, e) -> logger.error("线程执行异常!", e));
        return thread;
    };
    /**
     * 拒绝处理任务时的策略
     */
    private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.AbortPolicy();
    /**
     * 线程池
     */
    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, BLOCKING_QUEUE, THREAD_FACTORY, HANDLER);

    private ThreadPoolManager() {
    }

    /**
     * 当前运行线程数自减
     *
     * @apiNote 当你创建任务时，务必在最后执行一次该方法
     * @see ThreadPoolManager
     */
    public static void threadContSelfSubtracting() {
        THREAD_COUNT.decrementAndGet();
    }

    /**
     * 获取线程池实例。
     *
     * @return 线程池实例
     */
    public static ExecutorService getThreadPool() {
        return THREAD_POOL;
    }
}
