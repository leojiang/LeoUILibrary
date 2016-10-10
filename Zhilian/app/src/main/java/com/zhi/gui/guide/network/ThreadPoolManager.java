package com.zhi.gui.guide.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
    public ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private static ThreadPoolManager instance = null;

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new ThreadPoolManager();
        }

        return instance;
    }

    public void runTask(final NetworkTask task) {
        if (!singleThreadExecutor.isShutdown() && !singleThreadExecutor.isTerminated()) {
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    task.execute();
                }
            });
        } else {
            singleThreadExecutor = Executors.newSingleThreadExecutor();
            if (!singleThreadExecutor.isShutdown() && !singleThreadExecutor.isTerminated()) {
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        task.execute();
                    }
                });
            }
        }
    }
}
