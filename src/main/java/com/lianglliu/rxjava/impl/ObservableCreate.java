package com.lianglliu.rxjava.impl;

public class ObservableCreate<T> extends Observable<T> {

    private ObservableOnSubscribe<T> observableOnSubscribe;

    public ObservableCreate(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.observableOnSubscribe = observableOnSubscribe;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        observer.onSubscribe();
        CreateEmitter<T> emitter = new CreateEmitter<>(observer);
        observableOnSubscribe.subscribe(emitter);
    }

    public static class CreateEmitter<T> implements Emitter<T> {

        Observer<T> observer;
        boolean done;

        public CreateEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            if (done) return;
            observer.onNext(t);
        }

        @Override
        public void onCompleted() {
            if (done) return;
            observer.onCompleted();
            done = true;
        }

        @Override
        public void onError(Throwable throwable) {
            if (done) return;
            observer.onError(throwable);
            done = true;
        }
    }
}
