package com.lianglliu.rxjava.impl.scheduler;

import java.util.concurrent.Executors;

public class Schedulers {

    private static final Scheduler DEFAULT_THREAD;
    private static final Scheduler NEW_THREAD;

    static {
        DEFAULT_THREAD = new DefaultThreadScheduler();
        NEW_THREAD = new NewThreadScheduler(Executors.newCachedThreadPool());
    }

    public static Scheduler defaultThread() {
        return DEFAULT_THREAD;
    }

    public static Scheduler newThread() {
        return NEW_THREAD;
    }
}
