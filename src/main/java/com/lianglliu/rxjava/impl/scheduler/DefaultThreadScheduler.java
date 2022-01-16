package com.lianglliu.rxjava.impl.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultThreadScheduler extends Scheduler {

    final ExecutorService executorService;

    public DefaultThreadScheduler() {
//        this.executorService = Executors.newSingleThreadExecutor();
        this.executorService = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable,"Default"));
    }

    @Override
    public Worker createWorker() {
        return new NewThreadWorker(executorService);
    }

    static final class NewThreadWorker implements Worker {

        final ExecutorService executorService;

        public NewThreadWorker(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public void schedule(Runnable runnable) {
            executorService.execute(runnable);
        }
    }
}
