package com.lianglliu.rxjava.impl;

public interface ObservableOnSubscribe<T> {

    void subscribe(Emitter<T> emitter);
}
