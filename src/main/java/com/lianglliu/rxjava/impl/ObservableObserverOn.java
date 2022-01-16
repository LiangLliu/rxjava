package com.lianglliu.rxjava.impl;

import com.lianglliu.rxjava.impl.scheduler.Scheduler;

import java.util.ArrayDeque;
import java.util.Deque;

public class ObservableObserverOn<T> extends AbstractObservableWithUpStream<T, T> {
    final Scheduler scheduler;

    protected ObservableObserverOn(ObservableSource<T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        Scheduler.Worker worker = scheduler.createWorker();
        source.subscribe(new ObserverSubscribeOn<T>(observer, worker));
    }

    static final class ObserverSubscribeOn<T> implements Observer<T>, Runnable {

        final Observer<T> downStream;
        final Scheduler.Worker worker;
        final Deque<T> queue;

        volatile boolean down;
        volatile Throwable throwable;
        volatile boolean over;

        public ObserverSubscribeOn(Observer<T> downStream, Scheduler.Worker worker) {
            this.downStream = downStream;
            this.worker = worker;
            this.queue = new ArrayDeque<>();
        }

        @Override
        public void onSubscribe() {
            downStream.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            queue.offer(t);
            schedule();
        }

        private void schedule() {
            worker.schedule(this);
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void run() {
            drainNormal();
        }

        /**
         * 从队列中排放事件并处理
         */
        private void drainNormal() {
            final Deque<T> q = queue;
            final Observer<T> stream = downStream;
            while (true) {
                boolean isDown = down;
                T data = q.poll();
                boolean dataIsEmpty = data == null;

                if (checkTerminated(isDown, dataIsEmpty, stream)) {
                    return;
                }

                if (dataIsEmpty) {
                    break;
                }

                stream.onNext(data);
            }
        }

        private boolean checkTerminated(boolean isDown, boolean dataIsEmpty, Observer<T> stream) {
            if (over) {
                queue.clear();
                return true;
            }

            if (isDown) {
                Throwable e = throwable;
                if (e != null) {
                    over = true;
                    stream.onError(e);
                    return true;
                } else if (dataIsEmpty) {
                    over = true;
                    stream.onCompleted();
                    return true;
                }
            }
            return false;
        }
    }
}
