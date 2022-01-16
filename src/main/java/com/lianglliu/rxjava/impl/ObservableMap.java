package com.lianglliu.rxjava.impl;

public class ObservableMap<T, U> extends AbstractObservableWithUpStream<T, U> {

    Function<T, U> function;

    protected ObservableMap(ObservableSource<T> source, Function<T, U> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<U> observer) {
        source.subscribe(new MapObserver<>(observer, function));
    }

    static class MapObserver<T, U> implements Observer<T> {

        final Observer<U> downStream;
        final Function<T, U> function;

        public MapObserver(Observer<U> downStream, Function<T, U> function) {
            this.downStream = downStream;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
            downStream.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            U u = function.apply(t);
            downStream.onNext(u);
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
}
