package com.lianglliu.rxjava.impl.scheduler;

import java.util.concurrent.ExecutorService;

public class NewThreadScheduler extends Scheduler {

    final ExecutorService executorService;

    public NewThreadScheduler(ExecutorService executorService) {
        this.executorService = executorService;
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
