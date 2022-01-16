package com.lianglliu.rxjava.impl;

/**
 * 事件发射器
 */
public interface Emitter<T> {

    void onNext(T t);

    void onCompleted();

    void onError(Throwable throwable);
}
