package com.lianglliu.rxjava.impl;

import com.lianglliu.rxjava.impl.scheduler.Scheduler;

/**
 * 被观察者核心抽象类，框架入口
 */
public abstract class Observable<T> implements ObservableSource<T> {

    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<T> observer);

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<T>(source);
    }

    public <U> ObservableMap<T, U> map(Function<T, U> function) {
        return new ObservableMap<T, U>(this, function);
    }

    public <U> ObservableFlatMap<T, U> flatMap(Function<T, ObservableSource<U>> function) {
        return new ObservableFlatMap<T, U>(this, function);
    }

    public ObservableSubscribeOn<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<>(this, scheduler);
    }

    public ObservableObserverOn<T> observerOn(Scheduler scheduler) {
        return new ObservableObserverOn<>(this, scheduler);
    }
}
