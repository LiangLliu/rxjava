package com.lianglliu.rxjava.impl;

public interface Observer<T> {

    void onSubscribe();

    void onNext(T t);

    void onCompleted();

    void onError(Throwable throwable);
}
