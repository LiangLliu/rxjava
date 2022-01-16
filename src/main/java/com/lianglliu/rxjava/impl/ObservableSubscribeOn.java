package com.lianglliu.rxjava.impl;


import com.lianglliu.rxjava.impl.scheduler.Scheduler;

public class ObservableSubscribeOn<T> extends AbstractObservableWithUpStream<T, T> {

    final Scheduler scheduler;

    protected ObservableSubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        observer.onSubscribe();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(new RunnableTask(new SubscribeOnObserver<>(observer)));
    }

    public static class SubscribeOnObserver<T> implements Observer<T> {

        private final Observer<T> downStream;

        public SubscribeOnObserver(Observer<T> downStream) {
            this.downStream = downStream;
        }

        @Override
        public void onSubscribe() {

        }

        @Override
        public void onNext(T t) {
            downStream.onNext(t);
        }

        @Override
        public void onCompleted() {
            downStream.onCompleted();
        }

        @Override
        public void onError(Throwable throwable) {
            downStream.onError(throwable);
        }
    }

    final class RunnableTask implements Runnable {

        private final SubscribeOnObserver<T> observer;

        public RunnableTask(SubscribeOnObserver<T> observer) {
            this.observer = observer;
        }

        @Override
        public void run() {
            source.subscribe(observer);
        }
    }
}
