package com.nanshan.support.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiangbo on 2017/12/24.
 */

public class ThreadManager {
    private static ThreadManager instance;
    private ExecutorService executorPool;

    private ThreadManager() {
        executorPool = Executors.newSingleThreadExecutor();
    }

    public synchronized static ThreadManager getInstance() {
        return instance == null ? instance = new ThreadManager() : instance;
    }

    //执行线程操作
    public void execute(Runnable runnable) {
        executorPool.execute(runnable);
    }

}
