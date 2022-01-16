package com.lianglliu.rxjava.impl.scheduler;

public abstract class Scheduler {
    public abstract Worker createWorker();

    public interface Worker {
        void schedule(Runnable runnable);
    }

}
