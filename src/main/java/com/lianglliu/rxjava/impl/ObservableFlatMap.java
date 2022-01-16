package com.lianglliu.rxjava.impl;

public class ObservableFlatMap<T, U> extends AbstractObservableWithUpStream<T, U> {

    Function<T, ObservableSource<U>> function;

    protected ObservableFlatMap(ObservableSource<T> source,
                                Function<T, ObservableSource<U>> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<U> observer) {
        source.subscribe(new MergeObserver<>(observer, function));
    }

    static class MergeObserver<T, U> implements Observer<T> {

        final Observer<U> downStream;
        Function<T, ObservableSource<U>> function;

        public MergeObserver(Observer<U> downStream, Function<T, ObservableSource<U>> function) {
            this.downStream = downStream;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
            downStream.onSubscribe();
        }

        @Override
        public void onNext(T t) {

            ObservableSource<U> observableSource = function.apply(t);

            observableSource.subscribe(new Observer<U>() {
                @Override
                public void onSubscribe() {

                }

                @Override
                public void onNext(U u) {
                    downStream.onNext(u);
                }

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable throwable) {

                }
            });
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
