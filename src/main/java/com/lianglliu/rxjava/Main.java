package com.lianglliu.rxjava;

import com.lianglliu.rxjava.impl.*;
import com.lianglliu.rxjava.impl.scheduler.Schedulers;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================");
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(Emitter<Object> emitter) {
                System.out.println("subscribe....." + "Thread: " + Thread.currentThread());
                emitter.onNext("11111");
                emitter.onNext("22222");
                emitter.onNext("33333");
                emitter.onNext("44444");
                emitter.onCompleted();
            }
        }).observerOn(Schedulers.defaultThread()).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                System.out.println("map --- apply   Thread: " + Thread.currentThread());
                return o + "-ooo";
            }
        }).observerOn(Schedulers.newThread()).flatMap(new Function<Object, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(Object o) {
                return Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(Emitter<Object> emitter) {
                        System.out.println("flatMap --- subscribe   Thread: " + Thread.currentThread());
                        emitter.onNext(" 处理过的：" + o);
                    }
                });
            }
        }).observerOn(Schedulers.defaultThread()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe....." + "Thread: " + Thread.currentThread());
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext....." + "Thread: " + Thread.currentThread() + o);
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted....." + "Thread: " + Thread.currentThread());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError....." + "Thread: " + Thread.currentThread());
            }
        });

        System.out.println("=================================");
    }
}
