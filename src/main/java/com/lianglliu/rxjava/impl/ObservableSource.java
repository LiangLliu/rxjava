package com.lianglliu.rxjava.impl;

public interface ObservableSource<T> {
    void subscribe(Observer<T> observer);
}
